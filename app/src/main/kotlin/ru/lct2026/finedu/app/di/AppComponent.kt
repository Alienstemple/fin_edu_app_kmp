package ru.lct2026.finedu.app.di

import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.app.sound.GameAudio
import ru.lct2026.finedu.feature.budget.ui.di.BudgetUiComponent
import ru.lct2026.finedu.feature.hero.ui.di.HeroUiComponent
import ru.lct2026.finedu.feature.home.ui.di.HomeUiComponent
import ru.lct2026.finedu.feature.learn.ui.di.LearnUiComponent
import ru.lct2026.finedu.feature.onboarding.ui.di.OnboardingUiComponent
import ru.lct2026.finedu.feature.parent.ui.di.ParentUiComponent
import ru.lct2026.finedu.feature.period.ui.di.PeriodUiComponent
import ru.lct2026.finedu.feature.quests.ui.di.QuestsUiComponent
import ru.lct2026.finedu.feature.savings.ui.di.SavingsUiComponent
import ru.lct2026.finedu.feature.shop.ui.di.ShopUiComponent
import ru.lct2026.finedu.productcore.data.di.AppScope
import ru.lct2026.finedu.productcore.data.di.ProductCoreDataComponent
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution

/**
 * Корневой граф kotlin-inject: собирает привязки `data` и экраны всех фич.
 * Фичи не видят друг друга и `data` — связывает их только этот компонент.
 */
@AppScope
@Component
internal abstract class AppComponent(@get:Provides val context: Context) :
    ProductCoreDataComponent,
    OnboardingUiComponent,
    HeroUiComponent,
    HomeUiComponent,
    BudgetUiComponent,
    ShopUiComponent,
    SavingsUiComponent,
    QuestsUiComponent,
    PeriodUiComponent,
    ParentUiComponent,
    LearnUiComponent {

    abstract val navigationContributions: Set<FeatureNavigationContribution>

    abstract val gameRepository: GameRepository

    abstract val gameAudio: GameAudio
}
