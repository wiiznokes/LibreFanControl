import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import logicControl.getSetControlList
import settings.Settings
import ui.screen.body.fanList.fan
import utils.initSensor
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class Application {

    private var jobUpdate: Job? = null

    fun onStart() {
        // initialize setting state, and settings.json
        Settings()

        val settings = State._settings.asStateFlow()

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
                    fans = State._fanList,
                    temps = State._tempList,
                    controls = State._controlItemList
                )
            }
            when (configId) {
                null -> initSensor()
                else -> Configuration.loadConfig(configId)
            }

            while (!updateShouldStop) {
                val setControlList = async {
                    /*
                        we catch exception because another thread can modify
                        state value, for example, if we remove an item
                        that can lead to null pointer exception.
                        This is not a problem because we can recalculate
                        an updateDelay later
                    */
                    try {
                        getSetControlList(
                            controlItemList = State._controlItemList.asStateFlow().value,
                            behaviorItemList = State._behaviorItemList.asStateFlow().value,
                            tempList = State._tempList.asStateFlow().value
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

                externalManager.updateFan(State._fanList)
                externalManager.updateTemp(State._tempList)
                externalManager.updateControl(State._controlItemList)

                setControlList.await()?.forEach {
                    // we update controlList here because
                    // this part of the coroutine is thread safe
                    State._controlItemList.update { controlList ->
                        if (controlList[it.index].controlShouldBeSet != it.controlShouldBeSet)
                            controlList[it.index].controlShouldBeSet = it.controlShouldBeSet
                        controlList
                    }
                    externalManager.setControl(it.libIndex, it.isAuto, it.value)
                }

                delay(updateDelay.toDuration(DurationUnit.SECONDS))
            }
            externalManager.stop()
        }
    }
}