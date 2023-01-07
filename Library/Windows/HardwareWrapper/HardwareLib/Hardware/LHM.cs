using System.Collections.Generic;
using HardwareLib.Hardware.Control;
using HardwareLib.Hardware.OSD;
using HardwareLib.Hardware.Sensor;
using LibreHardwareMonitor.Hardware;

namespace HardwareLib.Hardware
{
    public class Lhm : IVisitor
    {
        private Computer _mComputer;

        private bool _mIsStart;

        /////////////////////////// Visitor ///////////////////////////
        public void VisitComputer(IComputer computer)
        {
            computer.Traverse(this);
        }

        public void VisitHardware(IHardware hardware)
        {
            hardware.Update();
            foreach (var subHardware in hardware.SubHardware)
                subHardware.Accept(this);
        }

        public void VisitSensor(ISensor sensor)
        {
        }

        public void VisitParameter(IParameter parameter)
        {
        }

        public void Start()
        {
            if (_mIsStart)
                return;
            _mIsStart = true;

            _mComputer = new Computer
            {
                IsCpuEnabled = true,
                IsMotherboardEnabled = true,
                IsControllerEnabled = true,
                IsGpuEnabled = true,
                IsStorageEnabled = true,
                IsMemoryEnabled = true
            };

            _mComputer.Open();
            _mComputer.Accept(this);
        }

        public void Stop()
        {
            if (_mIsStart == false)
                return;
            _mIsStart = false;

            if (_mComputer == null) return;
            _mComputer.Close();
            _mComputer = null;
        }

        public void CreateFan(ref List<BaseDevice> deviceList)
        {
            var index = 0;
            var hardwareArray = _mComputer.Hardware;
            foreach (var t in hardwareArray)
            {
                var sensorArray = t.Sensors;
                foreach (var t1 in sensorArray)
                {
                    if (t1.SensorType != SensorType.Fan)
                        continue;

                    var id = t1.Identifier.ToString();
                    var name = t1.Name.Length > 0 ? t1.Name : "Fan";
                    var sensor = new LhmFanSpeed(id, t1, name, index);
                    deviceList.Add(sensor);
                    index++;
                }

                var subHardwareArray = t.SubHardware;
                foreach (var t1 in subHardwareArray)
                {
                    var subSensorList = t1.Sensors;
                    foreach (var t2 in subSensorList)
                    {
                        if (t2.SensorType != SensorType.Fan)
                            continue;

                        var id = t2.Identifier.ToString();
                        var name = t2.Name.Length > 0 ? t2.Name : "Fan";
                        var sensor = new LhmFanSpeed(id, t2, name, index);
                        deviceList.Add(sensor);
                        index++;
                    }
                }
            }
        }

        public void CreateTemp(ref List<BaseDevice> deviceList)
        {
            var index = 0;
            var hardwareArray = _mComputer.Hardware;
            foreach (var t in hardwareArray)
            {
                var sensorArray = t.Sensors;
                foreach (var t1 in sensorArray)
                {
                    if (t1.SensorType != SensorType.Temperature)
                        continue;

                    var id = t1.Identifier.ToString();
                    var name = t1.Name.Length > 0 ? t1.Name : "Temperature";
                    var sensor = new LhmTemp(id, t1, name, index);
                    deviceList.Add(sensor);
                    index++;
                }

                var subHardwareArray = t.SubHardware;
                foreach (var t1 in subHardwareArray)
                {
                    var subSensorList = t1.Sensors;
                    for (var k = 0; k < subSensorList.Length; k++)
                    {
                        if (subSensorList[k].SensorType != SensorType.Temperature)
                            continue;

                        var id = sensorArray[k].Identifier.ToString();
                        var name = subSensorList[k].Name.Length > 0 ? subSensorList[k].Name : "Temperature";
                        var sensor = new LhmTemp(id, subSensorList[k], name, index);
                        deviceList.Add(sensor);
                        index++;
                    }
                }
            }
        }


