using Google.Protobuf.WellKnownTypes;
using Grpc.Core.Logging;
using Proto.Generated.PCrossApi;

namespace HardwareDaemon.Proto;


public class CrossApi: PCrossApi.PCrossApiBase
{

    public Task<Empty> SayHelloAsync(Empty request)
    {
        
        return Task.FromResult(new Empty());
    }
}