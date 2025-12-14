package com.example.calculaimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculaimc.presentation.screens.HistoryScreen
import com.example.calculaimc.presentation.screens.Home
import com.example.calculaimc.presentation.screens.ResultDetailScreen
import com.example.calculaimc.presentation.viewmodel.HomeViewModelFactory
import com.example.calculaimc.ui.theme.Blue
import com.example.calculaimc.ui.theme.CalculaIMCTheme
import com.example.calculaimc.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculaIMCTheme {
                // 1. Configuração do Banco de Dados (MVVM - Model)
                val app = application as ImcApplication
                val dao = app.database.calculationDao()

                // 2. Configuração da Factory (MVVM - ViewModel Injection)
                val factory = HomeViewModelFactory(dao)

                // 3. Configuração da Navegação
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {

                    // ROTA 1: HOME (Calculadora)
                    composable("home") {
                        // Scaffold somente para usar o FAB
                        Scaffold(
                            floatingActionButton = {
                                FloatingActionButton(
                                    onClick = { navController.navigate("history") },
                                    containerColor = Blue,
                                    contentColor = White
                                ) {
                                    Icon(Icons.Default.History, contentDescription = "Histórico")
                                }
                            }
                        ) { paddingValues ->
                            // O Box garante que o padding do Scaffold seja respeitado
                            Box(modifier = Modifier.padding(paddingValues)) {
                                Home(viewModel = viewModel(factory = factory))
                            }
                        }
                    }

                    // ROTA 2: HISTÓRICO
                    composable("history") {
                        HistoryScreen(
                            viewModel = viewModel(factory = factory), // Reusa o mesmo ViewModel/Factory
                            onBack = { navController.popBackStack() },
                            onItemClick = { id ->
                                // Navega para detalhes passando o ID
                                navController.navigate("detail/$id")
                            }
                        )
                    }

                    // ROTA 3: DETALHES
                    composable(
                        route = "detail/{resultId}",
                        arguments = listOf(navArgument("resultId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        // Recupera o ID passado na rota
                        val resultId = backStackEntry.arguments?.getInt("resultId") ?: 0

                        ResultDetailScreen(
                            viewModel = viewModel(factory = factory),
                            resultId = resultId,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}