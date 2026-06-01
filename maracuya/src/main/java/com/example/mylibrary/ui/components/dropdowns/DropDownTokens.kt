package com.example.mylibrary.ui.components.dropdowns

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken
import com.example.mylibrary.ui.components.inputs.config.InputDropDownTokenGroup

/**
 * Token layer del componente compuesto DropDownMrcy.
 */
data class DropDownTokens(
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextIcon: IconToken,
    val inputDropDownTokenGroup: InputDropDownTokenGroup = InputDropDownTokenGroup.BASIC,
)
