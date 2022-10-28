package net.harrierattack.jeep;

public class IdleJeepControl extends JeepControl
{
  public IdleJeepControl(Jeep jeep)
  {
    super(jeep);
  }

  public boolean isIdle()
  {
    return true;
  }

  public void control()
  {
    super.control();
    aimControl();
    slowDown();
  }

  private void aimControl()
  {
    if (jeep.isTurning())
    {
      return;
    }

    if (jeep.facingLeft())
    {
      jeep.celFrame = Jeep.emptyIdleFrameLeft;
      jeep.linkFrame = 0;
    } else if (jeep.facingRight())
    {
      jeep.celFrame = Jeep.emptyIdleFrameRight;
      jeep.linkFrame = 1;
    }
  }
}
