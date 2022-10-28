package net.rasteriser;

import net.engine.GameFrame;

import java.awt.*;

/**
 *
 */
public class Rasteriser extends GameFrame<RasteriserPanel>
{
  public Rasteriser() throws HeadlessException
  {
    super("Rasteriser");
  }

  public RasteriserPanel buildGamePanel()
  {
    return new RasteriserPanel();
  }

  public static void main(String[] args)
  {
    Rasteriser rasteriser = new Rasteriser();
    rasteriser.setMinimumSize(new Dimension(640, 480));
    rasteriser.setVisible(true);
    rasteriser.pack();
  }
}

