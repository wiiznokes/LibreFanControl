namespace LibreFanControlService.Hardware.Sensor;

public abstract class BaseSensor : BaseHardware
{
    protected BaseSensor(string id, string name) : base(id, name)
    {
    }
}