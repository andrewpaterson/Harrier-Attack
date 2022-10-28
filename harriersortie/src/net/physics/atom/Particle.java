package net.physics.atom;

import net.engine.math.Float3;

public class Particle
{
  protected Float3 position;
  protected Float3 velocity;
  protected float mass;
  protected float charge;

  public Particle(Float3 position, float mass, float charge)
  {
    this.position = position;
    this.velocity = new Float3();
    this.mass = mass;
    this.charge = charge;
  }

  public void update()
  {
    position.add(velocity);
  }

  public void stop()
  {
    velocity = new Float3();
  }

  public void applyForce(Float3 force)
  {
    Float3 acceleration = new Float3(force);
    acceleration.divide(mass);
    velocity.add(acceleration);
  }

  public Float3 getPosition()
  {
    return position;
  }

  public float getCharge()
  {
    return charge;
  }

  public void drag()
  {
    velocity.multiply(0.95f);
  }
}

