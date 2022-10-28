package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.harrierattack.CollisionNames;

public class Shell extends Projectile
{
  public Shell(GamePanel gamePanel, float x, float y, float velocityX, double angle)
  {
    super(gamePanel, x, y, velocityX, angle, 6, "Shell");
    setCollisionBit(CollisionNames.Jet, true);
    setCollisionBit(CollisionNames.EnemySoldier, true);
    setControl(new ProjectileControl(this, 50, true));
  }
}
