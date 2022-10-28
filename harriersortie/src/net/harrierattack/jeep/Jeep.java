package net.harrierattack.jeep;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttack;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.player.PlayerControllable;
import net.harrierattack.soldier.Comrade;
import net.harrierattack.soldier.PlayerSoldierControl;
import net.harrierattack.soldier.SargeSoldier;
import net.harrierattack.soldier.Soldier;

public class Jeep extends PlayerControllable
{
  public static int wheelY = 26;
  public static int turnRate = 3;
  public static int gunnerAimDownLeft = 0;
  public static int gunnerIdleFrameLeft = 2;
  public static int driverIdleFrameLeft = 8;
  public static int emptyIdleFrameLeft = 9;
  public static int gunnerAimDownRight = 40 + 0;
  public static int gunnerIdleFrameRight = 40 + 2;
  public static int driverIdleFrameRight = 40 + 8;
  public static int emptyIdleFrameRight = 40 + 9;

  JeepWheel[] jeepWheels;
  private boolean gunner;
  private int parked;
  private boolean turning;
  private int wantedTurnFrame;
  private int turnTick;
  private boolean facingLeft;
  private boolean facingRight;

  private int firstLeftTurnFrame;
  private int lastLeftTurnFrame;
  private int firstLeftTurnFrameGunner;
  private int lastLeftTurnFrameGunner;
  private int firstRightTurnFrame;
  private int lastRightTurnFrame;
  private int firstRightTurnFrameGunner;
  private int lastRightTurnFrameGunner;
  public Sprite undercarriage;

  public Jeep(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Jeep Body"));
    celFrame = emptyIdleFrameLeft;

    firstRightTurnFrame = numCels();
    lastRightTurnFrame = firstRightTurnFrame + 4;
    firstRightTurnFrameGunner = lastRightTurnFrame + 1;
    lastRightTurnFrameGunner = firstRightTurnFrameGunner + 4;
    firstLeftTurnFrame = lastRightTurnFrame;
    lastLeftTurnFrame = firstRightTurnFrame;
    firstLeftTurnFrameGunner = lastRightTurnFrameGunner;
    lastLeftTurnFrameGunner = firstRightTurnFrameGunner;

    addCels(CelStore.getInstance().get("Jeep Turn"), 0, 1, 2, 7, 6, 3, 4, 5, 9, 8);

    setLayer(17);
    setName("Jeep");

    setLinks(0, 0, -15, wheelY);
    setLinks(0, 1, -15 + 27, wheelY);
    setLinks(0, 2, 0, 6);
    setLinks(1, 0, -15 + 27 + 3, wheelY);
    setLinks(1, 1, -15 + 3, wheelY);
    setLinks(1, 2, 0, 6);
    linkFrame = 0;

    jeepWheels = new JeepWheel[2];
    jeepWheels[0] = new JeepWheel(gamePanel);
    jeepWheels[1] = new JeepWheel(gamePanel);
    jeepWheels[0].linkTo(this, 0);
    jeepWheels[1].linkTo(this, 1);

    undercarriage = new Sprite(gamePanel, 0, 0);
    undercarriage.addCels(CelStore.getInstance().get("Jeep Undercarriage"));
    undercarriage.setLayer(19);
    undercarriage.setName("JeepUndercarriage");

    undercarriage.linkTo(this, 2);
    gunner = false;
    parked = 0;
    turning = false;
    wantedTurnFrame = -1;
    facingLeft = true;
    facingRight = false;

    setControl(new IdleJeepControl(this));
  }

