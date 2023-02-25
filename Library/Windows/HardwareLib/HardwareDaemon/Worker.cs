using System.Net;
using HardwareDaemon.Hardware;
using HardwareDaemon.Proto;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using static HardwareDaemon.State.ServiceState;

namespace HardwareDaemon;

public class Worker : BackgroundService
{
    private const int Port = 5002;

    private const int MaxDelay = 10000;
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

        HardwareManager.Start();
        SettingsHelper.LoadSettingsFile(State.Settings);

        StartGrpc();

        if (State.Settings.ConfId == null)
        {
            var totalDelay = 0;
            while (!IsOpen && totalDelay < MaxDelay)
            {
                Thread.Sleep(Delay);
                totalDelay += Delay;
            }

            if (IsOpen) return true;

            Console.WriteLine("[SERVICE] delay before open passed");
            return false;
        }

        ConfHelper.LoadConfFile(State.Settings.ConfId);
        Update.CreateUpdateList();

        return true;
    }


    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        if (!StartService()) AutoCancel();

        while (!stoppingToken.IsCancellationRequested)
        {
            await Task.Delay(State.Settings.UpdateDelay * 1000, stoppingToken);

            CheckChange();

            if (!IsOpen && State.Settings.ConfId == null && State.UpdateList.IsEmpty)
            {
                Console.WriteLine("[SERVICE] stop service");
                break;
            }


            if (State.Settings.ConfId == null)
                continue;

            Console.WriteLine("[SERVICE] update");

            Update.UpdateUpdateList();
        }

        AutoCancel();
    }


    private static void CheckChange()
    {
        if (SettingsHasChange)
        {
            SettingsHelper.LoadSettingsFile(State.Settings);
            SettingsHasChange = false;
        }

        if (SettingsAndConfHasChange)
        {
            Update.SetAutoAll();
            SettingsHelper.LoadSettingsFile(State.Settings);

            if (State.Settings.ConfId != null)
            {
                ConfHelper.LoadConfFile(State.Settings.ConfId);
                Update.CreateUpdateList();
            }

            SettingsAndConfHasChange = false;
        }
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

    private void AutoCancel()
    {
        _appLifetime.StopApplication();
    }


    public override Task StopAsync(CancellationToken cancellationToken)
    {
        Console.WriteLine("[SERVICE] StopAsync");

        StopGrpc();
        Update.SetAutoAll();
        HardwareManager.Stop();

        return base.StopAsync(cancellationToken);
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