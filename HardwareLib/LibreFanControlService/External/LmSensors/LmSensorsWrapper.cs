using System.Runtime.InteropServices;

namespace LibreFanControlService.External.LmSensors;

[StructLayout(LayoutKind.Sequential)]
public struct SensorsChipName
{
    public readonly IntPtr prefix;
    private readonly IntPtr bus;
    private readonly int addr;
    private readonly IntPtr path;
}

public enum SensorsFeatureType
{
    SensorsFeatureIn = 0x00,
    SensorsFeatureFan = 0x01,
    SensorsFeatureTemp = 0x02,
    SensorsFeaturePower = 0x03,
    SensorsFeatureEnergy = 0x04,
    SensorsFeatureCurr = 0x05,
    SensorsFeatureHumidity = 0x06,
    SensorsFeaturePwm = 0x07,
    SensorsFeatureMaxMain,
    SensorsFeatureVid = 0x10,
    SensorsFeatureIntrusion = 0x11,
    SensorsFeatureMaxOther,
    SensorsFeatureBeepEnable = 0x18,
    SensorsFeatureMax,
    SensorsFeatureUnknown = int.MaxValue
}

public enum SensorsSubFeatureType
{
    SensorsSubFeatureInInput = SensorsFeatureType.SensorsFeatureIn << 8,
    SensorsSubFeatureInMin,
    SensorsSubFeatureInMax,
    SensorsSubFeatureInLcrit,
    SensorsSubFeatureInCrit,
    SensorsSubFeatureInAverage,
    SensorsSubFeatureInLowest,
    SensorsSubFeatureInHighest,
    SensorsSubFeatureInAlarm = (SensorsFeatureType.SensorsFeatureIn << 8) | 0x80,
    SensorsSubFeatureInMinAlarm,
    SensorsSubFeatureInMaxAlarm,
    SensorsSubFeatureInBeep,
    SensorsSubFeatureInLcritAlarm,
    SensorsSubFeatureInCritAlarm,

    SensorsSubFeatureFanInput = SensorsFeatureType.SensorsFeatureFan << 8,
    SensorsSubFeatureFanMin,
    SensorsSubFeatureFanMax,
    SensorsSubFeatureFanAlarm = (SensorsFeatureType.SensorsFeatureFan << 8) | 0x80,
    SensorsSubFeatureFanFault,
    SensorsSubFeatureFanDiv,
    SensorsSubFeatureFanBeep,
    SensorsSubFeatureFanPulses,
    SensorsSubFeatureFanMinAlarm,
    SensorsSubFeatureFanMaxAlarm,

    SensorsSubFeatureTempInput = SensorsFeatureType.SensorsFeatureTemp << 8,
    SensorsSubFeatureTempMax,
    SensorsSubFeatureTempMaxHyst,
    SensorsSubFeatureTempMin,
    SensorsSubFeatureTempCrit,
    SensorsSubFeatureTempCritHyst,
    SensorsSubFeatureTempLcrit,
    SensorsSubFeatureTempEmergency,
    SensorsSubFeatureTempEmergencyHyst,
    SensorsSubFeatureTempLowest,
    SensorsSubFeatureTempHighest,
    SensorsSubFeatureTempMinHyst,
    SensorsSubFeatureTempLcritHyst,
    SensorsSubFeatureTempAlarm = (SensorsFeatureType.SensorsFeatureTemp << 8) | 0x80,
    SensorsSubFeatureTempMaxAlarm,
    SensorsSubFeatureTempMinAlarm,
    SensorsSubFeatureTempCritAlarm,
    SensorsSubFeatureTempFault,
    SensorsSubFeatureTempType,
    SensorsSubFeatureTempOffset,
    SensorsSubFeatureTempBeep,
    SensorsSubFeatureTempEmergencyAlarm,
    SensorsSubFeatureTempLcritAlarm,

