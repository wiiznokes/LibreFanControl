package proto

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import proto.generated.pCrossApi.PCrossApiGrpcKt
import proto.generated.pCrossApi.POk
import java.io.Closeable
import java.util.concurrent.TimeUnit

class CrossApi(
    private val channel: ManagedChannel,
) : Closeable {
    private val stub: PCrossApiGrpcKt.PCrossApiCoroutineStub = PCrossApiGrpcKt.PCrossApiCoroutineStub(channel)


    suspend fun open(): POk {
        return stub.pOpen(Empty.getDefaultInstance())
    }


    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}