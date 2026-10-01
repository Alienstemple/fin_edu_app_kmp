package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

/**
 * Kotlin Multiplatform библиотека: targets Android и iOS (`iosArm64`, `iosSimulatorArm64`).
 * Unit-тесты — JUnit4 в `src/androidHostTest` (JVM, как раньше `src/test`).
 */
class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(libs.pluginId("kotlin-multiplatform"))
        pluginManager.apply(libs.pluginId("android-kotlin-multiplatform-library"))

        configureAndroidTarget()
        kotlinMultiplatform.apply {
            iosArm64()
            iosSimulatorArm64()
        }

        tasks.withType<Test>().configureEach {
            // Модули-заглушки пока без тестов — не валим тесты на пустом наборе.
            failOnNoDiscoveredTests.set(false)
            // Robolectric (Android 36+) на JDK 17+ лезет во внутренности FileDescriptor.
            jvmArgs(
                "--add-opens=java.base/java.io=ALL-UNNAMED",
                "--add-exports=java.base/jdk.internal.access=ALL-UNNAMED"
            )
        }
        // В KMP-модуле нет задачи `test`: без неё `./gradlew test` молча пропускает модуль.
        tasks.register("test") {
            group = "verification"
            description = "Runs JVM unit tests of the Android target (androidHostTest)."
            dependsOn("testAndroidHostTest")
        }
        sourceSetDependencies(ANDROID_HOST_TEST) {
            implementation(libs.lib("test-junit"))
            implementation(libs.lib("test-mockk"))
            implementation(libs.lib("test-kotlinx-coroutines"))
            implementation(libs.lib("test-turbine"))
        }
    }
}
