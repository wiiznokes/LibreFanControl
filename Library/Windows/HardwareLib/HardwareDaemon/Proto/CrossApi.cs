using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.Extensions.Logging;
using Proto.Generated.PConf;
using Proto.Generated.PCrossApi;
using Proto.Generated.PSettings;

namespace HardwareDaemon.Proto;

public class CrossApi : PCrossApi.PCrossApiBase
{
    private readonly ILogger<CrossApi> _logger;

    public CrossApi(ILogger<CrossApi> logger)
    {
        _logger = logger;
    }


    public override Task<Empty> POpen(Empty request, ServerCallContext context)
    {
        Console.WriteLine("service open");


        return Task.FromResult(request);
    }

    public override Task<Empty> PClose(Empty request, ServerCallContext context)
    {
        Console.WriteLine("service open");
        return Task.FromResult(request);
    }

    public override Task<Empty> PChangeConf(PConf request, ServerCallContext context)
    {
        Console.WriteLine("service PChangeConf");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PChangeSetting(PSettings request, ServerCallContext context)
    {
        Console.WriteLine("service PChangeSetting");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PSetControls(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetControls");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PSetFans(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetFans");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PSetTemps(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetTemps");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PUpdateControls(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateControls");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PUpdateTemps(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateTemps");
        return Task.FromResult(new Empty());
    }

    public override Task<Empty> PUpdateFans(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateFans");
        return Task.FromResult(new Empty());
    }
}