namespace HardwareDaemon.Item.Behavior;

public class IFlat: IBehavior
{
    public IFlat(string id, int value): base(id, BehaviorType.Flat)
    {
        Value = value;
    }

    public int Value { get; }

   
}