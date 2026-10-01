plugins {
    alias(libs.plugins.convention.kmp.feature.data)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.appModules.features.quests.domain)
        }
    }
}
