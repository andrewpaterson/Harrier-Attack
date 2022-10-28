package net.harrierattack.soldier;

import net.engine.GamePanel;

public class GreenSoldier extends Soldier
{
  public GreenSoldier(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
  }

  protected String getGraphicName()
  {
    return "Green Soldier";
  }

}
