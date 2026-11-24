package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Модуль `ui` фичи в KMP: экраны на Compose Multiplatform, ViewModel, регистрация в навигации.
 * Видит `domain` своей фичи (подключается явно в build.gradle.kts), но никогда — `data`.
 */
class KmpFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("convention.kmp-library")
        pluginManager.apply("convention.kotlin-inject")
        configureComposeMultiplatform()

        sourceSetDependencies("commonMain") {
            implementation(project(":app-modules:product-core:ui"))
            implementation(project(":app-modules:product-core:navigation:api"))
            implementation(project(":app-modules:product-core:domain"))
            implementation(libs.lib("jetbrains-lifecycle-runtime-compose"))
            implementation(libs.lib("jetbrains-lifecycle-viewmodel-compose"))
            implementation(libs.lib("jetbrains-navigation-compose"))
        }
    }
}
