using System.Net;
using FanControlService.Hardware;
using FanControlService.Proto;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using static FanControlService.State.ServiceState;

namespace FanControlService;

public class Worker : BackgroundService
{
    private const int Port = 5002;

    private static readonly CancellationTokenSource CancellationTokenSource = new();
    private readonly IHostApplicationLifetime _appLifetime;
    private readonly CancellationToken _cancellationToken = CancellationTokenSource.Token;
    private readonly IPAddress _ip = IPAddress.Parse("127.0.0.1");

    private readonly ILogger<Worker> _logger;

    // use because sometime, we stay in the while loop even if
    // stoppingToken.IsCancellationRequested = true
    private bool _cancellationRequested;

    private Task _chatJob = null!;
    private WebApplication _grpcApp = null!;

    public Worker(ILogger<Worker> logger, IHostApplicationLifetime appLifetime)
    {
        _logger = logger;
        _appLifetime = appLifetime;
    }
    

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        if (!SettingsDir.CheckFiles())
        {
            Console.WriteLine("[SERVICE] settings file don't exist");
            AutoCancel();
            return;
        }
        
        HardwareManager.Start();
        SettingsHelper.LoadSettingsFile();

        StartGrpc();
        
        if (State.Settings.ConfId != null)
        {
            Console.WriteLine("[SERVICE] load config");
            
            ConfHelper.LoadConfFile(State.Settings.ConfId);
            Update.CreateUpdateList();
        }

        
        while (!stoppingToken.IsCancellationRequested && !_cancellationRequested)
        {
            await Task.Delay(State.Settings.UpdateDelay * 1000, stoppingToken);

            CheckChange();

            if (State.Settings.ConfId == null)
                continue;


            Update.UpdateUpdateList();
        }

        if (!_cancellationRequested)
            AutoCancel();
    }


    private static void CheckChange()
    {
        if (SettingsHasChange)
        {
            SettingsHelper.LoadSettingsFile();
            SettingsHasChange = false;
        }

        if (SettingsAndConfHasChange)
        {
            Update.SetAutoAll();
            SettingsHelper.LoadSettingsFile();

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
        _cancellationRequested = true;
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