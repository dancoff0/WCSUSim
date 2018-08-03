package edu.wcsu.WCSUSim.Display;

import edu.wcsu.WCSUSim.WCSUSim;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

// 
// Decompiled by Procyon v0.5.30
// 

public abstract class TableModel extends AbstractTableModel
{
  /**
   * Generated SerialID
   */
  private static final long serialVersionUID = -127502035291381175L;

  public void fireTableCellUpdated( final int n, final int n2 )
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableCellUpdated( n, n2 );
    }
  }

  public void fireTableChanged( final TableModelEvent tableModelEvent )
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableChanged( tableModelEvent );
    }
  }

  public void fireTableDataChanged()
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableDataChanged();
    }
  }

  public void fireTableRowsUpdated( final int n, final int n2 )
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableRowsUpdated( n, n2 );
    }
  }

  public void fireTableRowsInserted( final int n, final int n2 )
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableRowsInserted( n, n2 );
    }
  }

  public void fireTableRowsDeleted( final int n, final int n2 )
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableRowsDeleted( n, n2 );
    }
  }

  public void fireTableStructureChanged()
  {
    if( WCSUSim.GRAPHICAL_MODE )
    {
      super.fireTableStructureChanged();
    }
  }
}
