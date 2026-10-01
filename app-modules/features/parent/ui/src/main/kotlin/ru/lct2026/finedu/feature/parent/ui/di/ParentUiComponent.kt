package ru.lct2026.finedu.feature.parent.ui.di

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.parent.ui.ParentNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface ParentUiComponent {

    @Provides
    @IntoSet
    fun provideParentNavigation(
        gameRepository: GameRepository,
        contentRepository: ContentRepository
    ): FeatureNavigationContribution = ParentNavigationContribution(gameRepository, contentRepository)
}
