package org.christophertwo.bifrost.ui.screens.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.christophertwo.bifrost.utils.enums.NumeralSystem
import org.christophertwo.bifrost.utils.enums.Operation
import java.math.BigInteger

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.ChangeSystem -> handleChangeSystem(event.system)
            is CalculatorEvent.DigitInput -> handleDigitInput(event.digit)
            is CalculatorEvent.OperationInput -> handleOperationInput(event.operation)
            CalculatorEvent.Equals -> handleEquals()
            CalculatorEvent.Backspace -> handleBackspace()
            CalculatorEvent.Clear -> handleClear()
        }
    }

    private fun handleChangeSystem(newSystem: NumeralSystem) {
        _state.update { it.copy(currentSystem = newSystem) }
    }

    private fun handleDigitInput(digit: Char) {
        _state.update { currentState ->
            // Si se debe limpiar la pantalla (después de una operación o igual), se empieza un nuevo número.
            val currentStringValue = if (currentState.shouldClearDisplay) {
                ""
            } else {
                currentState.currentValue.toString(currentState.currentSystem.radix)
            }

            // Añade el nuevo dígito. Si el valor actual es "0", lo reemplaza.
            val newStringValue = if (currentStringValue == "0") digit.toString() else currentStringValue + digit
            try {
                // Intenta convertir el nuevo string a BigInteger.
                val newValue = BigInteger(newStringValue, currentState.currentSystem.radix)
                currentState.copy(currentValue = newValue, shouldClearDisplay = false)
            } catch (e: NumberFormatException) {
                // Si hay un error (ej. valor demasiado grande), no hace nada.
                currentState
            }
        }
    }

    private fun handleOperationInput(operation: Operation) {
        _state.update { currentState ->
            // Si hay una operación pendiente y se ha introducido un segundo número, calcula el resultado parcial.
            if (currentState.operand1 != null && currentState.operation != null && !currentState.shouldClearDisplay) {
                val operand2 = currentState.currentValue
                val result = performCalculation(currentState.operand1, operand2, currentState.operation)
                currentState.copy(
                    currentValue = result,
                    operand1 = result,
                    operation = operation,
                    shouldClearDisplay = true
                )
            } else {
                // Guarda el valor actual como primer operando y espera el segundo.
                currentState.copy(
                    operand1 = currentState.currentValue,
                    operation = operation,
                    shouldClearDisplay = true
                )
            }
        }
    }

    private fun handleEquals() {
        _state.update { currentState ->
            val operand1 = currentState.operand1
            val operation = currentState.operation
            val operand2 = currentState.currentValue

            // Solo calcula si hay una operación pendiente y se ha introducido un segundo operando.
            if (operand1 != null && operation != null && !currentState.shouldClearDisplay) {
                val result = performCalculation(operand1, operand2, operation)
                currentState.copy(
                    currentValue = result,
                    operand1 = null,
                    operation = null,
                    shouldClearDisplay = true
                )
            } else {
                currentState // No hay nada que hacer.
            }
        }
    }

    private fun performCalculation(operand1: BigInteger, operand2: BigInteger, operation: Operation): BigInteger {
        return when (operation) {
            Operation.ADD -> operand1.add(operand2)
            Operation.SUBTRACT -> operand1.subtract(operand2)
            Operation.MULTIPLY -> operand1.multiply(operand2)
            Operation.DIVIDE -> if (operand2 != BigInteger.ZERO) operand1.divide(operand2) else BigInteger.ZERO // Evita división por cero
            Operation.MODULO -> if (operand2 != BigInteger.ZERO) operand1.remainder(operand2) else BigInteger.ZERO // Evita división por cero
        }
    }

    private fun handleBackspace() {
        _state.update { currentState ->
            // No permite borrar si el display está mostrando un resultado.
            if (currentState.shouldClearDisplay) return@update currentState

            val currentStringValue = currentState.displayValue
            if (currentStringValue.isNotEmpty() && currentStringValue != "0") {
                val newStringValue = currentStringValue.dropLast(1)
                val newValue = if (newStringValue.isEmpty()) {
                    BigInteger.ZERO
                } else {
                    BigInteger(newStringValue, currentState.currentSystem.radix)
                }
                currentState.copy(currentValue = newValue)
            } else {
                currentState
            }
        }
    }

    private fun handleClear() {
        // Restablece todo el estado.
        _state.update {
            it.copy(
                currentValue = BigInteger.ZERO,
                operand1 = null,
                operation = null,
                shouldClearDisplay = false
            )
        }
    }
}
