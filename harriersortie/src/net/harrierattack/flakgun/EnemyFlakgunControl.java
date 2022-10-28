package net.harrierattack.flakgun;

import net.engine.Control;
import net.engine.graphics.Sprite;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.Shockwave;
import net.harrierattack.ordinance.FreefallBomb;
import net.harrierattack.ordinance.Rocket;

public class EnemyFlakgunControl extends Control
{
  protected HarrierAttackPanel gamePanel;
  protected Flakgun flakgun;
  protected int rotationTick;

  public EnemyFlakgunControl(Flakgun flakgun)
  {
    this.flakgun = flakgun;
    this.gamePanel = (HarrierAttackPanel) flakgun.gamePanel;
    rotationTick = 1;
  }

  public void control()
  {
    if (rotationTick == 0)
    {
      float x = gamePanel.player.getPosition().x;
      int wantedFrame = 4;
      if (x < flakgun.getPosition().x - 20)
      {
        wantedFrame = 0;
      }
      if (x > flakgun.getPosition().x + 20)
      {
        wantedFrame = 8;
      }

      if (flakgun.celFrame < wantedFrame)
      {
        flakgun.celFrame++;
      }
      if (flakgun.celFrame > wantedFrame)
      {
        flakgun.celFrame--;
      }
      rotationTick = 5;
    } else
    {
      rotationTick--;
    }
  }

  public void startCollision(Sprite sprite)
  {
    if ((sprite instanceof Rocket) || (sprite instanceof FreefallBomb))
    {
      flakgun.destroy();
    } else if (sprite instanceof Shockwave)
    {
      flakgun.destroy();
    }
  }
}
