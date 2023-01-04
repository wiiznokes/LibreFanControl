import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
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

                implementation("org.jetbrains.compose.material3:material3-desktop:1.2.1")
                implementation("org.json:json:20220924")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("junit:junit:4.13.1")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        jvmArgs += listOf("-Djava.library.path=./lib")
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FanControl"
            packageVersion = "1.0.0"


            windows {
                iconFile.set(project.file("./src/jvmMain/resources/drawable/app_icon.ico"))
            }
        }
    }
}
