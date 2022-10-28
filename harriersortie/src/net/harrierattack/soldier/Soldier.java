package net.harrierattack.soldier;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.player.PlayerControllable;

public abstract class Soldier extends PlayerControllable
{
  public static int walkRate = 3;
  public static int faceRightBase = 48;

  protected int walkTick;
  protected float maxSpeed;

  public Soldier(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);

    addCels(CelStore.getInstance().get(getGraphicName()));
    addBoundingBoxes(0);

    setLayer(16);
    setName(getGraphicName());
    walkTick = 1;
    maxSpeed = 1.5f;
  }

  protected abstract String getGraphicName();

  public boolean canPlayerControl()
  {
    return false;
  }

  public void playerControl()
  {
  }

  public boolean isComrade()
  {
    return false;
  }

  public void groundCollision()
  {
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) this.gamePanel;
    float groundlevel = gamePanel.terrain.getHeight((int) getPosition().x) - 17;
    if (getPosition().y >= groundlevel)
    {
      velocity.y = 0.0f;
      onGround = true;
      getPosition().y = groundlevel;
    } else
    {
      velocity.y += gravity;
      onGround = false;
    }
  }

  public boolean facingRight()
  {
    return (celFrame >= faceRightBase);
  }

  public boolean facingLeft()
  {
    return (celFrame >= 0) && (celFrame < faceRightBase);
  }

  public void walkRight()
  {
    velocity.x = maxSpeed;
    if (facingRight())
    {
      if (walkTick <= 0)
      {
        celFrame++;
        walkTick = walkRate;
        if (celFrame == faceRightBase + 9)
        {
          celFrame = faceRightBase;
        }
      } else
      {
        walkTick--;
      }
    } else
    {
      turnAround();
    }
  }


  public void walkLeft()
  {
    velocity.x = -maxSpeed;
    if (facingLeft())
    {
      if (walkTick <= 0)
      {
        celFrame++;
        walkTick = walkRate;
        if (celFrame == 9)
        {
          celFrame = 0;
        }
      } else
      {
        walkTick--;
      }
    } else
    {
      turnAround();
    }
  }

  public void stand()
  {
    velocity.x = 0;
    if (facingLeft())
    {
      celFrame = 0;
    } else if (facingRight())
    {
      celFrame = faceRightBase;
    }
  }

  public boolean isSoldier()
  {
    return true;
  }

  public boolean atPosition(float position)
  {
    return (this.getPosition().x > position - 3) && (this.getPosition().x < position + 3);
  }

  public boolean facingPosition(float position)
  {
    if ((this.getPosition().x < position) && (facingLeft()))
    {
      return false;
    } else if ((this.getPosition().x > position) && (facingRight()))
    {
      return false;
    }
    return true;
  }

  public void turnAround()
  {
    walkTick = 1;
    if (facingLeft())
    {
      celFrame = faceRightBase;
    } else
    {
      celFrame = 0;
    }
  }

  public void jump()
  {
    if (isOnGround())
    {
      velocity.y = -2;
      setPosition(getPosition().x, getPosition().y - gravity);
    }
  }

  public void moveTo(float position)
  {
    if (!facingPosition(position))
    {
      turnAround();
      return;
    }
    if (getPosition().x < position - 1)
    {
      walkRight();
    } else if (getPosition().x > position + 1)
    {
      walkLeft();
    } else
    {
      stand();
    }
  }

  private void faceTowardsDeath(Float2 velocity)
  {
    if (velocity.x > 0)
    {
      celFrame = 0;
    } else
    {
      celFrame = faceRightBase;
    }
  }

  public void gib(Float2 velocity)
  {
    faceTowardsDeath(velocity);
    setControl(new GibbedSoldierControl(this));
  }

  public void shoot(Float2 velocity)
  {
    faceTowardsDeath(velocity);
    setControl(new ShotSoldierControl(this));
  }

  public void behead(Float2 velocity)
  {
    faceTowardsDeath(velocity);
    setControl(new BeheadedSoldierControl(this));
  }
}
