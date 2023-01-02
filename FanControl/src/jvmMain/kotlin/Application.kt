import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import logicControl.Logic
import model.SettingsModel
import utils.initSensor


class Application(
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    private var jobUpdate: Job? = null
    private val externalManager = ExternalManager()

    fun onStart() {
        val configId = settings.value.configId
        externalManager.start()
        when (configId) {
            null -> initSensor()
            else -> {
                controlsChange.value = true
                Configuration.loadConfig(configId)
            }
        }
        jobUpdate = CoroutineScope(Dispatchers.IO).launch { startUpdate() }
    }


    private var updateShouldStop = false
    fun onStop() {
        runBlocking {
            updateShouldStop = true
            jobUpdate?.cancel()
            jobUpdate = null
        }
    }


    private suspend fun startUpdate() {

        val logic = Logic(
            externalManager = externalManager
        )

        while (!updateShouldStop) {
            logic.update()
        }
        externalManager.stop()
    }
}