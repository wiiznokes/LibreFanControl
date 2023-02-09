namespace HardwareDaemon.Item.Behavior;

public class IFlat
{
    public string Id { get; }
    public int Value { get; }

    public IFlat(string id, int value)
    {
        Id = id;
        Value = value;
    }
}