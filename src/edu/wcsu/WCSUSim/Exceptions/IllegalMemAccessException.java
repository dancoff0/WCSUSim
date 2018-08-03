package edu.wcsu.WCSUSim.Exceptions;//
// Decompiled by Procyon v0.5.30
// 

import edu.wcsu.WCSUSim.Exceptions.ExceptionException;
import edu.wcsu.WCSUSim.Machine.Word;

public class IllegalMemAccessException extends ExceptionException
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -416455180109841384L;
  private int addr;

  public IllegalMemAccessException( final int addr )
  {
    this.addr = addr;
  }

  public String getExceptionDescription()
  {
    return "edu.wcsu.edu.wcsu.WCSUSim.WCSUSim.Exceptions.IllegalMemAccessException accessing address " + Word.toHex( this.addr ) + "\n" + "(The MPR and PSR do not permit access to this address)";
  }
}
