package org.christophertwo.bifrost.ui.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.christophertwo.bifrost.ui.components.backgroundAnimated
import org.christophertwo.bifrost.utils.routes.RoutesApp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartRoot(
    viewModel: StartViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StartScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun StartScreen(
    state: StartState,
    navController: NavController,
    onAction: (StartAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundAnimated(
                    colorPrimary = colorScheme.background,
                    colorSecondary = colorScheme.primaryContainer
                )
            )
            .clickable {
                navController.navigate(RoutesApp.Calculator.route)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Un producto de Odin.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Hola, Soy Bifröst",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "Tu interprete entre varios mundos.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Click para comenzar",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.weight(0.1f))
    }
}