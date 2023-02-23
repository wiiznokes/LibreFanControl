package proto

import FState
import FState.showError
import com.google.protobuf.Empty
import io.grpc.ManagedChannel
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
        val isActive = !channel.isShutdown && !channel.isTerminated

        if (!isActive) {
            println("service is not active")
            return false
        }
        return true
    }

    suspend fun open(): Boolean {
        if (!isActive()) {
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
        } catch (e: Exception) {
            showError(e)
            false
        }
    }

    suspend fun getHardware() {
        if (!isActive()) return

        val pControls = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.CONTROL })
        FState.hControls.addAll(pControls.pHardwaresList.map {
            HControl(
                name = it.pName,
                id = it.pId
            )
        })

        val pTemps = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.TEMP })
        FState.hTemps.addAll(pTemps.pHardwaresList.map {
            HTemp(
                name = it.pName,
                id = it.pId
            )
        })

        val pFans = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.FAN })
        FState.hFans.addAll(pFans.pHardwaresList.map {
            HFan(
                name = it.pName,
                id = it.pId
            )
        })
        println("getHardware success")
    }


    suspend fun startUpdate() {
        if (!isActive()) return

        val stream: Flow<PUpdateList> = stub.pStartStream(Empty.getDefaultInstance())

        try {
            stream.collect { updateList ->
                when (updateList.pType) {
                    PHardwareType.CONTROL -> {
                        updateList.pUpdatesList.forEach {
                            FState.hControls[it.pIndex].value.value = it.pValue
                        }
                    }

                    PHardwareType.TEMP -> {
                        updateList.pUpdatesList.forEach {
                            FState.hTemps[it.pIndex].value.value = it.pValue
                        }
                    }

                    PHardwareType.FAN -> {
                        updateList.pUpdatesList.forEach {
                            FState.hFans[it.pIndex].value.value = it.pValue
                        }
                    }

                    else -> throw ProtoException("unknown control type")
                }
            }
        } catch (e: Exception) {
            println("ERROR: stream update has stop")
        }

    }


    override fun close() {
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

    }


    suspend fun settingsAndConfChange() {
        if (!isActive()) return

        val res = stub.pSettingsAndConfChange(Empty.getDefaultInstance())

        when (res.pIsSuccess) {
            true -> println("confChange success")
            false -> println("confChange failed")
        }
    }

    suspend fun settingsChange() {
        if (!isActive()) return

        val res = stub.pSettingsChange(Empty.getDefaultInstance())

        when (res.pIsSuccess) {
            true -> println("settingsChange success")
            false -> println("settingsChange failed")
        }
    }
}