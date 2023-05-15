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

            // version of service installed is not up to date
            if (FState.appVersion != settings.versionInstalled.value) {
                // uninstall
                if(!OsSpecific.os.uninstallService()) {
                    FState.serviceState.value = ServiceState.ERROR
                    return@launch
                }

                // install
                if(!OsSpecific.os.installService(FState.appVersion)) {
                    FState.serviceState.value = ServiceState.ERROR
                    return@launch
                }

                if (!OsSpecific.os.changeStartModeService(FState.settings.launchAtStartUp.value)) {
                    FState.serviceState.value = ServiceState.ERROR
                    return@launch
                }
            }




            if (OsSpecific.os.startService()) {
                val maxDelay = 7000L
                var delay = 0L
                FState.serviceState.value = ServiceState.WAIT_OPEN
                while (FState.serviceState.value == ServiceState.WAIT_OPEN && delay < maxDelay) {
                    delay += 500L
                    delay(delay)

                    if (api.open()) FState.serviceState.value = ServiceState.OPEN
                }

                if (FState.serviceState.value != ServiceState.OPEN) {
                    FState.serviceState.value = ServiceState.ERROR
                    FState.ui.showError(CustomError("service can't be opened for some reason"))
                }
            } else {
                FState.serviceState.value = ServiceState.ERROR
            }

            if (FState.serviceState.value == ServiceState.ERROR) {
                return@launch
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
            if (FState.serviceState.value == ServiceState.OPEN) {
                startCalculate()
            }
        }

        fetchSensorValueJob = scope.launch {
            startJob.join()

            if (FState.serviceState.value == ServiceState.OPEN) {
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