package net.harrierattack.background;

import java.awt.*;

public class ControlCurve
{
  protected Polygon points;

  public ControlCurve()
  {
    points = new Polygon();
  }

  public int addPoint(int x, int y)
  {
    points.addPoint(x, y);
    return points.npoints - 1;
  }

  public void setPoint(int index, int x, int y)
  {
    if (index >= 0)
    {
      points.xpoints[index] = x;
      points.ypoints[index] = y;
    }
  }

  public void removePoint(int index)
  {
    if (index >= 0)
    {
      points.npoints--;
      for (int i = index; i < points.npoints; i++)
      {
        points.xpoints[i] = points.xpoints[i + 1];
        points.ypoints[i] = points.ypoints[i + 1];
      }
    }
  }
}

