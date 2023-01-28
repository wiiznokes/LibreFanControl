using HardwareDaemon.Hardware.Control;
using HardwareDaemon.Hardware.Sensor;

namespace HardwareDaemon.Hardware;

public class HardwareManager
{
    private static List<BaseControl> _controlList = new();
    private static List<BaseSensor> _fanList = new();
    private static List<BaseSensor> _tempList = new();

    private readonly Lhm _lhm = new();


    public void Start()
    {
        _lhm.Start();

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

   
    public static void UpdateControl()
    {
        foreach (var control in _controlList)
        {
            control.Update();
        }
    }

    public void UpdateFan()
    {
        _lhm.Update();
        foreach (var fan in _fanList)
        {
            fan.Update();
        }
    }

    public static void UpdateTemp()
    {
        foreach (var temp in _tempList)
        {
            temp.Update();
        }
    }


    public static bool SetControl(int index, bool isAuto, int value)
    {
        try
        {
            var control = _controlList[index];
            return isAuto ? control.SetAuto() : control.SetSpeed(value);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
            return false;
        }
    }
}