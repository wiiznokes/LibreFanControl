using LibreHardwareMonitor.Hardware;

namespace HardwareLib.Hardware.OSD;

public class LhmOsdSensor : OsdSensor
{
    private readonly ISensor _mSensor;

    public LhmOsdSensor(string id, string prefix, string name, OsdUnitType unitType, ISensor sensor)
        : base(id, prefix, name, unitType)
    {
        _mSensor = sensor;
    }

    protected override void Update()
    {
        DoubleValue = _mSensor.Value.HasValue ? (double)_mSensor.Value : Value;
    }
}