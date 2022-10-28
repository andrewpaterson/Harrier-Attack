package net.harrierattack.soldier;

import net.engine.GamePanel;

public class SargeSoldier extends Soldier
{
  public SargeSoldier(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
  }

  protected String getGraphicName()
  {
    return "Sarge";
  }
}
