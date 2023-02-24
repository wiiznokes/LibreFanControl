using HardwareDaemon.Model;
using Proto.Generated.PSettings;

namespace HardwareDaemon.Proto;

public class ProtoException : Exception
{
    public ProtoException(string msg) : base(msg)
    {
    }
}

public static class SettingsDir
{
    private const string Dir = "C:\\ProgramData\\FanControl";

    private static readonly string ConfDir = Path.Combine(
        Dir,
        "conf"
    );

    public static readonly string SettingsFile = Path.Combine(
        Dir,
        "settings"
    );


    public static string GetConfFile(string confId)
    {
        return Path.Combine(
            ConfDir,
            confId
        );
    }

    public static bool CheckFiles()
    {
        return File.Exists(SettingsFile);
    }
}

public static class SettingsHelper
{
    public static void LoadSettingsFile(Settings settingsState)
    {
        var pSettings = PSettings.Parser.ParseFrom(GetSettingsBytes());
        var settings = ParsePSettings(pSettings);
        settingsState.UpdateDelay = settings.UpdateDelay;
        settingsState.ConfId = settings.ConfId;
    }

    private static byte[] GetSettingsBytes()
    {
        return File.ReadAllBytes(SettingsDir.SettingsFile);
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