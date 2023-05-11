using FanControlService.External.LmSensors;
using static FanControlService.External.LmSensors.LmSensorsWrapper;

namespace FanControlService.Hardware.Sensor;

public class LmSensor : BaseSensor
{
    private readonly unsafe SensorsChipName* _chip;
    private readonly unsafe SensorsFeature* _feat;
    private readonly int _subFeatureInputNumber;

    public unsafe LmSensor(string id, string name, SensorsChipName* chip, SensorsFeature* feat, SensorsFeatureType type)
        : base(id, name)
    {
        _chip = chip;
        _feat = feat;

        _subFeatureInputNumber = type switch
        {
            SensorsFeatureType.SensorsFeatureFan => LmSensors.get_sub_feature_number(_chip, _feat,
                SensorsSubFeatureType.SensorsSubFeatureFanInput),
            SensorsFeatureType.SensorsFeatureTemp => LmSensors.get_sub_feature_number(_chip, _feat,
                SensorsSubFeatureType.SensorsSubFeatureTempInput),
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };

        if (_subFeatureInputNumber < 0) throw new Exception("No sub feature was found");
    }


    public override unsafe void Update()
    {
        double value;
        var res = sensors_get_value(_chip, _subFeatureInputNumber, &value);

        if (res < 0)
        {
            Console.WriteLine("Error: Update sensor " + Name + ". Code: " + res);
            Value = 0;
        }
        else
        {
            Value = (int)value;
        }
    }
}