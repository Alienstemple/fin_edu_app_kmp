package ru.lct2026.finedu.feature.quests.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute
import ru.lct2026.finedu.productcore.navigation.api.navigateToTab
import ru.lct2026.finedu.productcore.ui.components.route

internal class QuestsNavigationContribution(
    private val gameRepository: GameRepository,
    private val contentRepository: ContentRepository
) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Quests> {
            QuestsRoute(
                viewModel = viewModel { QuestsViewModel(gameRepository, contentRepository) },
                onNavigate = { route -> navController.navigate(route) },
                onTabSelect = { tab -> navController.navigateToTab(tab.route) }
            )
        }
        composable<FinEduRoute.Quest> { entry ->
            val questId = entry.toRoute<FinEduRoute.Quest>().questId
            QuestRoute(
                onBack = { navController.popBackStack() },
                viewModel = viewModel { QuestViewModel(questId, gameRepository, contentRepository) }
            )
        }
    }
}
