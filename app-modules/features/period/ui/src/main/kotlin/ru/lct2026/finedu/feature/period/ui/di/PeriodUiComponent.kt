package ru.lct2026.finedu.feature.period.ui.di

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.period.ui.PeriodNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface PeriodUiComponent {

    @Provides
    @IntoSet
    fun providePeriodNavigation(gameRepository: GameRepository): FeatureNavigationContribution =
        PeriodNavigationContribution(gameRepository)
}
