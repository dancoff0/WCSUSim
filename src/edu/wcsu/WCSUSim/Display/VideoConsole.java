package edu.wcsu.WCSUSim.Display;

import edu.wcsu.WCSUSim.Machine.Machine;
import edu.wcsu.WCSUSim.Machine.Word;

import java.awt.image.BufferedImageOp;
import java.awt.Graphics;
import javax.swing.event.TableModelEvent;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.event.TableModelListener;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.30
// 
@SuppressWarnings( "unused" )
public class VideoConsole extends JPanel implements TableModelListener
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = 4923808058474302155L;
  private BufferedImage image;
  private static final int START = 49152;
  private static final int NROWS = 128;
  private static final int NCOLS = 124;
  private static final int END = 65024;
  private static final int SCALING = 2;
  private static final int WIDTH = 256;
  private static final int HEIGHT = 248;
  private Machine mac;

  public VideoConsole( final Machine mac )
  {
    final Dimension maximumSize = new Dimension( 256, 248 );
    this.setPreferredSize( maximumSize );
    this.setMinimumSize( maximumSize );
    this.setMaximumSize( maximumSize );
    this.mac = mac;
    this.image = new BufferedImage( 256, 248, 9 );
    final Graphics2D graphics = this.image.createGraphics();
    graphics.setColor( Color.black );
    graphics.fillRect( 0, 0, 256, 248 );
  }

  public void reset()
  {
    final Graphics2D graphics = this.image.createGraphics();
    graphics.setColor( Color.black );
    graphics.fillRect( 0, 0, 256, 248 );
    this.repaint();
  }

  public void tableChanged( final TableModelEvent tableModelEvent )
  {
    final int firstRow = tableModelEvent.getFirstRow();
    final int lastRow = tableModelEvent.getLastRow();
    if( firstRow == 0 && lastRow == 65535 )
    {
      this.reset();
    }
    else
    {
      if( firstRow < 49152 || firstRow > 65024 )
      {
        return;
      }
      final int n = 2;
      final int n2 = firstRow - 49152;
      final int n3 = n2 / 128 * n;
      final int n4 = n2 % 128 * n;
      final int convertToRGB = convertToRGB( this.mac.getMemory().read( firstRow ) );
      for( int i = 0; i < n; ++i )
      {
        for( int j = 0; j < n; ++j )
        {
          //System.out.println( "n4 + j = " + n4 + j + ", n3 + i = " + n3 + i );
          this.image.setRGB( n4 + j, n3 + i, convertToRGB );
        }
      }
      this.repaint( n4, n3, n, n );
    }
  }

  public void paintComponent( final Graphics graphics )
  {
    super.paintComponent( graphics );
    final Graphics2D graphics2D = (Graphics2D) graphics;
    if( this.image == null )
    {
      final int width = this.getWidth();
      final int height = this.getHeight();
      this.image = (BufferedImage) this.createImage( width, height );
      final Graphics2D graphics2 = this.image.createGraphics();
      graphics2.setColor( Color.white );
      graphics2.fillRect( 0, 0, width, height );
    }
    graphics2D.drawImage( this.image, null, 0, 0 );
  }

  private static int convertToRGB( final Word word )
  {
    return new Color( word.getZext( 14, 10 ) * 8, word.getZext( 9, 5 ) * 8, word.getZext( 4, 0 ) * 8 ).getRGB();
  }
}
