package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.math.Float2;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.UberSprite;
import net.harrierattack.explosion.ExplosionEmitter;
import net.harrierattack.explosion.Shockwave;

public class FreefallBomb extends UberSprite
{
  public FreefallBomb(GamePanel gamePanel, float x, float y, Float2 velocity)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Freefall Bomb"));
    addBoundingBoxes(0);
    setCollisionBit(CollisionNames.Jet, true);
    setCollisionBit(CollisionNames.Flakgun, true);
    setLayer(17);
    setName("Freefall Bomb");
    this.velocity = new Float2(velocity);
    setControl(new FreefallBombControl(this));
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
      explode();
    }
  }

  public void explode()
  {
    HarrierAttackPanel harrierAttackPanel = (HarrierAttackPanel) gamePanel;
    new Shockwave(harrierAttackPanel, getPosition().x, getPosition().y, 35);
    new ExplosionEmitter(harrierAttackPanel, getPosition().x, getPosition().y, 20, 35);
    remove();
  }

  public boolean isOnGround()
  {
    return onGround;
  }
}
