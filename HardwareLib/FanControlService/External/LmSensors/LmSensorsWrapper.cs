using System.Runtime.InteropServices;

namespace FanControlService.External.LmSensors;


[StructLayout(LayoutKind.Sequential)]
public struct SensorsChipName
{
    [MarshalAs(UnmanagedType.LPStr)] public string prefix;
    public IntPtr bus;
    public int addr;
    [MarshalAs(UnmanagedType.LPStr)] public string path;
}

public enum SensorsFeatureType : int
{
    SENSORS_FEATURE_IN = 0x00,
    SENSORS_FEATURE_FAN = 0x01,
    SENSORS_FEATURE_TEMP = 0x02,
    SENSORS_FEATURE_POWER = 0x03,
    SENSORS_FEATURE_ENERGY = 0x04,
    SENSORS_FEATURE_CURR = 0x05,
    SENSORS_FEATURE_HUMIDITY = 0x06,
    SENSORS_FEATURE_PWM = 0x07,
    SENSORS_FEATURE_MAX_MAIN,
    SENSORS_FEATURE_VID = 0x10,
    SENSORS_FEATURE_INTRUSION = 0x11,
    SENSORS_FEATURE_MAX_OTHER,
    SENSORS_FEATURE_BEEP_ENABLE = 0x18,
    SENSORS_FEATURE_MAX,
    SENSORS_FEATURE_UNKNOWN = int.MaxValue
}

public enum SensorsSubfeatureType {
	SENSORS_SUBFEATURE_IN_INPUT = SensorsFeatureType.SENSORS_FEATURE_IN << 8,
	SENSORS_SUBFEATURE_IN_MIN,
	SENSORS_SUBFEATURE_IN_MAX,
	SENSORS_SUBFEATURE_IN_LCRIT,
	SENSORS_SUBFEATURE_IN_CRIT,
	SENSORS_SUBFEATURE_IN_AVERAGE,
	SENSORS_SUBFEATURE_IN_LOWEST,
	SENSORS_SUBFEATURE_IN_HIGHEST,
	SENSORS_SUBFEATURE_IN_ALARM = (SensorsFeatureType.SENSORS_FEATURE_IN << 8) | 0x80,
	SENSORS_SUBFEATURE_IN_MIN_ALARM,
	SENSORS_SUBFEATURE_IN_MAX_ALARM,
	SENSORS_SUBFEATURE_IN_BEEP,
	SENSORS_SUBFEATURE_IN_LCRIT_ALARM,
	SENSORS_SUBFEATURE_IN_CRIT_ALARM,

	SENSORS_SUBFEATURE_FAN_INPUT = SensorsFeatureType.SENSORS_FEATURE_FAN << 8,
	SENSORS_SUBFEATURE_FAN_MIN,
	SENSORS_SUBFEATURE_FAN_MAX,
	SENSORS_SUBFEATURE_FAN_ALARM = (SensorsFeatureType.SENSORS_FEATURE_FAN << 8) | 0x80,
	SENSORS_SUBFEATURE_FAN_FAULT,
	SENSORS_SUBFEATURE_FAN_DIV,
	SENSORS_SUBFEATURE_FAN_BEEP,
	SENSORS_SUBFEATURE_FAN_PULSES,
	SENSORS_SUBFEATURE_FAN_MIN_ALARM,
	SENSORS_SUBFEATURE_FAN_MAX_ALARM,

	SENSORS_SUBFEATURE_TEMP_INPUT = SensorsFeatureType.SENSORS_FEATURE_TEMP << 8,
	SENSORS_SUBFEATURE_TEMP_MAX,
	SENSORS_SUBFEATURE_TEMP_MAX_HYST,
	SENSORS_SUBFEATURE_TEMP_MIN,
	SENSORS_SUBFEATURE_TEMP_CRIT,
	SENSORS_SUBFEATURE_TEMP_CRIT_HYST,
	SENSORS_SUBFEATURE_TEMP_LCRIT,
	SENSORS_SUBFEATURE_TEMP_EMERGENCY,
	SENSORS_SUBFEATURE_TEMP_EMERGENCY_HYST,
	SENSORS_SUBFEATURE_TEMP_LOWEST,
	SENSORS_SUBFEATURE_TEMP_HIGHEST,
	SENSORS_SUBFEATURE_TEMP_MIN_HYST,
	SENSORS_SUBFEATURE_TEMP_LCRIT_HYST,
	SENSORS_SUBFEATURE_TEMP_ALARM = (SensorsFeatureType.SENSORS_FEATURE_TEMP << 8) | 0x80,
	SENSORS_SUBFEATURE_TEMP_MAX_ALARM,
	SENSORS_SUBFEATURE_TEMP_MIN_ALARM,
	SENSORS_SUBFEATURE_TEMP_CRIT_ALARM,
	SENSORS_SUBFEATURE_TEMP_FAULT,
	SENSORS_SUBFEATURE_TEMP_TYPE,
	SENSORS_SUBFEATURE_TEMP_OFFSET,
	SENSORS_SUBFEATURE_TEMP_BEEP,
	SENSORS_SUBFEATURE_TEMP_EMERGENCY_ALARM,
	SENSORS_SUBFEATURE_TEMP_LCRIT_ALARM,

