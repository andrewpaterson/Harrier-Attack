package net.physics;

import net.engine.GamePanel;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.engine.math.Float3;
import net.engine.math.Float4x4;
import net.physics.atom.Atom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.physics.GlobalRandom.randomFloat;

/**
 *
 */
public class PhysicsPanel extends GamePanel
{
  public Float4x4 transform;
  private int halfWidth;
  private int halfHeight;
  private List<Atom> atoms;

  public PhysicsPanel(int width, int height, float defaultRadius, float forceModifier, int atomCount, int mass, int links)
  {
    super(false, width, height);
    GlobalRandom.random = random;

    this.halfWidth = width / 2;
    this.halfHeight = height / 2;
    transform = new Float4x4();

    atoms = new ArrayList<Atom>();
    for (int i = 0; i < atomCount; i++)
    {
      atoms.add(new Atom(createPosition(), defaultRadius, mass, mass * 0.1f, links, forceModifier));
    }
  }

  private Float3 createPosition()
  {
    int scale = 3;
    Float3 float3 = new Float3(randomFloat() * scale, randomFloat() * scale, randomFloat() * scale);
    return float3;
  }

  protected void initialise()
  {
    new Sprite(this, 0, 0);
  }

  public void resizedBuffer()
  {
  }

  public void preRender()
  {
    backBuffer.setColor(Color.DARK_GRAY);
    backBuffer.fillRect(0, 0, halfWidth * 2, halfHeight * 2);
  }

  public void postRender()
  {
    for (Atom atom : atoms)
    {
      drawAtom(atom);
    }
  }

  private void drawAtom(Atom atom)
  {
    Float2 center = transform(atom.getNucleusPosition());
    java.util.List<Float2> links = new ArrayList<Float2>();
    for (Float3 link : atom.getLinkPositions())
    {
      links.add(transform(link));
    }

    backBuffer.setColor(Color.GRAY);
    for (Float2 link : links)
    {
      backBuffer.setColor(Color.GRAY);
      backBuffer.drawLine((int) center.x, (int) center.y, (int) link.x, (int) link.y);
      backBuffer.setColor(Color.white);
      backBuffer.drawOval((int) link.x - 1, (int) link.y - 1, 3, 3);
    }
    backBuffer.setColor(Color.yellow);
    backBuffer.drawOval((int) center.x - 1, (int) center.y - 1, 3, 3);
  }

  private Float2 transform(Float3 position)
  {
    Float3 ptc = new Float3(transform.multiply(position));
    Float2 ppc = perspective(ptc);
    return toScreenSpace(ppc);
  }

  private Float2 perspective(Float3 point)
  {
    //        float zTransform = (-point.z - 8.0f) / 2;
    //        return new Float2(point.x / (zTransform + 8.0f), point.y = point.y / (zTransform + 8.0f));

    return new Float2(point.x, point.y);
  }

  private Float2 toScreenSpace(Float2 point)
  {
    float scale = 0.1f;
    return new Float2(point.x * halfWidth * scale + halfWidth, point.y * halfHeight * scale + halfHeight);
  }

  protected void update()
  {
    for (Atom atom : atoms)
    {
      atom.update();
    }

    super.update();
  }
}

