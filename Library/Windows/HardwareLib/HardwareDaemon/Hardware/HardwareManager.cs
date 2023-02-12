namespace HardwareDaemon.Hardware;

public static class HardwareManager
{
    private static readonly Lhm Lhm = new();

    public static void Start()
    {
        Lhm.Start();
        Lhm.CreateHardware();
    }

    public static void Stop()
    {
        Lhm.Stop();
    }


    public static void Update()
    {
        Lhm.Update();
    }
}