namespace FanControlService.Hardware.Control;

public class BaseControl : BaseHardware
{
    protected BaseControl(string name, int index, string id) : base(name, index, id)
    {
        IsSetSpeed = false;
    }

    protected bool IsSetSpeed { get; set; }

    public virtual int GetMinSpeed()
    {
        return 0;
    }

    public virtual int GetMaxSpeed()
    {
        return 100;
    }

    public virtual bool SetSpeed(int value)
    {
        return false;
    }

    public virtual bool SetAuto()
    {
        return false;
    }
}