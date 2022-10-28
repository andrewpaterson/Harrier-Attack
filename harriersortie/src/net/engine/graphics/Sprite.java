package net.engine.graphics;

import net.engine.ArrayExtended;
import net.engine.Camera;
import net.engine.Control;
import net.engine.GamePanel;
import net.engine.cel.Cel;
import net.engine.links.Links;
import net.engine.math.Float2;
import net.engine.shape.Rectangle;
import net.engine.shape.Shape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;

public class Sprite
{
  Float2 position;
  public Float2[] prevPositions;

  public Float2 velocity;
  public Float2 perceivedVelocity;
  public GamePanel gamePanel;
  public boolean positionTicked;
  public String name;
  public int layer;
  public int collisionBits;

  public ArrayExtended<Cel> cels;
  public ArrayExtended<Shape> collision;
  public ArrayExtended<Links> links;
  public int celFrame;
  public int collisionFrame;
  public int linkFrame;

  public Sprite parent;
  public int linkIndexOnParent;
  public ArrayExtended<Sprite> children;

  public Control control;
  public int numPrevPositions;

  public LinkedHashMap<Sprite, Integer> collidedTicks;

  public Sprite(GamePanel gamePanel)
  {
    this.gamePanel = gamePanel;
    position = null;
    numPrevPositions = 3;
    prevPositions = initPrevPositions(numPrevPositions);
    velocity = new Float2();
    perceivedVelocity = new Float2();
    parent = null;
    name = "";
    collisionBits = 0;
    celFrame = 0;
    collisionFrame = 0;
    linkFrame = 0;
    linkIndexOnParent = -1;
    children = new ArrayExtended<>();
    cels = new ArrayExtended<>();
    collision = new ArrayExtended<>();
    links = new ArrayExtended<>();
    control = null;
    collidedTicks = null;
    layer = 0;
  }

  public Sprite(GamePanel gamePanel, float x, float y)
  {
    this(gamePanel);
    position = new Float2(x, y);
    gamePanel.addSprite(this);
  }

  public Sprite(GamePanel gamePanel, List<Cel> celHelper, int frame, float x, float y)
  {
    this(gamePanel, x, y);
    addCels(celHelper);
    celFrame = frame;
  }

  public Sprite(GamePanel gamePanel, Cel cel, float x, float y)
  {
    this(gamePanel, x, y);
    cels.add(cel);
  }

