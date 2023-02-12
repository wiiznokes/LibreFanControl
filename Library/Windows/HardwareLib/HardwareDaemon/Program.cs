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

    private static void Main()
    {
        var builder = WebApplication.CreateBuilder();
        builder.WebHost.ConfigureKestrel(options =>
        {
            options.Listen(IPAddress.Parse("127.0.0.1"), 5002,
                listenOptions => { listenOptions.Protocols = HttpProtocols.Http2; });
        });

        builder.Services.AddGrpc();
        var app = builder.Build();
        app.MapGrpcService<CrossApi>();


        app.Run();

/*
        var t = new Task(delegate { app.Run(); });

        t.Start();

        

        HardwareManager.Start();

        var confId = State.Settings.ConfId;

        if (confId == null)
        {
            Console.WriteLine("conf Id == null -> return");
            return;
        }


        ConfHelper.LoadConfFile(confId);

        Update.CreateUpdateList();

        HardwareManager.Update();
        Update.UpdateUpdateList();
        UpdateJob();
        */
    }

    private static void UpdateJob()
    {
        while (!ServiceShouldStop)
        {
            HardwareManager.Update();
            if (State.IsOpen) Update.UpdateAllSensors();
            Update.UpdateUpdateList();

            Thread.Sleep(State.Settings.UpdateDelay * 1000);
        }
    }
}