package edu.wcsu.WCSUSim.Machine;

import edu.wcsu.WCSUSim.Display.Console;
import edu.wcsu.WCSUSim.Exceptions.IllegalMemAccessException;
import edu.wcsu.WCSUSim.WCSUSim;

import javax.swing.table.AbstractTableModel;
import java.util.Stack;

// This class implements the Register File for the LC3 computer
public class RegisterFile extends AbstractTableModel
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = 7358978233955494345L;

  // Constants
  public static final int NUM_REGISTERS = 8;

  // Instance variables
  private final String[]  colNames;
  private final Machine   machine;
  private final Word      PC;
  private final Word      MPR;
  private final Word      PSR;
  private final Word      MCR;
  private final Word[]    regArr;
  private static String[] indNames;
  private static int[]    indRow;
  private static int[]    indCol;
  private boolean dirty;
  private int             mostRecentlyWrittenValue;

  // This stack will hold the privilege levels of pending "TRAPS"
  private Stack<Boolean> privilegeStack = null;
  private static final int PRIVILEGE_BIT = 0x8000;

  RegisterFile( final Machine machine )
  {
    this.colNames = new String[]{ "Register", "Value", "Register", "Value" };
    this.regArr = new Word[8];
    this.machine = machine;
    if( !WCSUSim.isLC3() )
    {
      RegisterFile.indNames[11] = "";
    }
    for( int i = 0; i < 8; ++i )
    {
      this.regArr[i] = new Word();
    }
    this.PC  = new Word();
    this.MPR = new Word();
    this.MCR = new Word();
    this.PSR = new Word();
    this.reset();
  }

  public void reset()
  {
    for( int i = 0; i < 8; ++i )
    {
      this.regArr[i].setValue( 0 );
    }

    // Set the initial values
    this.PC.setValue( 512 );
    this.MPR.setValue( 0 );
    this.MCR.setValue( 32768 );
    this.PSR.setValue( 2 );
    this.setPrivMode( true );
    this.fireTableDataChanged();
  }

  public int getRowCount()
  {
    return 6;
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
    return n2 == 1 || n2 == 3;
  }

  public Object getValueAt( final int n, final int n2 )
  {
    if( n2 == 0 )
    {
      return RegisterFile.indNames[n];
    }
    if( n2 == 1 )
    {
      return this.regArr[n].toHex();
    }
    if( n2 == 2 )
    {
      return RegisterFile.indNames[n + 6];
    }
    if( n2 == 3 )
    {
      if( n < 2 )
      {
        return this.regArr[n + 6].toHex();
      }
      if( n == 2 )
      {
        return this.PC.toHex();
      }
      if( n == 3 )
      {
        return this.MPR.toHex();
      }
      if( n == 4 )
      {
        return this.PSR.toHex();
      }
      if( n == 5 )
      {
        if( WCSUSim.isLC3() )
        {
          return this.printCC();
        }
        return "";
      }
    }
    return null;
  }

  public void setValueAt( final Object o, final int n, final int n2 )
  {
    if( n2 == 1 )
    {
      this.regArr[n].setValue( Word.parseNum( (String) o ) );
    }
    else if( n2 == 3 )
    {
      if( n < 2 )
      {
        this.regArr[n + 6].setValue( Word.parseNum( (String) o ) );
      }
      else
      {
        if( n == 5 )
        {
          this.setNZP( (String) o );
          return;
        }
        if( o == null && n == 3 )
        {
          this.fireTableCellUpdated( n, n2 );
          return;
        }
        final int num = Word.parseNum( (String) o );
        if( n == 2 )
        {
          this.setPC( num );
          if( this.machine.getGUI() != null )
          {
            this.machine.getGUI().scrollToPC();
          }
        }
        else if( n == 3 )
        {
          this.setMPR( num );
        }
        else if( n == 4 )
        {
          this.setPSR( num );
        }
      }
    }
    this.fireTableCellUpdated( n, n2 );
  }

  boolean isDirty()
  {
    final boolean dirty = this.dirty;
    this.dirty = false;
    return dirty;
  }

  int getMostRecentlyWrittenValue()
  {
    return this.mostRecentlyWrittenValue;
  }

  public int getPC()
  {
    return this.PC.getValue();
  }

  public void setPC( final int value )
  {
    final int value2 = this.PC.getValue();
    this.PC.setValue( value );
    this.fireTableCellUpdated( RegisterFile.indRow[8], RegisterFile.indCol[8] );
    this.machine.getMemory().fireTableRowsUpdated( value2, value2 );
    this.machine.getMemory().fireTableRowsUpdated( value, value );
  }

  public void incPC( final int n )
  {
    this.setPC( this.PC.getValue() + n );
  }

  public String printRegister( final int n ) throws IndexOutOfBoundsException
  {
    if( n < 0 || n >= 8 )
    {
      throw new IndexOutOfBoundsException( "Register index must be from 0 to 7" );
    }
    return this.regArr[n].toHex();
  }

  public int getRegister( final int n ) throws IndexOutOfBoundsException
  {
    if( n < 0 || n >= 8 )
    {
      throw new IndexOutOfBoundsException( "Register index must be from 0 to 7" );
    }
    return this.regArr[n].getValue();
  }

  public void setRegister( final int index, final int value )
  {
    if( index < 0 || index >= 8 )
    {
      throw new IndexOutOfBoundsException( "Register index must be from 0 to 7" );
    }
    this.dirty = true;
    this.mostRecentlyWrittenValue = value;
    this.regArr[index].setValue( value );
    this.fireTableCellUpdated( RegisterFile.indRow[index], RegisterFile.indCol[index] );
  }

  public boolean getN()
  {
    return this.PSR.getBit( 2 ) == 1;
  }

  public boolean getZ()
  {
    return this.PSR.getBit( 1 ) == 1;
  }

  public boolean getP()
  {
    return this.PSR.getBit( 0 ) == 1;
  }

  // Get the carry register
  public boolean getC()
  {
    return PSR.getBit( 3 ) == 1;
  }

  boolean getPrivMode()
  {
    return this.PSR.getBit( 15 ) == 1;
  }

  void checkAddr( final int n ) throws IllegalMemAccessException
  {
    final boolean privMode = this.getPrivMode();
    if( n < 0 || n >= 65536 )
    {
      throw new IllegalMemAccessException( n );
    }
    if( privMode )
    {
      return;
    }
    if( (1 << (n >> 12) & this.getMPR()) == 0x0 )
    {
      throw new IllegalMemAccessException( n );
    }
  }

  public void checkAddr( final Word word ) throws IllegalMemAccessException
  {
    this.checkAddr( word.getValue() );
  }

  public String printCC()
  {
    if( !(this.getN() ^ this.getZ() ^ this.getP()) || (this.getN() && this.getZ() && this.getP()) )
    {
      return "invalid";
    }

    // Get the NZP values.
    String ccValue = "";
    if( this.getN() )
    {
      ccValue = "N";
    }
    else if( this.getZ() )
    {
      ccValue = "Z";
    }
    else if( this.getP() )
    {
      ccValue =  "P";
    }
    else
    {
      ccValue = "unset";
    }

    // Now add the 'C' value
    if( getC() )
    {
      ccValue += "C";
    }

    return ccValue;
  }

  public int getPSR()
  {
    return this.PSR.getValue();
  }

  void setNZP( int newValue )
  {
    // Get the last three bits of the Processor Status Register (PSR)
    final int currentNZP = this.PSR.getValue() & 0xFFFFFFF8;

    // Mask off the upper bits of the new value
    newValue &= 0xFFFF;

    int psr;
    // Check the sign bit
    if( (newValue & 0x8000) != 0x0 )
    {
      // If the sign bit is set, the value is negative
      psr = (currentNZP | 0x4);
    }
    else if( newValue == 0 )
    {
      // The value is zero
      psr = (currentNZP | 0x2);
    }
    else
    {
      // If not negative, or zero, it must be positive
      psr = (currentNZP | 0x1);
    }

    // Store away the new value.
    this.setPSR( psr );
  }

  private void setNZP( String newStringValue )
  {
    newStringValue = newStringValue.toLowerCase().trim();

    if( !newStringValue.equals( "n" ) && !newStringValue.equals( "z" ) && !newStringValue.equals( "p" ) )
    {
      Console.println( "Condition codes must be set as one of 'n', 'z' or 'p'" );
      return;
    }
    if( newStringValue.equals( "n" ) )
    {
      this.setN();
    }
    else if( newStringValue.equals( "z" ) )
    {
      this.setZ();
    }
    else
    {
      this.setP();
    }
  }

  public void setN()
  {
    this.setNZP( 32768 );
  }

  public void setZ()
  {
    this.setNZP( 0 );
  }

  public void setP()
  {
    this.setNZP( 1 );
  }

  // Set the value of the carry register
  public void setC( int newValue )
  {
    // Get the current value in the PSR.
    int currentPSR = PSR.getValue() & 0xFFFF;

    int newPSR;
    if( newValue == 0 )
    {
      // If the newValue is zero, clear bit 3 of the PSR
      newPSR = currentPSR & 0xFFF7;
    }
    else
    {
      newPSR = currentPSR | 0x0008;
    }

    // Store away the new value
    setPSR( newPSR );
  }

  void pushPrivMode()
  {
    if( privilegeStack == null ) privilegeStack = new Stack<Boolean>();

    // Get the current privilege level
    boolean currentPrivilege = (PSR.getValue() & PRIVILEGE_BIT) != 0;
    privilegeStack.push( currentPrivilege );
  }

  boolean popPrivMode()
  {
    // Sanity check
    if( privilegeStack == null || privilegeStack.isEmpty() )
    {
      System.out.println( "RegisterFile: popPrivMode: stack is empty!" );
      return false;
    }

    return privilegeStack.pop();
  }

  //Set the current privilege level
  void setPrivMode( final boolean b )
  {
    final int value = this.PSR.getValue();
    int psr;
    if( !b )
    {
      psr = (value & 0x7FFF);
    }
    else
    {
      psr = (value | 0x8000);
    }
    this.setPSR( psr );
  }

  void setClockMCR( final boolean b )
  {
    if( b )
    {
      this.setMCR( this.MCR.getValue() | 0x8000 );
    }
    else
    {
      this.setMCR( this.MCR.getValue() & 0x7FFF );
    }
  }

  boolean getClockMCR()
  {
    return (this.getMCR() & 0x8000) != 0x0;
  }

  void setMCR( final int value )
  {
    this.MCR.setValue( value );
  }

  int getMCR()
  {
    return this.MCR.getValue();
  }

  public void setPSR( final int value )
  {
    this.PSR.setValue( value );
    this.fireTableCellUpdated( RegisterFile.indRow[10], RegisterFile.indCol[10] );
    this.fireTableCellUpdated( RegisterFile.indRow[11], RegisterFile.indCol[11] );
  }

  public int getMPR()
  {
    return this.MPR.getValue();
  }

  void setMPR( final int value )
  {
    this.MPR.setValue( value );
    this.fireTableCellUpdated( RegisterFile.indRow[9], RegisterFile.indCol[9] );
  }

  public String toString()
  {
    String string = "[";
    for( int i = 0; i < 8; ++i )
    {
      string = string + "R" + i + ": " + this.regArr[i].toHex() + ((i != 7) ? "," : "");
    }
    return string + "]" + "\nPC = " + this.PC.toHex() + "\nMPR = " + this.MPR.toHex() + "\nPSR = " + this.PSR.toHex() + "\nCC = " + this.printCC();
  }

  public static boolean isLegalRegister( final int n )
  {
    return n >= 0 && n <= 8;
  }

  static
  {
    RegisterFile.indNames = new String[]{ "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "PC", "MPR", "PSR", "CC" };
    RegisterFile.indRow = new int[]{ 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5 };
    RegisterFile.indCol = new int[]{ 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3 };
  }
}
