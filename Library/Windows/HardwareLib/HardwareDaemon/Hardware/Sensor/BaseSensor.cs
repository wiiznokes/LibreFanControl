namespace HardwareDaemon.Hardware.Sensor;

public abstract class BaseSensor : BaseHardware
{
    protected BaseSensor(string name, int index, string id) : base(name, index, id)
    {
    }
}