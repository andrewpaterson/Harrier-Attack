package net.harrierattack.background;

import net.engine.picture.ColourGradient;
import net.engine.picture.Picture;

import java.awt.*;
import java.util.Random;

public class Sunset
{
  int width;
  Picture picture;

  public Sunset(int width)
  {
    this.width = width;
    generate();
  }

  private void generate()
  {
    Random random = new Random(System.nanoTime());
    int sunX = 400;
    int sunY = 100;
    int sunIndex = 29;
    picture = new Picture(width * 2, 480);
    initialisePalette(picture);

    for (int i = 0; i < 10; i++)
    {
      picture.circle(sunX, sunY, 1000 - ((i + 1) * 100), 10 + i * 2);
    }

    picture.speckle(40);
    for (int i = 0; i < 50; i++)
    {
      double angle = ((double) random.nextInt(360)) / (2 * Math.PI);
      int x = (int) (Math.sin(angle) * width * 2);
      int y = (int) (Math.cos(angle) * width * 2);
      picture.line(sunX, sunY, sunX + x, sunY + y, sunIndex);
      picture.line(sunX, sunY, sunX + x + 1, sunY + 1 + y, sunIndex);
      picture.line(sunX, sunY, sunX + x - 1, sunY + 1 + y, sunIndex);
      picture.line(sunX, sunY, sunX + x - 1, sunY - 1 + y, sunIndex);
      picture.line(sunX, sunY, sunX + x + 1, sunY - 1 + y, sunIndex);
    }
    picture.circle(sunX, sunY, 30, sunIndex);
    picture.plasma(0);
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
