package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.UberSprite;
import net.harrierattack.explosion.ExplosionEmitter;
import net.harrierattack.explosion.Shockwave;

public class StickyBomb extends UberSprite
{
  public StickyBomb(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Sticky Bomb"));
    setLayer(17);
    setName("Sticky Bomb");
    this.velocity = new Float2(velocity);
    setControl(new StickyBombControl(this, 300));
  }

  protected void groundCollision()
  {
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) this.gamePanel;
    float groundlevel = gamePanel.terrain.getHeight((int) getPosition().x) - 5;
    if (getPosition().y >= groundlevel)
    {
      velocity.y = 0.0f;
      onGround = true;
      getPosition().y = groundlevel;
    } else
    {
      velocity.y += gravity;
      onGround = false;
    }
  }

  public void explode()
  {
    HarrierAttackPanel harrierAttackPanel = (HarrierAttackPanel) gamePanel;
    new Shockwave(harrierAttackPanel, getPosition().x, getPosition().y, 35);
    new ExplosionEmitter(harrierAttackPanel, getPosition().x, getPosition().y, 20, 35);
    remove();
  }
}
