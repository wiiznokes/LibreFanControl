namespace HardwareDaemon.Hardware.Sensor;

public abstract class BaseSensor : BaseDevice
{
    protected BaseSensor(string name, int index, string id) : base(name, index, id)
    {
    }
}