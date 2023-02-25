namespace HardwareDaemon.Item.Behavior;

public class Flat : Behavior
{
    public Flat(string id, int value) : base(id)
    {
        Value = value;
        IsValid = true;
        SetOneTime = true;
    }


    private int Value { get; }

    public override int GetSpeed()
    {
        return Value;
    }
}