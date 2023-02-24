using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using HardwareDaemon.Hardware;
using Proto.Generated.PCrossApi;
using static HardwareDaemon.State.ServiceState;

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

        IsOpen = true;

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

    public override async Task PStartStream(Empty request, IServerStreamWriter<PUpdateList> responseStream,
        ServerCallContext context)
    {
        while (!context.CancellationToken.IsCancellationRequested && IsOpen)
        {
            HardwareManager.Update();
            Update.UpdateAllSensors();

            var updateList = CreatePUpdate(PHardwareType.Control, State.HControls.Values);

            await responseStream.WriteAsync(updateList, context.CancellationToken);

            updateList = CreatePUpdate(PHardwareType.Temp, State.HTemps.Values);
            await responseStream.WriteAsync(updateList, context.CancellationToken);

            updateList = CreatePUpdate(PHardwareType.Fan, State.HFans.Values);
            await responseStream.WriteAsync(updateList, context.CancellationToken);

            await Task.Delay(State.Settings.UpdateDelay * 1000, context.CancellationToken);
        }
    }


    public override Task<Empty> PCloseStream(Empty request, ServerCallContext context)
    {
        Console.WriteLine("[SERVICE] close");
        IsOpen = false;

        return Task.FromResult(new Empty());
    }

    public override Task<POk> PSettingsAndConfChange(Empty request, ServerCallContext context)
    {
        Console.WriteLine("[SERVICE] confChange");

        SettingsAndConfHasChange = true;

        var response = new POk
        {
            PIsSuccess = true
        };

        return Task.FromResult(response);
    }

    public override Task<POk> PSettingsChange(Empty request, ServerCallContext context)
    {
        Console.WriteLine("[SERVICE] settings Change");

        SettingsHasChange = true;

        var response = new POk
        {
            PIsSuccess = true
        };

        return Task.FromResult(response);
    }
}