using HardwareDaemon.Model;
using Proto.Generated.Setting;

namespace HardwareDaemon.Proto;


public class ProtoException : Exception
{
    public ProtoException(string msg) : base(msg)
    {
    }
}

public class SettingsHelper
{

    public static Settings ParseSettings(PSetting pSetting)
    {
        return new Settings(
            NullableToNull(pSetting.PConfId), 
            pSetting.PUpdateDelay
        );
    }
    
    
    
    public static string? NullableToNull(NullableId nullableId)
    {
        switch (nullableId.KindCase)
        {
            case NullableId.KindOneofCase.PId:
                return nullableId.PId;
            case NullableId.KindOneofCase.Null:
                return null;
            default:
                throw new ProtoException("Nullable id not set");
        }
    }
}


