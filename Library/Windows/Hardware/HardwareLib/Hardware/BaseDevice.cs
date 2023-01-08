namespace HardwareLib.Hardware;

public abstract class BaseDevice
{
    protected BaseDevice(string name, string id, int index)
    {
        Name = name;
        Id = id;
        Index = index;
    }

    public string Name { get; }
    public int Index { get; }
    public string Id { get; }

    public int Value { get; protected set; }

    public virtual void Update()
    {
    }
}