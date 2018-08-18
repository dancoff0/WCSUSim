package edu.wcsu.WCSUSim;//
// Decompiled by Procyon v0.5.30
// 

import edu.wcsu.WCSUSim.Display.GUI;

public class TempRun implements Runnable
{
  GUI gui;

  public TempRun( final GUI gui )
  {
    this.gui = gui;
  }

  public void run()
  {
    // Set up the GUI
    gui.setUpGUI();

    // Now initialize any necessary components.
    gui.initialize();
  }
}
