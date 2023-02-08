namespace HardwareDaemon.Item.Behavior;

public class ITarget
{
    public string Id;
    public int Value;

    public string? TempId;
    public int IdleTemp;
    public int LoadTemp;
    public int IdleFanSpeed;
    public int LoadFanSpeed;

    public bool IdleHasBeenReach;
}