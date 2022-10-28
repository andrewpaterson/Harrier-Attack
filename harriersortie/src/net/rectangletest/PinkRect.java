package net.rectangletest;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;

public class PinkRect extends Sprite
{
  public PinkRect(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    setName("pINK");
    addCels(CelStore.getInstance().get("PINK 10x10"));
    addBoundingBoxes(0);
    setCollisionBit(0, true);
    setLayer(3);
  }
}
