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
    ISA.createDef( "AND", "0101 ddd sss 0 00 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) & registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );
    ISA.createDef( "AND", "0101 ddd sss 1 iiiii", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) & this.getSignedImmed( word );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );
    ISA.createDef( "BR", "0000 111 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnzp", "0000 111 ppppppppp", new BranchDef() );
    ISA.createDef( "BRp", "0000 001 ppppppppp", new BranchDef() );
    ISA.createDef( "BRz", "0000 010 ppppppppp", new BranchDef() );
    ISA.createDef( "BRzp", "0000 011 ppppppppp", new BranchDef() );
    ISA.createDef( "BRn", "0000 100 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnp", "0000 101 ppppppppp", new BranchDef() );
    ISA.createDef( "BRnz", "0000 110 ppppppppp", new BranchDef() );
    ISA.createDef( "RET", "1100 000 111 000000", new InstructionDef()
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
        registerFile.setPrivMode( false );
        return registerFile.getRegister( 7 );
      }
    } );
    ISA.createDef( "JMPT", "1100 000 ddd 000001", new InstructionDef()
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
    ISA.createDef( "LD", "0010 ddd ppppppppp", new InstructionDef()
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
    ISA.createDef( "LDI", "1010 ddd ppppppppp", new InstructionDef()
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
    ISA.createDef( "ST", "0011 ddd ppppppppp", new InstructionDef()
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
    ISA.createDef( "STI", "1011 ddd ppppppppp", new InstructionDef()
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
    ISA.createDef( "NOT", "1001 ddd sss 111111", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = ~registerFile.getRegister( this.getSReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );
    ISA.createDef( "MUL", "1101 ddd sss 0 00 ttt", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) * registerFile.getRegister( this.getTReg( word ) );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
        return n + 1;
      }
    } );
    ISA.createDef( "MUL", "1101 ddd sss 1 iiiii", new InstructionDef()
    {
      public int execute( final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine )
      {
        final int nzp = registerFile.getRegister( this.getSReg( word ) ) * this.getSignedImmed( word );
        registerFile.setRegister( this.getDReg( word ), nzp );
        registerFile.setNZP( nzp );
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
    ISA.createDef( "GETC", "1111 0000 00100000", new TrapDef() );
    ISA.createDef( "OUT", "1111 0000 00100001", new TrapDef() );
    ISA.createDef( "PUTS", "1111 0000 00100010", new TrapDef() );
    ISA.createDef( "IN", "1111 0000 00100011", new TrapDef() );
    ISA.createDef( "PUTSP", "1111 0000 00100100", new TrapDef() );
    ISA.createDef( "HALT", "1111 0000 00100101", new TrapDef() );
    ISA.createDef( "TRAP", "1111 0000 uuuuuuuu", new TrapDef() );
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
      registerFile.setPrivMode( true );
      registerFile.setRegister( 7, n + 1 );
      return memory.read( word.getZext( 8, 0 ) ).getValue();
    }
  }
}
