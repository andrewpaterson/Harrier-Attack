package net.harrierattack.soldier;

import net.engine.Control;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.player.PlayerControllable;

enum SoldierOrders
{
  wait,
  follow,
  retreat
}

public class ComradeControl extends Control
{
  private Soldier soldier;
  private HarrierAttackPanel gamePanel;
  private SoldierOrders orders;
  private float moveToPosition;

  public ComradeControl(Soldier soldier)
  {
    this.soldier = soldier;
    this.gamePanel = (HarrierAttackPanel) soldier.gamePanel;
    this.orders = SoldierOrders.follow;
  }

  public void control()
  {
    PlayerControllable player = gamePanel.getPlayer();
    if (orders == SoldierOrders.wait)
    {
      soldier.stand();
    } else if (orders == SoldierOrders.follow)
    {
      if (player.isSoldier())
      {
        follow((Soldier) player);
      } else if (player.acceptsPassengers())
      {
        if (soldier.atPosition(player.getPosition().x))
        {
          player.passengerGetIn(soldier);
        } else
        {
          moveTo(player.getPosition().x);
        }
      } else
      {
        soldier.stand();
      }
    } else if (orders == SoldierOrders.retreat)
    {
      moveTo(moveToPosition);
    }
  }

  public void orderFollow()
  {
    orders = SoldierOrders.follow;
  }

  public void orderMoveTo(float position)
  {
    orders = SoldierOrders.retreat;
    moveToPosition = position;
  }

  public void orderWait()
  {
    orders = SoldierOrders.wait;
  }

  public void follow(Soldier orderer)
  {
    if (orderer.facingLeft())
    {
      if (soldier.getPosition().x < orderer.getPosition().x + 20)
      {
        soldier.walkRight();
      } else if (soldier.getPosition().x > orderer.getPosition().x + 40)
      {
        soldier.walkLeft();
      } else
      {
        soldier.stand();
      }
    } else if (orderer.facingRight())
    {
      if (soldier.getPosition().x > orderer.getPosition().x - 20)
      {
        soldier.walkLeft();
      } else if (soldier.getPosition().x < orderer.getPosition().x - 40)
      {
        soldier.walkRight();
      } else
      {
        soldier.stand();
      }
    }
  }

  public void moveTo(float position)
  {
    soldier.moveTo(position);
  }
}
