using System.Collections;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using HardwareDaemon.Proto;

namespace HardwareDaemon;

public class Update
{
    public static void CreateUpdateList(
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

            if (behavior.Type == BehaviorType.Flat)
            {
                iControl.SetHControl(hControls);
                iControl.SetValue((behavior as IFlat)!.Value);
                break; 
            }

            try
            {
                (behavior as IBehaviorWithTemp)!.Init(hTemps, iCustomTemps);
            } catch (BehaviorException e)
            {
                Console.WriteLine(e);
                break;
            }
            iControl.SetHControl(hControls);
            iControl.IBehavior = (behavior as IBehaviorWithTemp)!;
            updateList.Add(iControl);
        }
    }


    public static void UpdateUpdateList(ArrayList updateList)
    {
        foreach (IControl iControl in updateList)
        {
            iControl.SetValue(iControl.IBehavior.GetValue());
        }
    }
}