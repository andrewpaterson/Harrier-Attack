package net.harrierattack;

import net.engine.GameFrame;
import net.engine.GamePanel;

import java.awt.*;

public class HarrierAttack extends GameFrame
{
  public static final int SCREEN_WIDTH = 640;
  public static final int SCREEN_HEIGHT = 480;
  private static OptionsFrame optionsFrame;

  public HarrierAttackPanel gamePanel;

  public static void main(String[] args) throws InterruptedException
  {
    optionsFrame = new OptionsFrame();
    optionsFrame.setVisible(true);
    while (optionsFrame.exitType == 0)
    {
      Thread.sleep(100);
    }
    optionsFrame.setVisible(false);
    optionsFrame.dispose();

    if (optionsFrame.exitType == 3)
    {
      System.exit(0);
    }

    HarrierAttack harrierAttack = new HarrierAttack();

    if (optionsFrame.exitType == 1)
    {
      harrierAttack.initFullScreen(HarrierAttack.SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    harrierAttack.setSize(HarrierAttack.SCREEN_WIDTH, SCREEN_HEIGHT);
    harrierAttack.setVisible(true);
  }

  public HarrierAttack() throws HeadlessException
  {
    super();
  }

  public GamePanel buildGamePanel()
  {
    return new HarrierAttackPanel(optionsFrame.mountains, optionsFrame.showStatistics, HarrierAttack.SCREEN_WIDTH, SCREEN_HEIGHT);
  }
}
