package net.harrierattack.explosion;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;

public class Explosion extends Sprite
{
  public Explosion(GamePanel gamePanel, float x, float y, String explosion, int rate)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get(explosion));
    setControl(new ExplosionControl(this, rate));
    setLayer(7);
  }
}
