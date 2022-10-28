package net.harrierattack.soldier;

public class GibbedSoldierControl extends DeadSoldierControl
{
  public GibbedSoldierControl(Soldier soldier)
  {
    super(soldier, 19, 23);
  }

  protected void doneWithDeath()
  {
    soldier.remove();
  }
}
