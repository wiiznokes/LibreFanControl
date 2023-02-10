using System.Collections;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon;

public class Update
{
    public static ArrayList CreateUpdateList(
        ArrayList updateList,
        ArrayList controls,
        ArrayList temps,
        ArrayList fans,
        ArrayList iControls,
        ArrayList iBehaviors,
        ArrayList iCustomTemps)
    {
        updateList.Clear();

        foreach (IControl iControl in iControls)
        {
            if (iControl.IsValid())
            {
                IBehavior? behavior = null;
                
                foreach (IBehavior iBehavior in iBehaviors)
                {
                    if (iBehavior.Id != iControl.Id) continue;
                    behavior = iBehavior;
                    break;
                }
                if (behavior == null)
                {
                    throw new Exception("behavior should not be null");
                }
                
                switch (behavior.Type)
                {
                    case BehaviorType.Flat:
                        iControl.SetValue((behavior as IFlat)!.Value);
                        break;
                    case BehaviorType.Linear:
                        var linear = (behavior as ILinear)!;
                        if (!linear.IsValid())
                            break;
                    case BehaviorType.Target:
                        break;
                    default:
                        throw new Exception("unknown behavior type");
                }
            }
        }
    }
}