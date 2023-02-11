using System.Collections;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using Proto.Generated.PConf;

namespace HardwareDaemon.Proto;

public static class ConfHelper
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
            iControls.Add(new Control(
                SettingsHelper.NullableToNull(pIControl.PIBehaviorId),
                SettingsHelper.NullableToNull(pIControl.PHControlId),
                pIControl.PIsAuto
            ));

        foreach (var pIBehavior in pConf.PIBehaviors)
            iBehaviors.Add(
                pIBehavior.KindCase switch
                {
                    PIBehavior.KindOneofCase.PFlat => new Flat(pIBehavior.PId, pIBehavior.PFlat.PValue),

                    PIBehavior.KindOneofCase.PLinear => new Linear(
                        pIBehavior.PId,
                        SettingsHelper.NullableToNull(pIBehavior.PLinear.PTempId),
                        pIBehavior.PLinear.PMinTemp,
                        pIBehavior.PLinear.PMaxTemp,
                        pIBehavior.PLinear.PMinFanSpeed,
                        pIBehavior.PLinear.PMaxFanSpeed
                    ),

                    PIBehavior.KindOneofCase.PTarget => new Target(
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
                    new CustomTemp(
                        pITemp.PId,
                        pITemp.PICustomTemp.PHTempIds.ToList(),
                        pITemp.PICustomTemp.PType switch
                        {
                            PCustomTempTypes.Average => CustomTemp.CustomTempType.Average,
                            PCustomTempTypes.Max => CustomTemp.CustomTempType.Max,
                            PCustomTempTypes.Min => CustomTemp.CustomTempType.Min,
                            _ => throw new ProtoException("unknown custom temp type")
                        }
                    )
                );
    }
}