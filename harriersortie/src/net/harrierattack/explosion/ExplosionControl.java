package net.harrierattack.explosion;

import net.engine.Control;

/**
 * Created by IntelliJ IDEA.
 * User: Dragon_
 * Date: May 10, 2008
 * Time: 9:08:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExplosionControl extends Control
{
  private Explosion explosion;
  private int rate;
  private int frameTick;

  public ExplosionControl(Explosion explosion, int rate)
  {
    super();
    this.explosion = explosion;
    this.rate = rate;
  }

  public void control()
  {
    frameTick++;
    if (frameTick > rate)
    {
      if (explosion.celFrame == explosion.cels.size() - 1)
      {
        explosion.remove();
      } else
      {
        explosion.celFrame++;
      }
      frameTick = 0;
    }
  }
}
