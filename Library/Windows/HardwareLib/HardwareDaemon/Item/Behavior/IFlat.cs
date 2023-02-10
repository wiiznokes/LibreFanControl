namespace HardwareDaemon.Item.Behavior;

public class Flat: Behavior
{
    public Flat(string id, int value): base(id, BehaviorType.Flat)
    {
        Value = value;
    }

    public int Value { get; }

   
}