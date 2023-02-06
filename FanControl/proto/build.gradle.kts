import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("idea")
    id("com.google.protobuf")
}


repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-kotlin:${project.property("protobuf.version")}")
    implementation("io.grpc:grpc-kotlin-stub:${project.property("grpc.kotlin.version")}")
    implementation("io.grpc:grpc-protobuf:${project.property("grpc.version")}")
}

sourceSets {
    main {
        proto {
            srcDir("src")
        }
    }
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${project.property("protobuf.version")}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${project.property("grpc.version")}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${project.property("grpc.kotlin.version")}:jdk8@jar"
        }
    }

    generateProtoTasks {

        all().forEach {
            ofSourceSet("main")



            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
                id("csharp") {

                }
            }
        }
    }
}