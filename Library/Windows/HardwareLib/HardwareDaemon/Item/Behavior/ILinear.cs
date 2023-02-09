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

    public ILinear(string id, string? tempId, int minTemp, int maxTemp, int minFanSpeed, int maxFanSpeed)
    {
        Id = id;
        TempId = tempId;

        MinTemp = minTemp;
        MaxTemp = maxTemp;
        MinFanSpeed = minFanSpeed;
        MaxFanSpeed = maxFanSpeed;

        Value = 0;
    }
}