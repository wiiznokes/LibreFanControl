using LibreHardwareMonitor.Hardware;

namespace HardwareDaemon.Hardware.Control;

public class LhmControl : BaseControl
{
    // ISensor
    private readonly ISensor _mSensor;

    public LhmControl(string id, ISensor sensor, string name, int index) : base(name, index, id)
    {
        _mSensor = sensor;
    }

    public override void Update()
    {
        double temp = 0.0f;
        if (_mSensor.Value != null) temp = (double)_mSensor.Value;
        temp = Math.Round(temp);
        Value = (int)temp;
    }

    public override int GetMinSpeed()
    {
        if (_mSensor.Control != null) return (int)_mSensor.Control.MinSoftwareValue;
        return 0;
    }

    public override int GetMaxSpeed()
    {
        if (_mSensor.Control != null) return (int)_mSensor.Control.MaxSoftwareValue;
        return 100;
    }

    public override bool SetSpeed(int value)
    {
        if (_mSensor.Control != null)
        {
            _mSensor.Control.SetSoftware(value);
            IsSetSpeed = true;
        }
        else
        {
            return false;
        }

        Value = value;
        
        Console.WriteLine("[SERVICE] set control: " + Name + " = " + value);
        return true;
    }

    public override bool SetAuto()
    {
        if (_mSensor.Control == null) return false;

        if (IsSetSpeed == false)
            return true;

        _mSensor.Control.SetDefault();
        IsSetSpeed = false;
        Console.WriteLine("[SERVICE] set control to auto: " + Name);
        return true;
    }
}