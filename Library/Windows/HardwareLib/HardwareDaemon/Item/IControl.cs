namespace HardwareDaemon.Item;

public class IControl
{
    public IControl(string iBehaviorId, string hControlId, string id, bool isAuto, int value)
    {
        IBehaviorId = iBehaviorId;
        HControlId = hControlId;
        Id = id;
        IsAuto = isAuto;
        Value = value;
    }

    public string IBehaviorId { get; }
    public string HControlId { get; }
    public string Id { get; }
    public bool IsAuto { get; }
    public int Value { get; }
}