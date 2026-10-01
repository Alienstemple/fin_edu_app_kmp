package ru.lct2026.finedu.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * kotlin-inject (KSP). В KMP-модуле процессор подключается для каждого target'а отдельно — не через
 * `kspCommonMainMetadata`, иначе `@Component` генерируется дважды. В Android-модуле — обычный `ksp`.
 */
class KotlinInjectConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(libs.pluginId("ksp"))
        val runtime = libs.lib("kotlin-inject-runtime")
        val compiler = libs.lib("kotlin-inject-compiler")

        pluginManager.withPlugin(libs.pluginId("kotlin-multiplatform")) {
            sourceSetDependencies("commonMain") { implementation(runtime) }
            KMP_KSP_CONFIGURATIONS.forEach { dependencies.add(it, compiler) }
        }
        pluginManager.withPlugin(libs.pluginId("android-library")) {
            dependencies.add("implementation", runtime)
            dependencies.add("ksp", compiler)
        }
        pluginManager.withPlugin(libs.pluginId("android-application")) {
            dependencies.add("implementation", runtime)
            dependencies.add("ksp", compiler)
        }
    }

    private companion object {
        val KMP_KSP_CONFIGURATIONS = listOf("kspAndroid", "kspIosArm64", "kspIosSimulatorArm64")
    }
}
