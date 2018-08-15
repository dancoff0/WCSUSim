package edu.wcsu.WCSUSim.Machine;

import edu.wcsu.WCSUSim.Devices.KeyboardDevice;
import edu.wcsu.WCSUSim.Display.Console;
import edu.wcsu.WCSUSim.Display.GUI;
import edu.wcsu.WCSUSim.Exceptions.ExceptionException;
import edu.wcsu.WCSUSim.Exceptions.IllegalInstructionException;
import edu.wcsu.WCSUSim.Exceptions.IllegalMemAccessException;
import edu.wcsu.WCSUSim.WCSUSim;

import javax.swing.SwingUtilities;
import java.util.ListIterator;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.io.PrintWriter;
import java.util.LinkedList;

// 
// Decompiled by Procyon v0.5.30
// 

public class Machine implements Runnable
{
  private Memory memory;
  private RegisterFile registers;
  private BranchPredictor bpred;
  private GUI gui;
  private LinkedList<ActionListener> NotifyOnStop;
  private PrintWriter traceWriter;
  private final Hashtable<String, Integer> symbolTable;
  private final Hashtable<Integer, String> inverseTable;
  private final Hashtable<Integer, Boolean> addrToInsnTable;
  public int CYCLE_COUNT;
  public int INSTRUCTION_COUNT;
  public int LOAD_STALL_COUNT;
  public int BRANCH_STALL_COUNT;
  public static final int NUM_CONTINUES = 400;
  boolean stopImmediately;
  private boolean continueMode;

  public Machine()
  {
    this.gui = null;
    this.traceWriter = null;
    this.symbolTable = new Hashtable<String, Integer>();
    this.inverseTable = new Hashtable<Integer, String>();
    this.addrToInsnTable = new Hashtable<Integer, Boolean>();
    this.CYCLE_COUNT = 0;
    this.INSTRUCTION_COUNT = 0;
    this.LOAD_STALL_COUNT = 0;
    this.BRANCH_STALL_COUNT = 0;
    this.stopImmediately = false;
    this.continueMode = false;
    if( WCSUSim.isP37X() )
    {
      new P37X().init();
    }
    else if( WCSUSim.isLC3() )
    {
      new LC3().init();
    }
    this.memory = new Memory( this );
    this.registers = new RegisterFile( this );
    this.bpred = new BranchPredictor( 8 );
    this.NotifyOnStop = new LinkedList<ActionListener>();
  }

  public void setGUI( final GUI gui )
  {
    this.gui = gui;
  }

  public GUI getGUI()
  {
    return this.gui;
  }

  public void setStoppedListener( final ActionListener actionListener )
  {
    this.NotifyOnStop.add( actionListener );
  }

  public void reset()
  {
    this.symbolTable.clear();
    this.inverseTable.clear();
    this.addrToInsnTable.clear();
    this.memory.reset();
    this.registers.reset();
    if( this.gui != null )
    {
      this.gui.reset();
    }
    if( this.isTraceEnabled() )
    {
      this.disableTrace();
    }
    this.CYCLE_COUNT = 0;
    this.INSTRUCTION_COUNT = 0;
    this.LOAD_STALL_COUNT = 0;
    this.BRANCH_STALL_COUNT = 0;
  }

  public void cleanup()
  {
    ErrorLog.logClose();
    if( this.isTraceEnabled() )
    {
      this.disableTrace();
    }
  }


  public void signalInterrupt( final int interruptVector )
  {
    // Sanity check
    if( ( registers.getMCR() & Memory.ENABLE_INTERRUPTS_BIT ) == 0 )
    {
      System.out.println( "Machine: signalInterrupt: caught interrupt signal, but interrupts are currently disabled" );
      return;
    }

    System.out.println( "Machine: signalInterrupt: preparing interrupt for vector " + interruptVector );
  }

  public Memory getMemory()
  {
    return this.memory;
  }

  public RegisterFile getRegisterFile()
  {
    return this.registers;
  }

  public BranchPredictor getBranchPredictor()
  {
    return this.bpred;
  }