	SENSORS_SUBFEATURE_POWER_AVERAGE = SensorsFeatureType.SENSORS_FEATURE_POWER << 8,
	SENSORS_SUBFEATURE_POWER_AVERAGE_HIGHEST,
	SENSORS_SUBFEATURE_POWER_AVERAGE_LOWEST,
	SENSORS_SUBFEATURE_POWER_INPUT,
	SENSORS_SUBFEATURE_POWER_INPUT_HIGHEST,
	SENSORS_SUBFEATURE_POWER_INPUT_LOWEST,
	SENSORS_SUBFEATURE_POWER_CAP,
	SENSORS_SUBFEATURE_POWER_CAP_HYST,
	SENSORS_SUBFEATURE_POWER_MAX,
	SENSORS_SUBFEATURE_POWER_CRIT,
	SENSORS_SUBFEATURE_POWER_MIN,
	SENSORS_SUBFEATURE_POWER_LCRIT,
	SENSORS_SUBFEATURE_POWER_AVERAGE_INTERVAL = (SensorsFeatureType.SENSORS_FEATURE_POWER << 8) | 0x80,
	SENSORS_SUBFEATURE_POWER_ALARM,
	SENSORS_SUBFEATURE_POWER_CAP_ALARM,
	SENSORS_SUBFEATURE_POWER_MAX_ALARM,
	SENSORS_SUBFEATURE_POWER_CRIT_ALARM,
	SENSORS_SUBFEATURE_POWER_MIN_ALARM,
	SENSORS_SUBFEATURE_POWER_LCRIT_ALARM,

	SENSORS_SUBFEATURE_ENERGY_INPUT = SensorsFeatureType.SENSORS_FEATURE_ENERGY << 8,

	SENSORS_SUBFEATURE_CURR_INPUT = SensorsFeatureType.SENSORS_FEATURE_CURR << 8,
	SENSORS_SUBFEATURE_CURR_MIN,
	SENSORS_SUBFEATURE_CURR_MAX,
	SENSORS_SUBFEATURE_CURR_LCRIT,
	SENSORS_SUBFEATURE_CURR_CRIT,
	SENSORS_SUBFEATURE_CURR_AVERAGE,
	SENSORS_SUBFEATURE_CURR_LOWEST,
	SENSORS_SUBFEATURE_CURR_HIGHEST,
	SENSORS_SUBFEATURE_CURR_ALARM = (SensorsFeatureType.SENSORS_FEATURE_CURR << 8) | 0x80,
	SENSORS_SUBFEATURE_CURR_MIN_ALARM,
	SENSORS_SUBFEATURE_CURR_MAX_ALARM,
	SENSORS_SUBFEATURE_CURR_BEEP,
	SENSORS_SUBFEATURE_CURR_LCRIT_ALARM,
	SENSORS_SUBFEATURE_CURR_CRIT_ALARM,

	SENSORS_SUBFEATURE_HUMIDITY_INPUT = SensorsFeatureType.SENSORS_FEATURE_HUMIDITY << 8,

	SENSORS_SUBFEATURE_PWM_IO = SensorsFeatureType.SENSORS_FEATURE_PWM << 8,
	SENSORS_SUBFEATURE_PWM_FREQ,
	SENSORS_SUBFEATURE_PWM_ENABLE = (SensorsFeatureType.SENSORS_FEATURE_PWM << 8) | 0x80,
	SENSORS_SUBFEATURE_PWM_MODE,

	SENSORS_SUBFEATURE_VID = SensorsFeatureType.SENSORS_FEATURE_VID << 8,

	SENSORS_SUBFEATURE_INTRUSION_ALARM = SensorsFeatureType.SENSORS_FEATURE_INTRUSION << 8,
	SENSORS_SUBFEATURE_INTRUSION_BEEP,

	SENSORS_SUBFEATURE_BEEP_ENABLE = SensorsFeatureType.SENSORS_FEATURE_BEEP_ENABLE << 8,

	SENSORS_SUBFEATURE_UNKNOWN = Int32.MaxValue, 
};



[StructLayout(LayoutKind.Sequential)]
public struct SensorsFeature
{
	[MarshalAs(UnmanagedType.LPStr)] public string name;
	public int number;
	public SensorsFeatureType type;
	private IntPtr first_subfeature;
	private IntPtr padding1;
}

[StructLayout(LayoutKind.Sequential)]
public struct SensorsSubfeature
{
	[MarshalAs(UnmanagedType.LPStr)] public string name;
	public int number;
	public SensorsSubfeatureType type;
	public  int mapping;
	public uint flags;
}



public static class LmSensorsWrapper
{
    private const string DllPath = "External/LmSensors/libsensors.so.5.0.0";

    public enum SensorsFeatureType
    {
        SensorsFeatureFan = 1,
        SensorsFeatureTemp = 2,
        SensorsFeaturePwm = 3,
        SensorsFeatureUndefined = int.MaxValue
    }
    
    public enum SensorsSubFeatureType
    {
        SensorsSubFeatureFanInput = 1,
        SensorsSubFeatureTempInput = 2,
        SensorsSubFeaturePwmIo = 3,
        SensorsSubFeaturePwmEnable = 4,
        SensorsSubFeatureUndefined = int.MaxValue
    }

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
    public static extern unsafe SensorsChipName *sensors_get_detected_chips(SensorsChipName?
        match, int* nr);


    /* This returns all main features of a specific chip. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsFeature sensors_get_features(SensorsChipName name, int* nr);


    /* This returns all subfeatures of a given main feature. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe SensorsSubfeature sensors_get_all_subfeatures(SensorsChipName name,
	    SensorsFeature feature, int* nr);


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
    public static extern unsafe int sensors_get_value(SensorsChipName name, int subfeat_nr,
        double* value);
    
    
    /* Set the value of a subfeature of a certain chip. Note that chip should not
       contain wildcard values! This function will return 0 on success, and <0
       on failure. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int sensors_set_value(SensorsChipName name, int subfeat_nr,
        double value);
}