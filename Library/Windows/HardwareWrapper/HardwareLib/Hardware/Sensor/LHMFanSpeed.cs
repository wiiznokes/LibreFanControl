using LibreHardwareMonitor.Hardware;

namespace LHMWrapper.Hardware.Sensor
{
    public class LHMFanSpeed : BaseSensor
    {
        // ISensor
        private readonly ISensor _mSensor;

        public LHMFanSpeed(string id, ISensor sensor, string name, int index)
        {
            Id = id;
            _mSensor = sensor;
            Name = name;
            Index = index;
        }

        public override void Update()
        {
            if (_mSensor != null) Value = _mSensor.Value.HasValue ? (int)_mSensor.Value : Value;
        }
    }
}