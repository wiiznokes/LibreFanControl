using System;

namespace HardwareLib.Hardware.OSD
{
    public enum OsdUnitType
    {
        Temperature = 0,
        Rpm,
        Percent,
        MHz,
        KHz,
        Kb,
        Mb,
        Gb,
        MbPerSec,
        Voltage,
        Power,
        Fps,
        Blank,
        HWiNfo,
        Unknown
    }

    public class OsdSensor
    {
        protected OsdSensor(string id, string prefix, string name, OsdUnitType unitType)
        {
            ID = id;
            Prefix = prefix;
            Name = name;
            UnitType = unitType;
        }

        public string Prefix { get; set; }

        public string Name { get; set; }

        public string ID { get; set; }

        public OsdUnitType UnitType { get; set; }

        public double DoubleValue { get; set; }

        public int Value { get; set; }

        public virtual string GetString()
        {
            Update();

            switch (UnitType)
            {
                case OsdUnitType.Voltage:
                    return $"{DoubleValue:0.00}";
                case OsdUnitType.KHz:
                    Value = (int)Math.Round(DoubleValue / 1000);
                    return Value.ToString();
                case OsdUnitType.Kb:
                    Value = (int)Math.Round(DoubleValue / 1024);
                    return Value.ToString();
                case OsdUnitType.Gb:
                    Value = (int)Math.Round(DoubleValue * 1024);
                    return Value.ToString();
                case OsdUnitType.MbPerSec:
                {
                    var value = Math.Round(DoubleValue / 1024 / 1024);
                    return $"{value}";
                }
                case OsdUnitType.Temperature:
                case OsdUnitType.Rpm:
                case OsdUnitType.Percent:
                case OsdUnitType.MHz:
                case OsdUnitType.Mb:
                case OsdUnitType.Power:
                case OsdUnitType.Fps:
                case OsdUnitType.Blank:
                case OsdUnitType.HWiNfo:
                case OsdUnitType.Unknown:
                default:
                    Value = (int)Math.Round(DoubleValue);
                    return Value.ToString();
            }
        }

        protected virtual void Update()
        {
        }
    }
}