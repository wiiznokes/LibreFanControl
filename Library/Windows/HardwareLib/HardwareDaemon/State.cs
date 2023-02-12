using System.Collections.Concurrent;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;

namespace HardwareDaemon;

public class MyDataStore
{
    private static readonly ConcurrentDictionary<int, BaseControl> HControls = new ConcurrentDictionary<int, BaseControl>();
    private static readonly ConcurrentDictionary<int, BaseSensor> HTemps = new ConcurrentDictionary<int, BaseSensor>();
    private static readonly ConcurrentDictionary<int, BaseSensor> HFans = new ConcurrentDictionary<int, BaseSensor>();

    private static readonly ConcurrentDictionary<int, Control> Controls = new ConcurrentDictionary<int, Control>();
    private static readonly ConcurrentDictionary<int, Behavior> Behaviors = new ConcurrentDictionary<int, Behavior>();

    private static readonly ConcurrentDictionary<int, CustomTemp> CustomTemps =
        new ConcurrentDictionary<int, CustomTemp>();

    private static readonly ConcurrentDictionary<int, IBaseItem> UpdateList = new ConcurrentDictionary<int, IBaseItem>();

    public void AddHControl(BaseControl control)
    {
        HControls.TryAdd(HControls.Count, control);
    }

    public void AddHTemp(BaseSensor temp)
    {
        HTemps.TryAdd(HTemps.Count, temp);
    }

    public void AddHFan(BaseSensor fan)
    {
        HFans.TryAdd(HFans.Count, fan);
    }

    public void AddControl(Control control)
    {
        Controls.TryAdd(Controls.Count, control);
    }

    public void AddBehavior(Behavior behavior)
    {
        Behaviors.TryAdd(Behaviors.Count, behavior);
    }

    public void AddCustomTemp(CustomTemp customTemp)
    {
        CustomTemps.TryAdd(CustomTemps.Count, customTemp);
    }

    public void AddUpdate(IBaseItem update)
    {
        UpdateList.TryAdd(UpdateList.Count, update);
    }

    public IEnumerable<BaseControl> GetHControls()
    {
        return HControls.Values;
    }

    public IEnumerable<BaseSensor> GetHTemps()
    {
        return HTemps.Values;
    }

    public IEnumerable<BaseSensor> GetHFans()
    {
        return HFans.Values;
    }

    public IEnumerable<Control> GetControls()
    {
        return Controls.Values;
    }

    public IEnumerable<Behavior> GetBehaviors()
    {
        return Behaviors.Values;
    }

    public IEnumerable<CustomTemp> GetCustomTemps()
    {
        return CustomTemps.Values;
    }

    public IEnumerable<IBaseItem> GetUpdates()
    {
        return UpdateList.Values;
    }

    public bool TryGetHControl(int index, out BaseControl control)
    {
        return HControls.TryGetValue(index, out control);
    }

    public bool TryGetHTemp(int index, out BaseSensor temp)
    {
        return HTemps.TryGetValue(index, out temp);
    }
    
    public bool TryGetHFan(int index, out BaseSensor fan)
    {
        return HFans.TryGetValue(index, out fan);
    }

    public bool TryGetControl(int index, out Control control)
    {
        return Controls.TryGetValue(index, out control);
    }

    public bool TryGetBehavior(int index, out Behavior behavior)
    {
        return Behaviors.TryGetValue(index, out behavior);
    }

    public bool TryGetCustomTemp(int index, out CustomTemp customTemp)
    {
        return CustomTemps.TryGetValue(index, out customTemp);
    }

    public bool TryGetUpdate(int index, out IBaseItem update)
    {
        return UpdateList.TryGetValue(index, out update);
    }

    
    
    
}