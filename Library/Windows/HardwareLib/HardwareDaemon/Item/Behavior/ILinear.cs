namespace HardwareDaemon.Item.Behavior;

public class ILinear
{
    public string Id;
    public int MaxFanSpeed;
    public int MaxTemp;
    public int MinFanSpeed;
    public int MinTemp;

    public string? TempId;
    public int Value;

    public ILinear(string id, int maxFanSpeed, int maxTemp, int minFanSpeed, int minTemp, string tempId, int value)
    {
        Id = id;
        MaxFanSpeed = maxFanSpeed;
        MaxTemp = maxTemp;
        MinFanSpeed = minFanSpeed;
        MinTemp = minTemp;
        TempId = tempId;
        Value = value;
    }
}