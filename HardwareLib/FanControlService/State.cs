using FanControlService.Hardware.Control;
using FanControlService.Hardware.Sensor;
using FanControlService.Item;
using FanControlService.Item.Behavior;

namespace FanControlService;

public static class State
{
    public static readonly List<BaseControl> HControls = new();
    public static readonly List<BaseSensor> HTemps = new();
    public static readonly List<BaseSensor> HFans = new();

    public static readonly List<Control> Controls = new();
    public static readonly List<Behavior> Behaviors = new();
    public static readonly List<CustomTemp> CustomTemps = new();

    public static readonly List<int> UpdateList = new();


    public static class Settings
    {
        public static string? ConfId { get; set; }
        public static int UpdateDelay { get; set; }
        
        public static int ValueDisableControl { get; set; }
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