using Proto.Generated.PSettings;

namespace FanControlService.Proto;

public class ProtoException : Exception
{
    public ProtoException(string msg) : base(msg)
    {
    }
}

public static class SettingsDir
{
    #if WINDOWS
        private const string Dir = "C:\\ProgramData\\FanControl";
    #else
        private const string Dir = "/home/lenaic/.FanControl";
    #endif
    
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
    public static void LoadSettingsFile()
    {
        var pSettings = PSettings.Parser.ParseFrom(GetSettingsBytes());
        ParsePSettings(pSettings);
    }

    private static byte[] GetSettingsBytes()
    {
        return File.ReadAllBytes(SettingsDir.SettingsFile);
    }


    private static void ParsePSettings(PSettings pSetting)
    {
        State.Settings.UpdateDelay = pSetting.PUpdateDelay;
        State.Settings.ConfId = NullableToNull(pSetting.PConfId);
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