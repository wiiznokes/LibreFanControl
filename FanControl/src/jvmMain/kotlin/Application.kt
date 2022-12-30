import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asStateFlow
import logicControl.Logic
import settings.Settings
import utils.initSensor


class Application {

    private var jobUpdate: Job? = null
    private var jobControlUpdate: Job? = null


    companion object {
        private val externalManager = ExternalManager()
        fun setControl(libIndex: Int, isAuto: Boolean, value: Int? = null) {
            println("try to set control: index: $libIndex, isAuto: $isAuto, value: $value")
            /*
            externalManager.setControl(
                libIndex = libIndex,
                isAuto = isAuto,
                value = value
            )
            */
        }
    }

    fun onStart() {
        // initialize setting state, and settings.json
        Settings()

        // load library
        externalManager.start(
            State._fanList,
            State._tempList,
            State._controlItemList
        )

        when (val configId = State._settings.asStateFlow().value.configId.value) {
            null -> {
                initSensor(
                    fanList = State._fanList,
                    tempList = State._tempList,

                    fanItemList = State._fanItemList,
                    tempItemList = State._tempItemList
                )
            }

            else -> {
                Configuration.loadConfig(
                    configId = configId,
                    controlItemList = State._controlItemList,
                    behaviorItemList = State._behaviorItemList,
                    fanItemList = State._fanItemList,
                    tempItemList = State._tempItemList,

                    fanList = State._fanList,
                    tempList = State._tempList
                )
            }
        }
        startUpdate()
        startControlUpdate()
    }


    private var updateShouldStop = false
    private var updateControlShouldStop = false
    fun onStop() {
        runBlocking {
            updateShouldStop = true
            updateControlShouldStop = true
            jobUpdate?.cancel()
            jobControlUpdate?.cancel()
            jobUpdate = null
            jobControlUpdate = null
            externalManager.stop()
        }
    }


    private fun startUpdate() {
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            while (!updateShouldStop) {
                externalManager.updateFan(State._fanList)
                externalManager.updateTemp(State._tempList)
                externalManager.updateControl(State._controlItemList)

                delay(2000L)
            }
        }
    }

    private val logic = Logic()

    private fun startControlUpdate() {
        jobControlUpdate = CoroutineScope(Dispatchers.Default).launch {
            while (!updateControlShouldStop) {
                logic.update()

                delay(2000L)
            }
        }
    }
}