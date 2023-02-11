using HardwareDaemon.Model;
using Proto.Generated.PSettings;

namespace HardwareDaemon.Proto;

public class ProtoException : Exception
{
    public ProtoException(string msg) : base(msg)
    {
    }
}

public static class SettingsHelper
{
    private const string SettingsFile = "./conf/settings";

    public static Settings LoadSettingsFile()
    {
        var pSettings = PSettings.Parser.ParseFrom(GetSettingsBytes());
        return ParsePSettings(pSettings);
    }

    private static byte[] GetSettingsBytes()
    {
        var filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, SettingsFile);
        return File.ReadAllBytes(filePath);
    }


    public static Settings ParsePSettings(PSettings pSetting)
    {
        return new Settings(
            NullableToNull(pSetting.PConfId),
            pSetting.PUpdateDelay
        );
    }


    public static string? NullableToNull(NullableId nullableId)
    {
        return nullableId.KindCase switch
        {
            NullableId.KindOneofCase.PId => nullableId.PId,
            NullableId.KindOneofCase.Null => null,
            _ => throw new ProtoException("Nullable id not set")
        };
    }
}