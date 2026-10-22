package com.example.mylibrary.ui.components.checkboxes

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Token layer para CheckBoxMrcy (componente compuesto).
 */
data class CheckBoxTokens(
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextIcon: IconToken,
    val inputCheckBoxTokensGroup: InputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
)
