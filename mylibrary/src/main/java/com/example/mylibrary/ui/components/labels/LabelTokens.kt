package com.example.mylibrary.ui.components.labels

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Component Token Layer (DS puro)
 *
 * - NO conoce Compose
 * - NO conoce LocalComposition
 * - NO conoce MaterialTheme
 */
data class LabelTokens(
    val foregroundDefault: ColorToken,
    val foregroundError: ColorToken,
    val labelTypography: TypographyToken,
    val optionalTypography: TypographyToken,
    val infoIcon: IconToken,
    val fontFamily: FontFamilyToken
)
