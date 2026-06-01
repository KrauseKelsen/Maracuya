package com.example.mylibrary.ui.components.buttons.chipchoice

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Tokens visuales para ChipChoiceBase.
 * Contiene únicamente definición de tokens (sin lógica).
 */
data class ChipChoiceBaseTokens(

    // Background
    val containerColor: ColorToken,
    val pressedContainerColor: ColorToken,

    // Border
    val borderColor: ColorToken,

    // Content
    val contentColor: ColorToken,
    val pressedContentColor: ColorToken,

    //Disabled
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,

    // Typography
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)
