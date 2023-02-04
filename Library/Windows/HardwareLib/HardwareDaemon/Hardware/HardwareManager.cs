using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon.Hardware;

public static class HardwareManager
{
    private static readonly Lhm Lhm = new();


    public static void Start(
        ref List<BaseControl> controls,
        ref List<BaseSensor> fans,
        ref List<BaseSensor> temps
    )
    {
        Lhm.Start();

        Lhm.CreateControl(ref controls);
        Lhm.CreateFan(ref fans);
        Lhm.CreateTemp(ref temps);
    }

    public static void Stop(
        ref List<BaseControl> controls,
        ref List<BaseSensor> fans,
        ref List<BaseSensor> temps
    )
    {
        Lhm.Stop();

        controls.Clear();
        fans.Clear();
        temps.Clear();
    }


    public static void Update(
        ref List<BaseControl> controls,
        ref List<BaseSensor> fans,
        ref List<BaseSensor> temps
    )
    {
        Lhm.Update();
        foreach (var control in controls) control.Update();
        foreach (var fan in fans) fan.Update();
        foreach (var temp in temps) temp.Update();
    }


    public static bool SetControl(
        ref List<BaseControl> controls,
        int index, bool isAuto, int value)
    {
        try
        {
            var control = controls[index];
            return isAuto ? control.SetAuto() : control.SetSpeed(value);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
            return false;
        }
    }
}