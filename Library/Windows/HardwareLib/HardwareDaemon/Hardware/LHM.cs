using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using LibreHardwareMonitor.Hardware;

namespace HardwareDaemon.Hardware
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

        public void Update()
        {
            _mComputer.Accept(this);
        }
    }
}