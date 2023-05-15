using LibreFanControlService.Hardware.Control;
using LibreFanControlService.Hardware.Sensor;
using LibreHardwareMonitor.Hardware;

namespace LibreFanControlService.Hardware;

public class Lhm : IVisitor
{
    private readonly Computer _mComputer = new()
    {
        IsCpuEnabled = true,
        IsMotherboardEnabled = true,
        IsControllerEnabled = true,
        IsGpuEnabled = true,
        IsStorageEnabled = true,
        IsMemoryEnabled = true
    };

    private bool _isStarted;

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
        if (_isStarted)
            return;

        _mComputer.Open();
        _mComputer.Accept(this);

        _isStarted = true;
    }

    public void Stop()
    {
        if (!_isStarted)
            return;

        _mComputer.Close();
        _isStarted = false;
    }


    public void CreateHardware()
    {
        if (!_isStarted)
            return;

        var hardwareArray = _mComputer.Hardware;
        foreach (var hardware in hardwareArray)
        {
            var sensorArray = hardware.Sensors;
            foreach (var sensor in sensorArray)
            {
                if (sensor.SensorType != SensorType.Control && sensor.SensorType != SensorType.Temperature &&
                    sensor.SensorType != SensorType.Fan)
                    continue;

                var id = sensor.Identifier.ToString();
                var name = sensor.Name.Length > 0 ? sensor.Name : sensor.SensorType.ToString();

                switch (sensor.SensorType)
                {
                    case SensorType.Control:
                        State.HControls.Add(new LhmControl(id, name, sensor));
                        break;
                    case SensorType.Temperature:
                        State.HTemps.Add(new LhmSensor(id, name, sensor));
                        break;
                    case SensorType.Fan:
                        State.HFans.Add(new LhmSensor(id, name, sensor));
                        break;

                    default: throw new Exception("wrong sensor type");
                }
            }

            var subHardwareArray = hardware.SubHardware;
            foreach (var subHardware in subHardwareArray)
            {
                var subSensorArray = subHardware.Sensors;
                foreach (var subSensor in subSensorArray)
                {
                    if (subSensor.SensorType != SensorType.Control && subSensor.SensorType != SensorType.Temperature &&
                        subSensor.SensorType != SensorType.Fan)
                        continue;

                    var id = subSensor.Identifier.ToString();
                    var name = subSensor.Name.Length > 0 ? subSensor.Name : subSensor.SensorType.ToString();
                    switch (subSensor.SensorType)
                    {
                        case SensorType.Control:
                            State.HControls.Add(new LhmControl(id, name, subSensor));
                            break;
                        case SensorType.Temperature:
                            State.HTemps.Add(new LhmSensor(id, name, subSensor));
                            break;
                        case SensorType.Fan:
                            State.HFans.Add(new LhmSensor(id, name, subSensor));
                            break;

                        default: throw new Exception("wrong sensor type");
                    }
                }
            }
        }
    }

    public void Update()
    {
        _mComputer.Accept(this);
    }
}