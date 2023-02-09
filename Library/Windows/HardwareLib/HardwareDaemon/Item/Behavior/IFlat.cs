namespace HardwareDaemon.Item.Behavior;

public class IFlat
{
    public IFlat(string id, int value)
    {
        Id = id;
        Value = value;
    }

    public string Id { get; }
    public int Value { get; }
}