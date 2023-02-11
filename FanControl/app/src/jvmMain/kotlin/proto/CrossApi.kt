package proto

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import proto.generated.pConf.PConf
import proto.generated.pCrossApi.PCrossApiGrpcKt
import proto.generated.pSettings.PSettings
import java.io.Closeable
import java.util.concurrent.TimeUnit

class CrossApi(
    private val channel: ManagedChannel
) : Closeable{
    private val stub: PCrossApiGrpcKt.PCrossApiCoroutineStub  = PCrossApiGrpcKt.PCrossApiCoroutineStub(channel)


     suspend fun open() {
         println("try request")
         stub.pOpen(Empty.getDefaultInstance())
         println("request done")
     }
    suspend fun closeService() {
        println("try request")
        stub.pClose(Empty.getDefaultInstance())
        println("request done")
    }

    suspend fun changeConf(pConf: PConf) {
        println("try request")
        stub.pChangeConf(pConf)
        println("request done")
    }
    suspend fun changeSettings(pSettings: PSettings) {
        println("try request")
        stub.pChangeSetting(pSettings)
        println("request done")
    }

    suspend fun setControls() {
        println("setControls")
    }
    suspend fun setTemps() {
        println("setTemps")
    }
    suspend fun setFans() {
        println("setFans")
    }

    suspend fun updateControls() {
        println("updateControls")
    }
    suspend fun updateTemps() {
        println("updateTemps")
    }
    suspend fun updateFans() {
        println("updateFans")
    }



    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}