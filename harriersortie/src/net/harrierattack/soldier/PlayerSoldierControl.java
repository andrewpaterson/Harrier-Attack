package net.harrierattack.soldier;

import net.engine.math.Angle;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.ordinance.Bullet;
import net.harrierattack.ordinance.StickyBomb;
import net.harrierattack.player.PlayerControl;
import net.harrierattack.player.PlayerControllable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayerSoldierControl extends PlayerControl
{
  public Soldier soldier;
  public double fireAngle;
  public int fireTick;
  public int clip;
  public boolean firing;
  public boolean wasFiring;
  public int muzzleFlash;
  public int stickyHold;
  public boolean stickyReleased;
  public boolean prevSticky;
  public List<StickyBomb> stickyBombs;

  public PlayerSoldierControl(Soldier soldier)
  {
    this.soldier = soldier;
    fireAngle = Angle.LEFT;
    fireTick = 0;
    clip = 0;
    stickyHold = 0;
    stickyReleased = false;
    muzzleFlash = 0;
    firing = false;
    prevSticky = false;
    stickyBombs = new ArrayList<StickyBomb>();
    this.gamePanel = (HarrierAttackPanel) getPlayerControllable().gamePanel;
    gamePanel.setPlayer(soldier);
  }

  public PlayerControllable getPlayerControllable()
  {
    return soldier;
  }

  public void control()
  {
    camera();
    fireControl();
    if (!firing)
    {
      horizontalControl();
      enterControllableControl();
    }
    order();
    gunControl();
    super.control();
  }

  private void fireControl()
  {
    wasFiring = firing;

    if (gamePanel.keyDown(KeyEvent.VK_CONTROL))
    {
      firing = true;

      boolean right = gamePanel.keyDown(KeyEvent.VK_RIGHT);
      boolean left = gamePanel.keyDown(KeyEvent.VK_LEFT);
      boolean up = gamePanel.keyDown(KeyEvent.VK_UP);
      boolean down = gamePanel.keyDown(KeyEvent.VK_DOWN);
      if (right || left || up || down)
      {
        Float2 fireDirection = Angle.directionFrom(right, left, up, down);
        double angle = Angle.directionToAngle(fireDirection);
        fireAngle = Angle.rotateTowards(angle, fireAngle, Angle.degToRad(5.0));

        double maxDownAngle = Angle.degToRad(120);
        if (fireAngle > maxDownAngle)
        {
          fireAngle = maxDownAngle;
        }
        if (fireAngle < -maxDownAngle)
        {
          fireAngle = -maxDownAngle;
        }
        if (fireAngle == 0)
        {
          if (soldier.facingLeft())
          {
            fireAngle = Angle.degToRad(-1);
          } else if (soldier.facingRight())
          {
            fireAngle = Angle.degToRad(1);
          }
        }

        if (Angle.isLeftHalf(fireAngle) && soldier.facingRight())
        {
          soldier.turnAround();
        } else if (Angle.isRightHalf(fireAngle) && soldier.facingLeft())
        {
          soldier.turnAround();
        }
      }
      fireBullet(fireAngle);
      if (soldier.isOnGround())
      {
        soldier.velocity.x = 0;
      }
    } else
    {
      firing = false;
    }

    boolean sticky = gamePanel.keyDown(KeyEvent.VK_Z);
    stickyReleased = false;
    if (sticky)
    {
      if (!prevSticky)
      {
        stickyHold = 0;
      }
      stickyHold++;
    } else
    {
      if (prevSticky)
      {
        stickyReleased = true;
      }
    }
    prevSticky = sticky;

    if (stickyHold > 15)
    {
      if (stickyBombs.size() > 0)
      {
        for (StickyBomb stickyBomb : stickyBombs)
        {
          stickyBomb.explode();
        }
        stickyBombs = new ArrayList<StickyBomb>();
      }
    }

    if (stickyReleased)
    {
      if (stickyHold <= 15)
      {
        StickyBomb stickyBomb = new StickyBomb(gamePanel, soldier.getPosition().x, soldier.getPosition().y);
        stickyBombs.add(stickyBomb);
      }
    }
  }

  private void gunControl()
  {
    if (fireTick > 0)
    {
      fireTick--;
    } else if ((fireTick == 0) && (!firing))
    {
      clip = 0;
    }
  }

  private void enterControllableControl()
  {
    if (!holdingGetOut)
    {
      if (gamePanel.keyDown(KeyEvent.VK_SHIFT))
      {
        PlayerControllable controllable = gamePanel.getNearestControllable(soldier.getPosition());
        if (controllable != null)
        {
          soldier.remove();
          controllable.playerControl();
        }
      }
    }
  }

  private void horizontalControl()
  {
    if (wasFiring)
    {
      soldier.stand();
    }

    boolean leftOrRightPressed = false;
    if (gamePanel.keyDown(KeyEvent.VK_RIGHT))
    {
      leftOrRightPressed = true;
      soldier.walkRight();
      fireAngle = Angle.RIGHT;
    }

    if (gamePanel.keyDown(KeyEvent.VK_LEFT))
    {
      leftOrRightPressed = true;
      soldier.walkLeft();
      fireAngle = Angle.LEFT;
    }
    if (soldier.isOnGround())
    {
      if (gamePanel.keyDown(KeyEvent.VK_SPACE))
      {
        soldier.jump();
      }
    }
    if (!leftOrRightPressed)
    {
      soldier.velocity.x = 0;
      soldier.stand();
    }
  }

  private void fireBullet(double fireAngle)
  {
    int wantedCelFrame = getBaseFireCelFrame(fireAngle);
    if (fireTick == 0)
    {
      fireTick = 5;
      if (clip < 6)
      {
        muzzleFlash = 1;
        fireAngle += Angle.degToRad((gamePanel.getRandom().nextInt(101) - 50) / 20);
        new Bullet(gamePanel, soldier.getPosition().x, soldier.getPosition().y, soldier.perceivedVelocity.x, fireAngle);
      } else if (clip == 10)
      {
        clip = 0;
      }
      clip++;
    } else
    {
      if (muzzleFlash > 0)
      {
        muzzleFlash++;
      }
      if (muzzleFlash == 4)
      {
        muzzleFlash = 0;
      }
    }
    soldier.celFrame = wantedCelFrame + muzzleFlash;
  }

  private int getBaseFireCelFrame(double fireAngle)
  {
    if (fireAngle < 0.0f)
    {
      return _getBaseFireCelFrame(fireAngle);
    } else if (fireAngle > 0.0f)
    {
      return _getBaseFireCelFrame(-fireAngle) + Soldier.faceRightBase;
    } else
    {
      if (soldier.facingLeft())
      {
        return 44;
      } else if (soldier.facingRight())
      {
        return 44 + Soldier.faceRightBase;
      }
      return -1;
    }
  }

  private int _getBaseFireCelFrame(double fireAngle)
  {
    double _30 = Angle.degToRad(30);
    double _50 = Angle.degToRad(50);
    double _70 = Angle.degToRad(70);
    double _90 = Angle.degToRad(90);
    double _110 = Angle.degToRad(110);
    if (fireAngle < 0 && fireAngle >= -_30)
    {
      return 44;
    } else if (fireAngle < -_30 && fireAngle >= -_50)
    {
      return 40;
    } else if (fireAngle < -_50 && fireAngle >= -_70)
    {
      return 36;
    } else if (fireAngle < -_70 && fireAngle >= -_90)
    {
      return 32;
    } else if (fireAngle < -_90 && fireAngle >= -_110)
    {
      return 28;
    } else
    {
      return 24;
    }
  }
}
