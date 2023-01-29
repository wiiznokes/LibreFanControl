using System.Net;
using System.Net.Sockets;

// Socket Listener acts as a server and listens to the incoming
// messages on the specified port and protocol.
namespace HardwareDaemon;

public static class SocketListener
{

    public static void StartServer()
    {
        var host = Dns.GetHostEntry("localhost");
        var ipAddress = host.AddressList[0];
        var localEndPoint = new IPEndPoint(ipAddress, 11000);

        Socket? handler = null;
        while (true)
        {
            try
            {

                var listener = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
                listener.Bind(localEndPoint);
                // Specify how many requests a Socket can listen before it gives Server busy response.
                // We will listen 10 requests at a time
                listener.Listen(10);

                Console.WriteLine("Waiting for a connection...");
                handler = listener.Accept();
                Console.WriteLine("after accept");


                if (!handler.Connected)
                {
                    Console.WriteLine("Waiting for a connection...");
                    handler = listener.Accept();
                    Console.WriteLine("after accept");
                }

                var device = new Proto.Device
                {
                    Name = "Device1",
                    Index = 1,
                    Id = "123",
                    Value = 10
                };

                var msg = Google.Protobuf.MessageExtensions.ToByteArray(device);
                handler.Send(msg);

            }
            catch (Exception e)
            {   
                Console.WriteLine(e.ToString());
                Thread.Sleep(2000);
                handler?.Close();
            }
        }

    }
}