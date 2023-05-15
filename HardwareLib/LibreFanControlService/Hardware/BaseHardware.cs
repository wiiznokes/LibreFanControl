namespace LibreFanControlService.Hardware;

public abstract class BaseHardware
{
    protected BaseHardware(string id, string name)
    {
        Id = id;
        Name = name;
        Value = 0;
    }

    public string Id { get; }
    public string Name { get; }
    public int Value { get; protected set; }

    public virtual void Update()
    {
    }
}