using System.Collections;
using HardwareDaemon.Hardware.Sensor;
using LibreHardwareMonitor.Hardware;

namespace HardwareDaemon.Hardware;

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

        _mComputer.Open();
        _mComputer.Accept(this);
    }

    public void Stop()
    {
        if (_mIsStart == false)
            return;
        _mIsStart = false;

        _mComputer.Close();
    }


    public void CreateDevice(ArrayList deviceList, SensorType sensorType)
    {
        var index = 0;
        var hardwareArray = _mComputer.Hardware;
        foreach (var hardware in hardwareArray)
        {
            var sensorArray = hardware.Sensors;
            foreach (var sensor in sensorArray)
            {
                if (sensor.SensorType != sensorType)
                    continue;

                var id = sensor.Identifier.ToString();
                var name = sensor.Name.Length > 0 ? sensor.Name : sensorType.ToString();
                deviceList.Add(new LhmTemp(id, sensor, name, index));
                index++;
            }

            var subHardwareArray = hardware.SubHardware;
            foreach (var subHardware in subHardwareArray)
            {
                var subSensorArray = subHardware.Sensors;
                foreach (var subSensor in subSensorArray)
                {
                    if (subSensor.SensorType != sensorType)
                        continue;

                    var id = subSensor.Identifier.ToString();
                    var name = subSensor.Name.Length > 0 ? subSensor.Name : sensorType.ToString();
                    deviceList.Add(new LhmTemp(id, subSensor, name, index));
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