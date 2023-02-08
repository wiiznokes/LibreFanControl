namespace HardwareDaemon.Item;

public class ICustomTemp
{
    public string Id;
    
    public enum CustomTempType
    {       
        Averrage,
        Max,
        Min
    }

    public CustomTempType Type;

    public int Value;
    public List<string> HTempIds;

}