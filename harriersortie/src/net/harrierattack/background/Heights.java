package net.harrierattack.background;

import java.awt.*;
import java.util.Random;

public class Heights
{
  protected int[] generateHeights(Random random, int deviation, int offset, int width, int frequency)
  {
    CubicSpline controlPoints = new CubicSpline();
    int end = width / frequency;
    if (width % frequency != 0)
    {
      end++;
    }

    for (int i = 0; i <= end; i++)
    {
      int y;
      y = random.nextInt(deviation) - deviation / 2 + offset;
      if (i == end)
      {
        controlPoints.addPoint(width, y);
      } else
      {
        controlPoints.addPoint(i * frequency, y);
      }
    }
    Polygon spline = controlPoints.generatePolygon();

    Polygon polygon = new Polygon();
    polygon.addPoint(0, 800);
    for (int i = 0; i < spline.npoints; i++)
    {
      polygon.addPoint(spline.xpoints[i], spline.ypoints[i]);
    }
    polygon.addPoint(spline.xpoints[spline.npoints - 1], 800);

    int[] height = new int[width];

    int i = 0;
    for (int x = 0; x < width; x++)
    {
      int x1 = spline.xpoints[i];
      int x2 = spline.xpoints[i + 1];
      int y1 = spline.ypoints[i];
      int y2 = spline.ypoints[i + 1];

      float f;
      if (x2 == x1)
      {
        f = 0;
      } else
      {
        f = (float) (x2 - x) / (float) (x2 - x1);
      }
      height[x] = (int) (y1 + (y2 - y1) * (1 - f));
      if (x >= x2)
      {
        i++;
      }
    }
    return height;
  }
}
