using FanControlService.External.LSensors;

namespace FanControlService.Hardware;

public class LmSensors
{


    public void Start()
    {
        LmSensorsWrapper.fetch_temp2();
    }
}