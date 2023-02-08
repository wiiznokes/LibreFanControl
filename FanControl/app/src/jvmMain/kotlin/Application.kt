import external.ExternalManager
import kotlinx.coroutines.*
import model.Settings
import proto.ConfHelper
import proto.SettingsHelper
import utils.initSensor


class Application(
    private val settings: Settings = State.settings,
) {

    private lateinit var jobUpdate: Job

    fun onCreate() {
        if (SettingsHelper.checkSetting()) {
            println("load setting")
            SettingsHelper.loadSettings()
        } else {
            SettingsHelper.writeSettings()
        }
    }

    fun onStart() {
        ExternalManager.start()

        settings.confId.value.let {
            when (it) {
                null -> initSensor()
                else -> {
                    println("load conf $it")
                    ConfHelper.loadConf(it)
                }
            }
        }
        jobUpdate = CoroutineScope(Dispatchers.IO).launch { startUpdate() }
    }


    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking { jobUpdate.cancelAndJoin() }
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