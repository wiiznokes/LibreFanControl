import configuration.Configuration
import external.ExternalManager
import kotlinx.coroutines.*
import logicControl.Logic
import utils.initConfig
import utils.initSensor
import utils.initSettings


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
        // load library
        externalManager.start(
            State._fanList,
            State._tempList,
            State._controlItemList
        )
        initSettings()

        val configId = initConfig(
            configList = State._configList,
            idConfig = State._idConfig
        )

        when (configId) {
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

                    fanList = State._fanList.value,
                    tempList = State._tempList.value
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