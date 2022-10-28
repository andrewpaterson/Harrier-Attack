package net.physics.atom;

import net.engine.math.Float3;

import java.util.ArrayList;
import java.util.List;

import static net.physics.GlobalRandom.randomFloat;

public class AtomCreator
{
  public static ArrayList<Particle> createLinks(Float3 nucleusPosition, float linkMass, float radius, int linkCount, int initialisationSteps, float charge)
  {
    ArrayList<Float3> positions = createEquidistantPointsOnSphere(radius, linkCount, initialisationSteps);

    ArrayList<Particle> links = new ArrayList<Particle>(linkCount);
    for (Float3 linkPosition : positions)
    {
      linkPosition.add(nucleusPosition);
      links.add(new Particle(linkPosition, linkMass, charge));
    }


    return links;
  }

  private static ArrayList<Float3> createEquidistantPointsOnSphere(float radius, int linkCount, int initialisationSteps)
  {
    ArrayList<Float3> positions = new ArrayList<Float3>(linkCount);
    for (int i = 0; i < linkCount; i++)
    {
      positions.add(createPointOnSphere(radius));
    }

    for (int i = 0; i < initialisationSteps; i++)
    {
      createLinkPosition(radius, positions);
    }
    return positions;
  }

  private static Float3 createPointOnSphere(float radius)
  {
    for (; ; )
    {
      Float3 vector = new Float3(randomFloat(), randomFloat(), randomFloat());
      float length = vector.magnitude();
      if ((length >= 1f) && length >= 0.5f)
      {
        return vector.normalize().multiply(radius);
      }
    }
  }

  private static void createLinkPosition(float radius, ArrayList<Float3> links)
  {
    List<Float3> forces = new ArrayList<Float3>();
    for (Float3 link : links)
    {
      Float3 force = calculateInitialForce(links, link);
      forces.add(force.multiply(0.5f));
    }

    for (int j = 0; j < links.size(); j++)
    {
      Float3 link = links.get(j);
      Float3 force = forces.get(j);

      link.add(force);
      link.normalize().multiply(radius);
    }
  }

  private static Float3 calculateInitialForce(ArrayList<Float3> links, Float3 link)
  {
    Float3 totalForce = new Float3();

    if (links.size() >= 2)
    {
      for (Float3 otherLink : links)
      {
        if (otherLink != link)
        {
          Float3 force = new Float3(link);
          force.subtract(otherLink);

          force.inverseForce();
          totalForce.add(force);
        }
      }
    }

    return totalForce;
  }
}

