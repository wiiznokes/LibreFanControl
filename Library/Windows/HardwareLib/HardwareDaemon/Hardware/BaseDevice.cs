namespace HardwareDaemon.Hardware
{
    public class BaseDevice
    {
        public string Name { get; protected set; }
        public int Index { get; protected set; }
        public string Id { get; protected set; }

        public int Value { get; protected set; }

        public virtual void Update()
        {
        }
    }
}