  private Float2[] initPrevPositions(int numPrevPositions)
  {
    Float2[] positions = new Float2[numPrevPositions];
    if (position == null)
    {
      for (int i = 0; i < numPrevPositions; i++)
      {
        positions[i] = null;
      }
    }
    else
    {
      for (int i = 0; i < numPrevPositions; i++)
      {
        positions[i] = new Float2(position);
      }
    }
    return positions;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setPosition(float x, float y)
  {
    if (position != null)
    {
      position.x = x;
      position.y = y;
    }
    else
    {
      position = new Float2(x, y);
    }
  }

  public void setPosition(Point2D.Float position)
  {
    this.position.x = position.x;
    this.position.y = position.y;
  }

  public void setStaticPosition(float x, float y)
  {
    position.x = x;
    position.y = y;
    updatePrevPositions();
  }

  public void setPositionX(float x)
  {
    this.position.x = x;
  }

  public void setPositionY(float y)
  {
    this.position.y = y;
  }

  private boolean isLinked()
  {
    return parent != null;
  }

  public void linkTo(Sprite parent, int linkIndexOnParent)
  {
    parent.children.add(this);
    this.parent = parent;
    this.linkIndexOnParent = linkIndexOnParent;

    position = new Float2();
    velocity = new Float2();
  }

  public void unlink()
  {
    if (isLinked())
    {
      velocity = new Float2(parent.velocity);

      parent.children.remove(this);
      parent = null;
      linkIndexOnParent = -1;
    }
  }

  public void updatePosition()
  {
    if (isLinked())
    {
      if (parent.positionTicked)
      {
        updatePrevPositions();
        position = parent.getLinkPointInWorldSpace(linkIndexOnParent);
        if (position == null)
        {
          position = new Float2();
        }
        positionTicked = true;
      }
    }
    else
    {
      updatePrevPositions();
      position.x += velocity.x;
      position.y += velocity.y;
      positionTicked = true;
    }
  }

  public void perceivedVelocity()
  {
    if (numPrevPositions > 0)
    {
      perceivedVelocity = new Float2(position);
      perceivedVelocity.subtract(prevPositions[0]);
    }
  }

  private void updatePrevPositions()
  {
    if (numPrevPositions >= 1)
    {
      System.arraycopy(prevPositions, 0, prevPositions, 1, numPrevPositions - 2 + 1);
      prevPositions[0] = new Float2(position);
    }
  }

  public Float2 getLinkPoint(int linkIndex)
  {
    Links links = getLink();
    if (links == null)
    {
      return null;
    }
    return links.linkPoints.get(linkIndex);
  }

  protected Float2 getLinkPointInWorldSpace(int linkIndex)
  {
    Links link = getLink();
    if (link == null)
    {
      return null;
    }

    Float2 linkPoint = link.getLinkPoint(linkIndex);
    if (linkPoint == null)
    {
      return null;
    }
    else
    {
      return new Float2(linkPoint).add(position);
    }
  }

  public void tick()
  {
    if (control != null)
    {
      control.control();
    }
  }

  public String toString()
  {
    return name;
  }

  public Cel getCel()
  {
    return cels.get(celFrame);
  }

  public float getLeft()
  {
    return getCel().getGraphicsLeft() + position.x;
  }

  public float getTop()
  {
    return getCel().getGraphicsTop() + position.y;
  }

  public float getRight()
  {
    return getCel().getGraphicsRight() + position.x;
  }

  public float getBottom()
  {
    return getCel().getGraphicsBottom() + position.y;
  }

  public BufferedImage getBufferedImage()
  {
    return getCel().getBufferedImage();
  }

  public void setLayer(int layer)
  {
    this.layer = layer;
  }

  public void addCels(List<Cel> celList)
  {
    cels.addAll(celList);
  }

  public void addCels(List<Cel> celList, Integer... celOrder)
  {
    for (Integer index : celOrder)
    {
      Cel cel = celList.get(index);
      cels.add(cel);
    }
  }

  public void setCollision(int frame, Shape shape)
  {
    collision.set(frame, shape);
  }

  public void setLinks(int frame, Links links)
  {
    this.links.set(frame, links);
  }

  public void setLinks(int frame, int linkIndex, float x, float y)
  {
    Links links = this.links.get(frame);
    if (links == null)
    {
      links = new Links(this);
      setLinks(frame, links);
    }
    links.setLinkPoint(linkIndex, x, y);
  }

  public boolean isVisible()
  {
    if (isLinked())
    {
      Float2 float2 = parent.getLinkPoint(linkIndexOnParent);
      if (float2 == null)
      {
        return false;
      }
    }
    return getCel() != null;
  }

  public Control getControl()
  {
    return control;
  }

  public void setControl(Control control)
  {
    this.control = control;
  }

  private Links getLink()
  {
    return links.get(linkFrame);
  }

  public void remove()
  {
    gamePanel.removeSprite(this);
  }

  public int numCels()
  {
    return cels.size();
  }

  public Float2 getPosition()
  {
    return position;
  }

  public void prepositionTick()
  {
    positionTicked = false;
    updatePosition();
  }

  public void postPositionTick()
  {
    perceivedVelocity();
  }

  public void addBoundingBoxes(float inset)
  {
    for (int i = 0; i < cels.size(); i++)
    {
      Cel cel = cels.get(i);
      if (cel != null)
      {
        Float2 topLeft = new Float2();
        Float2 bottomRight = new Float2();

        topLeft.y = cel.getGraphicsTop() + inset;
        topLeft.x = cel.getGraphicsLeft() + inset;
        bottomRight.y = cel.getGraphicsBottom() - inset;
        bottomRight.x = cel.getGraphicsRight() - inset;

        setCollision(i, new Rectangle(topLeft, bottomRight, position));
      }
      else
      {
        setCollision(i, null);
      }
    }
  }

  public Shape getShape()
  {
    return collision.get(collisionFrame);
  }

  public void collide(Sprite sprite, int ticks)
  {
    if (control != null)
    {
      if (ticks > 0)
      {
        control.startCollision(sprite);
        control.collide(sprite, ticks);
      }
      else
      {
        control.endCollision(sprite);
      }
    }
  }

  public void setCollisionBit(int bit, boolean state)
  {
    if (state)
    {
      this.collisionBits |= 1 << bit;
    }
    else
    {
      this.collisionBits &= ~(1 << bit);
    }
  }

  public void collidedWith(Sprite sprite)
  {
    if (collidedTicks == null)
    {
      collidedTicks = new LinkedHashMap<>();
    }
    Integer integer = collidedTicks.get(sprite);
    if (integer == null)
    {
      integer = 1;
    }
    else
    {
      integer++;
    }
    collidedTicks.put(sprite, integer);
  }

  public void noCollideWith(Sprite sprite)
  {
    if (collidedTicks == null)
    {
      return;
    }

    Integer integer = collidedTicks.get(sprite);
    if (integer != null)
    {
      if (integer == 0)
      {
        collidedTicks.remove(sprite);
      }
      else
      {
        integer = 0;
        collidedTicks.put(sprite, integer);
      }
    }
  }

  public void render(Graphics graphics, Camera camera)
  {
    int x = (int) (getLeft() - camera.getPosition().x);
    int y = (int) (getTop() - camera.getPosition().y);
    graphics.drawImage(getBufferedImage(), x, y, null);
  }
}

