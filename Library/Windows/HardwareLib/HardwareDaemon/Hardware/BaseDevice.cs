namespace HardwareDaemon.Hardware;

public class BaseDevice
{
    protected BaseDevice(string name, int index, string id)
    {
        Name = name;
        Index = index;
        Id = id;
        Value = 0;
    }
    public string Name { get; }
    public int Index { get; }
    public string Id { get; }

    protected int Value { get; set; }

    public virtual void Update()
    {
    }
}