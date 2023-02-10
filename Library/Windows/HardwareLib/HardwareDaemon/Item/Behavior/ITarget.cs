namespace HardwareDaemon.Item.Behavior;

public class Target
{
    private bool _idleHasBeenReached = false;

    public string Id;

    public int IdleFanSpeed;
    public int IdleTemp;
    public int LoadFanSpeed;
    public int LoadTemp;
    public string? TempId;

    public int Value;

    public Target(string id, string? tempId, int idleTemp, int loadTemp, int idleFanSpeed, int loadFanSpeed)
    {
        Id = id;
        TempId = tempId;

        IdleTemp = idleTemp;
        LoadTemp = loadTemp;
        IdleFanSpeed = idleFanSpeed;
        LoadFanSpeed = loadFanSpeed;
        Value = 0;
    }
}