package ru.lct2026.finedu.buildlogic

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

/** Source set JVM unit-тестов Android target'а (замена `src/test`). */
internal const val ANDROID_HOST_TEST = "androidHostTest"

/**
 * Namespace KMP-модуля из его пути — так же, как раньше задавался вручную в `android { namespace }`:
 * `:app-modules:features:shop:ui` → `ru.lct2026.finedu.feature.shop.ui`,
 * `:app-modules:product-core:navigation:api` → `ru.lct2026.finedu.productcore.navigation.api`.
 */
internal val Project.kmpNamespace: String
    get() = "ru.lct2026.finedu." + path
        .removePrefix(":app-modules:")
        .replaceFirst("features:", "feature:")
        .replaceFirst("product-core:", "productcore:")
        .replace(':', '.')

internal val Project.kotlinMultiplatform: KotlinMultiplatformExtension
    get() = extensions.getByType<KotlinMultiplatformExtension>()

/** Зависимости source set'а; `androidHostTest` появляется только после `withHostTest`, поэтому — лениво. */
internal fun Project.sourceSetDependencies(name: String, block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatform.sourceSets.matching { it.name == name }.configureEach { dependencies(block) }
}

/**
 * Compose Multiplatform: компилятор Compose, общие артефакты и ресурсы `composeResources`.
 * Класс `Res` генерируется в пакете модуля — как раньше `R`.
 */
internal fun Project.configureComposeMultiplatform(publicResources: Boolean = false) {
    pluginManager.apply(libs.pluginId("kotlin-compose"))
    pluginManager.apply(libs.pluginId("compose-multiplatform"))

    (extensions.getByType<ComposeExtension>() as ExtensionAware).extensions.configure<ResourcesExtension> {
        packageOfResClass = kmpNamespace
        publicResClass = publicResources
    }

    sourceSetDependencies("commonMain") {
        implementation(libs.lib("cmp-runtime"))
        implementation(libs.lib("cmp-foundation"))
        implementation(libs.lib("cmp-ui"))
        implementation(libs.lib("cmp-material3"))
        implementation(libs.lib("cmp-components-resources"))
        implementation(libs.lib("cmp-ui-tooling-preview"))
    }
    sourceSetDependencies(ANDROID_HOST_TEST) {
        implementation(libs.lib("androidx-compose-ui-test-junit4"))
        // ui-test тянет старый espresso, несовместимый с Android 36 под Robolectric.
        implementation(libs.lib("test-espresso-core"))
        // ComponentActivity для createComposeRule() под Robolectric.
        implementation(libs.lib("androidx-compose-ui-test-manifest"))
        implementation(libs.lib("test-robolectric"))
    }
}

internal fun Project.configureAndroidTarget() {
    (kotlinMultiplatform as ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
        namespace = kmpNamespace
        compileSdk = libs.version("android-compileSdk").toInt()
        minSdk = libs.version("android-minSdk").toInt()
        withHostTest { isIncludeAndroidResources = true }
    }
    configureKotlinCompile()
}
