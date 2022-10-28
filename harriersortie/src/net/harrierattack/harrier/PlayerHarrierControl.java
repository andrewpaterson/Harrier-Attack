package net.harrierattack.harrier;

import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.ordinance.Flare;
import net.harrierattack.ordinance.FreefallBomb;
import net.harrierattack.ordinance.Rocket;
import net.harrierattack.player.PlayerControl;
import net.harrierattack.player.PlayerControllable;

import java.awt.event.KeyEvent;

public class PlayerHarrierControl extends PlayerControl
{
  public Harrier harrier;
  public int rotationTick;
  public int falling;
  public float acceleration;
  public float minSpeedX;
  public float maxSpeedY;
  public float maxSpeedX;
  public int fireTick;
  public int rocketsToFire;
  public boolean rocketFireReleased;
  public boolean bombFireReleased;
  public boolean flareFireReleased;
  public int bombTick;
  public int flareTick;

  public PlayerHarrierControl(Harrier harrier)
  {
    this.harrier = harrier;
    this.gamePanel = (HarrierAttackPanel) getPlayerControllable().gamePanel;
    acceleration = 0.75f;
    rotationTick = 1;
    minSpeedX = 2.0f;
    maxSpeedY = 3.0f;
    maxSpeedX = 5.0f;
    fireTick = 0;
    bombTick = 0;
    flareTick = 0;
    rocketsToFire = 0;
    rocketFireReleased = true;
    bombFireReleased = true;
    gamePanel.setPlayer(harrier);
  }

  public PlayerControllable getPlayerControllable()
  {
    return harrier;
  }

  public void control()
  {
    camera();
    fireControl();
    horizontalControl();
    verticalControl();
    harrier.collsion();
    rotation();
    gunControl();
    landControl();

    super.control();
  }

  private void landControl()
  {
    if (harrier.isLanded())
    {
      if (!holdingGetOut)
      {
        if (gamePanel.keyDown(KeyEvent.VK_SHIFT))
        {
          harrier.getOut();
        }
      }
    }
  }

  private void fireControl()
  {
    if (gamePanel.keyDown(KeyEvent.VK_CONTROL))
    {
      if (rocketsToFire == 0)
      {
        if (rocketFireReleased)
        {
          rocketsToFire = 5;
        }
      }
      rocketFireReleased = false;
    } else
    {
      rocketFireReleased = true;
    }
    if (gamePanel.keyDown(KeyEvent.VK_SPACE))
    {
      if (bombTick == 0)
      {
        if (bombFireReleased)
        {
          bombTick = 1;
        }
      }
      bombFireReleased = false;
    } else
    {
      bombFireReleased = true;
    }

    if (gamePanel.keyDown(KeyEvent.VK_Z))
    {
      if (flareTick == 0)
      {
        if (flareFireReleased)
        {
          flareTick = 1;
        }
      }
      flareFireReleased = false;
    } else
    {
      flareFireReleased = true;
    }
  }

  private void gunControl()
  {
    if (fireTick == 0)
    {
      if (rocketsToFire > 0)
      {
        rocketsToFire--;
        int offset = 0;
        if (harrier.facingLeft())
        {
          offset = -10;
        } else if (harrier.facingRight())
        {
          offset = 10;
        }
        if (offset != 0)
        {
          new Rocket(gamePanel, harrier.getPosition().x + offset, harrier.getPosition().y + 12, harrier.velocity, 50);
        }
        fireTick = 4;
      }
    }

    if (fireTick > 0)
    {
      fireTick--;
    }

    if (bombTick > 0)
    {
      if (bombTick == 1)
      {
        new FreefallBomb(gamePanel, harrier.getPosition().x + 0, harrier.getPosition().y + 12, harrier.velocity);
      }
      bombTick++;
      if (bombTick == 20)
      {
        bombTick = 0;
      }
    }

    if (flareTick > 0)
    {
      int offset = 0;
      if (harrier.facingLeft())
      {
        offset = 20;
      } else if (harrier.facingRight())
      {
        offset = -20;
      }
      new Flare(gamePanel, harrier.getPosition().x + offset, harrier.getPosition().y + gamePanel.getRandom().nextInt(11) - 5, harrier.velocity, 40 + gamePanel.getRandom().nextInt(30));
      flareTick++;
      if (flareTick == 30)
      {
        flareTick = 0;
      }
    }
  }

  private void rotation()
  {
    if (rotationTick == 0)
    {
      int wantedFrame = 9;
      if (harrier.velocity.x < 0)
      {
        wantedFrame = 12;
      } else if (harrier.velocity.x > 0)
      {
        wantedFrame = 6;
      }

      if (harrier.celFrame < wantedFrame)
      {
        harrier.celFrame++;
      } else if (harrier.celFrame > wantedFrame)
      {
        harrier.celFrame--;
      }
      rotationTick = 3;
    } else
    {
      rotationTick--;
    }
  }

  private void verticalControl()
  {
    boolean upOrDownPressed = false;
    if (gamePanel.keyDown(KeyEvent.VK_DOWN))
    {
      upOrDownPressed = true;
      harrier.velocity.y += acceleration;
    }

    if (gamePanel.keyDown(KeyEvent.VK_UP))
    {
      if (harrier.getPosition().y > 20)
      {
        upOrDownPressed = true;
        harrier.velocity.y -= acceleration;
      }
    }
    if (harrier.getPosition().y < 20)
    {
      harrier.setPositionY(20);
    }

    if (!upOrDownPressed)
    {
      if (harrier.velocity.y < 0)
      {
        harrier.velocity.y += acceleration;
      } else if (harrier.velocity.y > 0)
      {
        harrier.velocity.y -= acceleration;
      }
    } else
    {
      if (harrier.velocity.y > maxSpeedY)
      {
        harrier.velocity.y = maxSpeedY;
      } else if (harrier.velocity.y < -maxSpeedY)
      {
        harrier.velocity.y = -maxSpeedY;
      }
    }
  }

  private void horizontalControl()
  {
    if (!harrier.isLanded())
    {
      boolean leftOrRightPressed = false;
      if (gamePanel.keyDown(KeyEvent.VK_RIGHT))
      {
        leftOrRightPressed = true;
        harrier.velocity.x += acceleration;
      }

      if (gamePanel.keyDown(KeyEvent.VK_LEFT))
      {
        leftOrRightPressed = true;
        harrier.velocity.x -= acceleration;
      }

      if (!leftOrRightPressed)
      {
        if ((harrier.velocity.x < minSpeedX) && (harrier.velocity.x > -minSpeedX))
        {
          if (harrier.velocity.x > 0)
          {
            harrier.velocity.x -= acceleration;
          } else if (harrier.velocity.x < 0)
          {
            harrier.velocity.x += acceleration;
          }
        }
      } else
      {
        if (harrier.velocity.x < -maxSpeedX)
        {
          harrier.velocity.x += acceleration;
        } else if (harrier.velocity.x > maxSpeedX)
        {
          harrier.velocity.x -= acceleration;
        }
      }
    }
  }
}
