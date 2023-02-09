namespace HardwareDaemon.Model;

public class Settings
{
    public Settings(string? confId, int updateDelay)
    {
        ConfId = confId;
        UpdateDelay = updateDelay;
    }

    public string? ConfId { get; set; }
    public int UpdateDelay { get; set; }
}