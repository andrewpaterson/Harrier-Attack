package net.harrierattack.ordinance;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;

public class Flare extends Sprite
{
  public Flare(GamePanel gamePanel, float x, float y, Float2 velocity, int life)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Flare"));
    setLayer(17);
    setName("Flare");
    this.velocity = new Float2(velocity);
    this.velocity.x *= 0.9f;
    this.velocity.y *= 0.3f;
    setControl(new FlareControl(this, life));
  }
}

