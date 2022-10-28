package net.harrierattack.flakgun;

import net.engine.Control;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.explosion.Explosion;

import java.util.Random;

public class DestoyedFlakgunControl extends Control
{
  private Flakgun flakgun;
  int life;

  public DestoyedFlakgunControl(Flakgun flakgun)
  {
    super();
    this.flakgun = flakgun;
    flakgun.setCollisionBit(CollisionNames.Flakgun, false);
    life = 30;
  }

  public void control()
  {
    life--;
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) flakgun.gamePanel;
    Random random = gamePanel.getRandom();

    float x = flakgun.getPosition().x + random.nextInt(61) - 30;
    float y = flakgun.getPosition().y + random.nextInt(30);

    int rate = random.nextInt(2) + 2;

    int explosionSwitch = gamePanel.getRandom().nextInt(2);
    String explosion = "";
    if (explosionSwitch == 0)
    {
      explosion = "Explosion";
    } else if (explosionSwitch == 1)
    {
      explosion = "Small Explosion";
    }
    new Explosion(gamePanel, x, y, explosion, rate);


    if (life == 0)
    {
      flakgun.explode();
    }
  }
}
