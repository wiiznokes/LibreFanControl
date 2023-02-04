using System.Net.Sockets;
using HardwareDaemon.Hardware;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using Proto.Device;
using DeviceList = HidSharp.DeviceList;

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
        SocketListener.TryConnect();
        StartListening();
    }
    

    private static void StartListening()
    {
        var updateShouldStop = false;
        while (!updateShouldStop)
        {
            var command = SocketListener.GetMessage();
            switch (command)
            {
                case Command.GetInfo:
                    SendInfo();
                    break;
                
                case Command.Controls:
                    SocketListener.SendUpdate(
                        _controls.Cast<BaseDevice>().ToList()
                    );
                    break;
                case Command.Fans:
                    SocketListener.SendUpdate(
                        _fans.Cast<BaseDevice>().ToList()
                    );
                    break;
                case Command.Temps:
                    SocketListener.SendUpdate(
                        _temps.Cast<BaseDevice>().ToList()
                    );
                    break;
                case Command.Stop:
                    SocketListener.Close();
                    updateShouldStop = true;
                    break;
                default:
                    throw new ArgumentOutOfRangeException();
            }
            
            HardwareManager.Update(
                ref _controls,
                ref _fans,
                ref _temps
            );
            
            Console.WriteLine("new update");
        }
        
        
    }
    
    private static void SendInfo()
    {
        SocketListener.SendDevice(
            _controls.Cast<BaseDevice>().ToList(),
            DeviceType.Control
        );
        Thread.Sleep(500);
        SocketListener.SendDevice(
            _fans.Cast<BaseDevice>().ToList(),
            DeviceType.Fan
        );
        Thread.Sleep(500);
        SocketListener.SendDevice(
            _temps.Cast<BaseDevice>().ToList(),
            DeviceType.Temp
        );
    }
}