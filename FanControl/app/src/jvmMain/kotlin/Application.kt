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
import ui.drawer.settings.getStartMode
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


    private fun startService(): Boolean {
        val initScript = File(System.getProperty("compose.application.resources.dir"))
            .resolve("scripts/service/init.ps1")
            .absolutePath
        val startMode = getStartMode(settings.launchAtStartUp.value)

        val command = listOf(
            "powershell.exe",
            "-File",
            initScript,
            "Debug"
        )

        val res = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return when (res) {
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
                delay(500L)
                if (api.open()) {
                    FState.isServiceOpenned = true
                }
            }


            api.getHardware()

            settings.confId.value.let {
                when (it) {
                    null -> initSensor()
                    else -> {
                        if (!ConfHelper.loadConf(it)) {
                            settings.confId.value = null
                            SettingsHelper.writeSettings(false)
                        }
                        println("load conf $it success")
                    }
                }
            }
        }

        calculateValueJob = scope.launch {
            startJob.join()
            if (FState.isServiceOpenned) {
                startCalculate()
            }
        }

        fetchSensorValueJob = scope.launch {
            startJob.join()

            if (FState.isServiceOpenned) {
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