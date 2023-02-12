using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon;

public static class Update
{
    public static void CreateUpdateList()
    {
        State.UpdateList.Clear();

        foreach (var iControl in State.Controls.Values)
        {
            if (!iControl.IsValid)
                continue;

            Behavior? behavior = null;

            foreach (var iBehavior in State.Behaviors.Values)
            {
                if (iBehavior.Id != iControl.BehaviorId) continue;
                behavior = iBehavior;
                break;
            }

            if (behavior == null) continue;

            if (behavior.Type == BehaviorType.Flat)
            {
                iControl.SetHControl();
                Control.SetSpeed((behavior as Flat)!.Value);
                continue;
            }

            try
            {
                (behavior as BehaviorWithTemp)!.Init();
            }
            catch (BehaviorException e)
            {
                Console.WriteLine(e.Message);
                continue;
            }

            iControl.SetHControl();
            iControl.Behavior = (behavior as BehaviorWithTemp)!;
            State.UpdateList.TryAdd(State.UpdateList.Count, iControl);
        }
    }


    public static void UpdateUpdateList()
    {
        foreach (var iControl in State.UpdateList.Values) Control.SetSpeed(iControl.Behavior.GetSpeed());
    }

    public static void UpdateAllSensors()
    {
        foreach (var control in State.HControls.Values) control.Update();
        foreach (var temp in State.HTemps.Values) temp.Update();
        foreach (var fan in State.HFans.Values) fan.Update();
    }

    public static void SetAutoAll()
    {
        foreach (var control in State.HControls.Values) control.SetAuto();
    }
}