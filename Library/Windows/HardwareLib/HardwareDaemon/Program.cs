using HardwareDaemon.Hardware;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HidSharp;
using DeviceList = Proto.DeviceList;

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
        
        SocketListener.SendDevice(
            _controls.Cast<BaseDevice>().ToList(),
            DeviceList.Types.DeviceType.Control
        );
        Thread.Sleep(500);
        SocketListener.SendDevice(
            _fans.Cast<BaseDevice>().ToList(),
            DeviceList.Types.DeviceType.Fan
        );
        Thread.Sleep(500);
        SocketListener.SendDevice(
            _temps.Cast<BaseDevice>().ToList(),
            DeviceList.Types.DeviceType.Temp
        );
        Thread.Sleep(10000);
        //StartUpdate();
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

            Thread.Sleep(2000);
            
        }
    }
}