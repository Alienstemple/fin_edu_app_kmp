package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Ресурсы `composeResources` в KMP-модуле без Compose UI (например, JSON-контент в `product-core:data`).
 * Применяется поверх `convention.kmp-library` или плагина, который его подключает.
 */
class KmpComposeResourcesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("convention.kmp-library")
        configureComposeResources()
    }
}
