package edu.wcsu.WCSUSim.Machine;

import edu.wcsu.WCSUSim.Devices.DiskDevice;
import edu.wcsu.WCSUSim.Devices.KeyboardDevice;
import edu.wcsu.WCSUSim.Devices.MonitorDevice;
import edu.wcsu.WCSUSim.Devices.TimerDevice;
import edu.wcsu.WCSUSim.Display.Console;
import edu.wcsu.WCSUSim.Exceptions.IllegalMemAccessException;

import javax.swing.table.AbstractTableModel;
import java.io.RandomAccessFile;

// 
// Decompiled by Procyon v0.5.30
// 

public class Memory extends AbstractTableModel
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -7901043821814371784L;
  public static final int MEM_SIZE = 65536;
  private Word[] memArr;
  private String[] colNames;
  private boolean[] nextBreakPoints;
  private boolean[] breakPoints;

  // Built-in devices
  private KeyboardDevice kbDevice;
  private MonitorDevice  monitorDevice;
  private TimerDevice    timerDevice;
  private DiskDevice     diskDevice;

  // Device registers
  public static final int BEGIN_DEVICE_REGISTERS = 0xFE00;
  public static final int OS_KBSR = 0xFE00;
  public static final int OS_KBDR = 0xFE02;
  public static final int OS_DSR = 0xFE04;
  public static final int OS_DDR = 0xFE06;
  public static final int OS_TMR = 0xFE08;
  public static final int OS_TMI = 0xFE0A;

  // Control registers
  public static final int OS_MPR = 0xFE12;
  public static final int OS_MCR = 0xFFFE;

  // Disk registers
  public final static int OS_DDSR = 0xFE14;  // Disk Status  Register
  public final static int OS_DDCR = 0xFE16;  // Disk Control Register
  public final static int OS_DDBR = 0xFE18;  // Disk Block   Register
  public final static int OS_DDMR = 0xFE1A;  // Disk Memory  Register

  public static final int DISABLE_TIMER = 0;
  public static final int MANUAL_TIMER_MODE = 1;

  public static final int BREAKPOINT_COLUMN = 0;
  public static final int ADDRESS_COLUMN = 1;
  public static final int VALUE_COLUMN = 2;
  public static final int INSN_COLUMN = 3;

  // This is a reference to the actual machine
  private final Machine machine;

  // Writing this value to the DDR causes it to be cleared.
  private static final int ERASE_MONITOR = 0x8000;

  // This bit being set in the MCR indicates that interrupts are enabled.
  public static final int ENABLE_INTERRUPTS_BIT = 0x4000;

  // This interrupt vector corresponds to the keyboard
  private static final int KEYBOARD_INT_VECTOR = 0x0007;

  public Memory( final Machine machine )
  {
    this.memArr = new Word[65536];
    this.colNames = new String[]{ "BP", "Address", "Value", "Instruction" };
    this.nextBreakPoints = new boolean[65536];
    this.breakPoints = new boolean[65536];

    // Create the device drivers
    this.kbDevice      = new KeyboardDevice( this );
    this.monitorDevice = new MonitorDevice();
    this.timerDevice   = new TimerDevice();
    this.diskDevice    = new DiskDevice( this );

    this.machine = machine;
    for( int i = 0; i < 65536; ++i )
    {
      this.memArr[i] = new Word();
      this.breakPoints[i] = false;
    }
    this.timerDevice.setTimer();
  }

  public Machine getMachine()
  {
    return machine;
  }

  public KeyboardDevice getKeyBoardDevice()
  {
    return this.kbDevice;
  }

  public MonitorDevice getMonitorDevice()
  {
    return this.monitorDevice;
  }

  public void mountDiskFile( RandomAccessFile inputFile )
  {
    diskDevice.mountDiskFile( inputFile );
  }

  public void unmountDiskFile()
  {
    diskDevice.unmountDiskFile();
  }

  public void reset()
  {
    for( int i = 0; i < 65536; ++i )
    {
      this.memArr[i].reset();
    }
    //this.kbDevice.reset();  DMC no longer needed
    this.monitorDevice.reset();
    this.timerDevice.reset();
    this.clearAllBreakPoints();
    this.fireTableDataChanged();
  }

  public int getRowCount()
  {
    return this.memArr.length;
  }

  public int getColumnCount()
  {
    return this.colNames.length;
  }

  public String getColumnName( final int n )
  {
    return this.colNames[n];
  }

  public boolean isCellEditable( final int memoryLocation, final int n2 )
  {
    return (n2 == 2 || n2 == 0) && memoryLocation < BEGIN_DEVICE_REGISTERS;
  }

  public boolean isBreakPointSet( final int n )
  {
    return this.breakPoints[n];
  }

  public String setBreakPoint( final String s )
  {
    final int address = this.machine.getAddress( s );
    String s2;
    if( address != Integer.MAX_VALUE )
    {
      s2 = this.setBreakPoint( address );
      if( this.machine.existSym( s ) )
      {
        s2 = s2 + " ('" + s + "')";
      }
    }
    else
    {
      s2 = "Error: Invalid address or label ('" + s + "')";
    }
    return s2;
  }

  public String setBreakPoint( final int n )
  {
    if( n < 0 || n >= 65536 )
    {
      return "Error: Invalid address or label";
    }
    this.breakPoints[n] = true;
    this.fireTableCellUpdated( n, -1 );
    return "Breakpoint set at " + Word.toHex( n );
  }

  public String clearBreakPoint( final String s )
  {
    final int address = this.machine.getAddress( s );
    String s2;
    if( address != Integer.MAX_VALUE )
    {
      s2 = this.clearBreakPoint( address );
      if( this.machine.existSym( s ) )
      {
        s2 = s2 + " ('" + s + "')";
      }
    }
    else
    {
      s2 = "Error: Invalid address or label ('" + s + "')";
    }
    return s2;
  }

  public String clearBreakPoint( final int n )
  {
    if( n < 0 || n >= 65536 )
    {
      return "Error: Invalid address or label";
    }
    this.breakPoints[n] = false;
    this.fireTableCellUpdated( n, -1 );
    return "Breakpoint cleared at " + Word.toHex( n );
  }

  public void clearAllBreakPoints()
  {
    for( int i = 0; i < 65536; ++i )
    {
      this.breakPoints[i] = false;
      this.nextBreakPoints[i] = false;
    }
  }

  public void setNextBreakPoint( final int n )
  {
    assert 0 <= n && n < 65536;
    this.nextBreakPoints[n] = true;
  }

  public boolean isNextBreakPointSet( final int n )
  {
    assert 0 <= n && n < 65536;
    return this.nextBreakPoints[n];
  }

  public void clearNextBreakPoint( final int n )
  {
    assert 0 <= n && n < 65536;
    this.nextBreakPoints[n] = false;
  }

  public Object getValueAt( final int memoryLocation, final int n2 )
  {
    Object o = null;
    switch( n2 )
    {
      case 0:
      {
        o = new Boolean( this.isBreakPointSet( memoryLocation ) );
        break;
      }
      case 1:
      {
        o = Word.toHex( memoryLocation );
        final String lookupSym = this.machine.lookupSym( memoryLocation );
        if( lookupSym != null )
        {
          o = o + " " + lookupSym;
          break;
        }
        break;
      }
      case 2:
      {
        if( memoryLocation < BEGIN_DEVICE_REGISTERS )
        {
          o = this.memArr[memoryLocation].toHex();
          break;
        }
        o = "???";
        break;
      }
      case 3:
      {
        if( memoryLocation < BEGIN_DEVICE_REGISTERS )
        {
          o = ISA.disassemble( this.memArr[memoryLocation], memoryLocation, this.machine );
          break;
        }
        o = "Use 'list' to query";
        break;
      }
    }
    return o;
  }

  public Word getInst( final int n )
  {
    return this.memArr[n];
  }

  public Word checkAndRead( final int n ) throws IllegalMemAccessException
  {
    this.machine.getRegisterFile().checkAddr( n );
    return this.read( n );
  }

  public Word read( final int address )
  {
    Word word = null;
    switch( address )
    {
      /*
      case OS_KBSR:  // OS_KBSR
      {
        word = this.kbDevice.status();
        break;
      }
      case OS_KBDR:  // OS_KBDR
      {
        word = this.kbDevice.read();
        break;
      }
      */
      case OS_DSR: // OS_DSR
      {
        word = this.monitorDevice.status();
        break;
      }
      case OS_TMR:
      {
        word = this.timerDevice.status();
        break;
      }
      case OS_TMI:
      {
        word = new Word( (int) this.timerDevice.getInterval() );
        break;
      }
      case OS_MPR:  // Memory Protection Register
      {
        word = new Word( this.machine.getRegisterFile().getMPR() );
        break;
      }
      case OS_MCR:  // Machine Control Register
      {
        word = new Word( this.machine.getRegisterFile().getMCR() );
        break;
      }
      default:
      {
        if( address < 0 || address >= 65536 )
        {
          return null;
        }
        word = this.memArr[address];
        break;
      }
    }
    return word;
  }

  public void setValueAt( final Object o, final int breakPoint, final int n )
  {
    if( n == 2 )
    {
      this.write( breakPoint, Word.parseNum( (String) o ) );
      this.fireTableCellUpdated( breakPoint, n );
    }
    if( n == 0 )
    {
      if( o != null )
      {
        if( this.breakPoints[breakPoint] )
          Console.println( this.clearBreakPoint( breakPoint ) );
        else
          Console.println( this.setBreakPoint( breakPoint ) );
      }
      else
      {
        Console.println( this.clearBreakPoint( breakPoint ) );
      }
    }
  }

  public void checkAndWrite( final int address, final int value ) throws IllegalMemAccessException
  {
    this.machine.getRegisterFile().checkAddr( address );
    this.write( address, value );
  }

  public void write( final int address, final int value )
  {
    switch( address )
    {
      case OS_DDR:
      {
        if( value == ERASE_MONITOR )
        {
          monitorDevice.reset();
        }
        else
        {
          this.monitorDevice.write( (char) value );
        }
        this.fireTableCellUpdated( address, 3 );
        break;
      }

      case OS_KBSR:

        this.memArr[address].setValue( value );

        // Alert the keyboard device that its status register has just been changed
        this.kbDevice.statusRegisterUpdated();
        return;


      case OS_DDSR:
        this.memArr[ address ].setValue( value );

        // Alert the disk device that its status register has just been changed
        this.diskDevice.statusRegisterUpdated();
        return;

      case OS_TMI:
      {
        this.timerDevice.setTimer( value );
        if( value == 0 )
        {
          this.timerDevice.setEnabled( false );
          break;
        }
        this.timerDevice.setEnabled( true );
        if( value == 1 )
        {
          this.timerDevice.setTimer( this.kbDevice );
          break;
        }
        break;
      }
      case OS_MPR:
      {
        this.machine.getRegisterFile().setMPR( value );
        break;
      }
      case OS_MCR:
      {
        this.machine.getRegisterFile().setMCR( value );
        if( (value & 0x8000) == 0x0 )
        {
          this.machine.stopExecution( 1, true );
          break;
        }

        // Check if interrupts are now enabled
        if( (value & ENABLE_INTERRUPTS_BIT) != 0 )
        {
          // Propagate this to the keyboard, and potentially other devices
          int currentKSR = memArr[ OS_KBSR ].getValue();
          int newKSR = currentKSR | ENABLE_INTERRUPTS_BIT | KEYBOARD_INT_VECTOR;
          memArr[ OS_KBSR ] = new Word( newKSR );
        }
        this.machine.updateStatusLabel();
        break;
      }
    }
    this.memArr[address].setValue( value );
    this.fireTableCellUpdated( address, 3 );
  }
}