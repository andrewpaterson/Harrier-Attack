package net.physics;

import net.engine.GameFrame;

import java.awt.*;

public class Physics extends GameFrame<PhysicsPanel>
{
  public Physics() throws HeadlessException
  {
    super("Physiciser");
  }

  public static void main(String[] args)
  {
    Physics physics = new Physics();
    physics.setUndecorated(true);
    physics.setResizable(false);
    physics.setVisible(true);
    physics.pack();
  }

  public PhysicsPanel buildGamePanel()
  {
    GraphicsDevice graphicsDevice = getGraphicsDevice();

    int width;
    int height;
    boolean fullScreen = true;
    if (fullScreen)
    {
      DisplayMode displayMode = graphicsDevice.getDisplayMode();
      width = displayMode.getWidth();
      height = displayMode.getHeight();
    } else
    {
      width = 640;
      height = 640;
    }
    return new PhysicsPanel(width, height, 0.2f, 0.001f, 1, 1, 4);
  }
}

