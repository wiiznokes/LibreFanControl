namespace HardwareLib.Hardware.Sensor;

public abstract class BaseSensor : BaseDevice
{
    protected BaseSensor(string name, string id, int index): base(name, id, index)
    {
        Value = 0;
    }
}