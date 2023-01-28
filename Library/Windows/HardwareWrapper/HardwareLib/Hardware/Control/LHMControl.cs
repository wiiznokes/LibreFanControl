using System;
using LibreHardwareMonitor.Hardware;

namespace HardwareLib.Hardware.Control
{
    public class LhmControl : BaseControl
    {
        // ISensor
        private readonly ISensor _mSensor;

        public LhmControl(string id, ISensor sensor, string name, int index)
        {
            Id = id;
            _mSensor = sensor;
            Name = name;
            Index = index;
        }

        public override void Update()
        {
            double temp = 0.0f;
            if (_mSensor?.Value != null) temp = (double)_mSensor.Value;
            temp = Math.Round(temp);
            Value = (int)temp;
        }

        public override int GetMinSpeed()
        {
            if (_mSensor?.Control != null) return (int)_mSensor.Control.MinSoftwareValue;
            return 0;
        }

        public override int GetMaxSpeed()
        {
            if (_mSensor?.Control != null) return (int)_mSensor.Control.MaxSoftwareValue;
            return 100;
        }

        public override bool SetSpeed(int value)
        {
            if (_mSensor?.Control != null)
            {
                _mSensor.Control.SetSoftware(value);
                IsSetSpeed = true;
            }
            else
            {
                return false;
            }

            Value = value;
            return true;
        }

        public override bool SetAuto()
        {
            if (_mSensor?.Control == null) return false;

            if (IsSetSpeed == false)
                return true;

            _mSensor.Control.SetDefault();
            IsSetSpeed = false;
            return true;
        }
    }
}