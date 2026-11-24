package ru.lct2026.finedu.feature.budget.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute
import ru.lct2026.finedu.productcore.navigation.api.navigateToTab
import ru.lct2026.finedu.productcore.ui.components.FinTab
import ru.lct2026.finedu.productcore.ui.components.route

internal class BudgetNavigationContribution(
    private val gameRepository: GameRepository,
    private val contentRepository: ContentRepository
) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Budget> {
            BudgetRoute(
                viewModel = viewModel { BudgetViewModel(gameRepository, contentRepository) },
                onBack = { navController.popBackStack() },
                onHelp = { navController.navigate(FinEduRoute.Glossary) },
                onParent = { navController.navigate(FinEduRoute.Parent) },
                onShop = { navController.navigateToTab(FinTab.SHOP.route) },
                onHome = { navController.navigateToTab(FinTab.HOME.route) }
            )
        }
    }
}
