import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
    //mavenLocal()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    sourceSets {
        val jvmMain by getting {
            resources.srcDirs("resources")

            dependencies {
                implementation(compose.desktop.currentOs)

                implementation("org.jetbrains.compose.material3:material3-desktop:${extra["compose.version"] as String}")
                implementation("org.json:json:20220924")
                implementation("com.google.protobuf:protobuf-java:3.21.12")
                implementation("com.github.wiiznokes:setting-sliding-windows:2.0.1")
                //implementation("com.example:setting-sliding-windows-jvm:2.0.1")
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
            packageVersion = "1.0.0"
            copyright = "© 2023 Wiiznokes. All rights reserved."
            vendor = "Wiiznokes"
            licenseFile.set(project.file("./../LICENSE"))

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
