import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import logicControl.getSetControlList
import model.SettingsModel
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.initSensor
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class Application(
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow(),

    private val settings: StateFlow<SettingsModel> = State._settings.asStateFlow()
) {
    private val tempList = _tempList.asStateFlow()

    private var jobUpdate: Job? = null

    fun onStart() {
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            startUpdate(
                configId = settings.value.configId,
                updateDelay = settings.value.updateDelay
            )
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


    private suspend fun startUpdate(configId: Long?, updateDelay: Int) {
        coroutineScope {
            val externalManager = ExternalManager().apply {
                // load library
                start(
                    fans = _fanList,
                    temps = _tempList,
                    controls = _controlItemList
                )
            }
            when (configId) {
                null -> initSensor()
                else -> Configuration.loadConfig(configId)
            }

            while (!updateShouldStop) {
                    /*
                        we catch exception because another thread can modify
                        state value, for example, if we remove an item
                        that can lead to null pointer exception.
                        This is not a problem because we can recalculate
                        an updateDelay later
                    */
                    val setControlList = try {
                        getSetControlList(
                            controlItemList = _controlItemList,
                            behaviorItemList = behaviorItemList.value,
                            tempList = tempList.value
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }


                externalManager.updateFan(_fanList)
                externalManager.updateTemp(_tempList)
                externalManager.updateControl(_controlItemList)




                setControlList?.forEach { model ->
                    // we update controlList here because
                    // this part of the coroutine is thread safe
                    State._controlItemList.update {

                        it[model.index] = it[model.index].copy(
                            controlShouldBeSet = model.controlShouldBeSet
                        )
                        if (!it[model.index].logicHasVerify)
                            it[model.index] = it[model.index].copy(
                                logicHasVerify = true
                            )
                        it
                    }
                    externalManager.setControl(model.libIndex, model.isAuto, model.value)
                }
                delay(updateDelay.toDuration(DurationUnit.SECONDS))
            }
            externalManager.stop()
        }
    }
}