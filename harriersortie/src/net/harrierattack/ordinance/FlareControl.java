package net.harrierattack.ordinance;

import net.engine.Control;
import net.harrierattack.UberSprite;

public class FlareControl extends Control
{
  private Flare flare;
  private int life;

  public FlareControl(Flare flare, int life)
  {
    super();
    this.flare = flare;
    this.life = life;
  }

  public void control()
  {
    if (life <= 0)
    {
      flare.remove();
      return;
    }

    life--;

    flare.velocity.y += (UberSprite.gravity * 0.3f);
    flare.velocity.x *= 0.98f;

    flare.velocity.x += (flare.gamePanel.getRandom().nextInt(11) - 5) / 50.0f;
    flare.velocity.y += (flare.gamePanel.getRandom().nextInt(11) - 5) / 50.0f;

    flare.celFrame = flare.gamePanel.getRandom().nextInt(3);
  }
}
