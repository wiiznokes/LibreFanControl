using System.Collections;
using Proto.Generated.Conf;
using Proto.Generated.Setting;

namespace HardwareDaemon.Proto;

public class ConfHelper
{
    private const string ConfDir = "./conf/";

    public static void LoadConfFile(string confId,
        ArrayList iControls,
        ArrayList iTemps,
        ArrayList iFans)
    {
        var pConf = PConf.Parser.ParseFrom(GetConfBytes(confId));
        ParsePConf(pConf, iControls, iTemps, iFans);
    }

    private static byte[] GetConfBytes(string confId)
    {
        var filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, ConfDir + confId);
        return File.ReadAllBytes(filePath);
    }


    public static void ParsePConf(PConf pConf,
        ArrayList iControls,
        ArrayList iTemps,
        ArrayList iFans)
    {
        foreach (var pIControl in pConf.PIControls)
        {
            
        }
    }
}