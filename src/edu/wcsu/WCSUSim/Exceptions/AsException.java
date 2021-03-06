package edu.wcsu.WCSUSim.Exceptions;//
// Decompiled by Procyon v0.5.30
// 

import edu.wcsu.WCSUSim.Machine.Instruction;

public class AsException extends Exception
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -1299640770849672386L;
  public Instruction insn;

  public AsException( final Instruction insn, final String s )
  {
    super( s );
    this.insn = insn;
  }

  public AsException( final String s )
  {
    super( s );
  }

  public String getMessage()
  {
    String string = "Assembly error: ";
    if( this.insn != null )
    {
      string = string + "[line " + this.insn.getLineNumber() + ", '" + this.insn.getOriginalLine() + "']: ";
    }
    return string + super.getMessage();
  }
}
