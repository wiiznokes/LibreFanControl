using System.Collections;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon;

public class Update
{
    public static ArrayList CreateUpdateList(
        ArrayList updateList,
        ArrayList hControls,
        ArrayList hTemps,
        ArrayList iControls,
        ArrayList iBehaviors,
        ArrayList iCustomTemps)
    {
        updateList.Clear();

        foreach (IControl iControl in iControls)
        {
            if (!iControl.IsValid)
                continue;
            
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
                    iControl.SetHControl(hControls);
                    iControl.SetValue((behavior as IFlat)!.Value);
                    continue;
                
                case BehaviorType.Linear:
                    var linear = (behavior as ILinear)!;
                    if (!linear.IsValid)
                        continue;
                    if (linear.IsCustomTemp)
                    {
                        foreach (ICustomTemp iCustomTemp in iCustomTemps)
                        {
                            if (iCustomTemp.Id != linear.TempId) continue;
                            
                            if (!iCustomTemp.IsValid) break;
                            linear.ICustomTemp = iCustomTemp;
                            break;
                        }
                    }
                    else
                    {
                        foreach (BaseSensor hTemp in hTemps)
                        {
                            if (hTemp.Id != linear.TempId) continue;

                            linear.Htemp = hTemp;
                            break;
                        }
                    }
                    
                case BehaviorType.Target:
                    break;
                default:
                    throw new Exception("unknown behavior type");
            }
        }
    }
}