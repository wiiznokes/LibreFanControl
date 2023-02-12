using System.Collections.Concurrent;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using HardwareDaemon.Model;
using HardwareDaemon.Proto;

namespace HardwareDaemon;

public static class State
{
    public static readonly ConcurrentDictionary<int, BaseControl> HControls = new();
    public static readonly ConcurrentDictionary<int, BaseSensor> HTemps = new();
    public static readonly ConcurrentDictionary<int, BaseSensor> HFans = new();

    public static readonly ConcurrentDictionary<int, Control> Controls = new();
    public static readonly ConcurrentDictionary<int, Behavior> Behaviors = new();

    public static readonly ConcurrentDictionary<int, CustomTemp> CustomTemps = new();

    public static readonly ConcurrentDictionary<int, Control> UpdateList = new();


    private static readonly object SettingLock = new();
    private static Settings _setting = SettingsHelper.LoadSettingsFile();

    public static Settings Settings
    {
        get
        {
            lock (SettingLock)
            {
                return _setting;
            }
        }
        set
        {
            lock (SettingLock)
            {
                _setting = value;
            }
        }
    }


    private static readonly object IsOpenLock = new();
    private static bool _isOpen;

    public static bool IsOpen
    {
        get
        {
            lock (IsOpenLock)
            {
                return _isOpen;
            }
        }
        set
        {
            lock (IsOpenLock)
            {
                _isOpen = value;
            }
        }
    }
}