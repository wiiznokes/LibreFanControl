namespace FanControlService.Hardware;

public static class HardwareManager
{
    private static readonly Lhm Lhm = new();
    private static readonly LmSensors LmSensors = new();

    public static void Start()
    {
        LmSensors.Start();
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