  public void setTraceWriter( final PrintWriter traceWriter )
  {
    this.traceWriter = traceWriter;
  }

  public PrintWriter getTraceWriter()
  {
    return this.traceWriter;
  }

  public boolean isTraceEnabled()
  {
    return this.traceWriter != null;
  }

  public void disableTrace()
  {
    this.traceWriter.close();
    this.traceWriter = null;
  }

  public String loadSymbolTable( final File file )
  {
    String string;
    try
    {
      final BufferedReader bufferedReader = new BufferedReader( new FileReader( file ) );
      int n = 0;
      while( bufferedReader.ready() )
      {
        final String line = bufferedReader.readLine();
        if( ++n < 5 )
        {
          continue;
        }
        final String[] split = line.split( "\\s+" );
        if( split.length < 3 )
        {
          continue;
        }
        final Integer n2 = new Integer( Word.parseNum( "x" + split[2] ) );
        if( "$".equals( split[1] ) )
        {
          this.addrToInsnTable.put( n2, true );
        }
        else
        {
          this.symbolTable.put( split[1].toLowerCase(), n2 );
          this.inverseTable.put( n2, split[1] );
        }
      }
      string = "Loaded symbol file '" + file.getPath() + "'";
      bufferedReader.close();
    }
    catch( IOException ex )
    {
      return "Could not load symbol file '" + file.getPath() + "'";
    }
    return string;
  }

  public boolean isContinueMode()
  {
    return this.continueMode;
  }

  public void setContinueMode()
  {
    this.continueMode = true;
  }

  public void clearContinueMode()
  {
    this.continueMode = false;
  }

  public String loadObjectFile( final File file )
  {
    final byte[] array = new byte[2];
    final String path = file.getPath();
    if( !path.endsWith( ".obj" ) )
    {
      return "Error: object filename '" + path + "' does not end with .obj";
    }
    String string;
    try
    {
      // Open the .obj file
      final FileInputStream fileInputStream = new FileInputStream( file );

      // Read in the first two bytes and convert them into a word.
      int bytesRead = fileInputStream.read( array );

      // This should be the "ADDRESS_MARKER"
      Word testMarker = new Word( Word.convertByteArray( array[0], array[1] ) );
      if( !testMarker.equals( ISA.ADDRESS_MARKER ) )
      {
        throw new IOException( ".obj file did not start with the require address marker: " + testMarker );
      }

      // Now get the next two bytes. These are the base address
      bytesRead = fileInputStream.read( array );

      // Convert this into an int. This will be the starting address
      int baseAddress = Word.convertByteArray( array[0], array[1] );

      // Now loop over the instructions
      while( fileInputStream.read( array ) == 2 )
      {
        // Decide what kind of information this is.
        int newValue = Word.convertByteArray( array[0], array[1] );
        testMarker = new Word( newValue );
        if( testMarker.equals( ISA.ADDRESS_MARKER ) )
        {
          // Get the new address
          bytesRead = fileInputStream.read( array );

          // Convert this into an int. This will be the starting address
          baseAddress = Word.convertByteArray( array[0], array[1] );

          //continue;
        }
        else if( testMarker.equals( ISA.DATA_MARKER ) )
        {
          // Remove the current address from the symbol table
          //final Integer n = new Integer( baseAddress );
          if( this.symbolTable.contains( baseAddress ) )
          {
            this.symbolTable.remove( ((String) this.inverseTable.get( baseAddress )).toLowerCase() );
            this.inverseTable.remove( baseAddress );
          }

          // Get the new data
          bytesRead = fileInputStream.read( array );

          // Convert this into an int. This will be the starting address
          int newData = Word.convertByteArray( array[0], array[1] );
          this.memory.write( baseAddress, newData );
          ++baseAddress;
        }
        else
        {
          throw new IOException( ".obj file contains an unknown MARKER: " + testMarker );
        }
      }
      fileInputStream.close();
      string = "Loaded object file '" + path + "'";
    }
    catch( IOException ex )
    {
      return "Error: Could not load object file '" + path + "'" + ": " + ex.getMessage();
    }
    String substring = path;
    if( path.endsWith( ".obj" ) )
    {
      substring = path.substring( 0, path.length() - 4 );
    }
    return string + "\n" + this.loadSymbolTable( new File( substring + ".sym" ) );
  }

