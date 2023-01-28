using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;
using HardwareLib.Hardware;
using NvAPIWrapper;
using NvAPIWrapper.GPU;
using NvAPIWrapper.Native.GPU;

namespace HardwareDaemon.Hardware
{
    public class HardwareManager
    {
        private static List<BaseDevice> _tempList;
        private static List<BaseDevice> _fanList;
        private static List<BaseDevice> _controlList;

        private Lhm _lhm;

        //static private List<OSDSensor> osdSensorList;
        //static private Dictionary<string, OSDSensor> osdSensorMap;


        public void Start()
        {
            _lhm = new Lhm();
            _lhm.Start();


            _fanList = new List<BaseDevice>();
            _tempList = new List<BaseDevice>();
            _controlList = new List<BaseDevice>();


            //osdSensorList = new List<OSDSensor>();
            //osdSensorMap = new Dictionary<string, OSDSensor>();

            _lhm.CreateFan(ref _fanList);
            _lhm.CreateTemp(ref _tempList);
            _lhm.CreateControl(ref _controlList);
            
            
            if(OptionManager.getInstance().IsNvAPIWrapper == true)
            {
                this.lockBus();
                try
                {
                    NVIDIA.Initialize();
                }
                catch { }
                try
                {
                    var gpuArray = PhysicalGPU.GetPhysicalGPUs();
                    for (int i = 0; i < gpuArray.Length; i++)
                    {
                        var gpu = gpuArray[i];
                        var hardwareName = gpu.FullName;

                        // temperature
                        var id = string.Format("NvAPIWrapper/{0}/{1}/Temp", hardwareName, gpu.GPUId);
                        var name = "GPU Core";
                        var temp = new NvAPITemp(id, name, i, NvAPITemp.TEMPERATURE_TYPE.CORE);
                        temp.LockBus += lockBus;
                        temp.UnlockBus += unlockBus;

                        var tempDevice = new HardwareDevice(hardwareName);
                        tempDevice.addDevice(temp);

                        gpu.ThermalInformation.ThermalSensors
                            
                        if (gpu.ThermalInformation.HasAnyThermalSensor == true)
                        {
                            if (gpu.ThermalInformation.HotSpotTemperature != 0)
                            {
                                id = string.Format("NvAPIWrapper/{0}/{1}/HotSpotTemp", hardwareName, gpu.GPUId);
                                name = "GPU Hot Spot";
                                temp = new NvAPITemp(id, name, i, NvAPITemp.TEMPERATURE_TYPE.HOTSPOT);
                                temp.LockBus += lockBus;
                                temp.UnlockBus += unlockBus;
                                tempDevice.addDevice(temp);
                            }

                            if (gpu.ThermalInformation.MemoryJunctionTemperature != 0)
                            {
                                id = string.Format("NvAPIWrapper/{0}/{1}/MemoryJunctionTemp", hardwareName, gpu.GPUId);
                                name = "GPU Memory Junction";
                                temp = new NvAPITemp(id, name, i, NvAPITemp.TEMPERATURE_TYPE.MEMORY);
                                temp.LockBus += lockBus;
                                temp.UnlockBus += unlockBus;
                                tempDevice.addDevice(temp);
                            }
                        }

                        var tempList = TempList[(int)LIBRARY_TYPE.NvAPIWrapper];
                        tempList.Add(tempDevice);

                        var fanDevice = new HardwareDevice(hardwareName);
                        var controlDevice = new HardwareDevice(hardwareName);

                        int num = 1;
                        var e = gpuArray[i].CoolerInformation.Coolers.GetEnumerator();
                        while (e.MoveNext())
                        {
                            var value = e.Current;
                            int coolerID = value.CoolerId;
                            int speed = value.CurrentLevel;
                            int minSpeed = value.DefaultMinimumLevel;
                            int maxSpeed = value.DefaultMaximumLevel;
                            CoolerPolicy policy = value.DefaultPolicy;

                            // fan
                            id = string.Format("NvAPIWrapper/{0}/{1}/Fan/{2}", hardwareName, gpu.GPUId, coolerID);
                            name = "GPU Fan #" + num;
                            var fan = new NvAPIFanSpeed(id, name, i, coolerID);
                            fan.LockBus += lockBus;
                            fan.UnlockBus += unlockBus;
                            fanDevice.addDevice(fan);

                            // control
                            id = string.Format("NvAPIWrapper/{0}/{1}/Control/{2}", hardwareName, gpu.GPUId, coolerID);
                            name = "GPU Fan #" + num;
                            var control = new NvAPIFanControl(id, name, i, coolerID, speed, minSpeed, maxSpeed, policy);
                            control.LockBus += lockBus;
                            control.UnlockBus += unlockBus;
                            controlDevice.addDevice(control);
                            num++;
                        }

                        if (fanDevice.DeviceList.Count > 0)
                        {
                            var fanList = FanList[(int)LIBRARY_TYPE.NvAPIWrapper];
                            fanList.Add(fanDevice);
                        }

                        if (controlDevice.DeviceList.Count > 0)
                        {
                            var controlList = ControlList[(int)LIBRARY_TYPE.NvAPIWrapper];
                            controlList.Add(controlDevice);
                        }
                    }
                }
                catch { }
                this.unlockBus();
            }
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


        public void UpdateFan()
        {
            _lhm.Update();
            foreach (var fan in _fanList.Cast<BaseSensor>())
            {
                fan.Update();
            }
        }

        public static void UpdateTemp()
        {
            foreach (var temp in _tempList.Cast<BaseSensor>())
            {
                temp.Update();
            }
        }

        public static void UpdateControl()
        {
            foreach (var control in _controlList.Cast<BaseControl>())
            {
                control.Update();
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