package net.physics.atom;

import net.engine.math.Float3;

import java.util.ArrayList;
import java.util.List;

public class Atom
{
  private static final float nucleusCharge = 1.0f;
  private static final float linkCharge = -1.0f;

  protected Particle nucleus;
  protected List<Particle> links;
  private float forceModifier;
  private float radius;

  public Atom(Float3 position, float radius, float nucleusMass, float linkMass, int linkCount, float forceModifier)
  {
    this.radius = radius;
    this.nucleus = new Particle(position, nucleusMass, nucleusCharge * linkCount);
    this.forceModifier = forceModifier;

    links = AtomCreator.createLinks(position, linkMass, radius * 1.1f, linkCount, 200, linkCharge);
  }

  public Float3 getNucleusPosition()
  {
    return nucleus.getPosition();
  }

  public List<Float3> getLinkPositions()
  {
    List<Float3> linkPositions = new ArrayList<Float3>(links.size());
    for (Particle link : links)
    {
      linkPositions.add(link.getPosition());
    }
    return linkPositions;
  }

  public void update()
  {
    List<Float3> linkForces = new ArrayList<Float3>();
    for (Particle link : links)
    {
      Float3 force = calculateForceOnLink(link);
      linkForces.add(force.multiply(forceModifier));
    }
    Float3 nucleusForce = calculateForceOnNucleus();

    for (int i = 0; i < links.size(); i++)
    {
      Particle link = links.get(i);
      Float3 force = linkForces.get(i);
      link.applyForce(force);
    }
    nucleus.applyForce(nucleusForce);

    update2();
  }

  private void update2()
  {
    nucleus.update();
    nucleus.drag();
    for (Particle link : links)
    {
      link.update();
      link.stop();
    }
  }

  private Float3 calculateForceOnLink(Particle link)
  {
    Float3 totalForce = new Float3();

    if (links.size() >= 2)
    {
      for (Particle otherLink : links)
      {
        if (otherLink != link)
        {
          Float3 force = calculateMagnitude(link.getPosition(), otherLink.getPosition(), 2);
          force = calculateDirection(force, link.getCharge(), otherLink.getCharge());
          totalForce.add(force);
        }
      }
    }

    Float3 force = calculateMagnitude(link.getPosition(), nucleus.getPosition(), 1, radius);
    force = calculateDirection(force, link.getCharge(), nucleus.getCharge());
    totalForce.add(force);

    return totalForce;
  }

  private Float3 calculateForceOnNucleus()
  {
    Float3 totalForce = new Float3();

    for (Particle otherLink : links)
    {
      Float3 force = calculateMagnitude(nucleus.getPosition(), otherLink.getPosition(), 2);
      force = calculateDirection(force, nucleus.getCharge(), otherLink.getCharge());
      totalForce.add(force);
    }

    return totalForce;
  }

  private Float3 calculateDirection(Float3 force, float startCharge, float endCharge)
  {
    return force.multiply(startCharge * endCharge);
  }

  private Float3 calculateMagnitude(Float3 start, Float3 end, int pow)
  {
    Float3 force = new Float3(start);
    force.subtract(end);
    float distance = force.magnitude();
    force.normalize(distance);

    if (distance > 0.0f)
    {
      return force.multiply(1.0f / integerPow(pow, distance));
    } else
    {
      return new Float3();
    }
  }

  private Float3 calculateMagnitude(Float3 start, Float3 end, int pow, float radius)
  {
    Float3 force = new Float3(start);
    force.subtract(end);
    float distance = force.magnitude();
    force.normalize(distance);

    distance = (distance - radius);
    float v = 0.1f;
    if (distance > 0.0f)
    {
      return force.multiply(integerPow(pow, distance) * v);
    } else if (distance < 0.0f)
    {
      force.multiply(-1);
      return force.multiply(integerPow(pow, -distance) * v);
    } else
    {
      return new Float3();
    }
  }

  private float integerPow(int pow, float num)
  {
    float result = num;
    for (int i = 1; i < pow; i++)
    {
      result *= num;
    }
    return result;
  }
}

