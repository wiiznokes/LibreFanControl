using FanControlService.External.LmSensors;

namespace FanControlService.Hardware.Control;

using static LmSensorsWrapper;

public class LmControl : BaseControl
{
    private readonly unsafe SensorsChipName* _chip;
    private readonly unsafe SensorsFeature* _feat;
    private readonly int _subFeatureIoNumber;
    private readonly int _subFeatureEnableNumber;

    public unsafe LmControl(string id, string name, SensorsChipName* chip, SensorsFeature* feat) : base(id, name)
    {
        _chip = chip;
        _feat = feat;

        _subFeatureIoNumber = LmSensors.get_sub_feature_number(_chip, _feat,
            SensorsSubfeatureType.SensorsSubfeaturePwmIo);

        _subFeatureEnableNumber = LmSensors.get_sub_feature_number(_chip, _feat,
            SensorsSubfeatureType.SensorsSubfeaturePwmEnable);
    }

    public override unsafe void Update()
    {
        double value;
        var res = sensors_get_value(_chip, _subFeatureIoNumber, &value);

        Value = res < 0 ? 0 : (int)(value / 255 * 100);
    }

    public override unsafe bool SetSpeed(int value)
    {
        int res;
        // need to enable manual control of pwn
        if (!IsSetSpeed)
        {
            res = sensors_set_value(_chip, _subFeatureEnableNumber, 1);
            if (res < 0)
            {
                Console.WriteLine("Error: can't enable control " + Name + ". Code: " + res);
                return false;
            }
        }

        var valueCalibrated = value * 2.55;
        res = sensors_set_value(_chip, _subFeatureIoNumber, valueCalibrated);
        if (res < 0)
        {
            Console.WriteLine("Error: set speed on control " + Name + ". Code: " + res);
            return false;
        }

        IsSetSpeed = true;
        Console.WriteLine("[SERVICE] set control: " + Name + " = " + value);
        return true;
    }


    public override unsafe bool SetAuto()
    {
        if (IsSetSpeed == false)
            return true;

        var res = sensors_set_value(_chip, _subFeatureEnableNumber, State.Settings.ValueDisableControl);
        if (res < 0)
        {
            Console.WriteLine("Error: can't disable control " + Name + ". Code: " + res);
            return false;
        }

        IsSetSpeed = false;
        Console.WriteLine("[SERVICE] set control to auto: " + Name);
        return true;
    }
}