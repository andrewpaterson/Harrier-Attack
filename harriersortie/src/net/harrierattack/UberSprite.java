package net.harrierattack;

import net.engine.GamePanel;
import net.engine.graphics.Sprite;

public abstract class UberSprite extends Sprite
{
  public static float gravity = 0.1f;
  protected boolean onGround;

  public UberSprite(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
  }

  protected abstract void groundCollision();

  public boolean isOnGround()
  {
    return onGround;
  }

  public void tick()
  {
    super.tick();
    groundCollision();
  }
}
