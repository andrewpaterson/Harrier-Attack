package net.harrierattack.jet;

import net.engine.Control;
import net.engine.math.Float2;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.ExplosionEmitter;

public class DestoyedJetControl extends Control
{
  protected Jet jet;
  protected HarrierAttackPanel gamePanel;
  public float maxSpeedX;
  int life;

  public DestoyedJetControl(Jet jet)
  {
    this.jet = jet;
    this.gamePanel = (HarrierAttackPanel) jet.gamePanel;
    jet.setCollisionBit(CollisionNames.Jet, false);
    maxSpeedX = 6;
    life = 35;
  }

  public void control()
  {
    jet.velocity.y += jet.gravity / 2;
    jet.velocity.x *= 0.98f;

    ExplosionEmitter.createExplosion(gamePanel, new Float2(jet.getPosition()), 10);

    if ((jet.getPosition().x <= 0) || (jet.getPosition().x >= gamePanel.getTerrainWidth()))
    {
      jet.remove();
      return;
    }

    life--;

    if (jet.isOnGround() || life == 0)
    {
      jet.explode();
    }
  }
}
