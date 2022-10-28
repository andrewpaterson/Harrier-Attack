package net.harrierattack.explosion;

import net.engine.GamePanel;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.engine.shape.Rectangle;
import net.harrierattack.CollisionNames;

public class Shockwave extends Sprite
{
  public Shockwave(GamePanel gamePanel, float x, float y, int radius)
  {
    super(gamePanel, x, y);
    setControl(new ShockwaveControl(this));
    setCollision(0, new Rectangle(new Float2(-radius, -radius), new Float2(radius, radius), getPosition()));
    setCollisionBit(CollisionNames.EnemySoldier, true);
    setCollisionBit(CollisionNames.Flakgun, true);
    setCollisionBit(CollisionNames.Jet, true);
  }
}