        public void CreateControl(ref List<BaseDevice> deviceList)
        {
            var index = 0;
            var hardwareArray = _mComputer.Hardware;
            foreach (var t in hardwareArray)
            {
                var sensorArray = t.Sensors;
                foreach (var t1 in sensorArray)
                {
                    if (t1.SensorType != SensorType.Control)
                        continue;

                    var id = t1.Identifier.ToString();
                    var name = t1.Name.Length > 0 ? t1.Name : "Control";
                    var sensor = new LhmControl(id, t1, name, index);
                    deviceList.Add(sensor);
                    index++;
                }

                var subHardwareArray = t.SubHardware;
                foreach (var t1 in subHardwareArray)
                {
                    var subSensorList = t1.Sensors;
                    foreach (var t2 in subSensorList)
                    {
                        if (t2.SensorType != SensorType.Control)
                            continue;

                        var id = t2.Identifier.ToString();
                        var name = t2.Name.Length > 0 ? t2.Name : "Control";
                        var sensor = new LhmControl(id, t2, name, index);
                        deviceList.Add(sensor);
                        index++;
                    }
                }
            }
        }

        public void CreateOsdSensor(List<OsdSensor> osdList, Dictionary<string, OsdSensor> osdMap)
        {
            try
            {
                var hardwareArray = _mComputer.Hardware;
                foreach (var t in hardwareArray)
                {
                    var sensorArray = t.Sensors;
                    SetOsdSensor(sensorArray, SensorType.Load, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.Clock, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.Voltage, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.Data, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.SmallData, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.Power, osdList, osdMap);
                    SetOsdSensor(sensorArray, SensorType.Throughput, osdList, osdMap);

                    var subHardwareArray = t.SubHardware;
                    foreach (var t1 in subHardwareArray)
                    {
                        var subSensorArray = t1.Sensors;
                        SetOsdSensor(subSensorArray, SensorType.Load, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.Clock, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.Voltage, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.Data, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.SmallData, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.Power, osdList, osdMap);
                        SetOsdSensor(subSensorArray, SensorType.Throughput, osdList, osdMap);
                    }
                }
            }
            catch
            {
                // ignored
            }
        }

        private void SetOsdSensor(ISensor[] sensorArray, SensorType sensorType, List<OsdSensor> osdList,
            Dictionary<string, OsdSensor> osdMap)
        {
            foreach (var t in sensorArray)
            {
                if (t.SensorType != sensorType) continue;

                OsdUnitType unitType;
                var prefix = "";
                switch (t.SensorType)
                {
                    case SensorType.Voltage:
                        unitType = OsdUnitType.Voltage;
                        prefix = "[Voltage] ";
                        break;

                    case SensorType.Power:
                        unitType = OsdUnitType.Power;
                        prefix = "[Power] ";
                        break;

                    case SensorType.Load:
                        unitType = OsdUnitType.Percent;
                        prefix = "[Load] ";
                        break;

                    case SensorType.Clock:
                        unitType = OsdUnitType.MHz;
                        prefix = "[Clock] ";
                        break;

                    case SensorType.Data:
                        unitType = OsdUnitType.Gb;
                        prefix = "[Data] ";
                        break;

                    case SensorType.SmallData:
                        unitType = OsdUnitType.Mb;
                        prefix = "[Data] ";
                        break;

                    case SensorType.Throughput:
                        unitType = OsdUnitType.MbPerSec;
                        prefix = "[Throughput] ";
                        break;

                    case SensorType.Current:
                    case SensorType.Temperature:
                    case SensorType.Frequency:
                    case SensorType.Fan:
                    case SensorType.Flow:
                    case SensorType.Control:
                    case SensorType.Level:
                    case SensorType.Factor:
                    case SensorType.TimeSpan:
                    case SensorType.Energy:
                    default:
                        unitType = OsdUnitType.Unknown;
                        break;
                }

                if (unitType == OsdUnitType.Unknown)
                    continue;

                var id = t.Identifier.ToString();
                var osdSensor = new LhmOsdSensor(id, prefix, t.Name, unitType, t);
                osdList.Add(osdSensor);
                osdMap.Add(id, osdSensor);
            }
        }

        public void Update()
        {
            _mComputer.Accept(this);
        }
    }
}