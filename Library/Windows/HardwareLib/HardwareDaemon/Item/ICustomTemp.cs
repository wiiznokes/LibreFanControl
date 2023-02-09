namespace HardwareDaemon.Item;

public class ICustomTemp
{
    public enum CustomTempType
    {
        Averrage,
        Max,
        Min
    }

    public List<string> HTempIds;
    public string Id;

    public CustomTempType Type;

    public int Value;
}