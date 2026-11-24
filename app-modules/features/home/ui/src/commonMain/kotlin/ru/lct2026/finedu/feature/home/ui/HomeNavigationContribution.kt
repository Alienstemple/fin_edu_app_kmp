package ru.lct2026.finedu.feature.home.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlin.time.Clock
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute
import ru.lct2026.finedu.productcore.navigation.api.navigateToTab
import ru.lct2026.finedu.productcore.ui.components.route

internal class HomeNavigationContribution(
    private val gameRepository: GameRepository,
    private val contentRepository: ContentRepository,
    private val clock: Clock
) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Home> {
            HomeRoute(
                viewModel = viewModel { HomeViewModel(gameRepository, contentRepository, clock) },
                onNavigate = { route -> navController.navigate(route) },
                onSelectTab = { tab -> navController.navigateToTab(tab.route) }
            )
        }
    }
}