  public String setKeyboardInputStream( final File file )
  {
    String s;
    try
    {
      this.memory.getKeyBoardDevice().setInputStream( new FileInputStream( file ) );
      this.memory.getKeyBoardDevice().setInputMode( KeyboardDevice.SCRIPT_MODE );
      s = "Keyboard input file '" + file.getPath() + "' enabled";
      if( this.gui != null )
      {
        this.gui.setTextConsoleEnabled( false );
      }
    }
    catch( FileNotFoundException ex )
    {
      s = "Could not open keyboard input file '" + file.getPath() + "'";
      if( this.gui != null )
      {
        this.gui.setTextConsoleEnabled( true );
      }
    }
    return s;
  }

  public void executeStep() throws ExceptionException
  {
    this.registers.setClockMCR( true );
    this.stopImmediately = false;
    this.executePumpedContinues( 1 );
    this.updateStatusLabel();
    if( this.gui != null )
    {
      this.gui.scrollToPC( 0 );
    }
  }

  public void executeNext() throws ExceptionException
  {
    if( ISA.isCall( this.memory.read( this.registers.getPC() ) ) )
    {
      this.memory.setNextBreakPoint( (this.registers.getPC() + 1) % 65536 );
      this.executeMany();
    }
    else
    {
      this.executeStep();
    }
  }

  public synchronized String stopExecution( final boolean b )
  {
    return this.stopExecution( 0, b );
  }

  public synchronized String stopExecution( final int n, final boolean b )
  {
    this.stopImmediately = true;
    this.clearContinueMode();
    this.updateStatusLabel();
    if( this.gui != null )
    {
      this.gui.scrollToPC( n );
    }
    this.memory.fireTableDataChanged();
    if( b )
    {
      final ListIterator<ActionListener> listIterator = this.NotifyOnStop.listIterator( 0 );
      while( listIterator.hasNext() )
      {
        listIterator.next().actionPerformed( null );
      }
    }
    return "Stopped at " + Word.toHex( this.registers.getPC() );
  }

  public void executePumpedContinues() throws ExceptionException
  {
    this.executePumpedContinues( 400 );
  }

  public void executePumpedContinues( final int n ) throws ExceptionException
  {
    int n2 = n;
    this.registers.setClockMCR( true );
    if( this.gui != null )
    {
      this.gui.setStatusLabelRunning();
    }
    while( !this.stopImmediately && n2 > 0 )
    {
      try
      {
        final int pc = this.registers.getPC();
        this.registers.checkAddr( pc );
        final Word inst = this.memory.getInst( pc );
        final InstructionDef instructionDef = ISA.lookupTable[inst.getValue()];
        if( instructionDef == null )
        {
          throw new IllegalInstructionException( "Undefined instruction:  " + inst.toHex() );
        }
        final int execute = instructionDef.execute( inst, pc, this.registers, this.memory, this );
        this.registers.setPC( execute );
        ++this.CYCLE_COUNT;
        ++this.INSTRUCTION_COUNT;
        if( execute != this.bpred.getPredictedPC( pc ) )
        {
          this.CYCLE_COUNT += 2;
          this.BRANCH_STALL_COUNT += 2;
          this.bpred.update( pc, execute );
        }
        if( instructionDef.isLoad() )
        {
          final Word inst2 = this.memory.getInst( execute );
          final InstructionDef instructionDef2 = ISA.lookupTable[inst2.getValue()];
          if( instructionDef2 == null )
          {
            throw new IllegalInstructionException( "Undefined instruction:  " + inst2.toHex() );
          }
          if( !instructionDef2.isStore() )
          {
            final int destinationReg = instructionDef.getDestinationReg( inst );
            if( destinationReg >= 0 && (destinationReg == instructionDef2.getSourceReg1( inst2 ) || destinationReg == instructionDef2.getSourceReg2( inst2 )) )
            {
              ++this.CYCLE_COUNT;
              ++this.LOAD_STALL_COUNT;
            }
          }
        }
        if( this.isTraceEnabled() )
        {
          this.generateTrace( instructionDef, pc, inst );
        }
        if( this.memory.isBreakPointSet( this.registers.getPC() ) )
        {
          Console.println( "Hit breakpoint at " + Word.toHex( this.registers.getPC() ) );
          this.stopExecution( true );
        }
        if( this.memory.isNextBreakPointSet( this.registers.getPC() ) )
        {
          this.stopExecution( true );
          this.memory.clearNextBreakPoint( this.registers.getPC() );
        }
        --n2;
        continue;
      }
      catch( ExceptionException ex )
      {
        this.stopExecution( true );
        throw ex;
      }
    }
    if( this.isContinueMode() )
    {
      SwingUtilities.invokeLater( this );
    }
  }

