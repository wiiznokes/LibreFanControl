using HardwareLib.Hardware;

namespace HardwareLib
{
    public static class Api
    {
        private static HardwareManager _hardwareManager;

        private static string[] _getItemsList = new string[100];

        public static void Start()
        {
            _hardwareManager = new HardwareManager();
            _hardwareManager.Start();
        }

        public static void Stop()
        {
            _hardwareManager.Stop();
        }

        public static string[] GetFanList()
        {
            HardwareManager.GetFanList(ref _getItemsList);
            return _getItemsList;
        }

        public static string[] GetTempList()
        {
            HardwareManager.GetTempList(ref _getItemsList);
            return _getItemsList;
        }

        public static string[] GetControlList()
        {
            HardwareManager.GetControlList(ref _getItemsList);
            return _getItemsList;
        }

        public static unsafe void UpdateFan(int* values)
        {
            _hardwareManager.UpdateFan(values);
        }

        public static unsafe void UpdateTemp(int* values)
        {
            HardwareManager.UpdateTemp(values);
        }

        public static unsafe void UpdateControl(int* values)
        {
            HardwareManager.UpdateControl(values);
        }

        public static bool SetControl(int index, bool isAuto, int value)
        {
            return HardwareManager.SetControl(index, isAuto, value);
        }
    }
}