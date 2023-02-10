package proto

import io.grpc.ManagedChannel
import proto.generated.api.Api
import java.io.Closeable
import java.util.concurrent.TimeUnit

class Api {
}

/*

class HelloWorldClient(
    private val channel: ManagedChannel
) : Closeable {
    private val stub: GreeterCoroutineStub  = GreeterCoroutineStub(channel)

    suspend fun greet(name: String) {

    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

 */