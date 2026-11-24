package ru.lct2026.finedu.feature.quests.ui.di

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.feature.quests.ui.QuestsNavigationContribution
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/** Экраны фичи для корневого kotlin-inject компонента: вклад в общий граф навигации. */
interface QuestsUiComponent {

    @Provides
    @IntoSet
    fun provideQuestsNavigation(
        gameRepository: GameRepository,
        contentRepository: ContentRepository
    ): FeatureNavigationContribution = QuestsNavigationContribution(gameRepository, contentRepository)
}
