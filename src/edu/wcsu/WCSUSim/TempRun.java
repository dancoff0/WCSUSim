package edu.wcsu.WCSUSim;//
// Decompiled by Procyon v0.5.30
// 

import edu.wcsu.WCSUSim.Display.GUI;

public class TempRun implements Runnable
{
  GUI ms;

  public TempRun( final GUI ms )
  {
    this.ms = ms;
  }

  public void run()
  {
    this.ms.setUpGUI();
  }
}
