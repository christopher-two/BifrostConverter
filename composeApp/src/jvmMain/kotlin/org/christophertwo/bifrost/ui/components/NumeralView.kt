package org.christophertwo.bifrost.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.christophertwo.bifrost.ui.screens.calculator.CalculatorEvent
import org.christophertwo.bifrost.utils.enums.NumeralSystem
import org.christophertwo.bifrost.utils.enums.Operation

@Composable
fun NumeralView(
    value: String,
    system: NumeralSystem,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colorScheme.surfaceContainer,
        contentColor = colorScheme.onSurface,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = system.displayName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 52.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun CalculatorKeypad(
    currentSystem: NumeralSystem,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSystemDigits = currentSystem.validDigits.toList()

    // Define la disposición del teclado como una lista de filas.
    val keypadLayout = listOf(
        listOf("A", "B", "C", Operation.DIVIDE.symbol),
        listOf("7", "8", "9", Operation.MULTIPLY.symbol),
        listOf("4", "5", "6", Operation.SUBTRACT.symbol),
        listOf("1", "2", "3", Operation.ADD.symbol),
        listOf("D", "E", "F", "="),
        listOf("Clear", "0", "⌫", Operation.MODULO.symbol)
    )

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        keypadLayout.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                row.forEach { key ->
                    val itemModifier = Modifier.weight(1f)
                    when (key) {
                        Operation.ADD.symbol -> OperationButton(key, Operation.ADD, onEvent, itemModifier)
                        Operation.SUBTRACT.symbol -> OperationButton(key, Operation.SUBTRACT, onEvent, itemModifier)
                        Operation.MULTIPLY.symbol -> OperationButton(key, Operation.MULTIPLY, onEvent, itemModifier)
                        Operation.DIVIDE.symbol -> OperationButton(key, Operation.DIVIDE, onEvent, itemModifier)
                        Operation.MODULO.symbol -> OperationButton(key, Operation.MODULO, onEvent, itemModifier)
                        "=" -> EqualsButton(key, onEvent, itemModifier)
                        "Clear" -> SpecialButton("C", { onEvent(CalculatorEvent.Clear) }, itemModifier)
                        "⌫" -> SpecialButton(key, { onEvent(CalculatorEvent.Backspace) }, itemModifier)
                        else -> {
                            val digitChar = key.first()
                            val isEnabled = currentSystemDigits.contains(digitChar)
                            val colorAnimatedButton = animateColorAsState(
                                targetValue = if (isEnabled) colorScheme.primaryContainer else colorScheme.surfaceContainer,
                                label = "Color Animated Buttons",
                                animationSpec = tween(durationMillis = 600)
                            )
                            CalculatorButton(
                                text = key,
                                onClick = { onEvent(CalculatorEvent.DigitInput(digitChar)) },
                                enabled = isEnabled,
                                color = colorAnimatedButton.value,
                                modifier = itemModifier
                            )
                        }
                    }
                }
            }
        }
    }
}

// Componentes auxiliares para cada tipo de botón para mejorar la claridad.
@Composable
private fun OperationButton(
    text: String,
    operation: Operation,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier
) {
    CalculatorButton(
        text = text,
        onClick = { onEvent(CalculatorEvent.OperationInput(operation)) },
        color = colorScheme.secondaryContainer,
        contentColor = colorScheme.onSecondaryContainer,
        modifier = modifier
    )
}

@Composable
private fun EqualsButton(text: String, onEvent: (CalculatorEvent) -> Unit, modifier: Modifier) {
    CalculatorButton(
        text = text,
        onClick = { onEvent(CalculatorEvent.Equals) },
        color = colorScheme.tertiary,
        contentColor = colorScheme.onTertiary,
        modifier = modifier
    )
}

@Composable
private fun SpecialButton(text: String, onClick: () -> Unit, modifier: Modifier) {
    CalculatorButton(
        text = text,
        onClick = onClick,
        color = colorScheme.tertiaryContainer,
        contentColor = colorScheme.onTertiaryContainer,
        modifier = modifier
    )
}

// Componente de botón genérico para el teclado.
@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    color: Color = colorScheme.primaryContainer,
    contentColor: Color = colorScheme.onPrimaryContainer,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = contentColor,
            disabledContainerColor = colorScheme.surfaceContainer.copy(alpha = 0.5f),
            disabledContentColor = colorScheme.onSurface.copy(alpha = 0.5f)
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}
