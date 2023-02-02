using System.Net;
using System.Net.Sockets;
using System.Text;
using Google.Protobuf;
using HardwareDaemon.Hardware;
using Proto;

namespace HardwareDaemon;

public static class SocketListener
{

    private static Socket? _handler;
    private static readonly IPHostEntry Host = Dns.GetHostEntry("localhost");
    private static readonly IPAddress IpAddress = Host.AddressList[0];
    private const int Port = 11000;

    public static void TryConnect()
    {
        var localEndPoint = new IPEndPoint(IpAddress, Port);

        var listener = new Socket(IpAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        listener.Bind(localEndPoint);
        listener.Listen(10);

        Console.WriteLine("Waiting for a connection...");
        _handler = listener.Accept();
        Console.WriteLine("Accept");
    }

    public static void SendDevice(
        List<BaseDevice> list,
        DeviceList.Types.DeviceType type
    )
    {
        var deviceList = new List<Device>();
        foreach (var item in list)
        {
            deviceList.Add(new Device {
                Name = item.Name,
                Index = item.Index,
                Id = item.Id,
                Value = item.Value
            }); 
        }
        
        var sDeviceList = new DeviceList
        {
            Type = type,
            Device = { deviceList }
        };

        try
        {
            _handler?.Send(sDeviceList.ToByteArray());
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    
    public static void SendUpdate(
        List<BaseDevice> list
    )
    {
        var updateList = new List<Update>();
        foreach (var device in list)
        {
            updateList.Add(new Update {
                Index = device.Index,
                Value = device.Value
            }); 
        }
        
        var sUpdateList = new UpdateList
        {
            Update = { updateList }
        };

        try
        {
            _handler?.Send(sUpdateList.ToByteArray());
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    
    
    public static Command GetMessage()
    { 
        var buffer = new byte[1024];
        var bytesReceived = _handler?.Receive(buffer) ?? 0;
        
        var message = Encoding.ASCII.GetString(buffer, 0, bytesReceived);
        Console.WriteLine("message = " + message);
        
        return message switch
        {
            "GetInfo" => Command.GetInfo,
            "Controls" => Command.Controls,
            "Fans" => Command.Fans,
            "Temps" => Command.Temps,
            "Stop" => Command.Stop,
            _ => throw new Exception()
        };
    }

    public static void Close()
    {
        _handler?.Close();
    }
}