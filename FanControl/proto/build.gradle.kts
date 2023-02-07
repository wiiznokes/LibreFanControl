import com.google.protobuf.gradle.*
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

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

tasks.register("copyJava") {
    doLast {
        val srcDir = file("build/generated/source/proto/main/java/")
        val destDir = file("../app/src/jvmMain/java/")
        copy {
            from(srcDir)
            into(destDir)
        }
    }
}

tasks.register("copyKotlin") {
    doLast {
        val srcDir = file("build/generated/source/proto/main/kotlin/")
        val destDir = file("../app/src/jvmMain/kotlin/")
        copy {
            from(srcDir)
            into(destDir)
        }
    }
}

tasks.register("copyCsharp") {
    doLast {
        val srcDir = file("build/generated/source/proto/main/csharp/")
        val destDir = file("../../Library/Windows/HardwareLib/HardwareDaemon/Proto/Generated")
        copy {
            from(srcDir)
            into(destDir)
        }
    }
}

tasks.register("cleanJava") {
    doLast {
        val destDir = file("../app/src/jvmMain/java/proto/generated")
        delete(destDir)
    }
}
tasks.register("cleanKotlin") {
    doLast {
        val destDir = file("../app/src/jvmMain/kotlin/proto/generated")
        delete(destDir)
    }
}
tasks.register("cleanCsharp") {
    doLast {
        val destDir = file("../../Library/Windows/HardwareLib/HardwareDaemon/Proto/Generated")
        delete(destDir)
    }
}