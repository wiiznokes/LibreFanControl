using System.Collections;
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

    public static void Stop()
    {
        Lhm.Stop();
    }


    public static void Update()
    {
        Lhm.Update();
    }
}