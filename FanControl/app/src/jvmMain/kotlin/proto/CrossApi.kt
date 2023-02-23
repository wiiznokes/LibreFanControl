package proto

import State
import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import proto.generated.pCrossApi.*
import java.io.Closeable
import java.util.concurrent.TimeUnit

class CrossApi(
    private val channel: ManagedChannel,
) : Closeable {
    private val stub: PCrossApiGrpcKt.PCrossApiCoroutineStub = PCrossApiGrpcKt.PCrossApiCoroutineStub(channel)


    suspend fun open(): Boolean {
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
            e.printStackTrace()
            false
        }
    }

    suspend fun getHardware() {
        val pControls = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.CONTROL })
        State.hControls.addAll(pControls.pHardwaresList.map {
            HControl(
                name = it.pName,
                id = it.pId
            )
        })

        val pTemps = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.TEMP })
        State.hTemps.addAll(pTemps.pHardwaresList.map {
            HTemp(
                name = it.pName,
                id = it.pId
            )
        })

        val pFans = stub.pGetHardware(pHardwareTypeMessage { pType = PHardwareType.FAN })
        State.hFans.addAll(pFans.pHardwaresList.map {
            HFan(
                name = it.pName,
                id = it.pId
            )
        })
        println("getHardware success")
    }



    suspend fun startUpdate() {
        val stream: Flow<PUpdateList> = stub.pStartStream(Empty.getDefaultInstance())

        stream.collect { updateList ->
            when (updateList.pType) {
                PHardwareType.CONTROL -> {
                    updateList.pUpdatesList.forEach {
                        State.hControls[it.pIndex].value.value = it.pValue
                    }
                }
                PHardwareType.TEMP -> {
                    updateList.pUpdatesList.forEach {
                        State.hTemps[it.pIndex].value.value = it.pValue
                    }
                }
                PHardwareType.FAN -> {
                    updateList.pUpdatesList.forEach {
                        State.hFans[it.pIndex].value.value = it.pValue
                    }
                }
                else -> throw ProtoException("unknown control type")
            }
        }

    }


    override fun close() {
        try {
            runBlocking{
                stub.pCloseStream(Empty.getDefaultInstance())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}