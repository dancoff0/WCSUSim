package edu.wcsu.WCSUSim;

import edu.wcsu.WCSUSim.Display.CommandLine;
import edu.wcsu.WCSUSim.Display.GUI;
import edu.wcsu.WCSUSim.Exceptions.ExceptionException;
import edu.wcsu.WCSUSim.Machine.ErrorLog;
import edu.wcsu.WCSUSim.Machine.Machine;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;

// 
// Decompiled by Procyon v0.5.30
// 

public class WCSUSim
{
    public static String version;
    public static boolean GRAPHICAL_MODE;
    public static boolean PIPELINE_MODE;
    public static boolean LC3;
    public static boolean P37X;
    
    public static boolean isGraphical() {
        return WCSUSim.GRAPHICAL_MODE;
    }
    
    public static boolean isPipelined() {
        return WCSUSim.PIPELINE_MODE;
    }
    
    public static boolean isLC3() {
        return WCSUSim.LC3;
    }
    
    public static boolean isP37X() {
        return WCSUSim.P37X;
    }
    
    public static String getISA()
    {
        if ( WCSUSim.LC3)
        {
            return "LC3 ISA";
        }
        if ( WCSUSim.P37X)
        {
            return "P37X ISA";
        }
        return null;
    }
    
    public static String getVersion() {
        return "edu.wcsu.WCSUSim.WCSUSim Version " + WCSUSim.version;
    }
    
    private static void printUsage()
    {
        System.out.println("\nUsage: java edu.wcsu.WCSUSim.WCSUSim [-lc3] [-p37x] [-pipeline] [-t] [-s script]");
        System.out.println("  -lc3 : simulate the LC-3 edu.wcsu.WCSUSim.edu.wcsu.WCSUSim.Machine.Machine.ISA");
        System.out.println("  -p37x : simulate the edu.wcsu.WCSUSim.Machine.P37X edu.wcsu.WCSUSim.edu.wcsu.WCSUSim.Machine.Machine.ISA");
        System.out.println("  -pipeline : simulate a 5-stage fully-bypassed pipeline");
        System.out.println("  -t : start in command-line mode");
        System.out.println("  -s script : run 'script' from a script file");
    }
    
    public static void main(final String[] array)
    {
        String s = null;
        System.out.println(getVersion() + "\n");
        for( int i = 0; i < array.length; ++i )
        {
            if( array[i].equalsIgnoreCase( "-t" ) )
            {
                WCSUSim.GRAPHICAL_MODE = false;
            }
            else if( array[i].equalsIgnoreCase( "-s" ) )
            {
                if( ++i >= array.length )
                {
                    System.out.println( "Error: -s requires a script filename" );
                    return;
                }
                s = array[i];
            }
            else if( array[i].equalsIgnoreCase( "-lc3" ) )
            {
                WCSUSim.LC3 = true;
            }
            else if( array[i].equalsIgnoreCase( "-p37x" ) )
            {
                WCSUSim.P37X = true;
            }
            else
            {
                if( !array[i].equalsIgnoreCase( "-pipeline" ) )
                {
                    System.out.println( "Arg '" + array[i] + "' not recognized" );
                    printUsage();
                    return;
                }
                WCSUSim.PIPELINE_MODE = true;
            }
        }
        if( WCSUSim.LC3 && WCSUSim.P37X )
        {
            System.out.println( "Error: can't specify more than one edu.wcsu.WCSUSim.edu.wcsu.WCSUSim.Machine.Machine.ISA" );
            printUsage();
            return;
        }
        if( !WCSUSim.LC3 && !WCSUSim.P37X )
        {
            System.out.println( "Error: edu.wcsu.WCSUSim.edu.wcsu.WCSUSim.Machine.Machine.ISA not specified" );
            printUsage();
            return;
        }
        System.out.println( getISA() );
        final Machine machine = new Machine();
        final CommandLine commandLine = new CommandLine( machine );
        if( s != null )
        {
            commandLine.scheduleCommand( "@script " + s );
        }
        if( WCSUSim.GRAPHICAL_MODE )
        {
            System.out.println( "Loading graphical interface\n" );
            GUI.initLookAndFeel();
            final GUI gui = new GUI( machine, commandLine );
            machine.setGUI( gui );
            SwingUtilities.invokeLater( new TempRun( gui ) );
        }
        else
        {
            try
            {
                final BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
                Block_23:
                while( true )
                {
                    if( !machine.isContinueMode() )
                    {
                        System.out.print( CommandLine.PROMPT );
                    }
                    if( s == null )
                    {
                        final String line = bufferedReader.readLine();
                        if( line != null )
                        {
                            commandLine.scheduleCommand( line );
                        }
                    }
                    while( commandLine.hasMoreCommands() && (!machine.isContinueMode() || commandLine.hasQueuedStop()) )
                    {
                        final String nextCommand = commandLine.getNextCommand();
                        if( s != null && !nextCommand.startsWith( "@" ) )
                        {
                            s = null;
                        }
                        String s2;
                        try
                        {
                            s2 = commandLine.runCommand( nextCommand );
                        }
                        catch( ExceptionException ex )
                        {
                            s2 = ex.getExceptionDescription();
                        }
                        catch( NumberFormatException ex2 )
                        {
                            s2 = "NumberFormatException: " + ex2.getMessage();
                        }
                        if( s2 == null )
                        {
                            break Block_23;
                        }
                        System.out.println( s2 );
                    }
                    if( s != null && !commandLine.hasMoreCommands() )
                    {
                        s = null;
                    }
                }
                machine.cleanup();
                System.out.println( "Bye!" );
            }
            catch( IOException ex3 )
            {
                ErrorLog.logError( ex3 );
            }
        }
    }
    
    static
    {
        WCSUSim.version = "1.3.0 $Rev: 1 $";
        WCSUSim.GRAPHICAL_MODE = true;
        WCSUSim.PIPELINE_MODE = false;
        WCSUSim.LC3 = true;
        WCSUSim.P37X = false;

    }
}
