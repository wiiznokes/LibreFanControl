using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Microsoft.Extensions.Logging;
using Proto.Generated.PCrossApi;

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
        return base.POpen(request, context);
    }
}