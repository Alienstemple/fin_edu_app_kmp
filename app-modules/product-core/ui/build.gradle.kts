plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Общие компоненты рисуют доменные сущности: мешочки, показатели, питомца, обратную связь.
            api(projects.appModules.productCore.domain)
            implementation(projects.appModules.productCore.navigation.api)
            // ViewModel — суперкласс StatelessViewModel, торчит в публичном API модуля
            api(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(libs.compottie)
            implementation(libs.jetbrains.navigationevent.compose)
        }
    }
}
