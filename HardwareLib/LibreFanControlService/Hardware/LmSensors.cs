using System.Runtime.InteropServices;
using LibreFanControlService.External.LmSensors;
using LibreFanControlService.Hardware.Control;
using LibreFanControlService.Hardware.Sensor;
using static LibreFanControlService.External.LmSensors.LmSensorsWrapper;

namespace LibreFanControlService.Hardware;

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
                var featLabelPtr = sensors_get_label(sensorsChipName, sensorsFeature);
                var featLabel = Marshal.PtrToStringAnsi(new IntPtr(featLabelPtr)) ?? "";
                    
                // Free the allocated memory
                Marshal.FreeCoTaskMem(new IntPtr(featLabelPtr));
                
                var featName = Marshal.PtrToStringAnsi(sensorsFeature->name);

                var id = chipName + "/" + featName;
                
                if (string.IsNullOrWhiteSpace(featLabel) || featLabel == featName)
                {
                    featLabel = id;
                }


                switch (sensorsFeature->type)
                {
                    case SensorsFeatureType.SensorsFeatureFan:
                        State.HFans.Add(new LmSensor(id, featLabel,
                            sensorsChipName, sensorsFeature, sensorsFeature->type));
                        break;
                    case SensorsFeatureType.SensorsFeatureTemp:
                        State.HTemps.Add(new LmSensor(id, featLabel,
                            sensorsChipName, sensorsFeature, sensorsFeature->type));
                        break;
                    case SensorsFeatureType.SensorsFeaturePwm:
                        State.HControls.Add(new LmControl(id, featLabel,
                            sensorsChipName, sensorsFeature));
                        break;
                    default:
                        continue;
                }
            }
        }
    }


    public static unsafe int get_sub_feature_number(SensorsChipName* chip, SensorsFeature* feat,
        SensorsSubFeatureType type)
    {
        SensorsSubFeature* subFeature;
        var nr = 0;

        while ((subFeature = sensors_get_all_subfeatures(chip, feat, &nr)) != null)
            if (subFeature->type == type)
                return subFeature->number;

        return -1;
    }
}