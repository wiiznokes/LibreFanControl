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


    public override Task PUpdate(Empty request, IServerStreamWriter<PUpdateList> responseStream, ServerCallContext context)
    {
        var updateList = new PUpdateList
        {
            PType = PHardwareType.Control
        };
        
        for (var i = 0; i < State.HControls.Count; i++)
        {
            var update = new PUpdate
            {
                PIndex = State.HControls[i].Index,
                PValue = State.HControls[i].Value
            };
            updateList.PUpdates.Add(update);
        }
        

        return Task.FromResult(responseStream.WriteAsync(updateList));
    }


    public override Task<Empty> PClose(Empty request, ServerCallContext context)
    {
        Console.WriteLine("service closed");
        State.IsOpen = false;
        
        return Task.FromResult(new Empty());
    }
}