    SensorsSubFeaturePowerAverage = SensorsFeatureType.SensorsFeaturePower << 8,
    SensorsSubFeaturePowerAverageHighest,
    SensorsSubFeaturePowerAverageLowest,
    SensorsSubFeaturePowerInput,
    SensorsSubFeaturePowerInputHighest,
    SensorsSubFeaturePowerInputLowest,
    SensorsSubFeaturePowerCap,
    SensorsSubFeaturePowerCapHyst,
    SensorsSubFeaturePowerMax,
    SensorsSubFeaturePowerCrit,
    SensorsSubFeaturePowerMin,
    SensorsSubFeaturePowerLcrit,
    SensorsSubFeaturePowerAverageInterval = (SensorsFeatureType.SensorsFeaturePower << 8) | 0x80,
    SensorsSubFeaturePowerAlarm,
    SensorsSubFeaturePowerCapAlarm,
    SensorsSubFeaturePowerMaxAlarm,
    SensorsSubFeaturePowerCritAlarm,
    SensorsSubFeaturePowerMinAlarm,
    SensorsSubFeaturePowerLcritAlarm,

    SensorsSubFeatureEnergyInput = SensorsFeatureType.SensorsFeatureEnergy << 8,

    SensorsSubFeatureCurrInput = SensorsFeatureType.SensorsFeatureCurr << 8,
    SensorsSubFeatureCurrMin,
    SensorsSubFeatureCurrMax,
    SensorsSubFeatureCurrLcrit,
    SensorsSubFeatureCurrCrit,
    SensorsSubFeatureCurrAverage,
    SensorsSubFeatureCurrLowest,
    SensorsSubFeatureCurrHighest,
    SensorsSubFeatureCurrAlarm = (SensorsFeatureType.SensorsFeatureCurr << 8) | 0x80,
    SensorsSubFeatureCurrMinAlarm,
    SensorsSubFeatureCurrMaxAlarm,
    SensorsSubFeatureCurrBeep,
    SensorsSubFeatureCurrLcritAlarm,
    SensorsSubFeatureCurrCritAlarm,

    SensorsSubFeatureHumidityInput = SensorsFeatureType.SensorsFeatureHumidity << 8,

    SensorsSubFeaturePwmIo = SensorsFeatureType.SensorsFeaturePwm << 8,
    SensorsSubFeaturePwmFreq,
    SensorsSubFeaturePwmEnable = (SensorsFeatureType.SensorsFeaturePwm << 8) | 0x80,
    SensorsSubFeaturePwmMode,

    SensorsSubFeatureVid = SensorsFeatureType.SensorsFeatureVid << 8,

    SensorsSubFeatureIntrusionAlarm = SensorsFeatureType.SensorsFeatureIntrusion << 8,
    SensorsSubFeatureIntrusionBeep,

    SensorsSubFeatureBeepEnable = SensorsFeatureType.SensorsFeatureBeepEnable << 8,

    SensorsSubFeatureUnknown = int.MaxValue
}

[StructLayout(LayoutKind.Sequential)]
public struct SensorsFeature
{
    public readonly IntPtr name;
    private readonly int number;
    public readonly SensorsFeatureType type;
    private readonly IntPtr first_sub_feature;
    private readonly IntPtr padding1;
}

[StructLayout(LayoutKind.Sequential)]
public struct SensorsSubFeature
{
    private readonly IntPtr name;
    public readonly int number;
    public readonly SensorsSubFeatureType type;
    private readonly int mapping;
    private readonly uint flags;
}

public static class LmSensorsWrapper
{
    private const string DllPath = "External/LmSensors/libsensors.so.5.0.0";


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int sensors_init(IntPtr f);


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern void sensors_cleanup();


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsChipName* sensors_get_detected_chips(
        SensorsChipName* chip, int* nr);


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsFeature* sensors_get_features(SensorsChipName* chip, int* nr);


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsSubFeature* sensors_get_all_subfeatures(SensorsChipName* chip,
        SensorsFeature* feat, int* nr);


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_get_value(SensorsChipName* chip, int subFeatNr,
        double* value);


    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_set_value(SensorsChipName* chip, int subFeatNr,
        double value);
    
    
    
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_snprintf_chip_name(char* str, int size,
        SensorsChipName* chip);
    
    
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe char* sensors_get_label(SensorsChipName* name,
        SensorsFeature* feature);
}