package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Модуль `domain` фичи в KMP: модели, интерфейсы репозиториев, use case'ы в `commonMain`.
 * Чистый Kotlin без Android и без DI-аннотаций.
 */
class KmpFeatureDomainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("convention.kmp-library")

        sourceSetDependencies("commonMain") {
            api(project(":app-modules:product-core:domain"))
            implementation(libs.lib("kotlinx-coroutines-core"))
        }
    }
}
