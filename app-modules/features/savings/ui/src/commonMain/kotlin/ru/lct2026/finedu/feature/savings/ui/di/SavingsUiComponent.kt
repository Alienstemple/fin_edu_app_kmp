package ru.lct2026.finedu.feature.savings.ui.di

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.savings.ui.SavingsNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface SavingsUiComponent {

    @Provides
    @IntoSet
    fun provideSavingsNavigation(
        gameRepository: GameRepository,
        contentRepository: ContentRepository
    ): FeatureNavigationContribution = SavingsNavigationContribution(gameRepository, contentRepository)
}
