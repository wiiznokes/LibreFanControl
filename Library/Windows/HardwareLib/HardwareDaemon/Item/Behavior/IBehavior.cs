using System.Collections;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon.Item.Behavior;


public enum BehaviorType
{
    Flat,
    Linear,
    Target
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
    
    protected IBehaviorWithTemp(string id, string? tempId, BehaviorType type): base(id, type)
    {
        TempId = tempId;
        IsCustomTemp = 
    }
    
    private string? TempId { get; }
    public bool IsCustomTemp { get; }

    private BaseSensor? _hTemp = null;
    private ICustomTemp? _customTemp = null;

    public bool IsValid()
    {
        return TempId != null;
    }

    public void SetSensor(ArrayList sensorList)
    {
        if (IsCustomTemp)
        {
            foreach (ICustomTemp customTemp in sensorList)
            {
                if (customTemp.Id != TempId) continue;
                _customTemp = customTemp;
                break;
            } 
        }
        else
        {
            foreach (BaseSensor hTemp in sensorList)
            {
                if (hTemp.Id != TempId) continue;
                _hTemp = hTemp;
                break;
            } 
        }
       
    }

    public int? GetSensorValue()
    {
        if (IsCustomTemp)
        {
            return _customTemp!
        }
        else
        {
            _hTemp!.Update();
            return _hTemp.Value;
        }
    }
    

    public abstract int? GetValue();


}