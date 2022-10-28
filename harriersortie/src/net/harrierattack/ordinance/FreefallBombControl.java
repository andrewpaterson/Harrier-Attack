package net.harrierattack.ordinance;

import net.engine.Control;
import net.engine.graphics.Sprite;
import net.harrierattack.UberSprite;

public class FreefallBombControl extends Control
{
  private FreefallBomb freefallBomb;

  public FreefallBombControl(FreefallBomb freefallBomb)
  {
    super();
    this.freefallBomb = freefallBomb;
  }

  public void control()
  {
    freefallBomb.velocity.y += UberSprite.gravity;
    freefallBomb.velocity.x *= 0.98f;
  }

  public void startCollision(Sprite sprite)
  {
    freefallBomb.explode();
  }
}
