using System.Net;
using HardwareDaemon.Hardware;
using HardwareDaemon.Proto;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using Microsoft.Extensions.DependencyInjection;

namespace HardwareDaemon;


internal static class Program
{
    public static bool ServiceShouldStop = false;

    private static Task _chatJob = null!;
    private static readonly CancellationTokenSource CancellationTokenSource = new();
    private static readonly CancellationToken CancellationToken = CancellationTokenSource.Token;
    
    private const int Port = 5002;
    private static readonly IPAddress Ip = IPAddress.Parse("127.0.0.1");
    private static WebApplication _app = null!;
    
    private const int MaxDelay = 5000;
    private const int Delay = 500;

    private static void Main(string [] args)
    {
        HardwareManager.Start();

        var confId = State.Settings.ConfId;

        if (confId == null)
        {
            Console.WriteLine("[SERVICE] conf Id == null");
            if (args[0] == "app")
            {
                Console.WriteLine("[SERVICE] no config, start service for the app");
                StartGrpc();
                var totalDelay = 0;
                while (!State.IsOpen && totalDelay < MaxDelay)
                {
                    Thread.Sleep(Delay);
                    totalDelay += Delay;
                }

                if (!State.IsOpen)
                {
                    Console.WriteLine("[SERVICE] delay before open passed -> stop service");
                    _app.StopAsync();
                    return;
                }
            }
            else
            {
                Console.WriteLine("[SERVICE] no config -> stop service"); 
                return;
            }
        }
        else
        {
            StartGrpc();
            ConfHelper.LoadConfFile(confId);
            //Update.CreateUpdateList();
        }


        _chatJob = new Task(() =>
        {
            _app.Run();
        }, CancellationToken);
        

        _chatJob.Start();

        HardwareManager.Update();
        //Update.UpdateUpdateList();
        UpdateJob();
        
    }

    private static void StartGrpc()
    {
        var builder = WebApplication.CreateBuilder();
        builder.WebHost.ConfigureKestrel(options =>
        {
            options.Listen(Ip, Port,
                listenOptions => { listenOptions.Protocols = HttpProtocols.Http2; });
        });

        builder.Services.AddGrpc();
        _app = builder.Build();
        _app.MapGrpcService<CrossApi>();
    }

    private static void UpdateJob()
    {
        while (!ServiceShouldStop)
        {
            HardwareManager.Update();
            
            //Update.UpdateUpdateList();

            Thread.Sleep(State.Settings.UpdateDelay * 1000);
        }
        Update.SetAutoAll();
        _app.StopAsync();
        CancellationTokenSource.Cancel();
        _chatJob.Wait();
        _chatJob.Dispose();
        Console.WriteLine("[SERVICE] ServiceShouldStop -> stop service"); 
    }
}