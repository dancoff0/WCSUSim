package edu.wcsu.WCSUSim.Machine;//
// Decompiled by Procyon v0.5.30
// 

import edu.wcsu.WCSUSim.Exceptions.IllegalInstructionException;
import edu.wcsu.WCSUSim.Exceptions.IllegalMemAccessException;

public class LC3 extends ISA
{
  public void init()
  {
    super.init();

    // Branch instructions: OPCode 0x0000
    ISA.createDef( "BR",    "0000 111 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnzp", "0000 111 ppppppppp", new BranchDef() );
    ISA.createDef( "BRp",   "0000 001 ppppppppp", new BranchDef() );
    ISA.createDef( "BRz",   "0000 010 ppppppppp", new BranchDef() );
    ISA.createDef( "BRzp",  "0000 011 ppppppppp", new BranchDef() );
    ISA.createDef( "BRn",   "0000 100 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnp",  "0000 101 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnz",  "0000 110 ppppppppp", new BranchDef() );

    // NOP instruction: also OPCode 0x0000    It is really a branch on an impossible condition
    ISA.createDef( "NOP", "0000 000 xxxxxxxxx", new InstructionDef()
    {
      @Override
      public int execute( Word word, int pc, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        return pc + 1;
      }
    } );

    // Synonym for the common pattern of copying the value of one register to another
    ISA.createDef( "CPR", "0001 ddd sss 1 00000", new InstructionDef()
    {
      @Override
      public int execute( Word word, int pc, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        final int sValue = registerFile.getRegister( getSReg( word ) );
        registerFile.setRegister( getDReg( word ), sValue );
        return pc + 1;

      }
    } );

    // Arithmetic instructions: OPCode 0x0001
    ISA.createDef( "ADD", "0001 ddd sss 0 00 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) + registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "MUL", "0001 ddd sss 0 01 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) * registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "SUB", "0001 ddd sss 0 10 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) - registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "DIV", "0001 ddd sss 0 11 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) / registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "ADD", "0001 ddd sss 1 iiiii", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) + this.getSignedImmed( word );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "CLR", "0101 ddd xxx 1 00000", new InstructionDef()
    {
      @Override
      public int execute( Word word, int pc, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        registerFile.setRegister( this. getDReg( word), 0 );
        registerFile.setNZP( 0 );
        return pc + 1;
      }
    } );

    ISA.createDef( "AND", "0101 ddd sss 000 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) & registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "NOT", "0101 ddd sss 001 xxx", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = ~registerFile.getRegister( this.getSReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "OR", "0101 ddd sss 010 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) | registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "XOR", "0101 ddd sss 011 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) ^ registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "AND", "0101 ddd sss 1 uuuuu", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) & this.getUnsignedImmed( word );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    // Compare instructions: OPCode 0x1001
    ISA.createDef( "CMP", "1001 sss 00 xxxx ttt", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        final int sValue = registerFile.getRegister( getSReg( word  ) );
        final int tValue = registerFile.getRegister( getTReg( word  ) );
        final int nzp = sValue - tValue;
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "CMPU", "1001 sss 01 xxxx ttt", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        final int sValue = registerFile.getRegister( getSReg( word ) );
        final int tValue = registerFile.getRegister( getTReg( word ) );
        final int nzp = sValue - tValue;
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "CMPi", "1001 sss 10 iiiiiii", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        final int sValue = registerFile.getRegister( getSReg( word  ) );
        final int iValue = getSignedImmed( word );
        final int nzp = sValue - iValue;
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );

    ISA.createDef( "CMPUi", "1001 sss 11 uuuuuuu", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        final int sValue = registerFile.getRegister( getSReg( word  ) );
        final int uValue = getUnsignedImmed( word );
        final int nzp = sValue - uValue;
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );


    ISA.createDef( "RET",   "1100 000 111 000000", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        return registerFile.getRegister( 7 );
      }
    } );

    ISA.createDef( "JMP", "1100 000 ddd 000000", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        return registerFile.getRegister( this.getDReg( word ) );
      }
    } );

    ISA.createDef( "RTT", "1100 000 111 000001", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        //System.out.println( "RTT: popping privilege bit at pc = " + n );
        registerFile.setPrivMode( registerFile.popPrivMode() );
        return registerFile.getRegister( 7 );
      }
    } );

    ISA.createDef( "JMPT", "1100 000 ddd 000010", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        registerFile.setPrivMode( false );
        return registerFile.getRegister( this.getDReg( word ) );
      }
    } );

    ISA.createDef( "JSR", "0100 1 ppppppppppp", new InstructionDef()
    {
      public boolean isCall()
      {
        return true;
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        registerFile.setRegister( 7, n + 1 );
        return n + 1 + this.getPCOffset( word );
      }
    } );
    ISA.createDef( "JSRR", "0100 000 ddd 000000", new InstructionDef()
    {
      public boolean isCall()
      {
        return true;
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int register = registerFile.getRegister( this.getDReg( word ) );
        registerFile.setRegister( 7, n + 1 );
        return register;
      }
    } );
    ISA.createDef( "LD", "0010 ddd 0 pppppppp", new InstructionDef()
    {
      public boolean isLoad()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return n + 1 + this.getPCOffset( word );
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        final int value = memory.checkAndRead( n + 1 + this.getPCOffset( word ) ).getValue();
        registerFile.setRegister( this.getDReg( word ), value );
        registerFile.setNZP( value );
        return n + 1;
      }
    } );

    // Load a register with an immediate value
    ISA.createDef( "LC", "1010 ddd iiiiiiiii", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        final int value = getSignedImmed( word );
        registerFile.setRegister( this.getDReg( word ), value );
        registerFile.setNZP( value );
        return n + 1;
      }
    } );

    ISA.createDef( "LDI", "0010 ddd 1 pppppppp", new InstructionDef()
    {
      public boolean isLoad()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return memory.checkAndRead( n + 1 + this.getPCOffset( word ) ).getValue();
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        final int value = memory.checkAndRead( memory.checkAndRead( n + 1 + this.getPCOffset( word ) ).getValue() ).getValue();
        registerFile.setRegister( this.getDReg( word ), value );
        registerFile.setNZP( value );
        return n + 1;
      }
    } );
    ISA.createDef( "LDR", "0110 ddd sss iiiiii", new InstructionDef()
    {
      public boolean isLoad()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return registerFile.getRegister( this.getSReg( word ) ) + this.getSignedImmed( word );
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        final int value = memory.checkAndRead( registerFile.getRegister( this.getSReg( word ) ) + this.getSignedImmed( word ) ).getValue();
        registerFile.setRegister( this.getDReg( word ), value );
        registerFile.setNZP( value );
        return n + 1;
      }
    } );
    ISA.createDef( "LEA", "1110 ddd ppppppppp", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        registerFile.setRegister( this.getDReg( word ), n + 1 + this.getPCOffset( word ) );
        registerFile.setNZP( n + 1 + this.getPCOffset( word ) );
        return n + 1;
      }
    } );
    ISA.createDef( "ST", "0011 ddd 0 pppppppp", new InstructionDef()
    {
      public boolean isStore()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return n + 1 + this.getPCOffset( word );
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        memory.checkAndWrite( n + 1 + this.getPCOffset( word ), registerFile.getRegister( this.getDReg( word ) ) );
        return n + 1;
      }
    } );

    ISA.createDef( "STI", "0011 ddd 1 pppppppp", new InstructionDef()
    {
      public boolean isStore()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return memory.checkAndRead( n + 1 + this.getPCOffset( word ) ).getValue();
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        memory.checkAndWrite( memory.checkAndRead( n + 1 + this.getPCOffset( word ) ).getValue(), registerFile.getRegister( this.getDReg( word ) ) );
        return n + 1;
      }
    } );

    ISA.createDef( "STR", "0111 ddd sss iiiiii", new InstructionDef()
    {
      public boolean isStore()
      {
        return true;
      }

      public int getRefAddr( final Word word, final int n, final RegisterFile registerFile, final Memory memory ) throws IllegalMemAccessException
      {
        return registerFile.getRegister( this.getSReg( word ) ) + this.getSignedImmed( word );
      }

      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException
      {
        memory.checkAndWrite( registerFile.getRegister( this.getSReg( word ) ) + this.getSignedImmed( word ), registerFile.getRegister( this.getDReg( word ) ) );
        return n + 1;
      }
    } );

    // Implement shifts:
    // Shift Left
    //     Logical Shift left with bit count in a register
    ISA.createDef( "SHFl", "1101 ddd sss 0 0 0 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int TReg = getTReg( word );
        System.out.println( "TReg = " + TReg );
        int bitsToShift = registerFile.getRegister( getTReg( word ) );

        // Constrain this to a maximum value of 15.
        bitsToShift = Math.min( Math.abs( bitsToShift ), 16 );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the left-most bit is zero or one.
          int cValue = (sourceValue & 0x8000) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        int shiftedValue = ( sourceValue << bitsToShift ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }

    } );

    //     Logical Shift left with an immediate bit count
    ISA.createDef( "SHFli", "1101 ddd sss 1 0 0 uuu", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int bitsToShift = getUnsignedImmed( word );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the left-most bit is zero or one.
          int cValue = (sourceValue & 0x8000) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        int shiftedValue = ( sourceValue << bitsToShift ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }
    } );

    // Right Shifts
    //     Logical Shift right with bit count in a register
    ISA.createDef( "SHFr", "1101 ddd sss 0 1 0 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int bitsToShift = registerFile.getRegister( getTReg( word ) );

        // Constrain this to a maximum value of 15.
        bitsToShift = Math.min( Math.abs( bitsToShift ), 16 );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the right-most bit is zero or one.
          int cValue = (sourceValue & 0x0001) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        int shiftedValue = ( sourceValue >>> bitsToShift ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }

    } );

    //     Logical Shift right with an immediate bit count
    ISA.createDef( "SHFri", "1101 ddd sss 1 1 0 uuu", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int bitsToShift = getUnsignedImmed( word );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the right-most bit is zero or one.
          int cValue = (sourceValue & 0x0001) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        int shiftedValue = ( sourceValue >>> bitsToShift ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }
    } );

    //     Arithmetic Shift right with bit count in a register
    ISA.createDef( "SHFar", "1101 ddd sss 0 1 1 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int bitsToShift = registerFile.getRegister( getTReg( word ) );

        // Constrain this to a maximum value of 15.
        bitsToShift = Math.min( Math.abs( bitsToShift ), 16 );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the right-most bit is zero or one.
          int cValue = (sourceValue & 0x0001) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        //   First shift the value 16 bits to the left so that the sign bit is set.
        int intermediateValue = sourceValue << 16;

        //   Now shift the value to the right. This will cause the sign bit to be replicated.
        int shiftedValue = ( ( intermediateValue >> bitsToShift ) >>> 16 ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }

    } );

    //     Logical Shift right with an immediate bit count
    ISA.createDef( "SHFari", "1101 ddd sss 1 1 1 uuu", new InstructionDef()
    {
      public int execute( final Word word, final int pc, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        // Get the number of bits to shift
        int bitsToShift = getUnsignedImmed( word );

        // Sanity check: if the number of bits to shift is zero, we are already done.
        if( bitsToShift == 0 )
        {
          return pc + 1;
        }

        // Get the source value
        int sourceValue = registerFile.getRegister( getSReg( word ) );

        // Shift it as appropriate
        if( bitsToShift == 1 )
        {
          // Check if the right-most bit is zero or one.
          int cValue = (sourceValue & 0x0001) == 0 ? 0x0000 : 0x0001;

          // Set the C bit
          registerFile.setC( cValue );
        }

        // Compute the shifted value.
        //   First shift the value 16 bits to the left so that the sign bit is set.
        int intermediateValue = sourceValue << 16;

        //   Now shift the value to the right. This will cause the sign bit to be replicated.
        int shiftedValue = ( ( intermediateValue >> bitsToShift ) >>> 16 ) & 0xFFFF;

        // Set the NZ and P values.
        registerFile.setNZP( shiftedValue );

        // Store away the shifted value.
        registerFile.setRegister( getDReg( word ), shiftedValue );

        // That's it.
        return pc + 1;
      }
    } );

    ISA.createDef( "MOD", "1101 ddd sss 001 ttt", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        // Get the value in the t register
        final int tValue = registerFile.getRegister( getTReg( word ) );

        // Get the value in the source register
        final int sValue = registerFile.getRegister( getSReg( word ) );

        // Compute the result and ...
        final int result = sValue % tValue;

        // ... store it away
        registerFile.setRegister( getDReg( word ), result );

        // Update the condition registers
        registerFile.setNZP( result );

        // That's it
        return n + 1;
      }
    } );

    ISA.createDef( "MODi", "1101 ddd sss 101 uuu", new InstructionDef()
    {
      @Override
      public int execute( Word word, int n, RegisterFile registerFile, Memory memory, Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
      {
        // Get the immediate value
        final int uValue = getUnsignedImmed( word );

        // Get the value in the source register
        final int sValue = registerFile.getRegister( getSReg( word ) );

        // Compute the result and ...
        final int result = sValue % uValue;

        // ... store it away
        registerFile.setRegister( getDReg( word ), result );

        // Update the condition registers
        registerFile.setNZP( result );

        // That's it
        return n + 1;
      }
    } );

    ISA.createDef( "RTI", "1000 000000000000", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalInstructionException
      {
        if( registerFile.getPrivMode() )
        {
          final int register = registerFile.getRegister( 6 );
          final int value = memory.read( register ).getValue();
          registerFile.setRegister( 6, register + 1 );
          registerFile.setPSR( memory.read( register ).getValue() );
          return value;
        }
        throw new IllegalInstructionException( "RTI can only be executed in privileged mode" );
      }
    } );
    ISA.createDef( "GETC",          "1111 0000 00100000", new TrapDef() );
    ISA.createDef( "OUT",           "1111 0000 00100001", new TrapDef() );
    ISA.createDef( "PUTS",          "1111 0000 00100010", new TrapDef() );
    ISA.createDef( "IN",            "1111 0000 00100011", new TrapDef() );
    ISA.createDef( "PUTSP",         "1111 0000 00100100", new TrapDef() );
    ISA.createDef( "HALT",          "1111 0000 00100101", new TrapDef() );
    ISA.createDef( "CLEAR_CONSOLE", "1111 0000 00100110", new TrapDef() );
    ISA.createDef( "DRAW_RECT",     "1111 0000 00110000", new TrapDef() );
    ISA.createDef( "DRAW_LINE",     "1111 0000 00110001", new TrapDef() );
    ISA.createDef( "TRAP",          "1111 0000 uuuuuuuu", new TrapDef() );

  }

  private class BranchDef extends InstructionDef
  {
    public boolean isBranch()
    {
      return true;
    }

    public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
    {
      if( (word.getBit( 11 ) == 1 && registerFile.getN()) || (word.getBit( 10 ) == 1 && registerFile.getZ()) || (word.getBit( 9 ) == 1 && registerFile.getP()) )
      {
        return n + 1 + this.getPCOffset( word );
      }
      return n + 1;
    }
  }

  private class TrapDef extends InstructionDef
  {
    public boolean isCall()
    {
      return true;
    }

    public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine ) throws IllegalMemAccessException, IllegalInstructionException
    {
      registerFile.pushPrivMode();
      registerFile.setPrivMode( true );
      registerFile.setRegister( 7, n + 1 );
      //System.out.println( "Pushed privilege mode for " + word.getZext( 8, 0 ) );
      return memory.read( word.getZext( 8, 0 ) ).getValue();
    }
  }
}
