using LibreHardwareMonitor.Hardware;

namespace HardwareLib.Hardware.Sensor;

public class LhmFanSpeed : BaseSensor
{
    // ISensor
    private readonly ISensor _mSensor;

    public LhmFanSpeed(string id, ISensor sensor, string name, int index) : base(name, id, index)
    {
        _mSensor = sensor;
    }

    public override void Update()
    {
        Value = _mSensor.Value.HasValue ? (int)_mSensor.Value : Value;
    }
}