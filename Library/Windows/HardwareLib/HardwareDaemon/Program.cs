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
    public static bool ServiceShouldStop;

    private static Task _chatJob;

    private static void Main(string [] args)
    {
        HardwareManager.Start();

        var confId = State.Settings.ConfId;

        if (confId == null)
        {
            Console.WriteLine("conf Id == null");
            if (args[0] == "app")
            {
                Console.WriteLine("no config, start service for the app"); 
            }
            else
            {
                Console.WriteLine("no config -> stop service"); 
                return;
            }
        }
        else
        {
            ConfHelper.LoadConfFile(confId);
            Update.CreateUpdateList();
        }
        
        
        var builder = WebApplication.CreateBuilder();
        builder.WebHost.ConfigureKestrel(options =>
        {
            options.Listen(IPAddress.Parse("127.0.0.1"), 5002,
                listenOptions => { listenOptions.Protocols = HttpProtocols.Http2; });
        });

        builder.Services.AddGrpc();
        var app = builder.Build();
        app.MapGrpcService<CrossApi>();
        
        
        _chatJob = new Task(delegate
        {
            app.Run();
        });
        

        _chatJob.Start();

        HardwareManager.Update();
        Update.UpdateUpdateList();
        UpdateJob();
        
    }

    private static void UpdateJob()
    {
        while (!ServiceShouldStop)
        {
            HardwareManager.Update();
            
            Update.UpdateUpdateList();

            Thread.Sleep(State.Settings.UpdateDelay * 1000);
        }
        Update.SetAutoAll();
    }
}