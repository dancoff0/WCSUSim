// 
// Decompiled by Procyon v0.5.30
// 

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
    return "IllegalMemAccessException accessing address " + Word.toHex( this.addr ) + "\n" + "(The MPR and PSR do not permit access to this address)";
  }
}
