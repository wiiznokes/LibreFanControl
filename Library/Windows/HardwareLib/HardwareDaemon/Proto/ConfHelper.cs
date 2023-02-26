using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using HardwareDaemon.Utils;
using Proto.Generated.PConf;

namespace HardwareDaemon.Proto;

public static class ConfHelper
{
    public static void LoadConfFile(string confId)
    {
        var pConf = PConf.Parser.ParseFrom(GetConfBytes(confId));
        ParsePConf(pConf);
    }

    private static byte[] GetConfBytes(string confId)
    {
        return File.ReadAllBytes(SettingsDir.GetConfFile(confId));
    }


    // the order is important here (for initialization)
    private static void ParsePConf(PConf pConf)
    {
        State.Controls.Clear();
        State.Behaviors.Clear();
        State.CustomTemps.Clear();


        foreach (var pITemp in pConf.PITemps)
            if (pITemp.KindCase == PITemp.KindOneofCase.PICustomTemp)
                State.CustomTemps.Add(
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

        foreach (var pIBehavior in pConf.PIBehaviors)
            State.Behaviors.Add(
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


        foreach (var (pIControl, index) in pConf.PIControls.WithIndex())
            State.Controls.Add(
                new Control(
                    SettingsHelper.NullableToNull(pIControl.PIBehaviorId),
                    SettingsHelper.NullableToNull(pIControl.PHControlId),
                    pIControl.PIsAuto,
                    index
                ));
    }
}