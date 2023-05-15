using LibreHardwareMonitor.Hardware;

namespace LibreFanControlService.Hardware.Sensor;

public class LhmSensor : BaseSensor
{
    // ISensor
    private readonly ISensor _mSensor;

    public LhmSensor(string id, string name, ISensor sensor) : base(id, name)
    {
        _mSensor = sensor;
    }

    public override void Update()
    {
        Value = _mSensor.Value.HasValue ? (int)_mSensor.Value : 0;
    }
}