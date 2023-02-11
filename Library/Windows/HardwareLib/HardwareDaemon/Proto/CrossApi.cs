using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.Extensions.Logging;
using Proto.Generated.PConf;
using Proto.Generated.PCrossApi;
using Proto.Generated.PSettings;

namespace HardwareDaemon.Proto;


public class CrossApi: PCrossApi.PCrossApiBase
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
        return base.PClose(request, context);
    }

    public override Task<Empty> PChangeConf(PConf request, ServerCallContext context)
    {
        Console.WriteLine("service PChangeConf");
        return base.PChangeConf(request, context);
    }

    public override Task<Empty> PChangeSetting(PSettings request, ServerCallContext context)
    {
        Console.WriteLine("service PChangeSetting");
        return base.PChangeSetting(request, context);
    }

    public override Task<Empty> PSetControls(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetControls");
        return base.PSetControls(request, context);
    }

    public override Task<Empty> PSetFans(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetFans");
        return base.PSetFans(request, context);
    }

    public override Task<Empty> PSetTemps(PDeviceList request, ServerCallContext context)
    {
        Console.WriteLine("service PSetTemps");
        return base.PSetTemps(request, context);
    }

    public override Task<Empty> PUpdateControls(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateControls");
        return base.PUpdateControls(request, context);
    }

    public override Task<Empty> PUpdateTemps(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateTemps");
        return base.PUpdateTemps(request, context);
    }

    public override Task<Empty> PUpdateFans(PUpdateList request, ServerCallContext context)
    {
        Console.WriteLine("service PUpdateFans");
        return base.PUpdateFans(request, context);
    }
}