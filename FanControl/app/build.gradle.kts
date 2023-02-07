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
    //mavenLocal()
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
                implementation(compose.desktop.currentOs)

                implementation("org.jetbrains.compose.material3:material3-desktop:${project.property("compose.version")}")
                implementation("com.github.wiiznokes:setting-sliding-windows:2.0.1")

                implementation("org.json:json:20220924")
                implementation("com.google.protobuf:protobuf-java:3.21.12")


                // use to debug the lib locally
                //implementation("com.example:setting-sliding-windows-jvm:2.0.1")

                implementation(project(":proto"))
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
            licenseFile.set(project.file("./../LICENSE"))

            appResourcesRootDir.set(project.layout.projectDirectory.dir("include"))

            windows {
                iconFile.set(project.file("app/drawable/app_icon.ico"))
            }

            linux {
                iconFile.set(project.file("app/drawable/app_icon.png"))
            }
        }
    }
}