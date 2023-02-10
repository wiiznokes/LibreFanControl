using System.Collections;
using HardwareDaemon.Hardware.Control;

namespace HardwareDaemon.Item;

public class IControl
{
    public IControl(string? iBehaviorId, string? hControlId, string id, bool isAuto)
    {
        IBehaviorId = iBehaviorId;
        HControlId = hControlId;
        Id = id;
        IsAuto = isAuto;
    }


    public bool IsValid()
    {
        return HControlId != null && IsAuto == false && IBehaviorId != null;
    }

    private string? IBehaviorId { get; }
    private string? HControlId { get; }
    public string Id { get; }
    private bool IsAuto { get; }

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