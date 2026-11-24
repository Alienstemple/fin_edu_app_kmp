package ru.lct2026.finedu.feature.budget.ui.di

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.budget.ui.BudgetNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface BudgetUiComponent {

    @Provides
    @IntoSet
    fun provideBudgetNavigation(
        gameRepository: GameRepository,
        contentRepository: ContentRepository
    ): FeatureNavigationContribution = BudgetNavigationContribution(gameRepository, contentRepository)
}
