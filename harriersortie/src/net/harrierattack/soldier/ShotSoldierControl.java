package net.harrierattack.soldier;

public class ShotSoldierControl extends DeadSoldierControl
{
  public ShotSoldierControl(Soldier soldier)
  {
    super(soldier, 9, 13);
  }

  protected void doneWithDeath()
  {
    soldier.setControl(null);
  }
}
