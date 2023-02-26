using HardwareDaemon.Utils;

namespace HardwareDaemon.Item.Behavior;

public abstract class Behavior
{
    protected Behavior(string id)
    {
        Id = id;
    }

    public string Id { get; }

    public bool IsValid { get; protected init; }
    public bool SetOneTime { get; protected init; }

    public abstract int GetSpeed();
}

public abstract class BehaviorWithTemp : Behavior
{
    private const string CustomTempPrefix = "iCustomTemp";

    protected BehaviorWithTemp(string id, string? tempId) : base(id)
    {
        TempId = tempId;
        SetOneTime = false;

        IsValid = TempId != null;

        if (!IsValid) return;

        var parts = TempId!.Split('#');
        IsCustomTemp = parts.Length > 0 && parts[0] == CustomTempPrefix;

        if (!SetTempIndex()) IsValid = false;
    }


    private string? TempId { get; }
    private bool IsCustomTemp { get; }


    // either hTemp or iCustomTemp
    private int TempIndex { get; set; }


    protected int GetSensorValue()
    {
        if (IsCustomTemp) return State.CustomTemps[TempIndex].GetValue();

        State.HTemps[TempIndex].Update();
        return State.HTemps[TempIndex].Value;
    }


    
    private bool SetTempIndex()
    {
        if (IsCustomTemp)
            foreach (var (iCustomTemp, index) in State.CustomTemps.Values.WithIndex())
            {
                if (iCustomTemp.Id != TempId) continue;


                if (!iCustomTemp.IsValid) return false;
                TempIndex = index;
                return true;
            }
        else
            foreach (var (hTemp, index) in State.HTemps.Values.WithIndex())
            {
                if (hTemp.Id != TempId) continue;

                TempIndex = index;
                return true;
            }

        Console.WriteLine("Error: behavior " + Id + ", none temp id was found");
        return false;
    }
}