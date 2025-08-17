package org.christophertwo.bifrost.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.christophertwo.bifrost.ui.screens.calculator.CalculatorRoot
import org.christophertwo.bifrost.ui.screens.start.StartRoot
import org.christophertwo.bifrost.utils.routes.RoutesApp

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RoutesApp.Calculator.route
    ) {
        composable(
            route = RoutesApp.Calculator.route
        ) {
            CalculatorRoot()
        }
        composable(
            route = RoutesApp.Start.route
        ) {
            StartRoot(
                navController = navController
            )
        }
    }
}