namespace FanControlService.Item;

public class Control
{
    public Control(string? iBehaviorId, string? hControlId, bool isAuto, int index)
    {
        BehaviorId = iBehaviorId;
        HControlId = hControlId;
        IsAuto = isAuto;
        Index = index;
        IsValid = HControlId != null && !IsAuto && BehaviorId != null;

        if (!IsValid) return;

        if (!SetBehaviorIndex() || !SetHControlIndex()) IsValid = false;

        SetOneTime = State.Behaviors[BehaviorIndex].SetOneTime;
    }

    public int Index { get; }
    public bool SetOneTime { get; }

    private string? HControlId { get; }
    private bool IsAuto { get; }
    private string? BehaviorId { get; }

    public bool IsValid { get; }


    private int BehaviorIndex { get; set; }


    private int HControlIndex { get; set; }

    private bool SetHControlIndex()
    {
        var i = 0;
        foreach (var hControl in State.HControls)
        {
            if (hControl.Id == HControlId)
            {
                HControlIndex = i;
                return true;
            }

            i++;
        }

        return false;
    }


    private bool SetBehaviorIndex()
    {
        var i = 0;
        foreach (var behavior in State.Behaviors)
        {
            if (behavior.Id == BehaviorId)
            {
                if (!behavior.IsValid) return false;

                BehaviorIndex = i;
                return true;
            }

            i++;
        }

        return false;
    }


    public void SetSpeed()
    {
        var value = State.Behaviors[BehaviorIndex].GetSpeed();
        State.HControls[HControlIndex].SetSpeed(value);
    }
}