package ru.lct2026.finedu.feature.period.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute

internal class PeriodNavigationContribution(private val gameRepository: GameRepository) :
    FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.PeriodSummary> {
            PeriodSummaryRoute(
                viewModel = viewModel { PeriodSummaryViewModel(gameRepository, createSavedStateHandle()) },
                onNextWeek = {
                    navController.navigate(FinEduRoute.Home) {
                        popUpTo(FinEduRoute.Home) { inclusive = true }
                    }
                },
                onHelpClick = { navController.navigate(FinEduRoute.Glossary) },
                onParentClick = { navController.navigate(FinEduRoute.Parent) }
            )
        }
    }
}
