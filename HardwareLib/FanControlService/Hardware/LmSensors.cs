using System.Runtime.InteropServices;
using FanControlService.External.LmSensors;
using FanControlService.Hardware.Control;
using FanControlService.Hardware.Sensor;
using static FanControlService.External.LmSensors.LmSensorsWrapper;
using SensorsFeatureType = FanControlService.External.LmSensors.SensorsFeatureType;

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


        SensorsChipName* sensorsChipName;
        var chipCount = 0;
        while ((sensorsChipName = sensors_get_detected_chips(null, &chipCount))
               != null)
        {
            var chipName = Marshal.PtrToStringAnsi(sensorsChipName->prefix);

            SensorsFeature* sensorsFeature;
            var featCount = 0;
            while ((sensorsFeature = sensors_get_features(sensorsChipName, &featCount))
                   != null)
            {
                var featName = Marshal.PtrToStringAnsi(sensorsFeature->name);

                var id = chipName + "/" + featName;


                switch (sensorsFeature->type)
                {
                    case SensorsFeatureType.SensorsFeatureFan:
                        State.HFans.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature, sensorsFeature->type));
                        break;
                    case SensorsFeatureType.SensorsFeatureTemp:
                        State.HTemps.Add(new LmSensor(id, id,
                            sensorsChipName, sensorsFeature, sensorsFeature->type));
                        break;
                    case SensorsFeatureType.SensorsFeaturePwm:
                        State.HControls.Add(new LmControl(id, id,
                            sensorsChipName, sensorsFeature));
                        break;
                    default:
                        continue;
                }
            }
        }
    }


    public static unsafe int get_sub_feature_number(SensorsChipName* chip, SensorsFeature* feat,
        SensorsSubfeatureType type)
    {
        SensorsSubFeature* subFeature;
        var nr = 0;

        while ((subFeature = sensors_get_all_subfeatures(chip, feat, &nr)) != null)
            if (subFeature->type == type)
                return subFeature->number;

        return -1;
    }
}