package edu.wcsu.WCSUSim.Exceptions;

import edu.wcsu.WCSUSim.Display.Console;

import javax.swing.JOptionPane;
import java.awt.Container;

// 
// Decompiled by Procyon v0.5.30
// 

public abstract class ExceptionException extends Exception
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = 8289091119629472219L;

  public ExceptionException()
  {
  }

  public ExceptionException( final String s )
  {
    super( s );
  }

  public String getExceptionDescription()
  {
    return "Generic Exception: " + this.getMessage();
  }

  public void showMessageDialog( final Container container )
  {
    JOptionPane.showMessageDialog( container, this.getExceptionDescription() );
    Console.println( "Exception: " + this.getExceptionDescription() );
  }
}
