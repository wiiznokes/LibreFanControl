import hardware.external.ExternalManager
import hardware.external.getOS
import kotlinx.coroutines.*


class Application() {

    private var jobUpdate: Job? = null

    companion object {
        private val externalManager = ExternalManager(getOS())

        fun setValue(index: Int, isAuto: Boolean, value: Int) {
            externalManager.setControl(index, isAuto, value)
        }

    }

    fun onStart() {
        //check si conf exist

        // get conf

        //load viewModelFactory

        //load library

        // applique conf if conf

        // start update


        externalManager.start(
            State._fanList,
            State._tempList,
            State._controlList
        )

        startUpdate()
    }


    fun onStop() {
        runBlocking {
            updateShouldStop = true
            jobUpdate?.cancel()
            jobUpdate = null
            externalManager.stop()
        }
    }


    private fun startUpdate() {
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            while (!updateShouldStop) {
                externalManager.updateFan(State._fanList, State._addFanList)
                externalManager.updateTemp(State._tempList, State._addTempList)
                externalManager.updateControl(State._controlList, State._addControlList)

                delay(2000L)
            }
        }
    }

    private var updateShouldStop = false
}