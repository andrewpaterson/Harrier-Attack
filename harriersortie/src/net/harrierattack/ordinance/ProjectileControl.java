package net.harrierattack.ordinance;

import net.engine.Control;
import net.engine.graphics.Sprite;

public class ProjectileControl extends Control
{
  private Projectile projectile;
  private int lifeTime;
  private boolean explode;

  public ProjectileControl(Projectile projectile, int lifeTime, boolean explode)
  {
    super();
    this.projectile = projectile;
    this.lifeTime = lifeTime;
    this.explode = explode;
  }

  private void destroy()
  {
    if (explode)
    {
      projectile.explode();
    } else
    {
      projectile.remove();
    }
  }

  public void control()
  {
    lifeTime--;
    if (lifeTime <= 0)
    {
      projectile.remove();
    }

    if (projectile.isOnGround())
    {
      destroy();
    }
  }

  public void startCollision(Sprite sprite)
  {
    destroy();
  }
}
