package net.harrierattack;

import net.engine.GamePanel;
import net.engine.IntegerRange;
import net.engine.cel.CelHelper;
import net.engine.cel.CelResizer;
import net.engine.cel.CelStore;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.harrierattack.background.Backdrop;
import net.harrierattack.background.Terrain;
import net.harrierattack.harrier.Harrier;
import net.harrierattack.jeep.Jeep;
import net.harrierattack.overmind.Overmind;
import net.harrierattack.player.PlayerControllable;
import net.harrierattack.soldier.Comrade;

public class HarrierAttackPanel extends GamePanel
{
  static int BLOCK_WIDTH = 400;

  public Terrain terrain;
  public Backdrop backdrop;
  public PlayerControllable player;
  public Overmind overmind;
  public int mountainLayers;

  public HarrierAttackPanel(int mountainLayers, boolean showStatistics, int width, int height)
  {
    super(showStatistics, width, height);
    this.mountainLayers = mountainLayers;
  }

  protected void initialise()
  {
    terrain = new Terrain(this, 30, BLOCK_WIDTH);
    backdrop = new Backdrop(this, terrain.getFarSide(), mountainLayers);
    loadImages();
    new Harrier(this, 1000, 240);
    new Jeep(this, 800, 480);
    camera.setStaticPosition(getCamera().getPosition().x, 0);
    overmind = new Overmind(this);
  }

  private void loadImages()
  {
    CelStore celStore = CelStore.getInstance();

    celStore.addCels("Harrier", new CelHelper("harrier.png", 1, 10, false, true).addFlippedFrames(true, false, 8, 7, 6, 0, 1, 2, 3, 4, 5).getCels());

    celStore.addCels("Jeep Body", new CelHelper("jeep.png", 4, 10, false, false).addFlippedFrames(true, false, new IntegerRange(0, 4 * 10 - 1)).offsetFrames(0, -10, 0, 0).getCels());

    celStore.addCels("Jeep Turn", new CelHelper("jeep_turn.png", 2, 3, false, false).addFlippedFrames(true, false, 0, 1, 3, 4).offsetFrames(0, 17, 0, 0).getCels());

    celStore.addCels("Jeep Undercarriage", new CelHelper("jeep_undercarriage.png", false).addFlippedFrames(true, false, 0).getCels());

    celStore.addCels("Jeep Wheel", new CelHelper("jeep_wheel.png", 1, 12, false, true).getCels());

    celStore.addCels("Green Soldier", new CelHelper("soldier.png", 4, 12, false, false).addFlippedFrames(true, false, new IntegerRange(0, 4 * 12 - 1)).getCels());

    celStore.addCels("Blue Soldier", new CelHelper("soldier_b.png", 4, 12, false, false).addFlippedFrames(true, false, new IntegerRange(0, 4 * 12 - 1)).getCels());

    celStore.addCels("Sarge", new CelHelper("sarge.png", 4, 12, false, false).addFlippedFrames(true, false, new IntegerRange(0, 4 * 12 - 1)).getCels());

    celStore.addCels("Cargo Plane", new CelHelper("cargo_plane.png", 1, 4, false, false).addFlippedFrames(true, false, new IntegerRange(0, 4 - 1)).getCels());

    celStore.addCels("Fighter Plane", new CelHelper("fighter_plane.png", 1, 5, false, false).addFlippedFrames(true, false, new IntegerRange(0, 5 - 1)).getCels());

    celStore.addCels("Flakgun", new CelHelper("flakgun.png", 2, 9, false, false).getCels());

    celStore.addCels("Big Explosion", new CelHelper(CelResizer.scale2x((new CelHelper("explosion_big.png", false)).get(0).getBufferedImage()), 7, 2, true, true).getCels());
    celStore.addCels("Explosion", new CelHelper(CelResizer.scale2x((new CelHelper("explosion.png", false)).get(0).getBufferedImage()), 9, 1, true, true).getCels());
    celStore.addCels("Small Explosion", new CelHelper(CelResizer.scale2x((new CelHelper("explosion_small.png", false)).get(0).getBufferedImage()), 7, 1, true, true).getCels());

    celStore.addCels("Bullet", new CelHelper("bullet.png", 8, 1, true, true).getCels());
    celStore.addCels("Shell", new CelHelper("shell.png", 8, 1, true, true).getCels());
    celStore.addCels("Rocket", new CelHelper("rocket.png", 8, 1, true, true).getCels());
    celStore.addCels("Fireball", new CelHelper("fireball.png", 8, 1, true, true).getCels());
    celStore.addCels("Freefall Bomb", new CelHelper("bomb_falling.png", 1, 1, true, true).getCels());
    celStore.addCels("Sticky Bomb", new CelHelper("bomb_sticky.png", 1, 2, true, true).getCels());
    celStore.addCels("Flare", new CelHelper("flare.png", 1, 3, true, true).getCels());
  }

  public PlayerControllable getNearestControllable(Float2 position)
  {
    PlayerControllable nearestControllable = null;
    float nearestDistance = -1.0f;

    for (Sprite sprite : activeObjects)
    {
      if (sprite instanceof PlayerControllable)
      {
        PlayerControllable controllable = (PlayerControllable) sprite;
        if (controllable.canPlayerControl())
        {
          float distance = ((new Float2(controllable.getPosition())).subtract(position)).magnitude();
          if (distance < 10)
          {
            if (nearestControllable == null)
            {
              nearestControllable = controllable;
              nearestDistance = distance;
            } else if (nearestDistance > distance)
            {
              nearestControllable = controllable;
              nearestDistance = distance;
            }
          }
        }
      }
    }
    return nearestControllable;
  }

  public Comrade getNearestOrderableComrade(Float2 position)
  {
    Comrade nearestComrade = null;
    float nearestDistance = -1.0f;

    for (Sprite sprite : activeObjects)
    {
      if (sprite instanceof Comrade)
      {
        Comrade comrade = (Comrade) sprite;
        float distance = ((new Float2(comrade.getPosition())).subtract(position)).magnitude();
        if (distance < 320)
        {
          if (nearestComrade == null)
          {
            nearestComrade = comrade;
            nearestDistance = distance;
          } else if (nearestDistance > distance)
          {
            nearestComrade = comrade;
            nearestDistance = distance;
          }
        }
      }
    }
    return nearestComrade;
  }

  public PlayerControllable getPlayer()
  {
    return player;
  }

  public void setPlayer(PlayerControllable player)
  {
    this.player = player;
  }

  public void orderFollow(Sprite sprite)
  {
    Comrade comrade = getNearestOrderableComrade(sprite.getPosition());
    if (comrade != null)
    {
      comrade.getComradeControl().orderFollow();
    }
  }

  public void orderWait(Sprite sprite)
  {
    Comrade comrade = getNearestOrderableComrade(sprite.getPosition());
    if (comrade != null)
    {
      comrade.getComradeControl().orderWait();
    }
  }

  public void orderMoveTo(Sprite sprite, float position)
  {
    Comrade comrade = getNearestOrderableComrade(sprite.getPosition());
    if (comrade != null)
    {
      comrade.getComradeControl().orderMoveTo(position);
    }
  }

  protected void update()
  {
    overmind.update();
    super.update();
  }

  public int getTerrainWidth()
  {
    return terrain.height.length;
  }

  public void resizedBuffer()
  {
  }
}
