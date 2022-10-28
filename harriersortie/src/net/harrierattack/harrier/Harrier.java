package net.harrierattack.harrier;

import net.engine.cel.CelStore;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttack;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.player.PlayerControllable;
import net.harrierattack.soldier.Comrade;
import net.harrierattack.soldier.PlayerSoldierControl;
import net.harrierattack.soldier.SargeSoldier;
import net.harrierattack.soldier.Soldier;

public class Harrier extends PlayerControllable
{
  public boolean landed;
  public boolean coPilot;

  public Harrier(HarrierAttackPanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);

    addCels(CelStore.getInstance().get("Harrier"));
    celFrame = 9;

    setLayer(18);
    setName("Harrier");

    landed = false;
    coPilot = true;

    playerControl();
  }

  public boolean isLanded()
  {
    return landed;
  }

  public void collsion()
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
    int height = 15;
    int x = (int) getPosition().x;
    if (getPosition().y >= gamePanel.terrain.getHeight(x) - height)
    {
      setPositionY(gamePanel.terrain.getHeight(x) - height);
      velocity.x = 0;
      if (velocity.y > 0)
      {
        velocity.y = 0;
      }
      landed = (celFrame == 9);
      onGround = true;
    } else
    {
      onGround = false;
      landed = false;
    }
  }

  public boolean canPlayerControl()
  {
    return true;
  }

  public void playerControl()
  {
    setControl(new PlayerHarrierControl(this));
  }

  public void getOut()
  {
    velocity = new Float2();
    setControl(null);
    Soldier soldier = new SargeSoldier(gamePanel, getPosition().x, getPosition().y - 10);
    soldier.setControl(new PlayerSoldierControl(soldier));

    if (coPilot)
    {
      coPilot = false;
      new Comrade(gamePanel, getPosition().x, getPosition().y - 10);
    }
  }

  public boolean acceptsPassengers()
  {
    return (isLanded()) && (!coPilot);
  }

  public void passengerGetIn(Soldier soldier)
  {
    coPilot = true;
    soldier.remove();
  }

  public boolean facingLeft()
  {
    return celFrame == 12;
  }

  public boolean facingRight()
  {
    return celFrame == 6;
  }
}
