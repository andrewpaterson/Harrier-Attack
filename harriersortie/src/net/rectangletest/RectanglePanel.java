package net.rectangletest;

import net.engine.GamePanel;
import net.engine.cel.Cel;
import net.engine.cel.CelHelper;
import net.engine.cel.CelStore;
import net.engine.picture.Picture;

import java.awt.*;

public class RectanglePanel extends GamePanel
{
  private YellowRect yellowRect;

  public RectanglePanel()
  {
    super(true, 640, 480);
  }

  protected void initialise()
  {
    Picture green = new Picture(40, 40);
    green.setColor(0, Color.GREEN);
    green.rect(0, 0, 40, 40, 0);

    Picture yellow = new Picture(20, 20);
    yellow.setColor(0, Color.YELLOW);
    yellow.rect(0, 0, 20, 20, 0);

    Picture gray = new Picture(40, 40);
    gray.setColor(0, Color.GRAY);
    gray.rect(0, 0, 40, 40, 0);

    Picture yellow2 = new Picture(20, 40);
    yellow2.setColor(0, Color.YELLOW);
    yellow2.setColor(1, Color.RED);
    yellow2.rect(0, 0, 20, 20, 0);
    yellow2.rect(0, 20, 20, 40, 1);

    Picture pink = new Picture(10, 10);
    pink.setColor(0, Color.PINK);
    pink.rect(0, 0, 10, 10, 0);

    CelStore celStore = CelStore.getInstance();
    celStore.addCels("GREEN 40x40", new CelHelper(green).getCels());
    celStore.addCels("YELLOW 20x20", new CelHelper(yellow).getCels());
    celStore.addCels("GRAY 40x40", new CelHelper(gray).getCels());
    celStore.addCels("YELLOW2 20x20", new CelHelper(yellow2, 1, 2, true, false).getCels());
    celStore.addCels("PINK 10x10", new CelHelper(pink).getCels());

    yellowRect = new YellowRect(this, 320, 240);

    new GreenRect(this, 320, 240);
    new GrayRect(this, 150, 100);
    new Yellow2Rect(this, 400, 300);
    new PinkRect(this, 200, 100);

    camera.setPosition(0, 0);
  }

  public void preRender()
  {
    backBuffer.setColor(Color.BLUE);
    backBuffer.fillRect(0, 0, 640, 480);
  }

  public void postRender()
  {
    Cel cel = yellowRect.getCel();
    float top = cel.getGraphicsTop();
    float left = cel.getGraphicsLeft();
    float bottom = cel.getGraphicsBottom();
    float right = cel.getGraphicsRight();

    String topLeft = "Left Top (" + left + ", " + top + ")";
    String bottomRight = "Right Bottom (" + right + ", " + bottom + ")";
    backBuffer.setColor(Color.YELLOW);
    backBuffer.drawString(topLeft, 10, 440);
    backBuffer.drawString(bottomRight, 10, 460);
  }

  public void resizedBuffer()
  {
  }
}
