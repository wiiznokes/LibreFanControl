using System.Runtime.InteropServices;

namespace FanControlService.External.LmSensors;

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
};

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


    /* Load the configuration file and the detected chips list. If this
       returns a value unequal to zero, you are in trouble; you can not
       assume anything will be initialized properly. If you want to
       reload the configuration file, call sensors_cleanup() below before
       calling sensors_init() again. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int sensors_init(IntPtr f);


    /* Clean-up function: You can't access anything after
        this, until the next sensors_init() call! */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern void sensors_cleanup();


    /* This function returns all detected chips that match a given chip name,
       one by one. If no chip name is provided, all detected chips are returned.
       To start at the beginning of the list, use 0 for nr; NULL is returned if
       we are at the end of the list. Do not try to change these chip names, as
       they point to internal structures! */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsChipName* sensors_get_detected_chips(
        SensorsChipName* chip, int* nr);


    /* This returns all main features of a specific chip. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsFeature* sensors_get_features(SensorsChipName* chip, int* nr);


    /* This returns all sub features of a given main feature. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsSubFeature* sensors_get_all_subfeatures(SensorsChipName* chip,
        SensorsFeature* feat, int* nr);
    


    /* Read the value of a sub feature of a certain chip. Note that chip should not
     contain wildcard values! This function will return 0 on success, and <0
     on failure.  */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_get_value(SensorsChipName* chip, int subFeatNr,
        double* value);


    /* Set the value of a sub feature of a certain chip. Note that chip should not
       contain wildcard values! This function will return 0 on success, and <0
       on failure. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_set_value(SensorsChipName* chip, int subFeatNr,
        double value);
}