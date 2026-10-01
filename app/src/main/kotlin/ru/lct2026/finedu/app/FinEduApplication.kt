package ru.lct2026.finedu.app

import android.app.Application
import ru.lct2026.finedu.app.di.AppComponent
import ru.lct2026.finedu.app.di.InjectAppComponent

class FinEduApplication : Application() {

    /**
     * Граф зависимостей один на процесс: синглтоны (`@AppScope`) живут столько же, сколько приложение.
     * [InjectAppComponent] генерирует kotlin-inject (KSP) — без рефлексии и без `AppComponent::class`.
     */
    internal val component: AppComponent by lazy { InjectAppComponent(this) }
}
