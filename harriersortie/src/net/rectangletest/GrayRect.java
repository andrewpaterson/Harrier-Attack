package net.rectangletest;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;

public class GrayRect extends Sprite
{
  public GrayRect(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    setName("Gray");
    addCels(CelStore.getInstance().get("GRAY 40x40"));
    addBoundingBoxes(0);
    setCollisionBit(0, true);
    setLayer(3);
  }
}
