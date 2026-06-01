package com.example.mylibrary.ui.components.textfields

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken
import com.example.mylibrary.ui.components.inputs.config.InputFieldBasicTokenGroup

//Component token layer del TextField por defecto

data class TextFieldTokens(
    // Texto inferior
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextColor: ColorToken,
    val bottomTextIcon: IconToken,
    val inputFieldTokenGroup: InputFieldBasicTokenGroup = InputFieldBasicTokenGroup.BASIC,
    val passwordHiddenIcon: IconToken? = null,
    val passwordVisibleIcon: IconToken? = null,
)
