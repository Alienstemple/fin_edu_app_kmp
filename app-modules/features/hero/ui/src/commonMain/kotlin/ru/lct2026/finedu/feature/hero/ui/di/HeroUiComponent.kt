package ru.lct2026.finedu.feature.hero.ui.di

import kotlin.time.Clock
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.hero.ui.HeroNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface HeroUiComponent {

    @Provides
    @IntoSet
    fun provideHeroNavigation(gameRepository: GameRepository, clock: Clock): FeatureNavigationContribution =
        HeroNavigationContribution(gameRepository, clock)
}
