namespace HardwareDaemon.Item.Behavior;

public class ILinear: IBehaviorWithTemp
{
    
    private int MinTemp { get; }
    private int MaxTemp { get; }
    private int MinFanSpeed { get; }
    private int MaxFanSpeed { get; }

    public ILinear(string id, string? tempId, int minTemp, int maxTemp, int minFanSpeed, int maxFanSpeed): base(id, tempId, BehaviorType.Linear)
    {
        MinTemp = minTemp;
        MaxTemp = maxTemp;
        MinFanSpeed = minFanSpeed;
        MaxFanSpeed = maxFanSpeed;
    }

    public override int? GetValue()
    {
        throw new NotImplementedException();
    }
}