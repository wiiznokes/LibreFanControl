using System.Collections;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using LibreHardwareMonitor.Hardware;

namespace HardwareDaemon.Hardware;

public static class HardwareManager
{
    private static readonly Lhm Lhm = new();

    public static void Start(
        ArrayList controls,
        ArrayList temps,
        ArrayList fans
    )
    {
        Lhm.Start();
        Lhm.CreateDevice(controls, SensorType.Control);
        Lhm.CreateDevice(temps, SensorType.Temperature);
        Lhm.CreateDevice(fans, SensorType.Fan);
    }

    public static void Stop(
        ArrayList controls,
        ArrayList temps,
        ArrayList fans
    )
    {
        Lhm.Stop();

        controls.Clear();
        temps.Clear();
        fans.Clear();
    }


    public static void Update(
        ArrayList controls,
        ArrayList temps,
        ArrayList fans
    )
    {
        Lhm.Update();
        foreach (BaseControl control in controls) control.Update();
        foreach (BaseSensor temp in temps) temp.Update();
        foreach (BaseSensor fan  in fans) fan.Update();
    }


    public static bool SetControl(
        ArrayList controls,
        int index, bool isAuto, int value)
    {
        try
        {
            var control = (BaseControl)controls[index]!;
            return isAuto ? control.SetAuto() : control.SetSpeed(value);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
            return false;
        }
    }
}