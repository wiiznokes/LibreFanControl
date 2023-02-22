﻿using System.Net;
using HardwareDaemon.Hardware;
using HardwareDaemon.Proto;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Server.Kestrel.Core;

namespace HardwareDaemon;

public class Worker : BackgroundService
{
    private const int Port = 5002;

    private const int MaxDelay = 5000;
    private const int Delay = 500;
    private static readonly CancellationTokenSource CancellationTokenSource = new();
    private readonly IHostApplicationLifetime _appLifetime;
    private readonly CancellationToken _cancellationToken = CancellationTokenSource.Token;
    private readonly IPAddress _ip = IPAddress.Parse("127.0.0.1");

    private readonly ILogger<Worker> _logger;

    private Task _chatJob = null!;
    private WebApplication _grpcApp = null!;

    public Worker(ILogger<Worker> logger, IHostApplicationLifetime appLifetime)
    {
        _logger = logger;
        _appLifetime = appLifetime;
    }


    private bool StartService()
    {
        if (!SettingsDir.CheckFiles())
        {
            Console.WriteLine("[SERVICE] settings file don't exist");
            return false;
        }
        SettingsHelper.LoadSettingsFile(State.Settings);
        StartGrpc();

        if (State.Settings.ConfId == null)
        {
            var totalDelay = 0;
            while (!State.IsOpen && totalDelay < MaxDelay)
            {
                Thread.Sleep(Delay);
                totalDelay += Delay;
            }

            if (!State.IsOpen)
            {
                Console.WriteLine("[SERVICE] delay before open passed");
                return false;
            }
        }
        else
        {
            ConfHelper.LoadConfFile(State.Settings.ConfId);
        }

        HardwareManager.Start();
        return true;
    }


    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        if (!StartService())
        {
            _appLifetime.StopApplication();
            return;
        }

        while (!stoppingToken.IsCancellationRequested)
        {
            HardwareManager.Update();

            Console.WriteLine("[SERVICE] update");

            await Task.Delay(1000, stoppingToken);
        }
    }

    public override Task StopAsync(CancellationToken cancellationToken)
    {
        Console.WriteLine("[SERVICE] StopAsync");
        
        StopGrpc();
        Update.SetAutoAll();
        HardwareManager.Stop();
        
        return base.StopAsync(cancellationToken);
    }

    private void StartGrpc()
    {
        var builder = WebApplication.CreateBuilder();
        builder.WebHost.ConfigureKestrel(options =>
        {
            options.Listen(_ip, Port,
                listenOptions => { listenOptions.Protocols = HttpProtocols.Http2; });
        });

        builder.Services.AddGrpc();
        _grpcApp = builder.Build();
        _grpcApp.MapGrpcService<CrossApi>();

        _chatJob = new Task(() => { _grpcApp.Run(); }, _cancellationToken);

        _chatJob.Start();
    }


    private void StopGrpc()
    {

        RunSafely(() => _grpcApp.StopAsync(_cancellationToken));
        // ReSharper disable once AccessToDisposedClosure
        RunSafely(() => _chatJob.Wait(_cancellationToken));
        // ReSharper disable once AccessToDisposedClosure
        RunSafely(() => _chatJob.Dispose());
    }

    private static void RunSafely(Action fun)
    {
        try
        {
            fun();
        }
        catch (Exception)
        {
            // ignored
        }
    }
}