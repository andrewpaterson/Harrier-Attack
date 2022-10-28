package net.harrierattack.soldier;

public class BeheadedSoldierControl extends DeadSoldierControl
{
  public BeheadedSoldierControl(Soldier soldier)
  {
    super(soldier, 14, 18);
  }

  protected void doneWithDeath()
  {
    soldier.setControl(null);
  }
}
