using System.Collections;
using HardwareDaemon.Hardware;
using HardwareDaemon.Model;
using HardwareDaemon.Proto;

namespace HardwareDaemon;

internal static class Program
{
    private static readonly ArrayList HControls = new();
    private static readonly ArrayList HTemps = new();
    private static readonly ArrayList HFans = new();

    private static readonly ArrayList Controls = new();
    private static readonly ArrayList Behaviors = new();
    private static readonly ArrayList CustomTemps = new();

    private static readonly ArrayList UpdateList = new();

    private static readonly Settings Settings = SettingsHelper.LoadSettingsFile();

    private static void Main()
    {
        HardwareManager.Start(HControls, HTemps, HFans);

        var confId = Settings.ConfId;
        if (confId == null) return;


        ConfHelper.LoadConfFile(confId, Controls, Behaviors, CustomTemps);

        Update.CreateUpdateList(UpdateList,
            HControls,
            HTemps,
            Controls,
            Behaviors,
            CustomTemps
        );

        Update.UpdateUpdateList(UpdateList);
    }
}