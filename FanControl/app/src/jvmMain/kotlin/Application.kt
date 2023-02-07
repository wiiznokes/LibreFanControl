import external.ExternalManager
import kotlinx.coroutines.*
import model.Settings
import proto.SettingsHelper
import utils.initSensor


class Application(
    private val settings: Settings = State.settings,
) {

    private lateinit var jobUpdate: Job

    fun onCreate() {
        if (SettingsHelper.checkSetting()) {
            SettingsHelper.loadSetting()
        }
    }

    fun onStart() {
        ExternalManager.start()
        when (settings.configId.value) {
            null -> initSensor()
            else -> {
                // load config
            }
        }
        jobUpdate = CoroutineScope(Dispatchers.IO).launch { startUpdate() }
    }


    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking { jobUpdate.cancelAndJoin() }
        SettingsHelper.writeSetting()
    }

    private suspend fun startUpdate() {

        while (!updateShouldStop) {
            ExternalManager.updateControls()
            ExternalManager.updateFans()
            ExternalManager.updateTemps()

            delay(settings.updateDelay.value * 1000L)
        }
        ExternalManager.close()
    }
}