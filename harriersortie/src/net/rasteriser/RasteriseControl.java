package net.rasteriser;

import net.engine.Control;
import net.engine.graphics.Sprite;
import net.engine.math.Angle;
import net.engine.math.Float4x4;

import java.awt.*;

public class RasteriseControl extends Control
{
  protected Sprite sprite;
  protected RasteriserPanel gamePanel;
  private Point prevMousePosition;

  public RasteriseControl(Sprite sprite)
  {
    super();
    this.sprite = sprite;
    gamePanel = (RasteriserPanel) sprite.gamePanel;
    prevMousePosition = null;
  }

  public void control()
  {
    Point mousePosition = gamePanel.mousePosition;

    if (mousePosition != null)
    {
      if (prevMousePosition != null)
      {
        Point diff = new Point(mousePosition.x - prevMousePosition.x,
                mousePosition.y - prevMousePosition.y);

        Float4x4 rotationY = Float4x4.rotationY(Angle.degToRad(diff.x));
        gamePanel.transform = gamePanel.transform.multiply(rotationY);
        Float4x4 rotationX = Float4x4.rotationX(Angle.degToRad(diff.y));
        gamePanel.transform = gamePanel.transform.multiply(rotationX);
      }
      prevMousePosition = mousePosition;
    }
  }
}