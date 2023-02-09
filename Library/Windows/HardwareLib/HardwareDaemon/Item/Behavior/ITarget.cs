namespace HardwareDaemon.Item.Behavior;

public class ITarget
{
    public string Id;
    public int IdleFanSpeed;

    public int IdleTemp;
    public int LoadFanSpeed;
    public int LoadTemp;

    public string? TempId;
    public int Value;
    
    private bool _idleHasBeenReached = false;

    public ITarget(string id, int idleFanSpeed, int idleTemp, int loadFanSpeed, int loadTemp, string tempId, int value)
    {
        Id = id;
        IdleFanSpeed = idleFanSpeed;
        IdleTemp = idleTemp;
        LoadFanSpeed = loadFanSpeed;
        LoadTemp = loadTemp;
        TempId = tempId;
        Value = value;
    }
}