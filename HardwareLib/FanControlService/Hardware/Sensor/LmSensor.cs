namespace FanControlService.Hardware.Sensor;

public class LmSensor : BaseSensor
{
    public LmSensor(string name, int index, string id) : base(name, index, id)
    {
    }


    public override void Update()
    {
        Value = 0;
    }
}