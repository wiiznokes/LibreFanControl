import Application.Api.api
import Application.Api.scope
import FState.hTemps
import FState.iBehaviors
import FState.iTemps
import FState.service
import ServiceState.Status
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.*
import model.item.ICustomTemp
import model.item.ILinear
import model.item.ITarget
import proto.ConfHelper
import proto.CrossApi
import proto.SettingsDir
import proto.SettingsHelper
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


    fun onStart() {

        if (!SettingsDir.tryCreateConfDirsIfNecessary()) {
            throw Exception("Need to be admin to initialise setting files")
        }

        if (SettingsHelper.isSettings()) {
            SettingsHelper.loadSettings()
        } else {
            SettingsHelper.writeSettings()
        }

        val startJob = scope.launch {

            // version of service installed is not up-to-date
            if (FState.appVersion != settings.versionInstalled.value) {
                println("version service is not up-to-date")

                println("uninstall service")
                if (!OsSpecific.os.uninstallService()) {
                    service.setErrorStatus()
                    return@launch
                }


                println("install service")
                if (!OsSpecific.os.installService(FState.appVersion)) {
                    service.setErrorStatus()
                    return@launch
                }

                println("change start mode service")
                if (!OsSpecific.os.changeStartModeService(FState.settings.launchAtStartUp.value)) {
                    service.setErrorStatus()
                    return@launch
                }
            }


            println("start service")
            if (OsSpecific.os.startService()) {
                val maxDelay = 7000L
                var delay = 0L
                service.status.value = Status.WAIT_OPEN
                while (service.status.value == Status.WAIT_OPEN && delay < maxDelay) {
                    delay += 500L
                    delay(delay)

                    if (api.open()) service.status.value = Status.OPEN
                }

                if (service.status.value != Status.OPEN) {
                    service.setErrorStatus()
                    FState.ui.showError(UiState.CustomError("service can't be opened for some reason"))
                }
            } else {
                service.setErrorStatus()
            }

            if (service.status.value == Status.ERROR) {
                return@launch
            }


        }

        calculateValueJob = scope.launch {
            startJob.join()

            while (true) {
                if (service.status.value == Status.OPEN) {
                    startCalculate()
                } else {
                    delay(1500L)
                }
            }
        }

        fetchSensorValueJob = scope.launch {
            startJob.join()

            while (true) {
                if (service.status.value == Status.OPEN) {
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

                    api.startUpdate()
                } else {
                    delay(1500L)
                }
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