package external

interface External {

    enum class Command {
        Close,
        GetControlsInfo,
        GetFansInfo,
        GetTempsInfo,

        GetUpdateControls,
        GetUpdateFans,
        GetUpdateTemps,

        ReloadSetting,
        ReloadConfig
    }

    fun start()

    fun close()

    fun setControls()
    fun setFans()
    fun setTemps()

    fun updateControls()
    fun updateFans()
    fun updateTemps()

    fun reloadSetting()
    fun reloadConfig(id: Long?)
}