plugins {
    `kotlin-dsl`
}

group = "ru.lct2026.finedu.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    compileOnly(libs.compose.multiplatform.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("rootProject") {
            id = "convention.root-project"
            implementationClass = "ru.lct2026.finedu.buildlogic.RootProjectConventionPlugin"
        }
        register("androidApplication") {
            id = "convention.android-application"
            implementationClass = "ru.lct2026.finedu.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "convention.android-library-compose"
            implementationClass = "ru.lct2026.finedu.buildlogic.AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureApi") {
            id = "convention.android-feature-api"
            implementationClass = "ru.lct2026.finedu.buildlogic.AndroidFeatureApiConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "convention.android-feature-ui"
            implementationClass = "ru.lct2026.finedu.buildlogic.AndroidFeatureUiConventionPlugin"
        }
        register("androidFeatureData") {
            id = "convention.android-feature-data"
            implementationClass = "ru.lct2026.finedu.buildlogic.AndroidFeatureDataConventionPlugin"
        }
        register("kotlinFeatureDomain") {
            id = "convention.kotlin-feature-domain"
            implementationClass = "ru.lct2026.finedu.buildlogic.KotlinFeatureDomainConventionPlugin"
        }
        register("kotlinJvm") {
            id = "convention.kotlin-jvm"
            implementationClass = "ru.lct2026.finedu.buildlogic.KotlinJvmConventionPlugin"
        }
        register("kmpLibrary") {
            id = "convention.kmp-library"
            implementationClass = "ru.lct2026.finedu.buildlogic.KmpLibraryConventionPlugin"
        }
        register("kmpLibraryCompose") {
            id = "convention.kmp-library-compose"
            implementationClass = "ru.lct2026.finedu.buildlogic.KmpLibraryComposeConventionPlugin"
        }
        register("kmpFeatureDomain") {
            id = "convention.kmp-feature-domain"
            implementationClass = "ru.lct2026.finedu.buildlogic.KmpFeatureDomainConventionPlugin"
        }
        register("kmpFeatureData") {
            id = "convention.kmp-feature-data"
            implementationClass = "ru.lct2026.finedu.buildlogic.KmpFeatureDataConventionPlugin"
        }
        register("kmpFeatureUi") {
            id = "convention.kmp-feature-ui"
            implementationClass = "ru.lct2026.finedu.buildlogic.KmpFeatureUiConventionPlugin"
        }
        register("kotlinInject") {
            id = "convention.kotlin-inject"
            implementationClass = "ru.lct2026.finedu.buildlogic.KotlinInjectConventionPlugin"
        }
    }
}
