namespace HardwareDaemon.Item;

public class IControl
{
    public string BehaviorId { get; }
    public string ControlId { get; }
    public string Id { get; }
    public bool IsAuto { get; }
    public int Value { get; }

    public IControl(string behaviorId, string controlId, string id, bool isAuto, int value)
    {
        BehaviorId = behaviorId;
        ControlId = controlId;
        Id = id;
        IsAuto = isAuto;
        Value = value;
    }
}