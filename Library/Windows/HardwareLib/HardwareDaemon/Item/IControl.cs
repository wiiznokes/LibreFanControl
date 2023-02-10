using System.Collections;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon.Item;

public class Control
{
    public Control(string? iBehaviorId, string? hControlId, string id, bool isAuto)
    {
        BehaviorId = iBehaviorId;
        HControlId = hControlId;
        Id = id;
        IsAuto = isAuto;
        IsValid = HControlId != null && IsAuto == false && BehaviorId != null;
    }

    public bool IsValid { get; }

    

    private string? BehaviorId { get; }
    public BehaviorWithTemp Behavior { get; set; }
    
    public string Id { get; }
    private bool IsAuto { get; }

    private string? HControlId { get; }
    private BaseControl HControl { get; set; }

    public void SetHControl(ArrayList hControls)
    {
        foreach (BaseControl hControl in hControls)
        {
            if (hControl.Id == HControlId)
            {
                HControl = hControl;
            }
        }
        
    }


    public void SetValue(int value)
    {
        HControl.SetSpeed(value);
    }
}