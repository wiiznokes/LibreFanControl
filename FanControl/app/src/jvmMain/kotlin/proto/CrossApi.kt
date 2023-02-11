package proto

import io.grpc.ManagedChannel
import proto.generated.api.PCrossApiGrpcKt
import java.io.Closeable
import java.util.concurrent.TimeUnit

class CrossApi(
    private val channel: ManagedChannel
) : Closeable{
    private val stub: PCrossApiGrpcKt.PCrossApiCoroutineStub  = GreeterCoroutineStub(channel)



    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}