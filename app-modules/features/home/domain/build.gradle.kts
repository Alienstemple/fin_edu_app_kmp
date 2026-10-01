plugins {
    alias(libs.plugins.convention.kmp.feature.domain)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Flow в публичных сигнатурах репозиториев
            api(libs.kotlinx.coroutines.core)
        }
    }
}
