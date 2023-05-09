namespace FanControlService.Hardware.Control;
using static FanControlService.External.LmSensors.LmSensorsWrapper;
public class LmControl : BaseControl
{
    private readonly IntPtr _chip;
    private readonly IntPtr _feat;
    private readonly int _subFeatureIoNumber;
    private readonly int _subFeatureEnableNumber;
    
    public LmControl(string id, string name, IntPtr chip, IntPtr feat) : base(id, name)
    {
        _chip = chip;
        _feat = feat;

        _subFeatureIoNumber = get_sub_feature_number(_chip, _feat,
            SensorsSubFeatureType.SensorsSubFeaturePwmIo);
        
        _subFeatureEnableNumber = get_sub_feature_number(_chip, _feat,
            SensorsSubFeatureType.SensorsSubFeaturePwmEnable);
    }
}