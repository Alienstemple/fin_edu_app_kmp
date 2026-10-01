package ru.lct2026.finedu.feature.learn.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute

internal class LearnNavigationContribution(
    private val gameRepository: GameRepository,
    private val contentRepository: ContentRepository
) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Glossary> {
            GlossaryRoute(
                viewModel = viewModel { GlossaryViewModel(contentRepository) },
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable<FinEduRoute.Shorts> {
            ShortsRoute(
                onBack = { navController.popBackStack() },
                viewModel = viewModel { ShortsViewModel(gameRepository, contentRepository) }
            )
        }
    }
}
