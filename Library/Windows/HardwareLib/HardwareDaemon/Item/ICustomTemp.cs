namespace HardwareDaemon.Item;

public class ICustomTemp
{
    public enum CustomTempType
    {
        Average,
        Max,
        Min
    }

    public ICustomTemp(string id, List<string> hTempIds, CustomTempType type)
    {
        Id = id;
        HTempIds = hTempIds;
        Type = type;
        Value = 0;
    }

    public List<string> HTempIds { get; }
    public string Id { get; }

    public CustomTempType Type { get; }

    public int Value { get; }
}