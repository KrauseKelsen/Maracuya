package com.example.mylibrary.ui.components.buttons.secondary

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.TypographyToken

data class SecondaryButtonTokens(
    val containerColor: ColorToken, // DefaultColors.Brand.Red.c500: Rojo
    val contentColor: ColorToken, // DefaultColors.Core.white: Blanco en ambos modos
    val contentPressColor: ColorToken,
    val hoverContainerColor: ColorToken, // DefaultColors.Brand.Red.c600: Rojo oscuro
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val borderContainerColor: ColorToken, // DefaultColors.Brand.Red.c500: Rojo
    val borderDisabledColor: ColorToken, // DefaultColors.Neutrals.Inks.c300: Rojo
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken
)