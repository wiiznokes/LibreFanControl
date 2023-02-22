import State.hTemps
import State.iBehaviors
import State.iTemps
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
    private val settings: Settings = State.settings,
) {


    private val scope = CoroutineScope(Dispatchers.IO)

    private val channel = ManagedChannelBuilder.forAddress("localhost", 5002)
        .usePlaintext()
        .build()
    private val api = CrossApi(channel)

    private lateinit var calculateValueJob: Job
    private lateinit var fetchSensorValueJob: Job

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
            if (!api.open()) {
                println("can't open service, exit app")
            }
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

        calculateValueJob = scope.launch {
            startUpdate()
        }
        fetchSensorValueJob = scope.launch {
            api.update()
        }
    }


    private var updateShouldStop = false
    fun onStop() {
        updateShouldStop = true
        runBlocking {
            calculateValueJob.cancelAndJoin()
            fetchSensorValueJob.cancelAndJoin()
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
            "debug"
        )
        val process = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        val exitCode = process.waitFor()

        if (exitCode == 3) {
            println("need admin, exit app")
            return false
        }

        return true
    }
}