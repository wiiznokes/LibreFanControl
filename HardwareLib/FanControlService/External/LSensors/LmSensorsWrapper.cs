using System.Runtime.InteropServices;

namespace FanControlService.External.LSensors;



public static class LmSensorsWrapper
{
    
    private const string DllPath = "External/LSensors/lsensorswrapper";
    
    
    [DllImport(DllPath, CallingConvention = CallingConvention.Cdecl)]
    public static extern void fetch_temp2();
    
    
}