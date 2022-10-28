package net.harrierattack.jet;

import net.engine.Control;
import net.engine.graphics.Sprite;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.Shockwave;
import net.harrierattack.ordinance.FreefallBomb;
import net.harrierattack.ordinance.Rocket;
import net.harrierattack.ordinance.Shell;

public class EnemyJetControl extends Control
{
  protected Jet jet;
  protected HarrierAttackPanel gamePanel;
  public float maxSpeedX;

  public EnemyJetControl(Jet jet)
  {
    this.jet = jet;
    this.gamePanel = (HarrierAttackPanel) jet.gamePanel;
    maxSpeedX = 6;
  }

  public void control()
  {
    if (jet.facingLeft())
    {
      jet.velocity.x = -maxSpeedX;
      jet.celFrame = 2;
    } else if (jet.facingRight())
    {
      jet.velocity.x = maxSpeedX;
      jet.celFrame = 5 + 2;
    }

    if ((jet.getPosition().x <= 0) || (jet.getPosition().x >= gamePanel.getTerrainWidth()))
    {
      jet.remove();
      return;
    }

    if (jet.isOnGround())
    {
      jet.destroy();
    }
  }

  public void startCollision(Sprite sprite)
  {
    if ((sprite instanceof Shell) || (sprite instanceof Rocket) || (sprite instanceof FreefallBomb))
    {
      jet.destroy();
    } else if (sprite instanceof Shockwave)
    {
      jet.destroy();
    }
  }
}
