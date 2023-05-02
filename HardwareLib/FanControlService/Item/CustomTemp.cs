using FanControlService.Utils;

namespace FanControlService.Item;

public class CustomTemp
{
    public enum CustomTempType
    {
        Average,
        Max,
        Min
    }

    public CustomTemp(string id, List<string> hTempIds, CustomTempType type)
    {
        Id = id;
        HTempIds = hTempIds;
        Type = type;
        HTempsIndexList = new List<int>();

        IsValid = HTempIds.Count > 0;

        if (!IsValid) return;

        if (!SetHTempsIndexList()) IsValid = false;
    }

    public string Id { get; }
    private List<string> HTempIds { get; }
    private CustomTempType Type { get; }


    public bool IsValid { get; }

    private List<int> HTempsIndexList { get; }

    private bool SetHTempsIndexList()
    {
        foreach (var hTempId in HTempIds)
        foreach (var (hTemp, index) in State.HTemps.WithIndex())
        {
            if (hTemp.Id != hTempId) continue;

            HTempsIndexList.Add(index);
            break;
        }

        if (HTempIds.Count == HTempsIndexList.Count) return true;


        Console.WriteLine("Error: set index for custom temp " + Id + ", not all index was found");
        return false;
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
        foreach (var index in HTempsIndexList)
        {
            var hTemp = State.HTemps[index];
            hTemp.Update();
            values.Add(hTemp.Value);
        }

        return values;
    }
}