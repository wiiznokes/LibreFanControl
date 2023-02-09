using System.Collections;
using HardwareDaemon.Hardware;

namespace HardwareDaemon;

internal static class Program
{
    private static readonly ArrayList HControls = new();
    private static readonly ArrayList HTemps = new();
    private static readonly ArrayList HFans = new();

    private static readonly ArrayList IControls = new();
    private static readonly ArrayList IBehaviors = new();
    private static readonly ArrayList ICustomTemps = new();

    private static void Main()
    {
        HardwareManager.Start(HControls, HTemps, HFans);

        
    }
}