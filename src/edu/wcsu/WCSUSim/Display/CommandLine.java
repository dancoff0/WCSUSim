package edu.wcsu.WCSUSim.Display;

import edu.wcsu.WCSUSim.Assembler.Assembler;
import edu.wcsu.WCSUSim.Exceptions.AsException;
import edu.wcsu.WCSUSim.Exceptions.ExceptionException;
import edu.wcsu.WCSUSim.Machine.*;
import edu.wcsu.WCSUSim.WCSUSim;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Hashtable;
import java.util.Stack;
import java.util.LinkedList;

// 
// Decompiled by Procyon v0.5.30
// 

public class CommandLine
{
  private Machine mac;
  private GUI GUI;
  private LinkedList<String> commandQueue;
  private Stack<String> prevHistoryStack;
  private Stack<String> nextHistoryStack;
  public static final String NEWLINE;
  public static final String PROMPT;
  private Hashtable<String, Command> commands;
  private TreeSet<Command> commandsSet;
  private int checksPassed;
  private int checksFailed;
  private int checksPassedCumulative;
  private int checksFailedCumulative;

  public CommandLine( final Machine mac )
  {
    this.checksPassed = 0;
    this.checksFailed = 0;
    this.checksPassedCumulative = 0;
    this.checksFailedCumulative = 0;
    this.mac = mac;
    this.commands = new Hashtable<String, Command>();
    this.setupCommands();
    (this.commandsSet = new TreeSet<Command>( new Comparator<Object>()
    {
      public int compare( final Object o, final Object o2 )
      {
        return ((Command) o).getUsage().split( "\\s+" )[0].compareTo( ((Command) o2).getUsage().split( "\\s+" )[0] );
      }

      public boolean equals( final Object o )
      {
        //return ((Command)o).getUsage().split("\\s+")[0].equals(((Command)this).getUsage().split("\\s+")[0]);
        return false;
      }
    } )).addAll( this.commands.values() );
    this.commandQueue = new LinkedList<String>();
    this.prevHistoryStack = new Stack<String>();
    this.nextHistoryStack = new Stack<String>();
  }

  public void scheduleCommand( final String s )
  {
    if( s.equalsIgnoreCase( "stop" ) )
    {
      this.commandQueue.addFirst( s );
    }
    else
    {
      this.commandQueue.add( s );
    }
  }

  public void scheduleScriptCommands( final ArrayList<String> list )
  {
    final ListIterator<String> listIterator = list.listIterator( list.size() );
    while( listIterator.hasPrevious() )
    {
      this.commandQueue.addFirst( listIterator.previous() );
    }
  }

  public boolean hasMoreCommands()
  {
    return this.commandQueue.size() != 0;
  }

  public String getNextCommand()
  {
    return this.commandQueue.removeFirst();
  }

  public boolean hasQueuedStop()
  {
    return this.commandQueue.getFirst().equalsIgnoreCase( "stop" );
  }

  public void addToHistory( final String s )
  {
    if( this.prevHistoryStack.empty() )
    {
      this.prevHistoryStack.push( s );
    }
    else if( !this.prevHistoryStack.peek().equals( s ) )
    {
      this.prevHistoryStack.push( s );
    }
  }

  public String getPrevHistory()
  {
    if( this.prevHistoryStack.empty() )
    {
      return null;
    }
    final String s = this.prevHistoryStack.pop();
    this.nextHistoryStack.push( s );
    return s;
  }

  public String getNextHistory()
  {
    if( this.nextHistoryStack.empty() )
    {
      return null;
    }
    final String s = this.nextHistoryStack.pop();
    this.prevHistoryStack.push( s );
    return s;
  }

  private void resetHistoryStack()
  {
    while( !this.nextHistoryStack.empty() )
    {
      this.prevHistoryStack.push( this.nextHistoryStack.pop() );
    }
  }

