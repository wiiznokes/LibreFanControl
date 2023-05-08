using FanControlService.External.LSensors;

namespace FanControlService.Hardware.Sensor;

public class LmSensor : BaseSensor
{
    
    private readonly IntPtr _chip;
    private readonly IntPtr _feat;
    private readonly IntPtr _sub_feature_value;
    private readonly int _sub_feature_value_number;
    
    public unsafe LmSensor(string id, string name, IntPtr chip, IntPtr feat) : base(id, name)
    {
        _chip = chip;
        _feat = feat;
        var subFeatCount = 0;
        _sub_feature_value = LmSensorsWrapper.sensors_get_all_subfeatures(_chip, _feat, &subFeatCount);
        _sub_feature_value_number = LmSensorsWrapper.get_sub_feature_number(_sub_feature_value);
    }


    public override unsafe void Update()
    {
        double value;
        var res = LmSensorsWrapper.sensors_get_value(_chip, _sub_feature_value_number, &value);
        
        Value = res < 0 ? 0 : (int) value;
    }
}