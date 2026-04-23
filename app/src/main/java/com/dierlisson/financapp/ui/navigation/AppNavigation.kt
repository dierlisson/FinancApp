package com.dierlisson.financapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dierlisson.financapp.data.repository.TransacaoRepository
import com.dierlisson.financapp.ui.screens.dashboard.DashboardScreen
import com.dierlisson.financapp.ui.screens.dashboard.DashboardViewModel
import com.dierlisson.financapp.ui.screens.dashboard.DashboardViewModelFactory
import com.dierlisson.financapp.ui.screens.formulario.FormularioScreen
import com.dierlisson.financapp.ui.screens.formulario.FormularioViewModel
import com.dierlisson.financapp.ui.screens.formulario.FormularioViewModelFactory

@Composable
fun AppNavigation(repository: TransacaoRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {

        // Rota: Dashboard
        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(repository)
            )
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = { navController.navigate("formulario") }
            )
        }

        // Rota: Formulário de Transação
        composable("formulario") {
            val viewModel: FormularioViewModel = viewModel(
                factory = FormularioViewModelFactory(repository)
            )
            FormularioScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}