  private void setupCommands()
  {
    this.commands.put( "help", new Command()
    {
      public String getUsage()
      {
        return "h[elp] [command]";
      }

      public String getHelp()
      {
        return "Print out help for all available commands, or for just a specified command.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n > 2 )
        {
          return this.getUsage();
        }
        if( n == 1 )
        {
          String string = "";
          final Iterator<Command> iterator = CommandLine.this.commandsSet.iterator();
          while( iterator.hasNext() )
          {
            final String usage = iterator.next().getUsage();
            string = string + usage.split( "\\s+" )[0] + " usage: " + usage + "\n";
          }
          return string;
        }
        final Command command = CommandLine.this.commands.get( array[1].toLowerCase() );
        if( command == null )
        {
          return array[1] + ": command not found";
        }
        return "usage: " + command.getUsage() + "\n   " + command.getHelp();
      }
    } );
    this.commands.put( "h", this.commands.get( "help" ) );
    this.commands.put( "quit", new Command()
    {
      public String getUsage()
      {
        return "quit";
      }

      public String getHelp()
      {
        return "Quit the simulator.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 1 )
        {
          return this.getUsage();
        }
        return null;
      }
    } );
    this.commands.put( "next", new Command()
    {
      public String getUsage()
      {
        return "n[ext]";
      }

      public String getHelp()
      {
        return "Executes the next instruction.";
      }

      public String doCommand( final String[] array, final int n ) throws ExceptionException
      {
        if( n != 1 )
        {
          return this.getUsage();
        }
        CommandLine.this.mac.executeNext();
        return "";
      }
    } );
    this.commands.put( "n", this.commands.get( "next" ) );
    this.commands.put( "step", new Command()
    {
      public String getUsage()
      {
        return "s[tep]";
      }

      public String getHelp()
      {
        return "Steps into the next instruction.";
      }

      public String doCommand( final String[] array, final int n ) throws ExceptionException
      {
        CommandLine.this.mac.executeStep();
        return "";
      }
    } );
    this.commands.put( "s", this.commands.get( "step" ) );
    this.commands.put( "continue", new Command()
    {
      public String getUsage()
      {
        return "c[ontinue]";
      }

      public String getHelp()
      {
        return "Continues running instructions until next breakpoint is hit.";
      }

      public String doCommand( final String[] array, final int n ) throws ExceptionException
      {
        Console.println( "use the 'stop' command to interrupt execution" );
        CommandLine.this.mac.executeMany();
        return "";
      }
    } );
    this.commands.put( "c", this.commands.get( "continue" ) );
    this.commands.put( "stop", new Command()
    {
      public String getUsage()
      {
        return "stop";
      }

      public String getHelp()
      {
        return "Stops execution temporarily.";
      }

      public String doCommand( final String[] array, final int n )
      {
        return CommandLine.this.mac.stopExecution( true );
      }
    } );
    this.commands.put( "reset", new Command()
    {
      public String getUsage()
      {
        return "reset";
      }

      public String getHelp()
      {
        return "Resets the machine and simulator.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 1 )
        {
          return this.getUsage();
        }
        CommandLine.this.mac.stopExecution( false );
        CommandLine.this.mac.reset();
        CommandLine.this.checksPassed = 0;
        CommandLine.this.checksFailed = 0;
        return "System reset";
      }
    } );
    this.commands.put( "print", new Command()
    {
      public String getUsage()
      {
        return "p[rint]";
      }

      public String getHelp()
      {
        return "Prints out all registers, PC, MPR and PSR.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 1 )
        {
          return this.getUsage();
        }
        return CommandLine.this.mac.getRegisterFile().toString();
      }
    } );
    this.commands.put( "p", this.commands.get( "print" ) );
    this.commands.put( "input", new Command()
    {
      public String getUsage()
      {
        return "input <filename>";
      }

      public String getHelp()
      {
        return "Specifies a file to read the input from instead of keyboard device (simulator must be restarted to restore normal keyboard input).";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 2 )
        {
          return this.getUsage();
        }
        final File keyboardInputStream = new File( array[1] );
        if( keyboardInputStream.exists() )
        {
          return CommandLine.this.mac.setKeyboardInputStream( keyboardInputStream );
        }
        return "Error: file " + array[1] + " does not exist.";
      }
    } );
    this.commands.put( "break", new Command()
    {
      public String getUsage()
      {
        return "b[reak] [ set | clear ] [ mem_addr | label ]";
      }

      public String getHelp()
      {
        return "Sets or clears break point at specified memory address or label.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 3 )
        {
          return this.getUsage();
        }
        if( array[1].toLowerCase().equals( "set" ) )
        {
          return CommandLine.this.mac.getMemory().setBreakPoint( array[2] );
        }
        if( array[1].toLowerCase().equalsIgnoreCase( "clear" ) )
        {
          return CommandLine.this.mac.getMemory().clearBreakPoint( array[2] );
        }
        return this.getUsage();
      }
    } );
    this.commands.put( "b", this.commands.get( "break" ) );
    this.commands.put( "script", new Command()
    {
      public String getUsage()
      {
        return "script <filename>";
      }

      public String getHelp()
      {
        return "Specifies a file from which to read commands.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 2 )
        {
          return this.getUsage();
        }
        final File file = new File( array[1] );
        try
        {
          final BufferedReader bufferedReader = new BufferedReader( new FileReader( file ) );
          final ArrayList<String> list = new ArrayList<String>();
          while( true )
          {
            final String line = bufferedReader.readLine();
            if( line == null )
            {
              break;
            }
            list.add( "@" + line );
          }
          CommandLine.this.scheduleScriptCommands( list );
        }
        catch( IOException ex )
        {
          return ex.getMessage();
        }
        return "";
      }
    } );
    this.commands.put( "load", new Command()
    {
      public String getUsage()
      {
        return "l[oa]d <filename>";
      }

      public String getHelp()
      {
        return "Loads an object file into the memory.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 2 )
        {
          return this.getUsage();
        }
        return CommandLine.this.mac.loadObjectFile( new File( array[1] ) );
      }
    } );
    this.commands.put( "ld", this.commands.get( "load" ) );
    this.commands.put( "check", new Command()
    {
      public String getUsage()
      {
        return "check [ count | cumulative | reset | PC | reg | PSR | MPR | mem_addr | label | N | Z | P ] [ mem_addr | label ] [ value | label ]";
      }

      public String getHelp()
      {
        return "Verifies that a particular value resides in a register or in a memory location, or that a condition code is set.\n" + "Samples:\n" + "'check PC LABEL' checks if the PC points to wherever LABEL points.\n" + "'check LABEL VALUE' checks if the value stored in memory at the location pointed to by LABEL is equal to VALUE.\n" + "'check VALUE LABEL' checks if the value stored in memory at VALUE is equal to the location pointed to by LABEL (probably not very useful). To find out where a label points, use 'list' instead.\n";
      }

      private String check( final boolean b, final String[] array, final String s )
      {
        final String s2 = "TRUE";
        final String s3 = "FALSE";
        String string = "(";
        for( int i = 0; i < array.length; ++i )
        {
          string = string + array[i] + ((i == array.length - 1) ? ")" : " ");
        }
        if( b )
        {
          CommandLine.this.checksPassed++;
          CommandLine.this.checksPassedCumulative++;
          return s2 + " " + string;
        }
        CommandLine.this.checksFailed++;
        CommandLine.this.checksFailedCumulative++;
        return s3 + " " + string + " (actual value: " + s + ")";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n < 2 || n > 4 )
        {
          return this.getUsage();
        }
        if( n == 2 )
        {
          if( array[1].equals( "count" ) )
          {
            return CommandLine.this.checksPassed + " " + ((CommandLine.this.checksPassed == 1) ? "check" : "checks") + " passed, " + CommandLine.this.checksFailed + " failed";
          }
          if( array[1].equals( "cumulative" ) )
          {
            return " -> " + CommandLine.this.checksPassedCumulative + " " + ((CommandLine.this.checksPassedCumulative == 1) ? "check" : "checks") + " passed, " + CommandLine.this.checksFailedCumulative + " failed";
          }
          if( array[1].equals( "reset" ) )
          {
            CommandLine.this.checksPassed = 0;
            CommandLine.this.checksFailed = 0;
            CommandLine.this.checksPassedCumulative = 0;
            CommandLine.this.checksFailedCumulative = 0;
            return "check counts reset";
          }
          final RegisterFile registerFile = CommandLine.this.mac.getRegisterFile();
          if( (array[1].toLowerCase().equals( "n" ) && registerFile.getN()) || (array[1].toLowerCase().equals( "z" ) && registerFile.getZ()) || (array[1].toLowerCase().equals( "p" ) && registerFile.getP()) )
          {
            return this.check( true, array, "" );
          }
          return this.check( false, array, registerFile.printCC() );
        }
        else
        {
          int n2 = Word.parseNum( array[n - 1] );
          if( n2 == Integer.MAX_VALUE )
          {
            n2 = CommandLine.this.mac.lookupSym( array[n - 1] );
            if( n2 == Integer.MAX_VALUE )
            {
              return "Bad value or label: " + array[n - 1];
            }
          }
          final Boolean checkRegister = CommandLine.this.checkRegister( array[1], n2 );
          if( checkRegister != null )
          {
            return this.check( checkRegister, array, CommandLine.this.getRegister( array[1] ) );
          }
          final int address = CommandLine.this.mac.getAddress( array[1] );
          if( address == Integer.MAX_VALUE )
          {
            return "Bad register, value or label: " + array[1];
          }
          if( address < 0 || address >= 65536 )
          {
            return "Address " + array[1] + " out of bounds";
          }
          int address2;
          if( n == 3 )
          {
            address2 = address;
          }
          else
          {
            address2 = CommandLine.this.mac.getAddress( array[2] );
            if( address2 == Integer.MAX_VALUE )
            {
              return "Bad register, value or label: " + array[2];
            }
            if( address2 < 0 || address2 >= 65536 )
            {
              return "Address " + array[2] + " out of bounds";
            }
            if( address2 < address )
            {
              return "Second address in range (" + array[2] + ") must be >= first (" + array[1] + ")";
            }
          }
          boolean b = true;
          String string = "";
          for( int i = address; i <= address2; ++i )
          {
            final Word read = CommandLine.this.mac.getMemory().read( i );
            if( read == null )
            {
              return "Bad register, value or label: " + array[1];
            }
            if( read.getValue() != (n2 & 0xFFFF) )
            {
              b = false;
              string = string + ((string.length() == 0) ? "" : ", ") + Word.toHex( i ) + ":" + read.toHex();
            }
          }
          return this.check( b, array, string );
        }
      }
    } );
    this.commands.put( "dump", new Command()
    {
      public String getUsage()
      {
        return "d[ump] [-check | -coe | -readmemh | -disasm] from_mem_addr to_mem_addr dumpfile";
      }

      public String getHelp()
      {
        return "dumps a range of memory values to a specified file as raw values.\n  -check: dump as 'check' commands that can be run as an LC-3 script.\n  -coe dump a Xilinx coregen image\n  -readmemh dump a file readable by Verilog's $readmemh() system task.\n  -disasm dump disassembled instructions.";
      }

      public String doCommand( final String[] array, final int n )
      {
        int n2 = 0;
        if( n < 4 || n > 5 )
        {
          return this.getUsage();
        }
        if( n == 5 )
        {
          if( array[1].equalsIgnoreCase( "-check" ) )
          {
            n2 = 1;
          }
          else if( array[1].equalsIgnoreCase( "-coe" ) )
          {
            n2 = 2;
          }
          else if( array[1].equalsIgnoreCase( "-readmemh" ) )
          {
            n2 = 3;
          }
          else
          {
            if( !array[1].equalsIgnoreCase( "-disasm" ) )
            {
              return "Unrecognized flag: " + array[1] + "\n" + this.getUsage();
            }
            n2 = 4;
          }
        }
        final int address = CommandLine.this.mac.getAddress( array[n - 3] );
        final int address2 = CommandLine.this.mac.getAddress( array[n - 2] );
        if( address == Integer.MAX_VALUE )
        {
          return "Error: Invalid register, address, or label  ('" + array[n - 3] + "')";
        }
        if( address < 0 || address >= 65536 )
        {
          return "Address " + array[n - 3] + " out of bounds";
        }
        if( address2 == Integer.MAX_VALUE )
        {
          return "Error: Invalid register, address, or label  ('" + array[n - 3] + "')";
        }
        if( address2 < 0 || address2 >= 65536 )
        {
          return "Address " + array[n - 2] + " out of bounds";
        }
        if( address2 < address )
        {
          return "Second address in range (" + array[n - 2] + ") must be >= first (" + array[n - 3] + ")";
        }
        final File file = new File( array[n - 1] );
        PrintWriter printWriter;
        try
        {
          if( !file.createNewFile() )
          {
            return "File " + array[n - 1] + " already exists. Choose a different filename.";
          }
          printWriter = new PrintWriter( new BufferedWriter( new FileWriter( file ) ) );
        }
        catch( IOException ex )
        {
          ErrorLog.logError( ex );
          return "Error opening file: " + file.getName();
        }
        if( n2 == 2 )
        {
          printWriter.println( "MEMORY_INITIALIZATION_RADIX=2;" );
          printWriter.println( "MEMORY_INITIALIZATION_VECTOR=" );
        }
        for( int i = address; i <= address2; ++i )
        {
          final Word read = CommandLine.this.mac.getMemory().read( i );
          if( read == null )
          {
            printWriter.close();
            return "Bad register, value or label: " + array[n - 3];
          }
          switch( n2 )
          {
            case 0:
            {
              printWriter.println( read.toHex() );
              break;
            }
            case 1:
            {
              printWriter.println( "check " + Word.toHex( i ) + " " + read.toHex() );
              break;
            }
            case 2:
            {
              if( i < address2 )
              {
                printWriter.println( read.toBinary().substring( 1 ) + "," );
                break;
              }
              printWriter.println( read.toBinary().substring( 1 ) + ";" );
              break;
            }
            case 3:
            {
              printWriter.println( read.toHex().substring( 1 ) );
              break;
            }
            case 4:
            {
              printWriter.println( ISA.disassemble( read, i, CommandLine.this.mac ) );
              break;
            }
            default:
            {
              assert false : "Invalid flag to `dump' command: " + array[1];
              break;
            }
          }
        }
        printWriter.close();
        return "edu.wcsu.WCSUSim.Machine.Memory dumped.";
      }
    } );
    this.commands.put( "d", this.commands.get( "dump" ) );
    this.commands.put( "trace", new Command()
    {
      public String getUsage()
      {
        return "trace [on <trace-file> | off]";
      }

      public String getHelp()
      {
        return "For each instruction executed, this command dumps a subset of processor state to a file, to create a trace that can be used to verify correctness of execution. The state consists of, in order, (1) PC, (2) current insn, (3) regfile write-enable, (4) regfile data in, (5) data memory write-enable, (6) data memory address, and (7) data memory data in. These values are written in hex to <trace-file>, one line for each instruction executed. Note that trace files can get very large very quickly!\n   Sometimes a signal may be a don't-care value - if we're not writing to the regfile, the `regfile data in' value is undefined - but the write-enable values should allow don't-care signals to be determined in all cases.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n < 2 || n > 3 )
        {
          return this.getUsage();
        }
        if( n == 3 )
        {
          if( !array[1].equalsIgnoreCase( "on" ) )
          {
            return this.getUsage();
          }
          if( CommandLine.this.mac.isTraceEnabled() )
          {
            return "Tracing is already on.";
          }
          final File file = new File( array[n - 1] );
          PrintWriter traceWriter;
          try
          {
            if( !file.createNewFile() )
            {
              return "File " + array[n - 1] + " already exists.";
            }
            traceWriter = new PrintWriter( new BufferedWriter( new FileWriter( file ) ), true );
          }
          catch( IOException ex )
          {
            ErrorLog.logError( ex );
            return "Error opening file: " + file.getName();
          }
          CommandLine.this.mac.setTraceWriter( traceWriter );
          return "Tracing is on.";
        }
        else
        {
          assert n == 2;
          if( !array[1].equalsIgnoreCase( "off" ) )
          {
            return this.getUsage();
          }
          if( !CommandLine.this.mac.isTraceEnabled() )
          {
            return "Tracing is already off.";
          }
          CommandLine.this.mac.getTraceWriter().flush();
          CommandLine.this.mac.getTraceWriter().close();
          CommandLine.this.mac.disableTrace();
          return "Tracing is off.";
        }
      }
    } );
    this.commands.put( "counters", new Command()
    {
      public String getUsage()
      {
        return "counters";
      }

      public String getHelp()
      {
        return "Print out values of internal performance counters.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n != 1 )
        {
          return this.getUsage();
        }
        return "Cycle count: " + String.valueOf( CommandLine.this.mac.CYCLE_COUNT ) + "\n" + "edu.wcsu.WCSUSim.edu.wcsu.WCSUSim.Machine.Machine.Instruction count: " + String.valueOf( CommandLine.this.mac.INSTRUCTION_COUNT ) + "\n" + "Load stall count: " + String.valueOf( CommandLine.this.mac.LOAD_STALL_COUNT ) + "\n" + "Branch stall count: " + String.valueOf( CommandLine.this.mac.BRANCH_STALL_COUNT ) + "\n";
      }
    } );
    this.commands.put( "set", new Command()
    {
      public String getUsage()
      {
        return "set [ PC | reg | PSR | MPR | mem_addr | label ] [ mem_addr | label ] [ value | N | Z | P ]";
      }

      public String getHelp()
      {
        return "Sets the value of a register/PC/PSR/label/memory location/memory range or set the condition codes individually.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n < 2 || n > 4 )
        {
          return this.getUsage();
        }
        if( n == 2 )
        {
          final String setConditionCodes = CommandLine.this.setConditionCodes( array[1] );
          if( setConditionCodes == null )
          {
            return this.getUsage();
          }
          return setConditionCodes;
        }
        else
        {
          int n2 = Word.parseNum( array[n - 1] );
          if( n2 == Integer.MAX_VALUE )
          {
            n2 = CommandLine.this.mac.lookupSym( array[n - 1] );
          }
          if( n2 == Integer.MAX_VALUE )
          {
            return "Error: Invalid value (" + array[n - 1] + ")";
          }
          if( n == 3 )
          {
            final String setRegister = CommandLine.this.setRegister( array[1], n2 );
            if( setRegister != null )
            {
              return setRegister;
            }
          }
          final int address = CommandLine.this.mac.getAddress( array[1] );
          if( address == Integer.MAX_VALUE )
          {
            return "Error: Invalid register, address, or label  ('" + array[1] + "')";
          }
          if( address < 0 || address >= 65536 )
          {
            return "Address " + array[1] + " out of bounds";
          }
          int address2;
          if( n == 3 )
          {
            address2 = address;
          }
          else
          {
            address2 = CommandLine.this.mac.getAddress( array[2] );
            if( address2 == Integer.MAX_VALUE )
            {
              return "Error: Invalid register, address, or label  ('" + array[1] + "')";
            }
            if( address2 < 0 || address2 >= 65536 )
            {
              return "Address " + array[2] + " out of bounds";
            }
            if( address2 < address )
            {
              return "Second address in range (" + array[2] + ") must be >= first (" + array[1] + ")";
            }
          }
          for( int i = address; i <= address2; ++i )
          {
            CommandLine.this.mac.getMemory().write( i, n2 );
          }
          if( n == 3 )
          {
            return "edu.wcsu.WCSUSim.Machine.Memory location " + Word.toHex( address ) + " updated to " + array[n - 1];
          }
          return "edu.wcsu.WCSUSim.Machine.Memory locations " + Word.toHex( address2 ) + " to " + Word.toHex( address2 ) + " updated to " + array[n - 1];
        }
      }
    } );
    this.commands.put( "list", new Command()
    {
      public String getUsage()
      {
        return "l[ist] [ addr1 | label1 [addr2 | label2] ]";
      }

      public String getHelp()
      {
        return "Lists the contents of memory locations (default address is PC. Specify range by giving 2 arguments).";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n > 3 )
        {
          return this.getUsage();
        }
        if( n == 1 )
        {
          CommandLine.this.scrollToPC();
          return Word.toHex( CommandLine.this.mac.getRegisterFile().getPC() ) + " : " + CommandLine.this.mac.getMemory().getInst( CommandLine.this.mac.getRegisterFile().getPC() ).toHex() + " : " + ISA.disassemble( CommandLine.this.mac.getMemory().getInst( CommandLine.this.mac.getRegisterFile().getPC() ), CommandLine.this.mac.getRegisterFile().getPC(), CommandLine.this.mac );
        }
        if( n == 2 )
        {
          final String register = CommandLine.this.getRegister( array[1] );
          if( register != null )
          {
            return array[1] + " : " + register;
          }
          final int address = CommandLine.this.mac.getAddress( array[1] );
          if( address == Integer.MAX_VALUE )
          {
            return "Error: Invalid address or label (" + array[1] + ")";
          }
          if( WCSUSim.GRAPHICAL_MODE && address < 65024 )
          {
            CommandLine.this.GUI.scrollToIndex( address );
          }
          return Word.toHex( address ) + " : " + CommandLine.this.mac.getMemory().read( address ).toHex() + " : " + ISA.disassemble( CommandLine.this.mac.getMemory().read( address ), address, CommandLine.this.mac );
        }
        else
        {
          final int address2 = CommandLine.this.mac.getAddress( array[1] );
          final int address3 = CommandLine.this.mac.getAddress( array[2] );
          if( address2 == Integer.MAX_VALUE )
          {
            return "Error: Invalid address or label (" + array[1] + ")";
          }
          if( address3 == Integer.MAX_VALUE )
          {
            return "Error: Invalid address or label (" + array[2] + ")";
          }
          if( address3 < address2 )
          {
            return "Error: addr2 should be larger than addr1";
          }
          final StringBuffer sb = new StringBuffer();
          for( int i = address2; i <= address3; ++i )
          {
            sb.append( Word.toHex( i ) + " : " + CommandLine.this.mac.getMemory().read( i ).toHex() + " : " + ISA.disassemble( CommandLine.this.mac.getMemory().read( i ), i, CommandLine.this.mac ) );
            if( i != address3 )
            {
              sb.append( "\n" );
            }
          }
          if( WCSUSim.GRAPHICAL_MODE )
          {
            CommandLine.this.GUI.scrollToIndex( address2 );
          }
          return new String( sb );
        }
      }
    } );
    this.commands.put( "l", this.commands.get( "list" ) );
    this.commands.put( "as", new Command()
    {
      public String getUsage()
      {
        return "as [-warn] <filename>";
      }

      public String getHelp()
      {
        return "Assembles <filename> showing errors and (optionally) warnings, and leaves a .obj file in the same directory.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( n < 2 || n > 3 )
        {
          return this.getUsage();
        }
        final String[] array2 = new String[n - 1];
        final String s = "";
        array2[0] = array[1];
        String s2 = s + array[1];
        if( n == 3 )
        {
          array2[1] = array[2];
          s2 = s2 + " " + array[2];
        }
        final Assembler assembler = new Assembler();
        try
        {
          final String as = assembler.as( array2 );
          if( as.length() != 0 )
          {
            return as + "Warnings encountered during assembly " + "(but assembly completed w/o errors).";
          }
        }
        catch( AsException ex )
        {
          return ex.getMessage() + "\nErrors encountered during assembly.";
        }
        return "Assembly of '" + s2 + "' completed without errors or warnings.";
      }
    } );
    this.commands.put( "clear", new Command()
    {
      public String getUsage()
      {
        return "clear";
      }

      public String getHelp()
      {
        return "Clears the commandline output window. Available only in GUI mode.";
      }

      public String doCommand( final String[] array, final int n )
      {
        if( WCSUSim.GRAPHICAL_MODE )
        {
          Console.clear();
          return "";
        }
        return "Error: clear is only available in edu.wcsu.WCSUSim.Display.GUI mode";
      }
    } );
    this.commands.put( "goto", new Command()
    {

      @Override
      public String getUsage()
      {
        return "goto [line]";
      }

      @Override
      public String getHelp()
      {
        return "Scrolls to the line number in memory";
      }

      @Override
      public String doCommand( String[] array, int n ) throws ExceptionException
      {
        if( n != 2 )
          return "Error: Invalid number of arguments";
        final int address = CommandLine.this.mac.getAddress( array[1] );
        if( address == Integer.MAX_VALUE )
        {
          return "Error: Invalid address or label (" + array[1] + ")";
        }
        if( WCSUSim.GRAPHICAL_MODE && address < 65024 )
        {
          CommandLine.this.GUI.scrollToIndex( address );
        }
        return "";
      }

    } );
    this.commands.put( "g", this.commands.get( "goto" ) );
    this.commands.put( "reload", new Command()
    {
      @Override
      public String getUsage()
      {
        return "reload [file]";
      }

      @Override
      public String getHelp()
      {
        return "Assembles and Loads the file, and resets the PC";
      }

      @Override
      public String doCommand( String[] array, int n ) throws ExceptionException
      {
        if( n != 2 )
          return "Error: Invalid number of arguments";
        int n2 = Word.parseNum( "x200" );
        return as( array, n ) + "\n" + ld( array, n ) + "\n" + CommandLine.this.setRegister( "pc", n2 );
      }


      private String as( final String[] array, final int n )
      {
        if( n < 2 || n > 3 )
        {
          return this.getUsage();
        }
        final String[] array2 = new String[n - 1];
        final String s = "";
        array2[0] = array[1] + ".asm";
        String s2 = s + array[1];
        if( n == 3 )
        {
          array2[1] = array[2];
          s2 = s2 + " " + array[2];
        }
        final Assembler assembler = new Assembler();
        try
        {
          final String as = assembler.as( array2 );
          if( as.length() != 0 )
          {
            return as + "Warnings encountered during assembly " + "(but assembly completed w/o errors).";
          }
        }
        catch( AsException ex )
        {
          return ex.getMessage() + "\nErrors encountered during assembly.";
        }
        return "Assembly of '" + s2 + "' completed without errors or warnings.";
      }

      private String ld( final String[] array, final int n )
      {
        if( n != 2 )
        {
          return this.getUsage();
        }
        return CommandLine.this.mac.loadObjectFile( new File( array[1] + ".obj" ) );
      }
    } );
  }

  public String runCommand( String replaceFirst ) throws ExceptionException, NumberFormatException
  {
    if( replaceFirst == null )
    {
      return "";
    }
    if( !replaceFirst.startsWith( "@" ) )
    {
      this.resetHistoryStack();
      this.addToHistory( replaceFirst );
    }
    else
    {
      replaceFirst = replaceFirst.replaceFirst( "^@", "" );
    }
    String[] split = replaceFirst.split( "\\s+" );
    int n = split.length;
    if( n == 0 )
    {
      return "";
    }
    final String lowerCase = split[0].toLowerCase();
    if( lowerCase.equals( "" ) )
    {
      return "";
    }
    int n2 = -1;
    for( int i = 0; i < split.length; ++i )
    {
      if( split[i].startsWith( "#" ) )
      {
        n2 = i;
        break;
      }
    }
    if( n2 == 0 )
    {
      return "";
    }
    if( n2 >= 0 )
    {
      final String[] array = new String[n2];
      for( int j = 0; j < n2; ++j )
      {
        array[j] = split[j];
      }
      split = array;
      n = split.length;
    }
    final Command command = this.commands.get( lowerCase );
    if( command == null )
    {
      return "Unknown command: " + lowerCase;
    }
    return command.doCommand( split, n );
  }

  public void scrollToPC()
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      this.GUI.scrollToPC();
    }
  }

  public String setRegister( final String s, final int n )
  {
    String string = "Register " + s.toUpperCase() + " updated to value " + Word.toHex( n );
    if( s.equalsIgnoreCase( "pc" ) )
    {
      this.mac.getRegisterFile().setPC( n );
      this.scrollToPC();
    }
    else if( s.equalsIgnoreCase( "psr" ) )
    {
      this.mac.getRegisterFile().setPSR( n );
    }
    else if( s.equalsIgnoreCase( "mpr" ) )
    {
      final Memory memory = this.mac.getMemory();
      this.mac.getMemory();
      memory.write( 65042, n );
    }
    else if( (s.startsWith( "r" ) || s.startsWith( "R" )) && s.length() == 2 )
    {
      this.mac.getRegisterFile().setRegister( new Integer( s.substring( 1, 2 ) ), n );
    }
    else
    {
      string = null;
    }
    return string;
  }

  public String setConditionCodes( final String s )
  {
    String s2 = null;
    if( s.equalsIgnoreCase( "n" ) )
    {
      this.mac.getRegisterFile().setN();
      s2 = "PSR N bit set";
    }
    else if( s.equalsIgnoreCase( "z" ) )
    {
      this.mac.getRegisterFile().setZ();
      s2 = "PSR Z bit set";
    }
    else if( s.equalsIgnoreCase( "p" ) )
    {
      this.mac.getRegisterFile().setP();
      s2 = "PSR P bit set";
    }
    return s2;
  }

  public String getRegister( final String s )
  {
    int n;
    if( s.equalsIgnoreCase( "pc" ) )
    {
      n = this.mac.getRegisterFile().getPC();
    }
    else if( s.equalsIgnoreCase( "psr" ) )
    {
      n = this.mac.getRegisterFile().getPSR();
    }
    else if( s.equalsIgnoreCase( "mpr" ) )
    {
      n = this.mac.getRegisterFile().getMPR();
    }
    else
    {
      if( (!s.startsWith( "r" ) && !s.startsWith( "R" )) || s.length() != 2 )
      {
        return null;
      }
      n = this.mac.getRegisterFile().getRegister( new Integer( s.substring( 1, 2 ) ) );
    }
    return Word.toHex( n );
  }

  public Boolean checkRegister( final String s, final int n )
  {
    final int num = Word.parseNum( this.getRegister( s ) );
    if( num == Integer.MAX_VALUE )
    {
      return null;
    }
    return new Boolean( num == new Word( n ).getValue() );
  }

  public void reset()
  {
    this.checksPassed = 0;
    this.checksFailed = 0;
  }

  public void setGUI( final GUI gui )
  {
    this.GUI = gui;
  }

  static
  {
    NEWLINE = System.getProperty( "line.separator" );
    PROMPT = CommandLine.NEWLINE + "==>";
  }

  private interface Command
  {
    String getUsage();

    String getHelp();

    String doCommand( final String[] p0, final int p1 ) throws ExceptionException;
  }
}
