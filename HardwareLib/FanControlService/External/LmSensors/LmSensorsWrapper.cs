using System.Runtime.InteropServices;

namespace FanControlService.External.LmSensors;

using sensors_chip_name = IntPtr;
using sensors_feature = IntPtr;
using sensors_subfeature = IntPtr;
using FilePtr = IntPtr;

public static class LmSensorsWrapper
{
    private const string DllPath = "External/LmSensors/lsensorswrapper";

    public enum SensorsFeatureType
    {
        SensorsFeatureFan = 1,
        SensorsFeatureTemp = 2,
        Undefined = int.MaxValue
    }

    /* Load the configuration file and the detected chips list. If this
       returns a value unequal to zero, you are in trouble; you can not
       assume anything will be initialized properly. If you want to
       reload the configuration file, call sensors_cleanup() below before
       calling sensors_init() again. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int sensors_init(FilePtr f);


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
    public static extern unsafe sensors_chip_name sensors_get_detected_chips(sensors_chip_name
        match, int* nr);


    /* This returns all main features of a specific chip. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe sensors_feature sensors_get_features(sensors_chip_name name, int* nr);


    /* This returns all subfeatures of a given main feature. nr is an internally
       used variable. Set it to zero to start at the begin of the list. If no
       more features are found NULL is returned.
       Do not try to change the returned structure; you will corrupt internal
       data structures. */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe sensors_subfeature sensors_get_all_subfeatures(sensors_chip_name name,
        sensors_feature feature, int* nr);


    /* CHIP **************/

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern IntPtr get_chip_name(sensors_chip_name chip);


    /* FEATURE **************/

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern IntPtr get_feature_name(sensors_feature feat);

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int get_feature_type(sensors_feature feat);

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int get_feature_number(sensors_feature feat);


    /* SUB_FEATURE **************/

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern IntPtr get_sub_feature_name(sensors_subfeature sub_feat);

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int get_sub_feature_type(sensors_subfeature sub_feat);

    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern int get_sub_feature_number(sensors_subfeature sub_feat);


    /* Read the value of a subfeature of a certain chip. Note that chip should not
     contain wildcard values! This function will return 0 on success, and <0
     on failure.  */
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern unsafe int sensors_get_value(sensors_chip_name name, int subfeat_nr,
        double* value);
}