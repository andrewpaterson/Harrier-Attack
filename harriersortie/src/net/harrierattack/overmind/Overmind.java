package net.harrierattack.overmind;

import net.harrierattack.HarrierAttack;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.flakgun.Flakgun;
import net.harrierattack.jet.Jet;
import net.harrierattack.soldier.BlueSoldier;

public class Overmind
{
  protected HarrierAttackPanel gamePanel;

  public Overmind(HarrierAttackPanel gamePanel)
  {
    this.gamePanel = gamePanel;
    for (int i = 0; i < 40; i++)
    {
      float x = gamePanel.getRandom().nextInt(gamePanel.getTerrainWidth());
      float y = gamePanel.terrain.getHeight(((int) x)) - 16;
      new Flakgun(gamePanel, x, y);
    }

    for (int i = 0; i < 100; i++)
    {
      float x = gamePanel.getRandom().nextInt(gamePanel.getTerrainWidth());
      float y = 480;
      new BlueSoldier(gamePanel, x, y);
    }
  }

  public void update()
  {
    float x = gamePanel.player.getPosition().x;
    if (gamePanel.getRandom().nextInt(50) == 0)
    {
      int y = 20 + gamePanel.getRandom().nextInt(150);
      int halfWidth = HarrierAttack.SCREEN_WIDTH / 2;
      boolean facingLeft;
      if (gamePanel.getRandom().nextInt(2) == 0)
      {
        x = x - halfWidth - 50;
        facingLeft = false;
      } else
      {
        x = x + halfWidth + 50;
        facingLeft = true;
      }
      if (x < 0)
      {
        x = 0;
      }
      if (x > gamePanel.getTerrainWidth())
      {
        x = gamePanel.getTerrainWidth();
      }
      new Jet(gamePanel, x, y, facingLeft);
    }
  }
}
