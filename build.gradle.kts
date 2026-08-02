plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.kotlin.multiplatform.library") version "9.2.1"
}

group = "io.github.rasulmingazov"
version = "0.1.0"

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    android {
        namespace = "io.github.rasulmingazov.stubcall"
        compileSdk = 36
        minSdk = 24
        withHostTest {}

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
