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

    private static readonly ArrayList IControls = new();
    private static readonly ArrayList IBehaviors = new();
    private static readonly ArrayList ICustomTemps = new();

    private static readonly Settings _settings = SettingsHelper.LoadSettingsFile();

    private static void Main()
    {
        HardwareManager.Start(HControls, HTemps, HFans);

        Console.WriteLine(_settings.ConfId);
        Console.WriteLine(_settings.UpdateDelay);
    }
}