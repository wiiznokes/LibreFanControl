using HardwareLib.Hardware.Control;
using HardwareLib.Hardware.Sensor;

namespace HardwareLib.Hardware
{
    public class HardwareManager
    {
        private static List<BaseDevice> _tempList  = new();
        private static List<BaseDevice> _fanList  = new();
        private static List<BaseDevice> _controlList  = new();

        private readonly Lhm _lhm;

        public HardwareManager()
        {
            _lhm = new Lhm();
        }

        //static private List<OSDSensor> osdSensorList;
        //static private Dictionary<string, OSDSensor> osdSensorMap;


        public void Start()
        {
            _lhm.Start();

            //osdSensorList = new List<OSDSensor>();
            //osdSensorMap = new Dictionary<string, OSDSensor>();

            _lhm.CreateFan(ref _fanList);
            _lhm.CreateTemp(ref _tempList);
            _lhm.CreateControl(ref _controlList);
        }

        public void Stop()
        {
            _lhm.Stop();

            _fanList.Clear();
            _tempList.Clear();
            _controlList.Clear();
        }

        public static void GetFanList(ref string[] getItemsList)
        {
            var i = 1;
            foreach (var fan in _fanList.Cast<BaseSensor>())
            {
                getItemsList[i] = fan.Index.ToString();
                getItemsList[i + 1] = fan.Id;
                getItemsList[i + 2] = fan.Name;
                i += 3;
            }

            getItemsList[0] = i.ToString();
        }


        public static void GetTempList(ref string[] getItemsList)
        {
            var i = 1;
            foreach (var temp in _tempList.Cast<BaseSensor>())
            {
                getItemsList[i] = temp.Index.ToString();
                getItemsList[i + 1] = temp.Id;
                getItemsList[i + 2] = temp.Name;
                i += 3;
            }

            getItemsList[0] = i.ToString();
        }

        public static void GetControlList(ref string[] getItemsList)
        {
            var i = 1;
            foreach (var control in _controlList.Cast<BaseControl>())
            {
                getItemsList[i] = control.Index.ToString();
                getItemsList[i + 1] = control.Id;
                getItemsList[i + 2] = control.Name;
                i += 3;
            }

            getItemsList[0] = i.ToString();
        }


        public unsafe void UpdateFan(int* updateList)
        {
            _lhm.Update();
            foreach (var fan in _fanList.Cast<BaseSensor>())
            {
                fan.Update();
                updateList[fan.Index] = fan.Value;
            }
        }

        public static unsafe void UpdateTemp(int* updateList)
        {
            foreach (var temp in _tempList.Cast<BaseSensor>())
            {
                temp.Update();
                updateList[temp.Index] = temp.Value;
            }
        }

        public static unsafe void UpdateControl(int* updateList)
        {
            foreach (var control in _controlList.Cast<BaseControl>())
            {
                control.Update();
                updateList[control.Index] = control.Value;
            }
        }


        public static bool SetControl(int index, bool isAuto, int value)
        {
            try
            {
                var control = (BaseControl)_controlList[index];
                return isAuto ? control.SetAuto() : control.SetSpeed(value);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return false;
            }
        }
    }
}