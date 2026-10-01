package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * KMP-библиотека с Compose Multiplatform для общих UI-модулей (`product-core:ui`).
 * Класс `Res` публичный: общие строки и картинки используют экраны фич.
 */
class KmpLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("convention.kmp-library")
        configureComposeMultiplatform(publicResources = true)
    }
}
