namespace HardwareLib.Hardware.Control;

public abstract class BaseControl : BaseDevice
{
    protected BaseControl(string name, string id, int index) : base(name, id, index)
    {
        Value = 0;
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