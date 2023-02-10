namespace HardwareDaemon.Item.Behavior;

public class Target : BehaviorWithTemp
{
    private bool _idleHasBeenReached;

    public Target(string id, string? tempId, int idleTemp, int loadTemp, int idleFanSpeed, int loadFanSpeed) : base(id,
        tempId, BehaviorType.Target)
    {
        IdleTemp = idleTemp;
        LoadTemp = loadTemp;
        IdleFanSpeed = idleFanSpeed;
        LoadFanSpeed = loadFanSpeed;
    }


    private int IdleTemp { get; }
    private int LoadTemp { get; }
    private int IdleFanSpeed { get; }
    private int LoadFanSpeed { get; }

    public override int GetSpeed()
    {
        var tempValue = GetSensorValue();

        if (_idleHasBeenReached)
        {
            if (tempValue < LoadTemp) return IdleFanSpeed;

            _idleHasBeenReached = false;
            return LoadFanSpeed;
        }

        if (tempValue > IdleTemp) return LoadFanSpeed;

        _idleHasBeenReached = true;
        return IdleFanSpeed;
    }
}