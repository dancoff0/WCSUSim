package edu.wcsu.WCSUSim.Exceptions;//
// Decompiled by Procyon v0.5.30
// 

public class InternalException extends RuntimeException
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -5603384466514364128L;

  public InternalException( final String s )
  {
    super( "Internal Error: " + s );
  }
}
