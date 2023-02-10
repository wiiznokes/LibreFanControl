using System.Collections;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon.Item;

public class ICustomTemp
{
    public enum CustomTempType
    {
        Average,
        Max,
        Min
    }

    public ICustomTemp(string id, List<string> hTempIds, CustomTempType type)
    {
        Id = id;
        HTempIds = hTempIds;
        Type = type;
        HTemps = new List<BaseSensor>();

        IsValid = HTempIds.Count > 0;
    }
    
    public string Id { get; }
    private List<string> HTempIds { get; }
    private CustomTempType Type { get; }
    
    
    public bool IsValid { get;  }

    private List<BaseSensor> HTemps { get;  }

    public void SetSensors(ArrayList sensorList)
    {
        foreach (BaseSensor hTemp in sensorList)
        {
            if (!HTempIds.Contains(hTemp.Id)) continue;
            
            HTemps.Add(hTemp);
            
            if (HTemps.Count == HTempIds.Count)
                return;
        }
    }

    public int GetValue()
    {
        return Type switch
        {
            CustomTempType.Average => Convert.ToInt32(GetTempValues().Average()),
            CustomTempType.Max => GetTempValues().Max(),
            CustomTempType.Min => GetTempValues().Min(),
            _ => throw new Exception("unknown custom temp type")
        };
    }

    private IEnumerable<int> GetTempValues()
    {
        List<int> values = new();
        foreach (var hTemp in HTemps)
        {
            hTemp.Update();
            values.Add(hTemp.Value);
        }

        return values;
    }
}