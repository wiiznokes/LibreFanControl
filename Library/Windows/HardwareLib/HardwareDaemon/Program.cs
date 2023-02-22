using HardwareDaemon;
using Microsoft.Extensions.Hosting.Internal;

using var host = Host.CreateDefaultBuilder(args)
    .UseWindowsService(config => { config.ServiceName = "FanControlService"; })
    .ConfigureServices((_, services) =>
    {
        services.AddSingleton<ConsoleLifetime>();
        services.AddHostedService<Worker>();
    })
    .Build();

await host.RunAsync();