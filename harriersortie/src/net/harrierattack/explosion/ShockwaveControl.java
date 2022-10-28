package net.harrierattack.explosion;

import net.engine.Control;

public class ShockwaveControl extends Control
{
  private Shockwave sprite;

  public ShockwaveControl(Shockwave shockwave)
  {
    this.sprite = shockwave;
  }

  public void control()
  {
    sprite.remove();  //<- Yes really
  }
}
