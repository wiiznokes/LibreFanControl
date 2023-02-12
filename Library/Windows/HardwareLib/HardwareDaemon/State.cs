using System.Collections.Concurrent;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon;

public static class MyDataStore
{
    private static readonly ConcurrentDictionary<int, BaseControl> HControls = new ConcurrentDictionary<int, BaseControl>();
    private static readonly ConcurrentDictionary<int, BaseSensor> HTemps = new ConcurrentDictionary<int, BaseSensor>();
    private static readonly ConcurrentDictionary<int, BaseSensor> HFans = new ConcurrentDictionary<int, BaseSensor>();

    private static readonly ConcurrentDictionary<int, Control> Controls = new ConcurrentDictionary<int, Control>();
    private static readonly ConcurrentDictionary<int, Behavior> Behaviors = new ConcurrentDictionary<int, Behavior>();

    private static readonly ConcurrentDictionary<int, CustomTemp> CustomTemps =
        new ConcurrentDictionary<int, CustomTemp>();

    private static readonly ConcurrentDictionary<int, IBaseItem> UpdateList = new ConcurrentDictionary<int, IBaseItem>();

    public static void AddHControl(BaseControl control)
    {
        HControls.TryAdd(HControls.Count, control);
    }

    public static void AddHTemp(BaseSensor temp)
    {
        HTemps.TryAdd(HTemps.Count, temp);
    }

    public static void AddHFan(BaseSensor fan)
    {
        HFans.TryAdd(HFans.Count, fan);
    }

    public static void AddControl(Control control)
    {
        Controls.TryAdd(Controls.Count, control);
    }

    public static void AddBehavior(Behavior behavior)
    {
        Behaviors.TryAdd(Behaviors.Count, behavior);
    }

    public static void AddCustomTemp(CustomTemp customTemp)
    {
        CustomTemps.TryAdd(CustomTemps.Count, customTemp);
    }

    public static void AddUpdate(IBaseItem update)
    {
        UpdateList.TryAdd(UpdateList.Count, update);
    }

    public static IEnumerable<BaseControl> GetHControls()
    {
        return HControls.Values;
    }

    public static IEnumerable<BaseSensor> GetHTemps()
    {
        return HTemps.Values;
    }

    public static IEnumerable<BaseSensor> GetHFans()
    {
        return HFans.Values;
    }

    public static IEnumerable<Control> GetControls()
    {
        return Controls.Values;
    }

    public static IEnumerable<Behavior> GetBehaviors()
    {
        return Behaviors.Values;
    }

    public static IEnumerable<CustomTemp> GetCustomTemps()
    {
        return CustomTemps.Values;
    }

    public static IEnumerable<IBaseItem> GetUpdates()
    {
        return UpdateList.Values;
    }

    public static bool TryGetHControl(int index, out BaseControl control)
    {
        return HControls.TryGetValue(index, out control);
    }

    public static bool TryGetHTemp(int index, out BaseSensor temp)
    {
        return HTemps.TryGetValue(index, out temp);
    }
    
    public static bool TryGetHFan(int index, out BaseSensor fan)
    {
        return HFans.TryGetValue(index, out fan);
    }

    public static bool TryGetControl(int index, out Control control)
    {
        return Controls.TryGetValue(index, out control);
    }

    public static bool TryGetBehavior(int index, out Behavior behavior)
    {
        return Behaviors.TryGetValue(index, out behavior);
    }

    public static bool TryGetCustomTemp(int index, out CustomTemp customTemp)
    {
        return CustomTemps.TryGetValue(index, out customTemp);
    }

    public static bool TryGetUpdate(int index, out IBaseItem update)
    {
        return UpdateList.TryGetValue(index, out update);
    }

    
    
    
}