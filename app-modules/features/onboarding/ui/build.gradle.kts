plugins {
    alias(libs.plugins.convention.kmp.feature.ui)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.appModules.features.onboarding.domain)
        }
    }
}
