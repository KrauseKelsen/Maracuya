package com.example.mylibrary.ui.components.inputs.basic

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Component Token Layer — InputFieldBasic
 *
 * - DS puro
 * - NO conoce Compose
 * - NO contiene lógica
 */
data class InputFieldBasicTokens(
    val borderDefault: ColorToken, // Borde gris por default
    val borderFocus: ColorToken, // Borde azulado selected
    val borderError: ColorToken, // Borde rojo de error

    val placeholderColor: ColorToken, // Placeholder gris
    val textColor: ColorToken, // Texto del usuario negro

    val backgroundDisabled: ColorToken, // Fondo del input gris oscuro

    val placeholderTypography: TypographyToken, // Tipografia del texto
    val textTypography: TypographyToken, // Tipografia del texto

    val fontFamilyToken: FontFamilyToken, // Fuente de letra

    val leadingIcon: IconToken? = null,
    val trailingIcon: IconToken? = null,
    val iconColor: ColorToken? = null,
    val implicitClearTrailingIcon: IconToken? = null,
)
