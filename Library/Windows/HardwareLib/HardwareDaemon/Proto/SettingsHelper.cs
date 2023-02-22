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
    private const string SettingsFile = "./.FanControl/settings";

    public static void LoadSettingsFile(Settings settingsState)
    {
        var pSettings = PSettings.Parser.ParseFrom(GetSettingsBytes());
        var settings =  ParsePSettings(pSettings);
        settingsState.UpdateDelay = settings.UpdateDelay;
        settingsState.ConfId = settings.ConfId;
    }

    private static byte[] GetSettingsBytes()
    {
        var filePath = Path.Combine(
            Environment.GetFolderPath(Environment.SpecialFolder.UserProfile),
            SettingsFile
        );
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