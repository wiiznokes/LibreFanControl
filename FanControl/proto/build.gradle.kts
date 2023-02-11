import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto

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
                id("grpc") {}
                id("grpckt") {}
            }
            it.builtins {
                id("kotlin") {}
                //id("csharp") {}
            }
        }
    }
}


tasks.register("generateAllProto", DefaultTask::class.java) {
    dependsOn("cleanCopiedFiles")
    dependsOn("generateProto")
    dependsOn("copyGeneratedFiles")

    mustRunAfter("cleanCopiedFiles")
    mustRunAfter("generateProto")
}

/**
 * copy of generated files
 */

data class CopyInfo(
    val srcDirs: List<File>,
    val destDir: File,
    val removeDir: File,
)

val infoList = listOf(
    CopyInfo(
        srcDirs = listOf(file("build/generated/source/proto/main/java"), file("build/generated/source/proto/main/grpc")),
        destDir = file("../app/src/jvmMain/java"),
        removeDir = file("../app/src/jvmMain/java/proto/generated")
    ),
    CopyInfo(
        srcDirs = listOf(file("build/generated/source/proto/main/kotlin"), file("build/generated/source/proto/main/grpckt")),
        destDir = file("../app/src/jvmMain/kotlin"),
        removeDir = file("../app/src/jvmMain/kotlin/proto/generated")
    ),
    CopyInfo(
        srcDirs = listOf(file("build/generated/source/proto/main/csharp")),
        destDir = file("../../Library/Windows/HardwareLib/HardwareDaemon/Proto/Generated"),
        removeDir = file("../../Library/Windows/HardwareLib/HardwareDaemon/Proto/Generated")
    )
)

tasks.register("copyGeneratedFiles") {
    doLast {
        infoList.forEach {
            it.srcDirs.forEach { srcDir ->
                copy {
                    from(srcDir)
                    into(it.destDir)
                }
            }
        }
    }
}

tasks.register("cleanCopiedFiles") {
    doLast {
        infoList.forEach {
            delete(it.removeDir)
        }
    }
}