package edu.wcsu.WCSUSim.SymbolTable;

import java.util.Enumeration;
import java.util.Hashtable;

// 
// Decompiled by Procyon v0.5.30
// 

public class SymTab
{
  Hashtable<String, Integer> table;

  public SymTab()
  {
    this.table = new Hashtable<String, Integer>();
  }

  public boolean insert( final String s, final int n )
  {
    if( this.lookup( s ) != -1 )
    {
      return false;
    }
    this.table.put( s, new Integer( n ) );
    return true;
  }

  public int lookup( final String s )
  {
    final Integer n = this.table.get( s );
    if( n == null )
    {
      return -1;
    }
    return n;
  }

  public Enumeration<String> get_labels()
  {
    return this.table.keys();
  }
}
