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
                    controlsChange state is updated only in _controlItemList.update function.
                    controlsChange is updated here, in ControlViewModel, in ReadItem  and
                    in BehaviorViewModel.

                    ControlViewModel, ReadItem and BehaviorViewModel are in the same coroutine,
                    but here, we are in another coroutine.
                    So we have to each time is controlsChange = true. We use a marker because
                    control may change after we calculate values to set. So we recalculate
                    one time before setting the values

                    The problem is we can't simply set controlsChange = false
                    because between the time we check the value of controlsChange here,
                    another coroutine may set controlsChange = true, but it may be
                    reassigned directly = false here.

                    This solution is not perfect (it can be slow), we should use a lock instead,
                    because it has a lighter implementation, and it is supposed to be use
                    for this.
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