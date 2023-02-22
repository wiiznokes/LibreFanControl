import State.hTemps
import State.iBehaviors
import State.iTemps
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusException
import kotlinx.coroutines.*
import model.Settings
import model.item.ICustomTemp
import model.item.ILinear
import model.item.ITarget
import proto.ConfHelper
import proto.CrossApi
import proto.SettingsDir.createDirs
import proto.SettingsHelper
import proto.generated.pCrossApi.pOk
import ui.screen.drawer.settings.getStartMode
import utils.initSensor
import java.io.File


class Application(
    private val settings: Settings = State.settings,
) {
    

    private val scope = CoroutineScope(Dispatchers.IO)

    private val channel = ManagedChannelBuilder.forAddress("localhost", 5002)
        .usePlaintext()
        .build()
    private val api = CrossApi(channel)

    private lateinit var jobUpdate: Job

    fun onCreate() {
        if (SettingsHelper.isSettings()) {
            println("load setting")
            SettingsHelper.loadSettings()
        } else {
            SettingsHelper.writeSettings()
        }



        scope.launch {
            startService()
            delay(500L)
            tryOpenService()
        }
    }

    fun onStart() {

        settings.confId.value.let {
            when (it) {
                null -> initSensor()
                else -> {
                    println("load conf $it")
                    ConfHelper.loadConf(it)
                }
            }
        }
        jobUpdate = scope.launch { startUpdate() }
    }


    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking { jobUpdate.cancelAndJoin() }
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


    private suspend fun tryOpenService() {
        val res = api.open()
        if (res != pOk { pIsSuccess = false }) {
            throw IllegalArgumentException("open service failed")
        }
    }

    private fun startService() {
        val command = listOf(
            "powershell.exe",
            "-File",
            File(System.getProperty("compose.application.resources.dir"))
                .resolve("scripts/init_service.ps1")
                .absolutePath,
            getStartMode(settings.launchAtStartUp.value)
        )
        ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
    }
}