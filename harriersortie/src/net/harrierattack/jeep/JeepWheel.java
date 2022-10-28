package net.harrierattack.jeep;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;

public class JeepWheel extends Sprite
{
  int rotationTick;

  public JeepWheel(GamePanel gamePanel)
  {
    super(gamePanel, CelStore.getInstance().get("Jeep Wheel"), 0, 0, 0);

    setLayer(18);
    setName("JeepWheel");
    rotationTick = 1;
  }

  public void tick()
  {
    super.tick();
    float velocity = this.parent.velocity.x;
    int wantedTick = 40;
    if (velocity != 0)
    {
      wantedTick = Math.abs((int) (10 / velocity));
    }
    if (rotationTick <= 0)
    {
      if (velocity <= -0.01)
      {
        celFrame++;
        if (celFrame >= numCels())
        {
          celFrame = 0;
        }
        rotationTick = wantedTick;
      } else if (velocity >= 0.01)
      {
        celFrame--;
        if (celFrame < 0)
        {
          celFrame = numCels() - 1;
        }
        rotationTick = wantedTick;
      } else
      {
        rotationTick = 1;
      }
    }
    if (wantedTick < rotationTick)
    {
      rotationTick = wantedTick;
    }
    rotationTick--;
  }
}
