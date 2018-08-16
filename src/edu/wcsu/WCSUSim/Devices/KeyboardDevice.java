package edu.wcsu.WCSUSim.Devices;

import edu.wcsu.WCSUSim.Machine.ErrorLog;
import edu.wcsu.WCSUSim.Machine.Memory;
import edu.wcsu.WCSUSim.Machine.Word;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

// 
// Decompiled by Procyon v0.5.30
// 

public class KeyboardDevice
{
  // Special values for the keyboard status register
  private static final Word KB_AVAILABLE   = new Word( 0x8000 );
  private static final Word KB_UNAVAILABLE = new Word( 0x0000 );

  private static final int KEYBOARD_READY_BIT = 0x8000;

  private Word currentStatus;

  // This the memory handler
  private final Memory memory;

  // Local parameters
  private static int CBUFSIZE        = 128;
  private static char TIMER_TICK     = '.';
  public static int SCRIPT_MODE      = 0;
  public static int INTERACTIVE_MODE = 1;

  private BufferedReader kbin;
  private BufferedReader defkbin;

  private int current;
  private int mode;
  private int defmode;

  public KeyboardDevice( final Memory memory )
  {
    this.kbin = null;
    this.defkbin = null;
    this.current = 0;
    this.kbin = new BufferedReader( new InputStreamReader( System.in ) );
    this.mode = KeyboardDevice.INTERACTIVE_MODE;
    this.defkbin = this.kbin;
    this.defmode = this.mode;
    currentStatus = KB_UNAVAILABLE;
    this.memory   = memory;
  }

  public void setDefaultInputStream()
  {
    this.defkbin = this.kbin;
  }

  public void setDefaultInputMode()
  {
    this.defmode = this.mode;
  }

  public void setInputStream( final InputStream inputStream )
  {
    this.kbin = new BufferedReader( new InputStreamReader( inputStream ) );
  }

  public void setInputMode( final int mode )
  {
    this.mode = mode;
  }

  /*
  public void reset()
  {
    this.kbin = this.defkbin;
    this.mode = this.defmode;
    this.current = 0;
  }
  */

  /*
  public Word status()
  {
    if( this.available() )
    {
      return KeyboardDevice.KB_AVAILABLE;
    }
    return KeyboardDevice.KB_UNAVAILABLE;
  }
  */

  /*
  public boolean available()
  {
    try
    {
      if( this.kbin.ready() )
      {
        this.kbin.mark( 1 );
        if( this.kbin.read() == KeyboardDevice.TIMER_TICK )
        {
          this.kbin.reset();
          return false;
        }
        this.kbin.reset();
        return true;
      }
    }
    catch( IOException ex )
    {
      ErrorLog.logError( ex );
    }
    return false;
  }
  */

  /*
  public Word read()
  {
    final char[] array = new char[KeyboardDevice.CBUFSIZE];
    try
    {
      if( this.available() )
      {
        if( this.mode == KeyboardDevice.INTERACTIVE_MODE )
        {
          // Read a buffer's worth of characters but return only the last one.
          this.current = array[this.kbin.read( array, 0, KeyboardDevice.CBUFSIZE ) - 1];
        }
        else
        {
          this.current = this.kbin.read();
        }
      }
    }
    catch( IOException ex )
    {
      ErrorLog.logError( ex );
    }
    return new Word( this.current );
  }
  */

  /*
  public boolean hasTimerTick()
  {
    try
    {
      this.kbin.mark( 1 );
      if( this.kbin.ready() )
      {
        if( this.kbin.read() == KeyboardDevice.TIMER_TICK )
        {
          return true;
        }
        this.kbin.reset();
        return false;
      }
    }
    catch( IOException ex )
    {
      ErrorLog.logError( ex );
    }
    return false;
  }
  */

  // A key was just pressed.
  public void handleKeyPressed()
  {
    // Get the current status
    currentStatus = memory.read( Memory.OS_KBSR );

    // Check if there is already a key press available.
    if( ( currentStatus.getValue() & KEYBOARD_READY_BIT ) == 0 )
    {
      currentStatus = new Word( currentStatus.getValue() | KEYBOARD_READY_BIT );

      // Get the new character
      try
      {
        int currentValue = this.kbin.read();

        // Save it in the keyboard data register
        memory.write( Memory.OS_KBDR, currentValue );
      }
      catch( IOException ioe )
      {
        System.out.println( "KeyboardDevice: Caught exception reading character" );
      }

      // Set the "Ready" bit of the keyboard status register
      memory.write( Memory.OS_KBSR,  currentStatus.getValue() );

      // Check if interrupts are enabled. If so, signal an interrupt
      if( (currentStatus.getValue() & Memory.ENABLE_INTERRUPTS_BIT ) != 0)
      {
        // Get our interrupt vector
        int interruptVector = currentStatus.getValue() & 0x00FF;
        memory.getMachine().signalInterrupt( interruptVector );
      }

    }
  }

  // Whenever the Keyboard Status Register is written to, this method will be called.
  public void statusRegisterUpdated()
  {
    Word newStatus = memory.read( Memory.OS_KBSR );

    // Check if this change entails the clearing of the Ready bit
    if( ( currentStatus.getValue() & KEYBOARD_READY_BIT ) != 0 )
    {
      if( ( newStatus.getValue() & KEYBOARD_READY_BIT ) == 0 )
      {
        // The ready bit has just been cleared. See if there is another character in the pipeline
        try
        {
          if( kbin.ready() )
          {
            int currentValue = this.kbin.read();

            // Save it in the keyboard data register
            memory.write( Memory.OS_KBDR, currentValue );

            // Set the Ready bit
            currentStatus = new Word( newStatus.getValue() | KEYBOARD_READY_BIT );
            memory.write( Memory.OS_KBSR, currentStatus.getValue() );

            // Check if interrupts are enabled. If so, signal an interrupt
            if( (currentStatus.getValue() & Memory.ENABLE_INTERRUPTS_BIT ) != 0)
            {
              // Get our interrupt vector
              int interruptVector = currentStatus.getValue() & 0x00FF;
              //System.out.println( "Signaling an additional interrupt for the keyboard" );
              memory.getMachine().signalInterrupt( interruptVector );
            }

          }
        }
        catch( IOException ioe )
        {
          System.out.println( "KeyboardDevice: statusRegisterUpdated: caught exception reading next character" );
        }
      }

    }
  }
}
