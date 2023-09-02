import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "${project.property("app.version")}"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}


kotlin {

    jvm {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn"
                )
            }
        }
        withJava()
    }
    sourceSets {

        val jvmMain by getting {
            resources.srcDirs("resources")

            dependencies {
                // ui
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)

                // json, use for string (will be replaced in the future)
                implementation("org.json:json:20230618")

                // protobuf
                implementation("com.google.protobuf:protobuf-kotlin:${project.property("protobuf.version")}")

                // grpc
                implementation("io.grpc:grpc-stub:${project.property("grpc.version")}")
                implementation("io.grpc:grpc-protobuf:${project.property("grpc.version")}")
                implementation("io.grpc:grpc-kotlin-stub:${project.property("grpc.kotlin.version")}")
                implementation("io.grpc:grpc-okhttp:${project.property("grpc.version")}")
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
        args.add("${project.property("app.version")}")

        mainClass = "MainKt"
        nativeDistributions {

            targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.AppImage, TargetFormat.Rpm, TargetFormat.Deb)

            buildTypes.release {
                proguard {
                    // because it causes problem with com.squareup.okhttp package
                    // https://github.com/square/okhttp/issues/3355
                    isEnabled.set(false)
                }
            }

            packageName = "LibreFanControl"
            packageVersion = version.toString()
            copyright = "© 2023 Wiiznokes. All rights reserved."
            vendor = "Wiiznokes"
            licenseFile.set(project.file("./../../LICENSE"))

            appResourcesRootDir.set(project.layout.projectDirectory.dir("include"))

            windows {
                iconFile.set(project.file("drawable/app_icon.ico"))
                dirChooser = true
                menuGroup = "start-menu-group"
                shortcut = true
                msiPackageVersion = "${project.property("app.version")}"
                exePackageVersion = "${project.property("app.version")}"
            }

            linux {
                iconFile.set(project.file("drawable/app_icon.png"))
                menuGroup = "Utility"
                shortcut = true
                appCategory = "Utility"
                packageVersion = "${project.property("app.version")}"
                debPackageVersion = "${project.property("app.version")}"
                rpmPackageVersion = "${project.property("app.version")}"
            }
        }
    }
}