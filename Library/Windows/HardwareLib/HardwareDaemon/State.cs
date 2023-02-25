using System.Collections.Concurrent;
using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareDaemon.Item;
using HardwareDaemon.Item.Behavior;
using HardwareDaemon.Model;

namespace HardwareDaemon;

public static class State
{
    public static readonly ConcurrentDictionary<int, BaseControl> HControls = new();
    public static readonly ConcurrentDictionary<int, BaseSensor> HTemps = new();
    public static readonly ConcurrentDictionary<int, BaseSensor> HFans = new();

    public static readonly ConcurrentDictionary<int, Control> Controls = new();
    public static readonly ConcurrentDictionary<int, Behavior> Behaviors = new();

    public static readonly ConcurrentDictionary<int, CustomTemp> CustomTemps = new();

    public static readonly ConcurrentDictionary<int, int> UpdateList = new();


    private static readonly object SettingLock = new();
    private static Settings _setting = new(null, 2);

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

    public static class ServiceState
    {
        private static bool _isOpen;
        private static bool _confHasChange;
        private static bool _settingsHasChange;
        private static readonly object LockObject = new();

        public static bool IsOpen
        {
            get
            {
                lock (LockObject)
                {
                    return _isOpen;
                }
            }
            set
            {
                lock (LockObject)
                {
                    _isOpen = value;
                }
            }
        }

        public static bool SettingsAndConfHasChange
        {
            get
            {
                lock (LockObject)
                {
                    return _confHasChange;
                }
            }
            set
            {
                lock (LockObject)
                {
                    _confHasChange = value;
                }
            }
        }

        public static bool SettingsHasChange
        {
            get
            {
                lock (LockObject)
                {
                    return _settingsHasChange;
                }
            }
            set
            {
                lock (LockObject)
                {
                    _settingsHasChange = value;
                }
            }
        }
    }
}