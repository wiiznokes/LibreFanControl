import androidx.compose.runtime.mutableStateOf
import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import logicControl.Logic
import logicControl.SetControlModel
import model.SettingsModel
import utils.initSensor
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class Application(
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val controlsChange: StateFlow<Boolean> = State.controlsChange.asStateFlow()
) {

    private var jobUpdate: Job? = null

    fun onStart() {
        jobUpdate = CoroutineScope(Dispatchers.IO).launch {
            startUpdate()
        }
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
        val configId = settings.value.configId

        coroutineScope {
            val externalManager = ExternalManager().apply {
                // load library
                start()
            }
            when (configId) {
                null -> initSensor()
                else -> Configuration.loadConfig(configId)
            }

            val controlsHasChangeMarker = mutableStateOf(configId != null)
            var shouldDelay: Boolean

            val logic = Logic()

            while (!updateShouldStop) {
                /*
                    we catch exception because another thread can modify
                    state value, for example, if we remove an item
                    that can lead to null pointer exception.
                    This is not a problem because we can recalculate
                */

                val setControlList: List<SetControlModel>? = try {
                    logic.getSetControlList(controlsHasChangeMarker.value)
                } catch (e: Exception) {
                    if (e !is NullPointerException) e.printStackTrace()
                    else println("null pointer exception in logic")
                    null
                }

                externalManager.updateFan()
                externalManager.updateTemp()
                externalManager.updateControl()

                /*
                    controlsChange is updated here, in ControlViewModel, in ConfigurationViewModel  and
                    in BehaviorViewModel.

                    ControlViewModel, ConfigurationViewModel and BehaviorViewModel are in the same coroutine,
                    but here, we are in another coroutine.
                    We use a marker because control may change after we calculate values to set.

                    So we recalculate one time before setting the values
                */
                if (setControlList == null) {
                    if (controlsChange.value) {
                        controlsHasChangeMarker.value = true
                    }
                    shouldDelay = false
                } else {
                    shouldDelay = logic.update(
                        setControlList = setControlList,
                        controlsHasChangeMarker = controlsHasChangeMarker,
                        setControl = { libIndex, isAuto, value ->
                            externalManager.setControl(libIndex, isAuto, value)
                        }
                    )
                }

                if (shouldDelay)
                    delay(settings.value.updateDelay.toDuration(DurationUnit.SECONDS))
            }
            externalManager.stop()
        }
    }
}