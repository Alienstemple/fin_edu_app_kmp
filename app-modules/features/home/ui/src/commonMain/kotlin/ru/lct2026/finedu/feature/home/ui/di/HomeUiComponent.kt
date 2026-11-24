package ru.lct2026.finedu.feature.home.ui.di

import kotlin.time.Clock
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.home.ui.HomeNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface HomeUiComponent {

    @Provides
    @IntoSet
    fun provideHomeNavigation(
        gameRepository: GameRepository,
        contentRepository: ContentRepository,
        clock: Clock
    ): FeatureNavigationContribution = HomeNavigationContribution(gameRepository, contentRepository, clock)
}
