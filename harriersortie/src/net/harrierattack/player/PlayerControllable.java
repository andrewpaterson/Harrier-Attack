package net.harrierattack.player;

import net.engine.GamePanel;
import net.harrierattack.UberSprite;
import net.harrierattack.soldier.Soldier;

public abstract class PlayerControllable extends UberSprite
{
  public PlayerControllable(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
  }

  public abstract boolean canPlayerControl();

  public abstract void playerControl();

  public boolean isSoldier()
  {
    return false;
  }

  public boolean acceptsPassengers()
  {
    return false;
  }

  public void passengerGetIn(Soldier soldier)
  {
  }

  public abstract boolean facingLeft();

  public abstract boolean facingRight();
}
