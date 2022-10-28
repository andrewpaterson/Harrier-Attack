package net.harrierattack.soldier;

import net.engine.Control;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.Shockwave;
import net.harrierattack.ordinance.Bullet;
import net.harrierattack.ordinance.Shell;

public class EnemySoldierControl extends Control
{
  protected BlueSoldier soldier;
  protected HarrierAttackPanel gamePanel;
  protected int boredTick;
  protected int moveToPosition;

  public EnemySoldierControl(BlueSoldier soldier)
  {
    this.soldier = soldier;
    this.gamePanel = (HarrierAttackPanel) soldier.gamePanel;
    boredTick = 1;
    soldier.stand();
  }

  public void control()
  {
    if (boredTick == 0)
    {
      boredTick = gamePanel.getRandom().nextInt(300) + 10;

      if (gamePanel.getRandom().nextInt(2) == 0)
      {
        moveToPosition = gamePanel.getRandom().nextInt(gamePanel.getTerrainWidth());
      } else
      {
        moveToPosition = -1;
      }
      soldier.stand();
    } else
    {
      boredTick--;
      if (moveToPosition >= 0)
      {
        soldier.moveTo(moveToPosition);
      } else
      {
        soldier.stand();
      }
    }
  }

  public void startCollision(Sprite sprite)
  {
    if ((sprite instanceof Bullet) || (sprite instanceof Shell))
    {
      int beheadModifier = 1;
      if (sprite instanceof Bullet)
      {
        beheadModifier = 10;
      } else if (sprite instanceof Shell)
      {
        beheadModifier = 4;
      }
      if (soldier.gamePanel.getRandom().nextInt(beheadModifier) == 0)
      {
        soldier.behead(sprite.velocity);
      } else
      {
        soldier.shoot(sprite.velocity);
      }
    } else if (sprite instanceof Shockwave)
    {
      soldier.gib(new Float2(soldier.getPosition()).subtract(sprite.getPosition()));
    }
  }
}
