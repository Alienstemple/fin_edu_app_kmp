plugins {
    alias(libs.plugins.convention.kmp.feature.data)
    alias(libs.plugins.convention.kmp.compose.resources)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
            implementation(libs.okio)
            implementation(libs.kotlinx.serialization.json)
            // HttpClient торчит в привязке ProductCoreDataComponent — корневой компонент должен видеть тип.
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        getByName("androidHostTest").dependencies {
            implementation(libs.test.ktor.client.mock)
        }
    }
}
