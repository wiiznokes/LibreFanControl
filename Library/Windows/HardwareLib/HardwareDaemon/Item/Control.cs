using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon.Item;

public class Control
{
    public Control(string? iBehaviorId, string? hControlId, bool isAuto)
    {
        BehaviorId = iBehaviorId;
        HControlId = hControlId;
        IsAuto = isAuto;
        IsValid = HControlId != null && IsAuto == false && BehaviorId != null;
    }

    public bool IsValid { get; }


    public string? BehaviorId { get; }
    public BehaviorWithTemp Behavior { get; set; } = null!;

    private bool IsAuto { get; }

    private string? HControlId { get; }
    private BaseControl HControl { get; set; } = null!;

    public void SetHControl()
    {
        foreach (var hControl in State.HControls.Values)
            if (hControl.Id == HControlId)
                HControl = hControl;
    }


    public static void SetSpeed(int value)
    {
        Console.WriteLine("set control: " + value);
        //HControl.SetSpeed(value);
    }
}