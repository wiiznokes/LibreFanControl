using System.Collections;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using Proto.Generated.Conf;

namespace HardwareDaemon.Proto;

public class ConfHelper
{
    private const string ConfDir = "./conf/";

    public static void LoadConfFile(string confId,
        ArrayList iControls,
        ArrayList iBehaviors,
        ArrayList iCustomTemps)
    {
        var pConf = PConf.Parser.ParseFrom(GetConfBytes(confId));
        ParsePConf(pConf, iControls, iBehaviors, iCustomTemps);
    }

    private static byte[] GetConfBytes(string confId)
    {
        var filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, ConfDir + confId);
        return File.ReadAllBytes(filePath);
    }


    public static void ParsePConf(PConf pConf,
        ArrayList iControls,
        ArrayList iBehaviors,
        ArrayList iCustomTemps)
    {
        iControls.Clear();
        iBehaviors.Clear();
        iCustomTemps.Clear();

        foreach (var pIControl in pConf.PIControls)
            iControls.Add(new IControl(
                SettingsHelper.NullableToNull(pIControl.PIBehaviorId),
                SettingsHelper.NullableToNull(pIControl.PHControlId),
                pIControl.PId,
                pIControl.PIsAuto
            ));

        foreach (var pIBehavior in pConf.PIBehaviors)
            iBehaviors.Add(
                pIBehavior.KindCase switch
                {
                    PIBehavior.KindOneofCase.PFlat => new IFlat(pIBehavior.PId, pIBehavior.PFlat.PValue),

                    PIBehavior.KindOneofCase.PLinear => new ILinear(
                        pIBehavior.PId,
                        SettingsHelper.NullableToNull(pIBehavior.PLinear.PTempId),
                        pIBehavior.PLinear.PMinTemp,
                        pIBehavior.PLinear.PMaxTemp,
                        pIBehavior.PLinear.PMinFanSpeed,
                        pIBehavior.PLinear.PMaxFanSpeed
                    ),

                    PIBehavior.KindOneofCase.PTarget => new ITarget(
                        pIBehavior.PId,
                        SettingsHelper.NullableToNull(pIBehavior.PTarget.PTempId),
                        pIBehavior.PTarget.PIdleTemp,
                        pIBehavior.PTarget.PLoadTemp,
                        pIBehavior.PTarget.PIdleFanSpeed,
                        pIBehavior.PTarget.PLoadFanSpeed
                    ),

                    _ => throw new ProtoException("behavior unknown kind")
                }
            );

        foreach (var pITemp in pConf.PITemps)
            if (pITemp.KindCase == PITemp.KindOneofCase.PICustomTemp)
                iCustomTemps.Add(
                    new ICustomTemp(
                        pITemp.PId,
                        pITemp.PICustomTemp.PHTempIds.ToList(),
                        pITemp.PICustomTemp.PType switch
                        {
                            PCustomTempTypes.Average => ICustomTemp.CustomTempType.Average,
                            PCustomTempTypes.Max => ICustomTemp.CustomTempType.Max,
                            PCustomTempTypes.Min => ICustomTemp.CustomTempType.Min,
                            _ => throw new ProtoException("unknown custom temp type")
                        }
                    )
                );
    }
}