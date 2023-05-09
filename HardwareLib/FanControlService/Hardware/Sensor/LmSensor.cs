using static FanControlService.External.LmSensors.LmSensorsWrapper;

namespace FanControlService.Hardware.Sensor;

public class LmSensor : BaseSensor
{
    private readonly IntPtr _chip;
    private readonly IntPtr _feat;
    private readonly int _subFeatureInputNumber;

    public LmSensor(string id, string name, IntPtr chip, IntPtr feat, SensorsFeatureType type) : base(id, name)
    {
        _chip = chip;
        _feat = feat;
        
        _subFeatureInputNumber = type switch
        {
            SensorsFeatureType.SensorsFeatureFan => get_sub_feature_number(_chip, _feat,
                SensorsSubFeatureType.SensorsSubFeatureFanInput),
            SensorsFeatureType.SensorsFeatureTemp => get_sub_feature_number(_chip, _feat,
                SensorsSubFeatureType.SensorsSubFeatureTempInput),
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };

        if (_subFeatureInputNumber < 0)
        {
            throw new Exception("No sub feature was found");
        }
    }


    public override unsafe void Update()
    {
        double value;
        var res = sensors_get_value(_chip, _subFeatureInputNumber, &value);

        Value = res < 0 ? 0 : (int)value;
    }
}