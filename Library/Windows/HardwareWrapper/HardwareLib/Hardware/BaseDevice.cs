namespace HardwareLib.Hardware
{
    public class BaseDevice
    {
        public string Name { get; set; }
        public int Index { get; set; }
        public string Id { get; set; }

        public int Value { get; set; }

        public virtual void Update()
        {
        }
    }
}