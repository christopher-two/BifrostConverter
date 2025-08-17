package org.christophertwo.bifrost.ui.screens.calculator

import org.christophertwo.bifrost.utils.enums.NumeralSystem
import org.christophertwo.bifrost.utils.enums.Operation
import java.math.BigInteger

data class CalculatorState(
    val currentValue: BigInteger = BigInteger.ZERO,
    val operand1: BigInteger? = null,
    val operation: Operation? = null,
    val shouldClearDisplay: Boolean = false, // Flag para reiniciar la pantalla
    val currentSystem: NumeralSystem = NumeralSystem.DECIMAL
) {
    // Propiedad computada para obtener la representación del valor en el sistema actual.
    val displayValue: String
        get() = currentValue.toString(currentSystem.radix).uppercase()
}
