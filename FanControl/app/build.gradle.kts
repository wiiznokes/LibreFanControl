import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0.0"

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
                // TODO: add specific dependencies for windows and linux, in order to remove implementation(compose.desktop.currentOs)
                //  and make cross compilation

                // ui
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.compose.material3:material3-desktop:${project.property("compose.version")}")

                // json, use for string (will be replaced in the future)
                implementation("org.json:json:20220924")

                // protobuf
                implementation("com.google.protobuf:protobuf-kotlin:${project.property("protobuf.version")}")

                // grpc
                implementation("io.grpc:grpc-stub:${project.property("grpc.version")}")
                implementation("io.grpc:grpc-protobuf:${project.property("grpc.version")}")
                implementation("io.grpc:grpc-kotlin-stub:${project.property("grpc.kotlin.version")}")
                implementation("io.grpc:grpc-okhttp:${project.property("grpc.version")}")


                implementation("com.github.wiiznokes:setting-sliding-windows:${project.property("settings.version")}")
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

            buildTypes.release {
                proguard {
                    // because it causes problem with com.squareup.okhttp package
                    // https://github.com/square/okhttp/issues/3355
                    isEnabled.set(false)
                }
            }

            packageName = "FanControl"
            packageVersion = version.toString()
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