import external.ExternalManager
import kotlinx.coroutines.*
import model.Settings
import utils.initSensor


/**
 * main logic of the app
 */
class Application(
    private val settings: Settings = State.settings,
) {

    private lateinit var jobUpdate: Job


    fun onStart() {
        val configId = settings.configId.value
        ExternalManager.start()
        when (configId) {
            null -> initSensor()
            else -> {}
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