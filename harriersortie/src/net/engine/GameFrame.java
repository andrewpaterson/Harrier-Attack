package net.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class GameFrame<T extends GamePanel> extends JFrame
{
  public T gamePanel;
  public GameRunnable runnable;

  protected GameFrame() throws HeadlessException
  {
    this("Harrier: The Second Sortie");
  }

  protected GameFrame(String title) throws HeadlessException
  {
    super(title);
    gamePanel = buildGamePanel();
    runnable = new GameRunnable(gamePanel);
    add(gamePanel);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        runnable.stopGame();
      }
    });
    gamePanel.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          runnable.stopGame();
        }
      }
    });
  }

  protected void initFullScreen(int width, int height)
  {
    GraphicsDevice graphicsDevice = getGraphicsDevice();
    setIgnoreRepaint(true);
    setUndecorated(true);
    setResizable(false);
    DisplayMode displayMode = getDisplayMode(graphicsDevice, width, height);
    graphicsDevice.setFullScreenWindow(this);
    graphicsDevice.setDisplayMode(displayMode);
  }

  protected GraphicsDevice getGraphicsDevice()
  {
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    return graphicsEnvironment.getDefaultScreenDevice();
  }

  protected DisplayMode getDisplayMode(GraphicsDevice graphicsDevice, int width, int height)
  {
    DisplayMode[] displayModes = graphicsDevice.getDisplayModes();
    int lastBitDepth = -1;
    DisplayMode lastDisplayMode = null;

    for (DisplayMode displayMode : displayModes)
    {
      if (displayMode.getHeight() == height)
      {
        if (displayMode.getWidth() == width)
        {
          if (displayMode.getBitDepth() >= lastBitDepth)
          {
            lastBitDepth = displayMode.getBitDepth();
            lastDisplayMode = displayMode;
          }
        }
      }
    }
    return lastDisplayMode;
  }

  protected abstract T buildGamePanel();
}
