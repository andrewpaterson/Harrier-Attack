package net.harrierattack.explosion;

import net.engine.Control;
import net.engine.GamePanel;
import net.engine.graphics.Sprite;
import net.engine.math.Angle;
import net.engine.math.Float2;
import net.harrierattack.HarrierAttackPanel;

public class ExplosionEmitter extends Control
{
  public int life;
  public int radius;
  private Sprite sprite;
  private int maxLife;

  public ExplosionEmitter(HarrierAttackPanel gamePanel, float x, float y, int maxLife, int radius)
  {
    super();
    this.maxLife = maxLife;
    this.radius = radius;
    sprite = new Sprite(gamePanel, x, y);
    sprite.setControl(this);
    life = 0;
  }

  public void control()
  {
    life++;
    if (life >= maxLife)
    {
      sprite.remove();
      return;
    }

    GamePanel gamePanel = sprite.gamePanel;
    Float2 position = sprite.getPosition();

    createExplosion(gamePanel, position, radius);
  }

  public static void createExplosion(GamePanel gamePanel, Float2 position, int radius)
  {
    double angle = Angle.degToRad(gamePanel.getRandom().nextInt(360));
    double distance = gamePanel.getRandom().nextInt(radius);
    float x = (float) (position.x + (Math.cos(angle) * distance));
    float y = (float) (position.y + (Math.sin(angle) * distance));

    int explosionSwitch = gamePanel.getRandom().nextInt(4);
    String explosion = "";
    if (explosionSwitch == 0)
    {
      explosion = "Big Explosion";
    } else if (explosionSwitch == 1)
    {
      explosion = "Explosion";
    } else if ((explosionSwitch == 2) || (explosionSwitch == 3))
    {
      explosion = "Small Explosion";
    }
    int rate = gamePanel.getRandom().nextInt(3) + 1;
    new Explosion(gamePanel, x, y, explosion, rate);
  }
}
