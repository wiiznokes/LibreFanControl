using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Proto.Generated.PCrossApi;

namespace HardwareDaemon.Proto;

public class CrossApi : PCrossApi.PCrossApiBase
{
    private readonly ILogger<CrossApi> _logger;

    public CrossApi(ILogger<CrossApi> logger)
    {
        _logger = logger;
    }


    public override Task<POk> POpen(Empty request, ServerCallContext context)
    {
        Console.WriteLine("service open");

        State.IsOpen = true;
        
        var response = new POk
        {
            PIsSuccess = true
        };

        return Task.FromResult(response);
    }
}