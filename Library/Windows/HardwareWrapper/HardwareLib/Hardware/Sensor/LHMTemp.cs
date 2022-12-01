using System;
using LibreHardwareMonitor.Hardware;

namespace LHMWrapper.Hardware.Sensor
{
    public class LHMTemp : BaseSensor
    {
        // ISensor
        private readonly ISensor _mSensor;

        public LHMTemp(string id, ISensor sensor, string name, int index)
        {
            Id = id;
            _mSensor = sensor;
            Name = name;
            Index = index;
        }


        public override void Update()
        {
            var temp = _mSensor.Value.HasValue ? Math.Round((double)_mSensor.Value) : 0;
            if (temp > 0.0f) Value = (int)temp;
        }
    }
}