package org.christophertwo.bifrost.utils.enums

enum class NumeralSystem(val radix: Int, val displayName: String, val validDigits: Set<Char>) {
    BINARY(2, "BIN", "01".toSet()),
    OCTAL(8, "OCT", "01234567".toSet()),
    DECIMAL(10, "DEC", "0123456789".toSet()),
    HEXADECIMAL(16, "HEX", "0123456789ABCDEF".toSet())
}
