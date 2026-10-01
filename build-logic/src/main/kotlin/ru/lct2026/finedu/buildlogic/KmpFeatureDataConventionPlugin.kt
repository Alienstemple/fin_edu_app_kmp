package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Модуль `data` фичи в KMP: реализации репозиториев из `domain`, хранение, контент из `composeResources`,
 * kotlin-inject привязки. Без Compose UI. Видит `domain` своей фичи (подключается явно в build.gradle.kts).
 */
class KmpFeatureDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("convention.kmp-library")
        pluginManager.apply("convention.kotlin-inject")

        sourceSetDependencies("commonMain") {
            implementation(project(":app-modules:product-core:domain"))
            implementation(libs.lib("kotlinx-coroutines-core"))
        }
    }
}
