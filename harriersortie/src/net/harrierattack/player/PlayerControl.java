package net.harrierattack.player;

import net.engine.Camera;
import net.engine.Control;
import net.harrierattack.HarrierAttack;
import net.harrierattack.HarrierAttackPanel;

import java.awt.event.KeyEvent;

public abstract class PlayerControl extends Control
{
  protected boolean holdingGetOut;
  protected HarrierAttackPanel gamePanel;

  protected PlayerControl()
  {
    holdingGetOut = true;
    gamePanel = null;
  }

  public abstract PlayerControllable getPlayerControllable();

  public void control()
  {
    if (gamePanel != null)
    {
      if (!gamePanel.keyDown(KeyEvent.VK_SHIFT))
      {
        holdingGetOut = false;
      }
    }
  }

  protected void camera()
  {
    float x = getPlayerControllable().getPosition().x;
    Camera camera = gamePanel.getCamera();
    camera.setStaticPosition(x - HarrierAttack.SCREEN_WIDTH / 2, camera.getPosition().y);
    gamePanel.backdrop.setFromPlayerPosition(x);
  }

  protected void order()
  {
    PlayerControllable playerControllable = getPlayerControllable();
    if (gamePanel.keyDown(KeyEvent.VK_F))
    {
      gamePanel.orderFollow(playerControllable);
    } else if (gamePanel.keyDown(KeyEvent.VK_W))
    {
      gamePanel.orderWait(playerControllable);
    } else if (gamePanel.keyDown(KeyEvent.VK_R))
    {
      if (playerControllable.facingLeft())
      {
        gamePanel.orderMoveTo(playerControllable, playerControllable.getPosition().x + 200);
      } else if (playerControllable.facingRight())
      {
        gamePanel.orderMoveTo(playerControllable, playerControllable.getPosition().x - 200);
      }
    }
  }
}
