package edu.wcsu.WCSUSim.Devices;

import edu.wcsu.WCSUSim.Machine.Memory;
import edu.wcsu.WCSUSim.Machine.Word;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskDevice
{
  // Local variables
  private final Memory memory;
  private RandomAccessFile inputFile;
  Word    currentStatus;

  // Constants
  private static final Word DD_READY = new Word(0x8000 );
  private static final Word DD_START = new Word(0x0000 );
  private static final int  DISK_READY_BIT = 0x8000;
  private static final int  BLOCK_SIZE     = 4096;

  private static final int READ_COMMAND  = 0x0001;
  private static final int WRITE_COMMAND = 0x0002;


  public DiskDevice( Memory memory )
  {
    this.memory = memory;
    inputFile = null;
    currentStatus = DD_READY;
  }

  public void mountDiskFile( RandomAccessFile inputFile )
  {
    this.inputFile = inputFile;
  }

  public void unmountDiskFile()
  {
    this.inputFile = null;
  }

  public void statusRegisterUpdated()
  {
    // Get the new value of the status register
    Word newStatus = memory.read( Memory.OS_DDSR );

    // Check if this change entails the clearing of the Ready bit
    if( ( currentStatus.getValue() & DISK_READY_BIT ) != 0 )
    {
      if( (newStatus.getValue() & DISK_READY_BIT ) == 0 )
      {
        // We should initiate a new operation
        Word commandRegister = memory.read( Memory.OS_DDCR );
        Word blockRegister   = memory.read( Memory.OS_DDBR );
        Word memoryRegister  = memory.read( Memory.OS_DDMR );

        switch( commandRegister.getValue() )
        {
          case READ_COMMAND:
            handleReadDisk( blockRegister.getValue(), memoryRegister.getValue() );
            break;

          case WRITE_COMMAND:
            handleWriteDisk( blockRegister.getValue(), memoryRegister.getValue() );
            break;
        }

        // Now we just need to set the ready bit
        currentStatus = new Word( newStatus.getValue() | DISK_READY_BIT );
        memory.write( Memory.OS_DDSR, currentStatus.getValue() );

      }
    }
  }

  private void handleReadDisk( int blockNumber, int DMAAddress )
  {
    try
    {
      // Read in the data block.
      inputFile.seek( blockNumber * BLOCK_SIZE );
      byte[] data = new byte[ BLOCK_SIZE ];
      inputFile.read( data, 0, BLOCK_SIZE );

      // Write the data to memory
      for(int i = 0; i < BLOCK_SIZE; i++ )
      {
        int datum = ((int)data[i]) & 0x000000FF;
        //System.out.format( "Writing datum 0x%x\n", datum );
        memory.write( DMAAddress + i, ((int)data[i]) & 0x000000FF );
      }
    }
    catch( IOException ioe )
    {
      System.out.println( "DiskDevice: caught exception reading disk:" + ioe );
    }
  }

  private void handleWriteDisk( int blockNumber, int DMAAddress )
  {
    try
    {
      inputFile.seek( blockNumber * BLOCK_SIZE );
      byte[] data = new byte[ BLOCK_SIZE ];
      for( int i = 0; i < BLOCK_SIZE; i++ )
      {
        data[i] = (byte)memory.read( DMAAddress + i ).getValue();
      }
      inputFile.write( data, 0, BLOCK_SIZE );
    }
    catch( IOException ioe )
    {
      System.out.println( "DiskDevice: caught exception writing to disk: " + ioe );
    }
  }
}
