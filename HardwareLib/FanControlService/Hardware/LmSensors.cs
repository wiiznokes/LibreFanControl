using System.Runtime.InteropServices;
using FanControlService.External.LmSensors;
using FanControlService.Hardware.Control;
using FanControlService.Hardware.Sensor;
using static FanControlService.External.LmSensors.LmSensorsWrapper;

namespace FanControlService.Hardware;

public class LmSensors
{
    private bool _isStarted;

    public void Start()
    {
        if (_isStarted)
            return;

        var res = sensors_init(IntPtr.Zero);
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

        sensors_cleanup();
        _isStarted = false;
    }


    public unsafe void CreateHardware()
    {
        if (!_isStarted)
            return;


        IntPtr sensorsChipName;
        var chipCount = 0;
        while ((sensorsChipName = sensors_get_detected_chips(IntPtr.Zero, &chipCount))
               != IntPtr.Zero)
        {
            var chipNamePtr = get_chip_name(sensorsChipName);
            var chipName = Marshal.PtrToStringAnsi(chipNamePtr) ?? "chip" + chipCount;

            IntPtr sensorsFeature;
            var featCount = 0;
            while ((sensorsFeature = sensors_get_features(sensorsChipName, &featCount))
                   != IntPtr.Zero)
            {
                var featureType =
                    (SensorsFeatureType)get_feature_type(sensorsFeature);
                

                if (featureType == SensorsFeatureType.SensorsFeatureUndefined)
                    continue;
                
                
                Console.WriteLine(featureType.ToString());

                var featNamePtr = get_feature_name(sensorsFeature);
                var featName = Marshal.PtrToStringAnsi(featNamePtr) ?? DefaultName(featureType) + featCount;

                var id = chipName + "/" + featName;
                switch (featureType)
                {
                    case SensorsFeatureType.SensorsFeaturePwm:
                        State.HControls.Add(new LmControl(id, id,
                            sensorsChipName, sensorsFeature));
                        break;
                    case SensorsFeatureType.SensorsFeatureFan:
                        State.HFans.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature, featureType));
                        break;
                    case SensorsFeatureType.SensorsFeatureTemp:
                        State.HTemps.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature, featureType));
                        break;
                    default:
                        continue;
                }
            }
        }
    }

    private string DefaultName(SensorsFeatureType featureType)
    {
        return featureType switch
        {
            SensorsFeatureType.SensorsFeaturePwm => "Control",
            SensorsFeatureType.SensorsFeatureFan => "Fan",
            SensorsFeatureType.SensorsFeatureTemp => "Temp",
            _ => throw new ArgumentOutOfRangeException(nameof(featureType), featureType, null)
        };
    }
}