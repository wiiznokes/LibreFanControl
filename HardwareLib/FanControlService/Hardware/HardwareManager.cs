namespace FanControlService.Hardware;

public static class HardwareManager
{
#if WINDOWS
    private static readonly Lhm Lhm = new();
#else
    private static readonly LmSensors LmSensors = new();
#endif


    public static void Start()
    {
        
#if WINDOWS
        Lhm.Start();
        Lhm.CreateHardware();
#else
        LmSensors.Start();
        LmSensors.CreateHardware();
#endif
    }

    public static void Stop()
    {
        
#if WINDOWS
        Lhm.Stop();
#else
        LmSensors.Stop();
#endif
    }


    public static void Update()
    {
#if WINDOWS
        Lhm.Update();
#endif
    }
}