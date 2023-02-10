namespace HardwareDaemon.Item.Behavior;

public class Linear : BehaviorWithTemp
{
    public Linear(string id, string? tempId, int minTemp, int maxTemp, int minFanSpeed, int maxFanSpeed) : base(id,
        tempId, BehaviorType.Linear)
    {
        MinTemp = minTemp;
        MaxTemp = maxTemp;
        MinFanSpeed = minFanSpeed;
        MaxFanSpeed = maxFanSpeed;
    }

    private int MinTemp { get; }
    private int MaxTemp { get; }
    private int MinFanSpeed { get; }
    private int MaxFanSpeed { get; }

    public override int GetValue()
    {
        throw new NotImplementedException();
    }
}