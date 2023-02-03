import State.hControls
import State.hFans
import State.hTemps
import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import settings.Settings
import settings.SettingsModel
import utils.initSensor


/**
 * main logic of the app
 */
class Application(
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow()
) {

    private lateinit var jobUpdate: Job


    fun onStart() {
        val configId = settings.value.configId
        ExternalManager.start()
        when (configId) {
            null -> initSensor()
            else -> {
                Configuration.loadConfig(configId)
            }
        }
        jobUpdate = CoroutineScope(Dispatchers.IO).launch { startUpdate() }
    }


    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking { jobUpdate.cancelAndJoin() }

        if (settings.value.firstStart) {
            Settings.setSetting("first_start", false)
        }
    }

    private suspend fun startUpdate() {

        while (!updateShouldStop) {
            ExternalManager.updateControls()
            ExternalManager.updateFans()
            ExternalManager.updateTemps()

            delay(settings.value.updateDelay * 1000L)
        }
        ExternalManager.close()
    }
}