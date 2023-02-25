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

            Console.WriteLine("control valid: " + iControl.Index);

            if (iControl.SetOneTime)
            {
                Console.WriteLine("set one time");
                iControl.SetSpeed();
            }
            else
            {
                State.UpdateList.TryAdd(State.UpdateList.Count, iControl.Index);
            }
        }
    }


    public static void UpdateUpdateList()
    {
        foreach (var index in State.UpdateList.Values) State.Controls[index].SetSpeed();
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