  public void collision()
  {
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) this.gamePanel;
    int halfWidth = HarrierAttack.SCREEN_WIDTH / 2;
    if (getPosition().x < halfWidth)
    {
      setPositionX(halfWidth);
      velocity.x = 0;
    } else if (getPosition().x > gamePanel.terrain.height.length - halfWidth - 1)
    {
      setPositionX(gamePanel.terrain.height.length - halfWidth - 1);
      velocity.x = 0;
    }
  }

  protected void groundCollision()
  {
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) this.gamePanel;
    Float2 frontWheel = getLinkPointInWorldSpace(0);
    Float2 backtWheel = getLinkPointInWorldSpace(1);
    float frontWheelY;
    float backWheelY;
    if ((frontWheel == null) || (backtWheel == null))
    {
      frontWheelY = backWheelY = gamePanel.terrain.height[((int) getPosition().x)];
    } else
    {
      frontWheelY = gamePanel.terrain.getHeight((int) frontWheel.x);
      backWheelY = gamePanel.terrain.getHeight((int) backtWheel.x);
      float frontOffset = frontWheelY - getPosition().y;
      float backOffset = backWheelY - getPosition().y;

      int maxOffset = 27;
      if (frontOffset > maxOffset)
      {
        frontOffset = maxOffset;
      }
      if (backOffset > maxOffset)
      {
        backOffset = maxOffset;
      }

      frontOffset *= 0.8f;
      backOffset *= 0.8f;
      setLinks(linkFrame, 0, getLinkPoint(0).x, frontOffset);
      setLinks(linkFrame, 1, getLinkPoint(1).x, backOffset);
    }

    boolean prevOnGround = isOnGround();
    float groundLevel = (((frontWheelY + backWheelY) / 2));
    if (getPosition().y >= groundLevel - wheelY)
    {
      setPositionY(groundLevel - wheelY);
      onGround = true;
      velocity.y = 0;
    } else
    {
      onGround = false;
    }

    if (!isOnGround())
    {
      if (prevOnGround)
      {
        velocity.y = perceivedVelocity.y;
      } else
      {
        velocity.y += gravity;
      }
    }
  }

  public void tick()
  {
    super.tick();
    park();
    turn();
    undercarriage();
  }

  private void undercarriage()
  {
    if (turning)
    {
      return;
    }

    if (facingLeft)
    {
      undercarriage.celFrame = 0;
    } else if (facingRight)
    {
      undercarriage.celFrame = 1;
    }
  }

  private void park()
  {
    if (isStopped())
    {
      parked++;
    } else
    {
      parked = 0;
    }
  }

  private void turn()
  {
    if (turning)
    {
      if (turnTick == 0)
      {
        if (wantedTurnFrame < celFrame)
        {
          celFrame--;
        } else if (wantedTurnFrame > celFrame)
        {
          celFrame++;
        } else
        {
          turning = false;
        }
        turnTick = turnRate;
      } else
      {
        turnTick--;
      }
    }
  }

  public boolean canPlayerControl()
  {
    return ((JeepControl) this.control).isIdle();
  }

  public void playerControl()
  {
    setControl(new PlayerJeepControl(this));
  }

  public boolean acceptsPassengers()
  {
    return (isStopped()) && (!gunner);
  }

  public boolean isStopped()
  {
    return (velocity.magnitude() < 0.1);
  }

  public boolean isParked()
  {
    return parked > 5;
  }

  public void passengerGetIn(Soldier soldier)
  {
    gunner = true;
    soldier.remove();
  }

  public void getOut()
  {
    setControl(new IdleJeepControl(this));
    Soldier soldier = new SargeSoldier(gamePanel, getPosition().x, getPosition().y);
    soldier.setControl(new PlayerSoldierControl(soldier));

    gunnerGetOut();
  }

  public Comrade gunnerGetOut()
  {
    if (gunner)
    {
      gunner = false;
      return new Comrade(gamePanel, getPosition().x, getPosition().y);
    }
    return null;
  }

  public boolean facingRight()
  {
    return facingRight;
  }

  public boolean facingLeft()
  {
    return facingLeft;
  }

  public void turnRight()
  {
    if (facingLeft())
    {
      facingRight = true;
      facingLeft = false;
      turning = true;
      turnTick = turnRate;
      linkFrame = 2;
      if (gunner)
      {
        celFrame = firstRightTurnFrameGunner;
        wantedTurnFrame = lastRightTurnFrameGunner;
      } else
      {
        celFrame = firstRightTurnFrame;
        wantedTurnFrame = lastRightTurnFrame;
      }
    }
  }

  public void turnLeft()
  {
    if (facingRight())
    {
      facingLeft = true;
      facingRight = false;
      turning = true;
      turnTick = turnRate;
      linkFrame = 2;
      if (gunner)
      {
        celFrame = firstLeftTurnFrameGunner;
        wantedTurnFrame = lastLeftTurnFrameGunner;
      } else
      {
        celFrame = firstLeftTurnFrame;
        wantedTurnFrame = lastLeftTurnFrame;
      }
    }
  }

  public boolean isTurning()
  {
    return turning;
  }

  public boolean hasGunner()
  {
    return gunner;
  }
}
