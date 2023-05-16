package proto

import FState
import FState.hControls
import FState.hFans
import FState.hTemps
import ServiceState
import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.grpc.StatusException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import proto.generated.pCrossApi.PCrossApiGrpcKt
import proto.generated.pCrossApi.PHardwareType
import proto.generated.pCrossApi.PUpdateList
import proto.generated.pCrossApi.pHardwareTypeMessage
import java.io.Closeable
import java.util.concurrent.TimeUnit

class CrossApi(
    private val channel: ManagedChannel,
) : Closeable {
    private val stub: PCrossApiGrpcKt.PCrossApiCoroutineStub = PCrossApiGrpcKt.PCrossApiCoroutineStub(channel)


    // don't always relevant
    private fun isActive(): Boolean {
        val isActive =
            FState.service.status.value == ServiceState.Status.OPEN && !channel.isShutdown && !channel.isTerminated

        if (!isActive) {
            println("service is not active")
            FState.service.setErrorStatus()
            return false
        }
        return true
    }

    suspend fun open(): Boolean {
        if (channel.isShutdown || channel.isTerminated) {
            println("api open: failed, channel is not valid")
            return false
        }

        return try {
            val res = stub.pOpen(Empty.getDefaultInstance())

            when (res.pIsSuccess) {
                true -> {
                    println("open success")
                    true
                }

                false -> {
                    println("open service returned false")
                    false
                }
            }
        } catch (e: StatusException) {
            println("Error: can't open, service status = ${e.status.code}")
            false
        } catch (e: Exception) {
            e.printStackTrace()
            FState.service.setErrorStatus()
            false
        }
    }

    suspend fun getHardware(): Boolean {
        if (!isActive()) return false

        try {
            val pControls = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.CONTROL })
            hControls.apply {
                clear()
                addAll(pControls.pHardwaresList.map {
                    HControl(
                        name = it.pName,
                        id = it.pId
                    )
                })
            }

            val pTemps = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.TEMP })
            hTemps.apply {
                clear()
                addAll(pTemps.pHardwaresList.map {
                    HTemp(
                        name = it.pName,
                        id = it.pId
                    )
                })
            }

            val pFans = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.FAN })
            hFans.apply {
                clear()
                addAll(pFans.pHardwaresList.map {
                    HFan(
                        name = it.pName,
                        id = it.pId
                    )
                })
            }
            println("getHardware success")
            return true

        } catch (e: Exception) {
            e.printStackTrace()
            FState.service.setErrorStatus()
            return false
        }
    }


    suspend fun startUpdate() {
        if (!isActive()) return

        val stream: Flow<PUpdateList> = stub.pStartStream(Empty.getDefaultInstance())

        println("streaming has started")

        try {
            stream.collect { updateList ->
                when (updateList.pType) {
                    PHardwareType.CONTROL -> {
                        updateList.pUpdatesList.forEach {
                            hControls[it.pIndex].value.value = it.pValue
                        }
                    }

                    PHardwareType.TEMP -> {
                        updateList.pUpdatesList.forEach {
                            hTemps[it.pIndex].value.value = it.pValue
                        }
                    }

                    PHardwareType.FAN -> {
                        updateList.pUpdatesList.forEach {
                            hFans[it.pIndex].value.value = it.pValue
                        }
                    }

                    else -> throw ProtoException("unknown control type")
                }
            }
        } catch (e: Exception) {
            if (!shouldClose) {
                shouldClose = false
                e.printStackTrace()
            }
            println("ERROR: stream update has stop")
            FState.service.setErrorStatus()
        }

    }

    private var shouldClose = false
    override fun close() {
        shouldClose = true

        try {
            if (isActive()) {
                runBlocking {
                    stub.pCloseStream(Empty.getDefaultInstance())
                }
            }

        } catch (e: Exception) {
            println("close has failed")
        }
        try {
            if (!channel.isShutdown) {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
            }
        } catch (e: Exception) {
            println("shutdown has failed")
        }

        println("close api finished")
    }


    suspend fun settingsAndConfChange() {
        if (!isActive()) return

        try {
            val res = stub.pSettingsAndConfChange(Empty.getDefaultInstance())

            when (res.pIsSuccess) {
                true -> println("confChange success")
                false -> println("confChange failed")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            FState.service.setErrorStatus()
        }


    }

    suspend fun settingsChange() {
        if (!isActive()) return

        try {
            val res = stub.pSettingsChange(Empty.getDefaultInstance())

            when (res.pIsSuccess) {
                true -> println("settingsChange success")
                false -> println("settingsChange failed")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            FState.service.setErrorStatus()
        }

    }
}