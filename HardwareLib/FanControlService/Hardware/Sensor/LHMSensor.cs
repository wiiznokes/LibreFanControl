using LibreHardwareMonitor.Hardware;

namespace FanControlService.Hardware.Sensor;

public class LhmSensor : BaseSensor
{
    // ISensor
    private readonly ISensor _mSensor;

    public LhmSensor(string id, ISensor sensor, string name, int index) : base(name, index, id)
    {
        _mSensor = sensor;
    }

    public override void Update()
    {
        Value = _mSensor.Value.HasValue ? (int)_mSensor.Value : 0;
    }
}