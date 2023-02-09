namespace HardwareDaemon.Item.Behavior;

public class ITarget
{
    public string Id;
    public int IdleFanSpeed;

    public bool IdleHasBeenReach;
    public int IdleTemp;
    public int LoadFanSpeed;
    public int LoadTemp;

    public string? TempId;
    public int Value;
}