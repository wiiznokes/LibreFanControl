import Application.Api.api
import Application.Api.scope
import FState.hTemps
import FState.iBehaviors
import FState.iTemps
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.*
import model.item.ICustomTemp
import model.item.ILinear
import model.item.ITarget
import proto.ConfHelper
import proto.CrossApi
import proto.SettingsHelper
import ui.settings.Settings
import utils.OsSpecific
import utils.initSensor


class Application(
    private val settings: Settings = FState.settings,
) {

    object Api {
        private val channel = ManagedChannelBuilder.forAddress("localhost", 5002)
            .usePlaintext()
            .build()
        val api = CrossApi(channel)
        val scope = CoroutineScope(Dispatchers.IO)
    }


    private var calculateValueJob: Job? = null
    private var fetchSensorValueJob: Job? = null


    private fun startService(): Boolean {


        return when (OsSpecific.os.startService()) {
            0 -> {
                println("startService: success")
                true
            }

            3 -> {
                println("startService: failed")
                FState.ui.dialogExpanded.value = UiState.Dialog.NEED_ADMIN
                false
            }

            else -> {
                println("startService: failed")
                false
            }
        }
    }

    fun onStart() {
        if (SettingsHelper.isSettings()) {
            SettingsHelper.loadSettings()
        } else {
            SettingsHelper.writeSettings()
        }


        val startJob = scope.launch {

            if (startService()) {
                val maxDelay = 7000L
                var delay = 0L
                while (!FState.isServiceOpened && delay < maxDelay) {
                    delay += 500L
                    delay(delay)

                    if (api.open()) FState.isServiceOpened = true
                }
                if (!FState.isServiceOpened) {
                    FState.ui.showError("service can't be opened for some reason")
                }
            }


            api.getHardware()

            settings.confId.value.let {
                when (it) {
                    null -> initSensor()
                    else -> {
                        if (!ConfHelper.loadConf(it)) {
                            settings.confId.value = null
                            SettingsHelper.writeSettings()
                        }
                        println("load conf $it success")
                    }
                }
            }
        }

        calculateValueJob = scope.launch {
            startJob.join()
            if (FState.isServiceOpened) {
                startCalculate()
            }
        }

        fetchSensorValueJob = scope.launch {
            startJob.join()

            if (FState.isServiceOpened) {
                api.startUpdate()
            }
        }
    }


    private var updateShouldStop = false

    private suspend fun startCalculate() {

        while (!updateShouldStop) {


            val iCustomTemps = iTemps.filterIsInstance<ICustomTemp>()

            iCustomTemps.forEach {
                it.calcAndSet(hTemps = hTemps)
            }

            iBehaviors.forEach {
                when (it) {
                    is ILinear -> {
                        it.calcAndSet(
                            hTemps = hTemps,
                            iCustomTemps = iCustomTemps
                        )
                    }

                    is ITarget -> {
                        it.calcAndSet(
                            hTemps = hTemps,
                            iCustomTemps = iCustomTemps
                        )
                    }
                }
            }

            delay(settings.updateDelay.value * 1000L)
        }
    }


    fun onStop() {
        api.close()
        updateShouldStop = true
        runBlocking {
            calculateValueJob?.cancelAndJoin()
            fetchSensorValueJob?.cancelAndJoin()
        }
    }
}