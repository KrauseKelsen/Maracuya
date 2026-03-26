package com.example.mylibrary.ui.components.checkboxes

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Component Token Layer — InputCheckBox.
 *
 * Tokeniza al átomo base para desacoplar UI y semántica.
 */
data class InputCheckBoxTokens(
    val borderDefault: ColorToken,
    val borderDisabled: ColorToken,
    val checkBackground: ColorToken,
    val checkIconColor: ColorToken,
    val labelColor: ColorToken,
    val labelDisabledColor: ColorToken,
    val labelTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
    val checkIcon: IconToken,
)
