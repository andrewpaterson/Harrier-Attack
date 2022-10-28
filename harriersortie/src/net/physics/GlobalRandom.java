package net.physics;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 2016/01/08
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalRandom
{
  public static Random random;

  public static float randomFloat()
  {
    return random.nextFloat() * 2.0f - 1.0f;
  }
}

