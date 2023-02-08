using HardwareDaemon.Hardware;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon;

internal static class Program
{
    private static List<BaseControl> _controls = new();
    private static List<BaseSensor> _fans = new();
    private static List<BaseSensor> _temps = new();

    private static void Main()
    {
        HardwareManager.Start(
            ref _controls,
            ref _fans,
            ref _temps
        );
        Console.WriteLine("hello");
    }
}