using System.Runtime.InteropServices;
using FanControlService.External.LSensors;
using FanControlService.Hardware.Sensor;

namespace FanControlService.Hardware;

public class LmSensors
{
    private bool _isStarted;
    
    public void Start()
    {
        if (_isStarted)
            return;
        
        var res = LmSensorsWrapper.sensors_init(IntPtr.Zero);
        if (res < 0)
        {
            Console.WriteLine("Error sensors_init: " + res);
            return;
        }
        _isStarted = true;
    }
    
    public void Stop()
    {
        if (!_isStarted)
            return;
        
        LmSensorsWrapper.sensors_cleanup();
        _isStarted = false;
    }
    

    public unsafe void CreateHardware()
    {
        if (!_isStarted)
            return;
        
        
        IntPtr sensorsChipName;
        var chipCount = 0;
        while ((sensorsChipName = LmSensorsWrapper.sensors_get_detected_chips(IntPtr.Zero, &chipCount))
               != IntPtr.Zero)
        {
            var chipNamePtr = LmSensorsWrapper.get_chip_name(sensorsChipName);
            var chipName = Marshal.PtrToStringAnsi(chipNamePtr) ?? "chip" + chipCount;
            
            IntPtr sensorsFeature;
            var featCount = 0;
            while ((sensorsFeature = LmSensorsWrapper.sensors_get_features(sensorsChipName, &featCount))
                   != IntPtr.Zero)
            {

                var featureType = (LmSensorsWrapper.SensorsFeatureType) LmSensorsWrapper.get_feature_type(sensorsFeature);
                
                if (featureType == LmSensorsWrapper.SensorsFeatureType.Undefined)
                    continue;

                var featNamePtr = LmSensorsWrapper.get_feature_name(sensorsFeature);
                var featName = Marshal.PtrToStringAnsi(featNamePtr) ?? "fan" + featCount;

                var id = chipName + "/" + featName;
                switch (featureType)
                {
                    case LmSensorsWrapper.SensorsFeatureType.SensorsFeatureFan:
                        State.HFans.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature));
                        break;
                    case LmSensorsWrapper.SensorsFeatureType.SensorsFeatureTemp:
                        State.HTemps.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature));
                        break;
                    default:
                        continue;
                }

            }
        }
        
    }

    
}