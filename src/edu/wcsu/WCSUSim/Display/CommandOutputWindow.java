package edu.wcsu.WCSUSim.Display;

import edu.wcsu.WCSUSim.Machine.ErrorLog;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.30
// 

public class CommandOutputWindow extends JFrame implements PrintableConsole
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -7670766227618408035L;
  private JTextArea textArea;

  public CommandOutputWindow( final String s )
  {
    super( s );
    (this.textArea = new JTextArea()).setEditable( false );
    this.textArea.setLineWrap( true );
    this.textArea.setWrapStyleWord( true );
    this.getContentPane().add( new JScrollPane( this.textArea, 22, 30 ) );
  }

  public void print( final String s )
  {
    this.textArea.append( s );
  }

  public void clear()
  {
    final Document document = this.textArea.getDocument();
    try
    {
      document.remove( 0, document.getLength() );
    }
    catch( BadLocationException ex )
    {
      ErrorLog.logError( ex );
    }
  }
}
