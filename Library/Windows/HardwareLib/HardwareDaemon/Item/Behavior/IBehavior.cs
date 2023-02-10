using System.Collections;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon.Item.Behavior;


public enum BehaviorType
{
    Flat,
    Linear,
    Target
}

public class BehaviorException : Exception
{
    public BehaviorException(string msg) : base(msg)
    {
    }
}



public abstract class IBehavior
{
    protected IBehavior(string id, BehaviorType type)
    {
        Id = id;
        Type = type;
    }
    
    public string Id { get; }
    public BehaviorType Type { get; }

    
}




public abstract class IBehaviorWithTemp : IBehavior
{

    private const string ICustomTempPrefix = "iCustomTemp";
    
    protected IBehaviorWithTemp(string id, string? tempId, BehaviorType type): base(id, type)
    {
        TempId = tempId;
        IsValid = TempId != null;

        if (!IsValid) return;
        
        var parts = TempId!.Split('/');
        IsCustomTemp = parts.Length > 0 && parts[0] == ICustomTempPrefix;

    }
    
    public string? TempId { get; }
    public bool IsCustomTemp { get; }
    
    public bool IsValid { get;  }

    public BaseSensor? Htemp { get; set; }
    public ICustomTemp? ICustomTemp { get; set; }

    

    public int? GetSensorValue()
    {
        if (IsCustomTemp)
        {
            return ICustomTemp!.GetValue();
        }

        Htemp!.Update();
        return Htemp.Value;
    }
    

    public abstract int? GetValue();


    public void Init(ArrayList hTemps, ArrayList iCustomTemps)
    {
        if (!IsValid)
            throw new BehaviorException("behavior not valid");
        
        if (IsCustomTemp)
        {
            foreach (ICustomTemp iCustomTemp in iCustomTemps)
            {
                if (iCustomTemp.Id != TempId) continue;
                            
                iCustomTemp.Init(hTemps);
                ICustomTemp = iCustomTemp;
                return;
            }
        }
        else
        {
            foreach (BaseSensor hTemp in hTemps)
            {
                if (hTemp.Id != TempId) continue;

                Htemp = hTemp;
                return;
            }
        }
        throw new BehaviorException("no sensor found (should not happened)");
    }

}