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

public enum SensorsSubfeatureType
{
    SensorsSubfeatureInInput = SensorsFeatureType.SensorsFeatureIn << 8,
    SensorsSubfeatureInMin,
    SensorsSubfeatureInMax,
    SensorsSubfeatureInLcrit,
    SensorsSubfeatureInCrit,
    SensorsSubfeatureInAverage,
    SensorsSubfeatureInLowest,
    SensorsSubfeatureInHighest,
    SensorsSubfeatureInAlarm = (SensorsFeatureType.SensorsFeatureIn << 8) | 0x80,
    SensorsSubfeatureInMinAlarm,
    SensorsSubfeatureInMaxAlarm,
    SensorsSubfeatureInBeep,
    SensorsSubfeatureInLcritAlarm,
    SensorsSubfeatureInCritAlarm,

    SensorsSubfeatureFanInput = SensorsFeatureType.SensorsFeatureFan << 8,
    SensorsSubfeatureFanMin,
    SensorsSubfeatureFanMax,
    SensorsSubfeatureFanAlarm = (SensorsFeatureType.SensorsFeatureFan << 8) | 0x80,
    SensorsSubfeatureFanFault,
    SensorsSubfeatureFanDiv,
    SensorsSubfeatureFanBeep,
    SensorsSubfeatureFanPulses,
    SensorsSubfeatureFanMinAlarm,
    SensorsSubfeatureFanMaxAlarm,

    SensorsSubfeatureTempInput = SensorsFeatureType.SensorsFeatureTemp << 8,
    SensorsSubfeatureTempMax,
    SensorsSubfeatureTempMaxHyst,
    SensorsSubfeatureTempMin,
    SensorsSubfeatureTempCrit,
    SensorsSubfeatureTempCritHyst,
    SensorsSubfeatureTempLcrit,
    SensorsSubfeatureTempEmergency,
    SensorsSubfeatureTempEmergencyHyst,
    SensorsSubfeatureTempLowest,
    SensorsSubfeatureTempHighest,
    SensorsSubfeatureTempMinHyst,
    SensorsSubfeatureTempLcritHyst,
    SensorsSubfeatureTempAlarm = (SensorsFeatureType.SensorsFeatureTemp << 8) | 0x80,
    SensorsSubfeatureTempMaxAlarm,
    SensorsSubfeatureTempMinAlarm,
    SensorsSubfeatureTempCritAlarm,
    SensorsSubfeatureTempFault,
    SensorsSubfeatureTempType,
    SensorsSubfeatureTempOffset,
    SensorsSubfeatureTempBeep,
    SensorsSubfeatureTempEmergencyAlarm,
    SensorsSubfeatureTempLcritAlarm,

    SensorsSubfeaturePowerAverage = SensorsFeatureType.SensorsFeaturePower << 8,
    SensorsSubfeaturePowerAverageHighest,
    SensorsSubfeaturePowerAverageLowest,
    SensorsSubfeaturePowerInput,
    SensorsSubfeaturePowerInputHighest,
    SensorsSubfeaturePowerInputLowest,
    SensorsSubfeaturePowerCap,
    SensorsSubfeaturePowerCapHyst,
    SensorsSubfeaturePowerMax,
    SensorsSubfeaturePowerCrit,
    SensorsSubfeaturePowerMin,
    SensorsSubfeaturePowerLcrit,
    SensorsSubfeaturePowerAverageInterval = (SensorsFeatureType.SensorsFeaturePower << 8) | 0x80,
    SensorsSubfeaturePowerAlarm,
    SensorsSubfeaturePowerCapAlarm,
    SensorsSubfeaturePowerMaxAlarm,
    SensorsSubfeaturePowerCritAlarm,
    SensorsSubfeaturePowerMinAlarm,
    SensorsSubfeaturePowerLcritAlarm,

    SensorsSubfeatureEnergyInput = SensorsFeatureType.SensorsFeatureEnergy << 8,

    SensorsSubfeatureCurrInput = SensorsFeatureType.SensorsFeatureCurr << 8,
    SensorsSubfeatureCurrMin,
    SensorsSubfeatureCurrMax,
    SensorsSubfeatureCurrLcrit,
    SensorsSubfeatureCurrCrit,
    SensorsSubfeatureCurrAverage,
    SensorsSubfeatureCurrLowest,
    SensorsSubfeatureCurrHighest,
    SensorsSubfeatureCurrAlarm = (SensorsFeatureType.SensorsFeatureCurr << 8) | 0x80,
    SensorsSubfeatureCurrMinAlarm,
    SensorsSubfeatureCurrMaxAlarm,
    SensorsSubfeatureCurrBeep,
    SensorsSubfeatureCurrLcritAlarm,
    SensorsSubfeatureCurrCritAlarm,

    SensorsSubfeatureHumidityInput = SensorsFeatureType.SensorsFeatureHumidity << 8,

    SensorsSubfeaturePwmIo = SensorsFeatureType.SensorsFeaturePwm << 8,
    SensorsSubfeaturePwmFreq,
    SensorsSubfeaturePwmEnable = (SensorsFeatureType.SensorsFeaturePwm << 8) | 0x80,
    SensorsSubfeaturePwmMode,

    SensorsSubfeatureVid = SensorsFeatureType.SensorsFeatureVid << 8,

    SensorsSubfeatureIntrusionAlarm = SensorsFeatureType.SensorsFeatureIntrusion << 8,
    SensorsSubfeatureIntrusionBeep,

    SensorsSubfeatureBeepEnable = SensorsFeatureType.SensorsFeatureBeepEnable << 8,

    SensorsSubfeatureUnknown = int.MaxValue
};

[StructLayout(LayoutKind.Sequential)]
public struct SensorsFeature
{
    public readonly IntPtr name;
    private readonly int number;
    public readonly SensorsFeatureType type;
    private IntPtr first_subfeature;
    private IntPtr padding1;
}

[StructLayout(LayoutKind.Sequential)]
public struct SensorsSubFeature
{
    public readonly IntPtr name;
    public readonly int number;
    public readonly SensorsSubfeatureType type;
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
        SensorsChipName* match, int* nr);


    /* This returns all main features of a specific chip. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsFeature* sensors_get_features(SensorsChipName* name, int* nr);


    /* This returns all subfeatures of a given main feature. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsSubFeature* sensors_get_all_subfeatures(SensorsChipName* name,
        SensorsFeature* feature, int* nr);


    /* CHIP **************/

    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern IntPtr get_chip_name(sensors_chip_name chip);


    /* FEATURE **************/

    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern IntPtr get_feature_name(sensors_feature feat);
    //
    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern int get_feature_type(sensors_feature feat);
    //
    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern int get_feature_number(sensors_feature feat);


    /* SUB_FEATURE **************/

    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern IntPtr get_sub_feature_name(sensors_subfeature sub_feat);
    //
    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern int get_sub_feature_type(sensors_subfeature sub_feat);
    //
    // /*
    //  * return -1 if no sub_feature with this type is found
    //  */
    // [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    // public static extern int get_sub_feature_number(sensors_chip_name chip, sensors_feature feat,
    //     SensorsSubFeatureType type);


    /* Read the value of a subfeature of a certain chip. Note that chip should not
     contain wildcard values! This function will return 0 on success, and <0
     on failure.  */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_get_value(SensorsChipName* name, int subfeatNr,
        double* value);


    /* Set the value of a subfeature of a certain chip. Note that chip should not
       contain wildcard values! This function will return 0 on success, and <0
       on failure. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_set_value(SensorsChipName* name, int subfeatNr,
        double value);
}