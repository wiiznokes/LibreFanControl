using LibreFanControlService;
using Microsoft.Extensions.Hosting.Internal;

using var host = Host.CreateDefaultBuilder(args)
    .UseWindowsService(config => { config.ServiceName = "LibreFanControlService"; })
    .ConfigureServices((_, services) =>
    {
        services.AddSingleton<ConsoleLifetime>();
        services.AddHostedService<Worker>();
    })
    .Build();

await host.RunAsync();