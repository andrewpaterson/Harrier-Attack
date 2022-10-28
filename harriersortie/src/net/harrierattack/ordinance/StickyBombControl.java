package net.harrierattack.ordinance;

import net.engine.Control;

public class StickyBombControl extends Control
{
  private StickyBomb stickyBomb;
  private int timer;
  private int flashTick;

  public StickyBombControl(StickyBomb stickyBomb, int timer)
  {
    super();
    this.stickyBomb = stickyBomb;
    this.timer = timer;
    flashTick = 0;
  }

  public void control()
  {
    timer--;
    if (timer == 0)
    {
      stickyBomb.explode();
    }

    if (flashTick >= (timer / 10 + 1))
    {
      stickyBomb.celFrame = 1 - stickyBomb.celFrame;
      flashTick = 0;
    }
    flashTick++;
  }
}
