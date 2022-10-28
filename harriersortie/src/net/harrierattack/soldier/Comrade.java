package net.harrierattack.soldier;

import net.engine.GamePanel;

public class Comrade extends GreenSoldier
{
  public Comrade(GamePanel gamePanel, float x, float y)
  {
    super(gamePanel, x, y);
    setControl(new ComradeControl(this));
  }

  public boolean isComrade()
  {
    return true;
  }

  public ComradeControl getComradeControl()
  {
    return (ComradeControl) control;
  }
}
