package ru.lct2026.finedu.feature.hero.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lct2026.finedu.productcore.domain.repository.GameRepository
import ru.lct2026.finedu.productcore.navigation.api.FeatureNavigationContribution
import ru.lct2026.finedu.productcore.navigation.api.FinEduRoute

internal class HeroNavigationContribution(private val gameRepository: GameRepository) : FeatureNavigationContribution {

    override fun NavGraphBuilder.register(navController: NavController) {
        composable<FinEduRoute.Hero> {
            HeroRoute(
                viewModel = viewModel { HeroViewModel(gameRepository) },
                onBack = { navController.popBackStack() },
                onOpenHome = {
                    // Стек очищается целиком: назад в онбординг и создание питомца не вернуться.
                    navController.navigate(FinEduRoute.Home) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}
