import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import logicControl.Logic
import logicControl.uiUpdate.UiUpdate
import settings.SettingsModel
import utils.initSensor


/**
 * main logic of the app
 */
class Application(
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    private lateinit var jobUpdate: Job
    private val externalManager = ExternalManager()
    private lateinit var logic: Logic

    /**
     * - init sensor lib
     * - check if configId exist in setting, if yes, load the configuration
     * if not, create one sensor item for each sensor
     * - launch a coroutine to update control with the last sensor value, and state
     */
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


    /**
     * only call when we click on open option of icon tray
     */
    fun onOpen() {
        uiUpdate = UiUpdate()
    }

    fun onClose() {
        uiUpdate = null
    }



    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking { jobUpdate.cancelAndJoin() }
        logic.finish()
        externalManager.stop()
    }

    private var uiUpdate: UiUpdate? = null
    private suspend fun startUpdate() {
        uiUpdate = UiUpdate()

        logic = Logic(
            externalManager = externalManager
        )
        while (!updateShouldStop) {
            logic.update()
            uiUpdate?.update()
        }
    }
}