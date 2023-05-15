namespace LibreFanControlService;

public static class Update
{
    public static void CreateUpdateList()
    {
        State.UpdateList.Clear();

        foreach (var iControl in State.Controls)
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
                State.UpdateList.Add(iControl.Index);
            }
        }
    }


    public static void UpdateUpdateList()
    {
        foreach (var index in State.UpdateList) State.Controls[index].SetSpeed();
    }

    public static void UpdateAllSensors()
    {
        foreach (var control in State.HControls) control.Update();
        foreach (var temp in State.HTemps) temp.Update();
        foreach (var fan in State.HFans) fan.Update();
    }

    public static void SetAutoAll()
    {
        foreach (var control in State.HControls) control.SetAuto();
    }
}