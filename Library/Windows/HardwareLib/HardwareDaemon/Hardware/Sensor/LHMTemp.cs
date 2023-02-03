using LibreHardwareMonitor.Hardware;

namespace HardwareDaemon.Hardware.Sensor;

public class LhmTemp : BaseSensor
{
    // ISensor
    private readonly ISensor _mSensor;

    public LhmTemp(string id, ISensor sensor, string name, int index) : base(name, index, id)
    {
        _mSensor = sensor;
    }

    public override void Update()
    {
        var temp = _mSensor.Value.HasValue ? Math.Round((double)_mSensor.Value) : 0;
        if (temp > 0.0f) Value = (int)temp;
    }
}