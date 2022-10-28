package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.math.Float2;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.UberSprite;
import net.harrierattack.explosion.ExplosionEmitter;

public class Rocket extends UberSprite
{
  public Rocket(GamePanel gamePanel, float x, float y, Float2 parentVelocity, int lifeTime)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Rocket"));
    addBoundingBoxes(0);
    setCollisionBit(CollisionNames.Jet, true);
    setCollisionBit(CollisionNames.Flakgun, true);
    setLayer(17);
    setName("Rocket");
    velocity = (new Float2(parentVelocity)).divide(2.0f);
    float speed = (gamePanel.getRandom().nextInt(10) + 30) / 7.0f;
    if (velocity.x > 0)
    {
      celFrame = 3;
      velocity.x += speed;
    } else if (velocity.x < 0)
    {
      celFrame = 7;
      velocity.x -= speed;
    }
    collisionFrame = celFrame;
    velocity.y += (gamePanel.getRandom().nextInt(10) - 3) / 3.0f;
    setControl(new RocketControl(this, lifeTime));
  }

  protected void groundCollision()
  {
    HarrierAttackPanel harrierAttackPanel = (HarrierAttackPanel) gamePanel;
    int height = harrierAttackPanel.terrain.getHeight((int) getPosition().x);
    if (height <= getPosition().y)
    {
      onGround = true;
    } else
    {
      onGround = false;
    }

    if (isOnGround())
    {
      new ExplosionEmitter(harrierAttackPanel, getPosition().x, getPosition().y, 15, 15);
      remove();
    }
  }

  public boolean isOnGround()
  {
    return onGround;
  }
}
