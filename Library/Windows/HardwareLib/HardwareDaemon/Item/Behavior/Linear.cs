namespace HardwareDaemon.Item.Behavior;

public class Linear : BehaviorWithTemp
{
    public Linear(string id, string? tempId, int minTemp, int maxTemp, int minFanSpeed, int maxFanSpeed) : base(id,
        tempId)
    {
        MinTemp = minTemp;
        MaxTemp = maxTemp;
        MinFanSpeed = minFanSpeed;
        MaxFanSpeed = maxFanSpeed;
    }

    private int MinTemp { get; }
    private int MaxTemp { get; }
    private int MinFanSpeed { get; }
    private int MaxFanSpeed { get; }

    public override int GetSpeed()
    {
        var tempValue = GetSensorValue();

        if (tempValue <= MinTemp) return MinFanSpeed;

        if (tempValue >= MaxTemp) return MaxFanSpeed;

        var affine = GetAffine();

        return (int)Math.Round(affine.A * tempValue + affine.B);
    }


    private Affine GetAffine()
    {
        var a = (MaxFanSpeed - MinFanSpeed) / (float)(MaxTemp - MinTemp);
        return new Affine(
            a,
            MinFanSpeed - a * MinTemp
        );
    }

    private class Affine
    {
        public Affine(float a, float b)
        {
            A = a;
            B = b;
        }

        public float A { get; }
        public float B { get; }
    }
}