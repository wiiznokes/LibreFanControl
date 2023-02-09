using System.Collections;
using HardwareDaemon.Hardware;
using HardwareDaemon.Hardware.Control;

namespace HardwareDaemon;

internal static class Program
{
    private static readonly ArrayList Controls = new();
    private static readonly ArrayList Fans = new();
    private static readonly ArrayList Temps = new();

    private static void Main()
    {
        HardwareManager.Start(Controls, Fans, Temps);

        foreach (BaseDevice control in Controls)
        {
            Console.WriteLine(control.Name);
        }
        foreach (BaseDevice control in Fans)
        {
            Console.WriteLine(control.Name);
        }
        foreach (BaseDevice control in Temps)
        {
            Console.WriteLine(control.Name);
        }
    }
}