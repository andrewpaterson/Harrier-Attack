package net.harrierattack.soldier;

import net.engine.GamePanel;
import net.harrierattack.CollisionNames;

public class BlueSoldier extends Soldier
{
  public BlueSoldier(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    setLayer(17);
    setCollisionBit(CollisionNames.EnemySoldier, true);

    setControl(new EnemySoldierControl(this));
    maxSpeed = 1.2f;
  }

  protected String getGraphicName()
  {
    return "Blue Soldier";
  }
}
