package net.harrierattack.ordinance;

import net.engine.Control;
import net.engine.graphics.Sprite;

public class RocketControl extends Control
{
  public Rocket rocket;
  public int lifeTime;
  public float acceleration;

  public RocketControl(Rocket rocket, int lifeTime)
  {
    super();
    this.rocket = rocket;
    this.lifeTime = lifeTime;
    acceleration = 0.08f;
  }

  public void control()
  {
    lifeTime--;
    if (lifeTime <= 0)
    {
      rocket.remove();
    }

    if (rocket.velocity.y < 0)
    {
      rocket.velocity.y += acceleration;
    }
    if (rocket.velocity.y > 0)
    {
      rocket.velocity.y -= acceleration;
    }

    if (rocket.velocity.x < 0)
    {
      rocket.velocity.x -= acceleration;
    }
    if (rocket.velocity.x > 0)
    {
      rocket.velocity.x += acceleration;
    }
  }

  public void startCollision(Sprite sprite)
  {
    rocket.remove();
  }
}
