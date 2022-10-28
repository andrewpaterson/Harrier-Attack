package net.harrierattack.jeep;

import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.player.PlayerControl;
import net.harrierattack.player.PlayerControllable;

public abstract class JeepControl extends PlayerControl
{
  public Jeep jeep;
  public float acceleration;
  public float maxSpeedX;

  public JeepControl(Jeep jeep)
  {
    this.jeep = jeep;
    this.gamePanel = (HarrierAttackPanel) getPlayerControllable().gamePanel;
    acceleration = 0.25f;
    maxSpeedX = 3.0f;
  }

  public PlayerControllable getPlayerControllable()
  {
    return jeep;
  }

  public void control()
  {
    jeep.collision();
    super.control();
  }

  public abstract boolean isIdle();

  protected void slowDown()
  {
    if (jeep.isOnGround())
    {
      if ((jeep.velocity.x < -acceleration) || (jeep.velocity.x > acceleration))
      {
        if (jeep.velocity.x > 0)
        {
          jeep.velocity.x -= acceleration;
        } else if (jeep.velocity.x < 0)
        {
          jeep.velocity.x += acceleration;
        }
      } else
      {
        jeep.velocity.x = 0;
      }
    }
  }
}
