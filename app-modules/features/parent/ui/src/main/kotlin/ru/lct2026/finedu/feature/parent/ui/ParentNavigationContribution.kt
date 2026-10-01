package ru.lct2026.finedu.feature.parent.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute

internal class ParentNavigationContribution(
    private val gameRepository: GameRepository,
    private val contentRepository: ContentRepository
) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Parent> {
            ParentRoute(
                viewModel = viewModel { ParentViewModel(gameRepository, contentRepository) },
                onBack = { navController.popBackStack() },
                onOpenHome = {
                    navController.navigate(FinEduRoute.Home) {
                        popUpTo(navController.graph.id) {
                            inclusive =
                                true
                        }
                    }
                },
                onOpenOnboarding = {
                    navController.navigate(FinEduRoute.Onboarding) {
                        popUpTo(navController.graph.id) {
                            inclusive =
                                true
                        }
                    }
                }
            )
        }
    }
}
