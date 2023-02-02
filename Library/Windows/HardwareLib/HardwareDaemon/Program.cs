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
        try
        {
            SocketListener.TryConnect();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
        HardwareManager.Start(
            ref _controls,
            ref _fans,
            ref _temps
        );
    }

    private static void StartUpdate()
    {
        while (true)
        {
            HardwareManager.Update(
                ref _controls,
                ref _fans,
                ref _temps
            );

            var t = _controls.Cast<BaseDevice>().ToList();
            
            SocketListener.SendDevice(
                ref t
            );
            
        }
    }
}