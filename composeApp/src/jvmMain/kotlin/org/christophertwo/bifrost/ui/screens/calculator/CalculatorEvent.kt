package org.christophertwo.bifrost.ui.screens.calculator

import org.christophertwo.bifrost.utils.enums.NumeralSystem
import org.christophertwo.bifrost.utils.enums.Operation

sealed interface CalculatorEvent {
    data class ChangeSystem(val system: NumeralSystem) : CalculatorEvent
    data class DigitInput(val digit: Char) : CalculatorEvent
    object Backspace : CalculatorEvent
    object Clear : CalculatorEvent

    // Nuevos eventos para las operaciones
    data class OperationInput(val operation: Operation) : CalculatorEvent
    object Equals : CalculatorEvent
}
