package net.harrierattack.soldier;

import net.engine.Control;
import net.harrierattack.CollisionNames;

public abstract class DeadSoldierControl extends Control
{
  protected Soldier soldier;
  protected int frame;
  protected int lastFrame;
  protected int speed;
  protected int frameTick;

  public DeadSoldierControl(Soldier soldier, int firstFrame, int lastFrame)
  {
    this.soldier = soldier;
    this.frame = firstFrame;
    this.lastFrame = lastFrame;
    this.speed = 4;
    frameTick = 0;
    setFrame();
    soldier.setCollisionBit(CollisionNames.EnemySoldier, false);
    soldier.velocity.x = 0;
  }

  private void setFrame()
  {
    if (soldier.facingRight())
    {
      soldier.celFrame = frame + Soldier.faceRightBase;
    } else
    {
      soldier.celFrame = frame;
    }
  }

  public void control()
  {
    if (frameTick == speed)
    {
      frameTick = 0;
      frame++;
      if (frame > lastFrame)
      {
        doneWithDeath();
        return;
      } else
      {
        setFrame();
      }
    }
    frameTick++;
  }

  protected abstract void doneWithDeath();
}
