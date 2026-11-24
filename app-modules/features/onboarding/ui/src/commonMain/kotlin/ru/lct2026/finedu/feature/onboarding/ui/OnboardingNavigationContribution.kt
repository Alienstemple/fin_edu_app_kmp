package ru.lct2026.finedu.feature.onboarding.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute

internal class OnboardingNavigationContribution(private val gameRepository: GameRepository) :
    FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Onboarding> {
            OnboardingRoute(
                viewModel = viewModel { OnboardingViewModel(gameRepository) },
                onOpenHero = { navController.navigate(FinEduRoute.Hero) },
                onClose = { navController.popBackStack() }
            )
        }
    }
}
