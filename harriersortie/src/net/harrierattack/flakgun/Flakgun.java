package net.harrierattack.flakgun;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.ExplosionEmitter;

public class Flakgun extends Sprite
{
  public Flakgun(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Flakgun"));
    setName("Flakgun");
    addBoundingBoxes(8);
    setCollisionBit(CollisionNames.Flakgun, true);
    setControl(new EnemyFlakgunControl(this));
    setLayer(24);
  }

  public void destroy()
  {
    setControl(new DestoyedFlakgunControl(this));
  }

  public void explode()
  {
    new ExplosionEmitter((HarrierAttackPanel) gamePanel, getPosition().x, getPosition().y, 30, 40);
    remove();
  }
}
