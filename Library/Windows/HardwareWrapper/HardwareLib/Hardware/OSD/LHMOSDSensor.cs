using LibreHardwareMonitor.Hardware;

namespace HardwareLib.Hardware.OSD
{
    public class LHMOSDSensor : OSDSensor
    {
        private readonly ISensor _mSensor;

        public LHMOSDSensor(string id, string prefix, string name, OSDUnitType unitType, ISensor sensor)
            : base(id, prefix, name, unitType)
        {
            _mSensor = sensor;
        }

        public override void update()
        {
            if (_mSensor != null) DoubleValue = _mSensor.Value.HasValue ? (double)_mSensor.Value : Value;
        }
    }
}