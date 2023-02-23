using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using HardwareDaemon.Hardware;
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
        Console.WriteLine("[SERVICE] open");

        State.IsOpen = true;
        
        var response = new POk
        {
            PIsSuccess = true
        };

        return Task.FromResult(response);
    }

    private static PHardwareList CreatePHardwareList(IEnumerable<BaseHardware> list)
    {
        var hardwareList = new PHardwareList();
        foreach (var hardware in list)
        {
            var pHardware = new PHardware
            {
                PName = hardware.Name,
                PId = hardware.Id
            };
            hardwareList.PHardwares.Add(pHardware);
        }

        return hardwareList;
    }

    public override Task<PHardwareList> PGetHardware(PHardwareTypeMessage request, ServerCallContext context)
    {
        Console.WriteLine("[SERVICE] getHardware: " + request.PType);

        var hardwareList = request.PType switch
        {
            PHardwareType.Control => CreatePHardwareList(State.HControls.Values),
            PHardwareType.Temp => CreatePHardwareList(State.HTemps.Values),
            PHardwareType.Fan => CreatePHardwareList(State.HFans.Values),
            _ => throw new ProtoException("unknown pHardware type")
        };
        
        Console.WriteLine("[SERVICE] " + hardwareList.PHardwares.Count);
        
        return Task.FromResult(hardwareList);
    }


    private static PUpdateList CreatePUpdate(PHardwareType type, IEnumerable<BaseHardware> list)
    {
        var updateList = new PUpdateList
        {
            PType = type
        };
        
        foreach (var hardware in list)
        {
            var update = new PUpdate
            {
                PIndex = hardware.Index,
                PValue = hardware.Value
            };
            updateList.PUpdates.Add(update);
        }

        return updateList;
    }

    public override Task PUpdate(Empty request, IServerStreamWriter<PUpdateList> responseStream, ServerCallContext context)
    {
        var updateList = CreatePUpdate(PHardwareType.Control, State.HControls.Values);
        responseStream.WriteAsync(updateList);
        
        updateList = CreatePUpdate(PHardwareType.Temp, State.HTemps.Values);
        responseStream.WriteAsync(updateList);
        
        updateList = CreatePUpdate(PHardwareType.Fan, State.HFans.Values);
        responseStream.WriteAsync(updateList);
        
        
        return Task.FromResult(responseStream.WriteAsync(updateList));
    }


    public override Task<Empty> PClose(Empty request, ServerCallContext context)
    {
        Console.WriteLine("[SERVICE] close");
        State.IsOpen = false;
        
        return Task.FromResult(new Empty());
    }
}