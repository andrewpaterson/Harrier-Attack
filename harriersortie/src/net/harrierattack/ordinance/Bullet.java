package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.harrierattack.CollisionNames;

public class Bullet extends Projectile
{
  public Bullet(GamePanel gamePanel, float x, float y, float velocityX, double angle)
  {
    super(gamePanel, x, y, velocityX, angle, 5, "Bullet");
    setCollisionBit(CollisionNames.EnemySoldier, true);
    setControl(new ProjectileControl(this, 50, false));
  }
}
