buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.property("kotlin.version")}")
        classpath("org.jetbrains.compose:compose-gradle-plugin:${project.property("compose.version")}")

        classpath("com.google.protobuf:protobuf-gradle-plugin:${project.property("protobuf.plugin.version")}")
    }
}