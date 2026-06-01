package com.example.mylibrary.ui.components.buttons

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.TypographyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.ui.components.utils.IconPosition

/**
 * ButtonIconTokens: tokens específicos para botones con icono.
 * Define:
 * - icono a renderizar
 * - color del icono
 * - posición del icono (inicio o fin)
 * - tipografía y color del texto
 */
data class ButtonIconTokens(
    val icon: IconToken,
    val iconTint: ColorToken,
    val textTypography: TypographyToken,
    val textColor: ColorToken,
    val iconPosition: IconPosition = IconPosition.START
)
