package net.harrierattack.background;

import net.engine.picture.ColourGradient;
import net.engine.picture.Picture;

import java.awt.*;
import java.util.Random;

public class Mountains extends Heights
{
  int width;
  int layer;
  Picture picture;
  int[] height;

  public Mountains(int layer, int width)
  {
    this.layer = layer;

    this.width = width;
    generate();
  }

  private void generate()
  {
    Random random = new Random(System.nanoTime());
    int layerWidth = width * (2 + layer);
    picture = new Picture(layerWidth, 480);
    initialisePalette(picture);
    height = generateHeights(random, 30 * layer, 140 + layer * 40, layerWidth, 30 * layer);

    for (int x = 0; x < layerWidth; x++)
    {
      int y = height[x];
      picture.line(x, y, x, 480, layer * 2);
    }
  }

  private void initialisePalette(Picture picture)
  {
    picture.setColor(0, new Color(255, 255, 255, 0));
    ColourGradient.generate(picture.palette,
            new Color(237, 0, 201, 255), new Integer(1),
            new Color(237, 0, 56, 255), new Integer(5),
            new Color(99, 44, 255, 255), new Integer(9),
            new Color(237, 0, 201, 255), new Integer(10),
            new Color(100, 100, 255), new Integer(23),
            new Color(255, 253, 75, 255), new Integer(29));
  }
}
