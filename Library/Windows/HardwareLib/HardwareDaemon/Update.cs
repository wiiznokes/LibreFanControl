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

        foreach (Control iControl in iControls)
        {
            if (!iControl.IsValid)
                continue;
            
            Behavior? behavior = null;
            
            foreach (Behavior iBehavior in iBehaviors)
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
                iControl.SetValue((behavior as Flat)!.Value);
                break; 
            }

            try
            {
                (behavior as BehaviorWithTemp)!.Init(hTemps, iCustomTemps);
            } catch (BehaviorException e)
            {
                Console.WriteLine(e);
                break;
            }
            iControl.SetHControl(hControls);
            iControl.Behavior = (behavior as BehaviorWithTemp)!;
            updateList.Add(iControl);
        }
    }


    public static void UpdateUpdateList(ArrayList updateList)
    {
        foreach (Control iControl in updateList)
        {
            iControl.SetValue(iControl.Behavior.GetValue());
        }
    }
}