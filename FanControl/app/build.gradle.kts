import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "0.1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
    mavenLocal()
}

kotlin {
    jvm {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        withJava()
    }
    sourceSets {

        val jvmMain by getting {
            resources.srcDirs("resources")

            dependencies {
                // ui/kotlin
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.compose.material3:material3-desktop:${project.property("compose.version")}")
                implementation("com.github.wiiznokes:setting-sliding-windows:${project.property("settings.version")}")

                // json, use for string (will be replaced in the future)
                implementation("org.json:json:20220924")

                // protobuf
                implementation("com.google.protobuf:protobuf-java:${project.property("protobuf.version")}")
                implementation("com.google.protobuf:protobuf-kotlin:${project.property("protobuf.version")}")

                // grpc
                implementation("io.grpc:grpc-stub:1.52.1")
                implementation("io.grpc:grpc-protobuf:${project.property("grpc.version")}")
                implementation("io.grpc:grpc-kotlin-stub:${project.property("grpc.kotlin.version")}")
                if (JavaVersion.current().isJava9Compatible) {
                    // Workaround for @javax.annotation.Generated
                    // see: https://github.com/grpc/grpc-java/issues/3633
                    implementation("javax.annotation:javax.annotation-api:1.3.1")
                }






                // use to debug the lib locally
                //implementation("com.example:setting-sliding-windows-jvm:${project.property("settings.version")}")
            }
        }


        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {

            targetFormats(TargetFormat.Msi, TargetFormat.AppImage, TargetFormat.Rpm)

            packageName = "FanControl"
            packageVersion = "0.1.0"
            copyright = "© 2023 Wiiznokes. All rights reserved."
            vendor = "Wiiznokes"
            licenseFile.set(project.file("./../../LICENSE"))

            appResourcesRootDir.set(project.layout.projectDirectory.dir("include"))

            windows {
                iconFile.set(project.file("drawable/app_icon.ico"))
            }

            linux {
                iconFile.set(project.file("drawable/app_icon.png"))
            }
        }
    }
}