import javax.swing.table.AbstractTableModel;

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
  private KeyboardDevice kbDevice;
  private MonitorDevice monitorDevice;
  private TimerDevice timerDevice;
  public static final int BEGIN_DEVICE_REGISTERS = 65024;
  public static final int KBSR = 65024;
  public static final int KBDR = 65026;
  public static final int DSR = 65028;
  public static final int DDR = 65030;
  public static final int TMR = 65032;
  public static final int TMI = 65034;
  public static final int DISABLE_TIMER = 0;
  public static final int MANUAL_TIMER_MODE = 1;
  public static final int MPR = 65042;
  public static final int MCR = 65534;
  public static final int BREAKPOINT_COLUMN = 0;
  public static final int ADDRESS_COLUMN = 1;
  public static final int VALUE_COLUMN = 2;
  public static final int INSN_COLUMN = 3;
  private final Machine machine;

  public Memory( final Machine machine )
  {
    this.memArr = new Word[65536];
    this.colNames = new String[]{ "BP", "Address", "Value", "Instruction" };
    this.nextBreakPoints = new boolean[65536];
    this.breakPoints = new boolean[65536];
    this.kbDevice = new KeyboardDevice();
    this.monitorDevice = new MonitorDevice();
    this.timerDevice = new TimerDevice();
    this.machine = machine;
    for( int i = 0; i < 65536; ++i )
    {
      this.memArr[i] = new Word();
      this.breakPoints[i] = false;
    }
    this.timerDevice.setTimer();
  }

  public KeyboardDevice getKeyBoardDevice()
  {
    return this.kbDevice;
  }

  public MonitorDevice getMonitorDevice()
  {
    return this.monitorDevice;
  }

  public void reset()
  {
    for( int i = 0; i < 65536; ++i )
    {
      this.memArr[i].reset();
    }
    this.kbDevice.reset();
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

  public boolean isCellEditable( final int n, final int n2 )
  {
    return (n2 == 2 || n2 == 0) && n < 65024;
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

  public Object getValueAt( final int n, final int n2 )
  {
    Object o = null;
    switch( n2 )
    {
      case 0:
      {
        o = new Boolean( this.isBreakPointSet( n ) );
        break;
      }
      case 1:
      {
        o = Word.toHex( n );
        final String lookupSym = this.machine.lookupSym( n );
        if( lookupSym != null )
        {
          o = o + " " + lookupSym;
          break;
        }
        break;
      }
      case 2:
      {
        if( n < 65024 )
        {
          o = this.memArr[n].toHex();
          break;
        }
        o = "???";
        break;
      }
      case 3:
      {
        if( n < 65024 )
        {
          o = ISA.disassemble( this.memArr[n], n, this.machine );
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

  public Word read( final int n )
  {
    Word word = null;
    switch( n )
    {
      case 65024:
      {
        word = this.kbDevice.status();
        break;
      }
      case 65026:
      {
        word = this.kbDevice.read();
        break;
      }
      case 65028:
      {
        word = this.monitorDevice.status();
        break;
      }
      case 65032:
      {
        word = this.timerDevice.status();
        break;
      }
      case 65034:
      {
        word = new Word( (int) this.timerDevice.getInterval() );
        break;
      }
      case 65042:
      {
        word = new Word( this.machine.getRegisterFile().getMPR() );
        break;
      }
      case 65534:
      {
        word = new Word( this.machine.getRegisterFile().getMCR() );
        break;
      }
      default:
      {
        if( n < 0 || n >= 65536 )
        {
          return null;
        }
        word = this.memArr[n];
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

  public void checkAndWrite( final int n, final int n2 ) throws IllegalMemAccessException
  {
    this.machine.getRegisterFile().checkAddr( n );
    this.write( n, n2 );
  }

  public void write( final int n, final int value )
  {
    switch( n )
    {
      case 65030:
      {
        this.monitorDevice.write( (char) value );
        this.fireTableCellUpdated( n, 3 );
        break;
      }
      case 65034:
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
      case 65042:
      {
        this.machine.getRegisterFile().setMPR( value );
        break;
      }
      case 65534:
      {
        this.machine.getRegisterFile().setMCR( value );
        if( (value & 0x8000) == 0x0 )
        {
          this.machine.stopExecution( 1, true );
          break;
        }
        this.machine.updateStatusLabel();
        break;
      }
    }
    this.memArr[n].setValue( value );
    this.fireTableCellUpdated( n, 3 );
  }
}
