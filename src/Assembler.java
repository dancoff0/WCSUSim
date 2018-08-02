import java.util.Enumeration;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.5.30
// 

class Assembler
{
  String as( final String[] array ) throws AsException
  {
    String s = null;
    final SymTab symTab = new SymTab();
    for( int i = 0; i < array.length; ++i )
    {
      if( array[i].length() == 0 )
      {
        throw new AsException( "Null arguments are not permitted." );
      }
      s = array[i];
    }
    if( s == null || s.length() == 0 )
    {
      throw new AsException( "No .asm file specified." );
    }
    final String base_filename = this.base_filename( s );
    final List<Instruction> pass_zero = this.pass_zero( this.parse( base_filename ) );
    this.pass_one( symTab, pass_zero );
    this.pass_two( symTab, pass_zero, base_filename );
    this.gen_sym( symTab, pass_zero, base_filename );
    return "";
  }

  String base_filename( final String s ) throws AsException
  {
    if( !s.endsWith( ".asm" ) )
    {
      throw new AsException( "Input file must have .asm suffix ('" + s + "')" );
    }
    return s.substring( 0, s.length() - 4 );
  }

  List<Instruction> parse( final String s ) throws AsException
  {
    final String string = s + ".asm";
    final ArrayList<Instruction> list = new ArrayList<Instruction>();
    int n = 1;
    try
    {
      final BufferedReader bufferedReader = new BufferedReader( new FileReader( string ) );
      String line;
      while( (line = bufferedReader.readLine()) != null )
      {
        final Instruction instruction = new Instruction( line, n++ );
        if( instruction.getOpcode() != null || instruction.getLabel() != null )
        {
          list.add( instruction );
        }
      }
      bufferedReader.close();
    }
    catch( IOException ex )
    {
      throw new AsException( "Couldn't read file (" + string + ")" );
    }
    return list;
  }

  private List<Instruction> pass_zero( final List<Instruction> list ) throws AsException
  {
    final ArrayList<Instruction> list2 = new ArrayList<Instruction>();
    final Iterator<Instruction> iterator = list.iterator();
    while( iterator.hasNext() )
    {
      iterator.next().splitLabels( list2 );
    }
    return list2;
  }

  void pass_one( final SymTab symTab, final List<Instruction> list ) throws AsException
  {
    int nextAddress = -1;
    for( final Instruction instruction : list )
    {
      if( instruction.getLabel() != null )
      {
        if( instruction.getLabel().length() > 20 )
        {
          instruction.error( "Labels can be no longer than 20 characters ('" + instruction.getLabel() + "')." );
        }
        if( nextAddress > 65535 )
        {
          instruction.error( "Label cannot be represented in 16 bits (" + nextAddress + ")" );
        }
        if( symTab.insert( instruction.getLabel(), nextAddress ) )
        {
          continue;
        }
        instruction.error( "Duplicate label ('" + instruction.getLabel() + "')" );
      }
      else
      {
        instruction.setAddress( nextAddress );
        final InstructionDef instructionDef = (InstructionDef) ISA.formatToDef.get( instruction.getFormat() );
        if( instructionDef == null )
        {
          throw new AsException( instruction, "Undefined opcode '" + instruction.getOpcode() + "'" );
        }
        nextAddress = instructionDef.getNextAddress( instruction );
      }
    }
  }

  void pass_two( final SymTab symTab, final List<Instruction> list, final String s ) throws AsException
  {
    final ArrayList<Word> list2 = new ArrayList<Word>();
    for( final Instruction instruction : list )
    {
      if( instruction.getLabel() != null )
      {
        continue;
      }
      if( instruction.getOpcode() == null )
      {
        Console.println( instruction.getOriginalLine() );
      }
      final InstructionDef instructionDef = (InstructionDef) ISA.formatToDef.get( instruction.getFormat() );
      if( instructionDef == null )
      {
        continue;
      }
      instructionDef.encode( symTab, instruction, list2 );
    }
    final String string = s + ".obj";
    try
    {
      final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream( new FileOutputStream( string ) );
      final Iterator<Word> iterator2 = list2.iterator();
      while( iterator2.hasNext() )
      {
        iterator2.next().writeWordToFile( bufferedOutputStream );
      }
      bufferedOutputStream.close();
    }
    catch( IOException ex )
    {
      throw new AsException( "Couldn't write file (" + string + ")" );
    }
  }

  void gen_sym( final SymTab symTab, final List<Instruction> list, final String s ) throws AsException
  {
    final String string = s + ".sym";
    final Enumeration<String> get_labels = symTab.get_labels();
    try
    {
      final BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( string ) );
      bufferedWriter.write( "// Symbol table\n" );
      bufferedWriter.write( "// Scope level 0:\n" );
      bufferedWriter.write( "//\tSymbol Name       Page Address\n" );
      bufferedWriter.write( "//\t----------------  ------------\n" );
      while( get_labels.hasMoreElements() )
      {
        final String s2 = get_labels.nextElement();
        bufferedWriter.write( "//\t" + s2 );
        for( int i = 0; i < 16 - s2.length(); ++i )
        {
          bufferedWriter.write( " " );
        }
        bufferedWriter.write( "  " + this.formatAddress( symTab.lookup( s2 ) ) + "\n" );
      }
      for( final Instruction instruction : list )
      {
        if( instruction.getOpcode() == null )
        {
          continue;
        }
        if( ((InstructionDef) ISA.formatToDef.get( instruction.getFormat() )).isDataDirective() )
        {
          continue;
        }
        bufferedWriter.write( "//\t$               " + this.formatAddress( instruction.getAddress() ) + "\n" );
      }
      bufferedWriter.newLine();
      bufferedWriter.close();
    }
    catch( IOException ex )
    {
      throw new AsException( "Couldn't write file (" + string + ")" );
    }
  }

  private String formatAddress( final int n )
  {
    final String string = "0000" + Integer.toHexString( n ).toUpperCase();
    return string.substring( string.length() - 4 );
  }
}
