package net.harrierattack.jet;

import net.engine.GamePanel;
import net.engine.cel.CelStore;
import net.harrierattack.CollisionNames;
import net.harrierattack.HarrierAttackPanel;
import net.harrierattack.UberSprite;
import net.harrierattack.explosion.ExplosionEmitter;

public class Jet extends UberSprite
{
  protected boolean facingLeft;

  public Jet(GamePanel gamePanel, float x, float y, boolean facingLeft)
  {
    super(gamePanel, x, y);
    addCels(CelStore.getInstance().get("Fighter Plane"));
    setName("Fighter Plane");
    addBoundingBoxes(0);
    setLayer(8);
    setCollisionBit(CollisionNames.Jet, true);
    setCollisionBit(CollisionNames.Player, true);
    this.facingLeft = facingLeft;
    setControl(new EnemyJetControl(this));
  }

  protected void groundCollision()
  {
    HarrierAttackPanel gamePanel = (HarrierAttackPanel) this.gamePanel;
    int height = 15;
    int x = (int) getPosition().x;

    if (((getPosition().x >= 0) && (getPosition().x < gamePanel.getTerrainWidth())) && (getPosition().y >= gamePanel.terrain.getHeight(x) - height))
    {
      setPositionY(gamePanel.terrain.getHeight(x) - height);
      onGround = true;
    } else
    {
      onGround = false;
    }
  }

  public boolean facingLeft()
  {
    return facingLeft;
  }

  public boolean facingRight()
  {
    return !facingLeft;
  }

  public void destroy()
  {
    setControl(new DestoyedJetControl(this));
  }

  public void explode()
  {
    new ExplosionEmitter((HarrierAttackPanel) gamePanel, getPosition().x, getPosition().y, 30, 40);
    remove();
  }
}
