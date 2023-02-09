namespace HardwareDaemon.Item;

public class ICustomTemp
{
    public enum CustomTempType
    {
        Average,
        Max,
        Min
    }

    public ICustomTemp(List<string> hTempIds, string id, CustomTempType type, int value)
    {
        HTempIds = hTempIds;
        Id = id;
        Type = type;
        Value = value;
    }

    public List<string> HTempIds { get; }
    public string Id { get; }

    public CustomTempType Type { get; }

    public int Value { get; }
}