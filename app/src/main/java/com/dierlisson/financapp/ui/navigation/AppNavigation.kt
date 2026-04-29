package com.dierlisson.financapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dierlisson.financapp.data.repository.TransacaoRepository
import com.dierlisson.financapp.ui.screens.dashboard.DashboardScreen
import com.dierlisson.financapp.ui.screens.dashboard.DashboardViewModel
import com.dierlisson.financapp.ui.screens.dashboard.DashboardViewModelFactory
import com.dierlisson.financapp.ui.screens.formulario.FormularioScreen
import com.dierlisson.financapp.ui.screens.formulario.FormularioViewModel
import com.dierlisson.financapp.ui.screens.formulario.FormularioViewModelFactory
import com.dierlisson.financapp.ui.screens.relatorios.RelatoriosScreen
import com.dierlisson.financapp.ui.screens.relatorios.RelatoriosViewModel
import com.dierlisson.financapp.ui.screens.relatorios.RelatoriosViewModelFactory
import com.dierlisson.financapp.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation(repository: TransacaoRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(onNavigateToDashboard = {
                navController.navigate("dashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory(repository))
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = { id ->
                    val rota = if (id != null) "formulario?transacaoId=$id" else "formulario"
                    navController.navigate(rota)
                },
                onNavigateToReports = { navController.navigate("relatorios") }
            )
        }

        composable(
            route = "formulario?transacaoId={transacaoId}",
            arguments = listOf(navArgument("transacaoId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val transacaoId = backStackEntry.arguments?.getInt("transacaoId") ?: -1
            val viewModel: FormularioViewModel = viewModel(factory = FormularioViewModelFactory(repository))

            LaunchedEffect(transacaoId) {
                if (transacaoId != -1) {
                    viewModel.carregarTransacao(transacaoId)
                }
            }

            FormularioScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("relatorios") {
            val viewModel: RelatoriosViewModel = viewModel(factory = RelatoriosViewModelFactory(repository))
            RelatoriosScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
