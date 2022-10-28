package net.harrierattack.jeep;

import net.engine.math.Angle;
import net.harrierattack.ordinance.Shell;
import net.harrierattack.soldier.Comrade;

import java.awt.event.KeyEvent;

public class PlayerJeepControl extends JeepControl
{
  public static double maxDownDegrees = 100;
  public static double maxUpDegrees = 10;

  public double fireAngle;
  public boolean firing;
  public int fireTick;
  public int muzzleFlash;

  public PlayerJeepControl(Jeep jeep)
  {
    super(jeep);
    gamePanel.setPlayer(jeep);
    fireAngle = Angle.LEFT;
    fireTick = 0;
  }

  public boolean isIdle()
  {
    return false;
  }

  public void control()
  {
    camera();
    horizontalControl();
    gunControl();
    fireControl();
    order();

    if (!jeep.isTurning() && (jeep.hasGunner()))
    {
      jeep.celFrame = jeep.celFrame + muzzleFlash * 10;
    }
    if (muzzleFlash > 0)
    {
      muzzleFlash++;
      if (muzzleFlash == 4)
      {
        muzzleFlash = 0;
      }
    }

    super.control();
    if (!holdingGetOut)
    {
      if (gamePanel.keyDown(KeyEvent.VK_SHIFT))
      {
        jeep.getOut();
      }
    }
  }

  private void fireControl()
  {
    if (fireTick > 0)
    {
      fireTick--;
    }
  }

  protected void order()
  {
    super.order();
    if (gamePanel.keyDown(KeyEvent.VK_E))
    {
      Comrade comrade = jeep.gunnerGetOut();
      if (comrade != null)
      {
        comrade.getComradeControl().orderWait();
      }
    }
  }

  private void gunControl()
  {
    if (jeep.isTurning())
    {
      return;
    }

    aimControl();

    if (jeep.hasGunner())
    {
      if (gamePanel.keyDown(KeyEvent.VK_CONTROL))
      {
        firing = true;
        fireBullet(fireAngle);
      } else
      {
        firing = false;
      }
    }

    if (jeep.facingLeft())
    {
      if (jeep.hasGunner())
      {
        jeep.celFrame = Jeep.gunnerAimDownLeft + 7 - (int) ((-Angle.radToDeg(fireAngle)) * 7 / maxDownDegrees);
        jeep.linkFrame = 0;
      } else
      {
        jeep.celFrame = Jeep.driverIdleFrameLeft;
        jeep.linkFrame = 0;
      }
    } else if (jeep.facingRight())
    {
      if (jeep.hasGunner())
      {
        jeep.celFrame = Jeep.gunnerAimDownRight + 7 - (int) ((Angle.radToDeg(fireAngle)) * 7 / maxDownDegrees);
        jeep.linkFrame = 1;
      } else
      {
        jeep.celFrame = Jeep.driverIdleFrameRight;
        jeep.linkFrame = 1;
      }
    }
  }

  private void aimControl()
  {
    if (jeep.hasGunner())
    {
      boolean up = gamePanel.keyDown(KeyEvent.VK_UP);
      boolean down = gamePanel.keyDown(KeyEvent.VK_DOWN);
      double turnDegrees = 5;
      if (up)
      {
        if (jeep.facingLeft())
        {
          fireAngle += Angle.degToRad(turnDegrees);
          if (fireAngle > Angle.degToRad(-maxUpDegrees))
          {
            fireAngle = Angle.degToRad(-maxUpDegrees);
          }
        }
        if (jeep.facingRight())
        {
          fireAngle -= Angle.degToRad(turnDegrees);
          if (fireAngle < Angle.degToRad(maxUpDegrees))
          {
            fireAngle = Angle.degToRad(maxUpDegrees);
          }
        }
      }
      if (down)
      {
        if (jeep.facingLeft())
        {
          fireAngle -= Angle.degToRad(turnDegrees);
          if (fireAngle < Angle.degToRad(-maxDownDegrees))
          {
            fireAngle = Angle.degToRad(-maxDownDegrees);
          }
        }
        if (jeep.facingRight())
        {
          fireAngle += Angle.degToRad(turnDegrees);
          if (fireAngle > Angle.degToRad(maxDownDegrees))
          {
            fireAngle = Angle.degToRad(maxDownDegrees);
          }
        }
      }
    } else
    {
      if (jeep.facingLeft())
      {
        fireAngle = Angle.LEFT;
      } else if (jeep.facingRight())
      {
        fireAngle = Angle.RIGHT;
      }
    }
  }

  private void fireBullet(double fireAngle)
  {
    if (fireTick == 0)
    {
      float offset = 0;
      if (jeep.facingLeft())
      {
        offset = 5;
      } else if (jeep.facingRight())
      {
        offset = -5;
      }
      fireAngle += Angle.degToRad((gamePanel.getRandom().nextInt(101) - 50) / 20);
      new Shell(gamePanel, jeep.getPosition().x + offset, jeep.getPosition().y - 7, jeep.perceivedVelocity.x, fireAngle);
      muzzleFlash = 1;
      fireTick = 7;
    }
  }

  private void horizontalControl()
  {
    if (jeep.isTurning())
    {
      return;
    }

    boolean leftOrRightPressed = false;
    if (gamePanel.keyDown(KeyEvent.VK_RIGHT))
    {
      if (jeep.facingLeft() && jeep.isParked())
      {
        fireAngle = -fireAngle;
        jeep.turnRight();
        return;
      }

      leftOrRightPressed = true;
      if (jeep.velocity.x < maxSpeedX)
      {
        jeep.velocity.x += acceleration;
      }
    }

    if (gamePanel.keyDown(KeyEvent.VK_LEFT))
    {
      if (jeep.facingRight() && jeep.isParked())
      {
        fireAngle = -fireAngle;
        jeep.turnLeft();
        return;
      }

      leftOrRightPressed = true;
      if (jeep.velocity.x > -maxSpeedX)
      {
        jeep.velocity.x -= acceleration;
      }
    }

    if (!leftOrRightPressed)
    {
      slowDown();
    }
  }
}


