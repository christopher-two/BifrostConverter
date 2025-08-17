package org.christophertwo.bifrost.ui.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.bifrost.ui.components.CalculatorKeypad
import org.christophertwo.bifrost.ui.components.NumeralView
import org.christophertwo.bifrost.utils.enums.NumeralSystem
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CalculatorRoot(
    viewModel: CalculatorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    CalculatorScreen(
        viewModel = viewModel,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    state: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumeralView(
            value = state.displayValue,
            system = state.currentSystem,
            modifier = Modifier.weight(0.5f)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumeralSystem.entries.forEach { system ->
                val isSelected = state.currentSystem == system
                Button(
                    onClick = { viewModel.onEvent(CalculatorEvent.ChangeSystem(system)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) colorScheme.primary else colorScheme.onPrimary,
                        contentColor = if (isSelected) colorScheme.onPrimary else colorScheme.primary
                    )
                ) {
                    Text(system.displayName)
                }
            }
        }

        // Teclado numérico
        CalculatorKeypad(
            currentSystem = state.currentSystem,
            onEvent = onEvent,
            modifier = Modifier.weight(1f)
        )
    }
}
