package net.harrierattack.background;

import net.engine.GamePanel;
import net.engine.cel.Cel;
import net.engine.cel.CelHelper;
import net.engine.graphics.Sprite;
import net.engine.picture.Picture;

import java.awt.*;
import java.util.Random;

public class Terrain extends Heights
{
  public int height[];
  public int frames;
  public int width;
  public Sprite sprites[];

  public Terrain(GamePanel gamePanel, int frames, int width)
  {
    this.frames = frames;
    this.width = width;
    Random random = new Random();
    height = generateHeights(random, 180, 250 + 90, getFarSide(), width);
    generateImage(gamePanel, random);
  }

  public void generateImage(GamePanel gamePanel, Random random)
  {
    Picture picture = new Picture(width * frames, 480);
    generatePalette(picture);

    int farSide = getFarSide();

    for (int x = 0; x < farSide; x++)
    {
      int dy1 = random.nextInt(3);
      int dy2 = random.nextInt(17) + 4;
      int height = this.height[x];
      picture.line(x, height - dy1, x, height, random.nextInt(14) + 7);

      for (int y = height; y <= height + dy2; y++)
      {
        int c = random.nextInt(20) + 20;
        picture.setPixel(x, y, c);
      }
      for (int y = height + dy2 + 1; y < 480; y++)
      {
        int c = random.nextInt(10) + 40 + (y - (height + 1)) / 10;
        picture.setPixel(x, y, c);
      }
    }
    picture.speckle(1);
    picture.plasma(0);

    CelHelper celHelper = new CelHelper(picture, frames, 1, true, true);
    sprites = new Sprite[frames];

    for (int i = 0; i < frames; i++)
    {
      sprites[i] = new Sprite(gamePanel, celHelper.get(i), (float) (i * width), 0.0f);
      sprites[i].setName("Terrain " + i);
      celHelper.get(i).setVerticalAlignment(Cel.TOP_ALIGNED);
      celHelper.get(i).setHorizontalAlignment(Cel.LEFT_ALIGNED);
      sprites[i].layer = 10;
    }
  }

  private void generatePalette(Picture picture)
  {
    picture.setColor(0, new Color(255, 255, 255, 0));
    picture.setColor(1, new Color(0, 0, 255, 255));
    picture.setColors(
            new Color(0, 255, 0, 255), 2,
            new Color(0, 240, 0, 200), 7,
            new Color(22, 229, 27, 255), 20,
            new Color(105, 72, 7, 255), 40,
            new Color(72, 42, 14, 255), 60,
            new Color(198, 183, 42, 255), 80);
  }

  public int getFarSide()
  {
    return (frames - 1) * width;
  }

  public int getHeight(int x)
  {
    if (x < 0)
    {
      return height[0];
    } else if (x >= height.length)
    {
      return height[height.length - 1];
    } else
    {
      return height[x];
    }
  }
}
