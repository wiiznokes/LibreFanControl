import Application.Api.api
import Application.Api.scope
import FState.hTemps
import FState.iBehaviors
import FState.iTemps
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.*
import model.Settings
import model.item.ICustomTemp
import model.item.ILinear
import model.item.ITarget
import proto.ConfHelper
import proto.CrossApi
import proto.SettingsHelper
import ui.screen.drawer.settings.getStartMode
import utils.initSensor
import java.io.File


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
        if (SettingsHelper.isSettings()) {
            SettingsHelper.loadSettings()
        } else {
            SettingsHelper.writeSettings()
        }

        var startServiceSuccess = false

        val startJob = scope.launch {

            if (!startService()) {
                startServiceSuccess = false
                return@launch
            }

            delay(500L)
            if (!api.open()) {
                startServiceSuccess = false
                return@launch
            }
            api.getHardware()

            settings.confId.value.let {
                when (it) {
                    null -> initSensor()
                    else -> {
                        println("load conf $it")
                        ConfHelper.loadConf(it)
                    }
                }
            }
            startServiceSuccess = true
        }

        calculateValueJob = scope.launch {
            startJob.join()
            if (startServiceSuccess) startUpdate()
        }


        fetchSensorValueJob = scope.launch {
            startJob.join()
            if (startServiceSuccess) api.startUpdate()
        }
    }


    private var updateShouldStop = false

    fun onStop() {
        api.close()
        updateShouldStop = true
        runBlocking {
            calculateValueJob?.cancelAndJoin()
            fetchSensorValueJob?.cancelAndJoin()
        }
    }

    private suspend fun startUpdate() {

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


    private fun startService(): Boolean {
        val initScript = File(System.getProperty("compose.application.resources.dir"))
            .resolve("scripts/service/init.ps1")
            .absolutePath
        val startMode = getStartMode(settings.launchAtStartUp.value)

        val command = listOf(
            "powershell.exe",
            "-File",
            initScript,
            startMode
        )

        val res = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return when (res) {
            0 -> true
            3 -> {
                FState.ui.dialogExpanded.value = UiState.Dialog.NEED_ADMIN
                false
            }

            else -> false
        }
    }
}