  public synchronized void executeMany() throws ExceptionException
  {
    this.setContinueMode();
    this.stopImmediately = false;
    try
    {
      this.executePumpedContinues();
    }
    catch( ExceptionException ex )
    {
      this.stopExecution( true );
      throw ex;
    }
  }

  public void generateTrace( final InstructionDef instructionDef, final int n, final Word word ) throws IllegalMemAccessException
  {
    if( this.isTraceEnabled() )
    {
      final PrintWriter traceWriter = this.getTraceWriter();
      traceWriter.print( Word.toHex( n, false ) );
      traceWriter.print( " " );
      traceWriter.print( word.toHex( false ) );
      traceWriter.print( " " );
      if( this.registers.isDirty() )
      {
        traceWriter.print( Word.toHex( 1, false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( this.registers.getMostRecentlyWrittenValue(), false ) );
      }
      else
      {
        traceWriter.print( Word.toHex( 0, false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( 0, false ) );
      }
      traceWriter.print( " " );
      if( instructionDef.isStore() )
      {
        traceWriter.print( Word.toHex( 1, false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( instructionDef.getRefAddr( word, n, this.registers, this.memory ), false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( this.registers.getRegister( instructionDef.getDReg( word ) ), false ) );
      }
      else
      {
        traceWriter.print( Word.toHex( 0, false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( 0, false ) );
        traceWriter.print( " " );
        traceWriter.print( Word.toHex( 0, false ) );
      }
      traceWriter.println( " " );
      traceWriter.flush();
    }
  }

  public String lookupSym( final int n )
  {
    return this.inverseTable.get( new Integer( n ) );
  }

  public int lookupSym( final String s )
  {
    final Integer value = this.symbolTable.get( s.toLowerCase() );
    if( value != null )
    {
      return value;
    }
    return Integer.MAX_VALUE;
  }

  public boolean lookupAddrToInsn( final int n )
  {
    return this.addrToInsnTable.get( n ) != null;
  }

  public boolean existSym( final String s )
  {
    return this.symbolTable.get( s.toLowerCase() ) != null;
  }

  public int getAddress( final String s )
  {
    int n = Word.parseNum( s );
    if( n == Integer.MAX_VALUE )
    {
      n = this.lookupSym( s );
    }
    return n;
  }

  public void run()
  {
    try
    {
      if( !this.stopImmediately )
        this.executePumpedContinues();
    }
    catch( ExceptionException ex )
    {
      if( this.gui != null )
      {
        ex.showMessageDialog( null );
      }
      Console.println( ex.getMessage() );
    }
  }

  public synchronized void updateStatusLabel()
  {
    if( this.gui != null )
    {
      if( !this.registers.getClockMCR() )
      {
        this.gui.setStatusLabelHalted();
      }
      else if( this.isContinueMode() )
      {
        this.gui.setStatusLabelRunning();
      }
      else
      {
        this.gui.setStatusLabelSuspended();
      }
    }
  }
}
