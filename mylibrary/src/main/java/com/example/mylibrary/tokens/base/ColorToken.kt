package com.example.mylibrary.tokens.base

/**
 * Representa un color ARGB sin depender de Compose.
 * Ejemplo de uso: ColorToken(0xFFED1C27)
 */
@JvmInline
value class ColorToken(val argb: Long) {

    val alpha: Int get() = ((argb shr 24) and 0xFF).toInt()
    val red: Int get() = ((argb shr 16) and 0xFF).toInt()
    val green: Int get() = ((argb shr 8) and 0xFF).toInt()
    val blue: Int get() = (argb and 0xFF).toInt()

    companion object {
        fun fromHex(hex: Long): ColorToken = ColorToken(hex)
    }
}