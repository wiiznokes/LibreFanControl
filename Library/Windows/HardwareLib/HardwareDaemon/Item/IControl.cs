using System.Collections;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon.Item;

public class IControl
{
    public IControl(string? iBehaviorId, string? hControlId, string id, bool isAuto)
    {
        IBehaviorId = iBehaviorId;
        HControlId = hControlId;
        Id = id;
        IsAuto = isAuto;
        IsValid = HControlId != null && IsAuto == false && IBehaviorId != null;
    }

    public bool IsValid { get; }

    

    private string? IBehaviorId { get; }
    public IBehavior IBehavior { get; set; }
    
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