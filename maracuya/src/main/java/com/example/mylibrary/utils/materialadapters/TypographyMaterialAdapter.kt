package com.example.mylibrary.utils.materialadapters

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import com.example.mylibrary.semantics.CorporateTypography
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

object TypographyMaterialAdapter {

    fun adaptedTypography(corporateTokens: CorporateTypography, fontFamily: FontFamily): Typography {
        return Typography(
            displayLarge = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline48,
                fontFamily
            ),
            displayMedium = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline36,
                fontFamily
            ),
            displaySmall = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline32,
                fontFamily
            ),
            headlineLarge = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline28,
                fontFamily
            ),
            headlineMedium = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline24,
                fontFamily
            ),
            headlineSmall = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline21,
                fontFamily
            ),
            titleLarge = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline18,
                fontFamily
            ),
            titleMedium = TypographyComposeAdapter.toTextStyle(
                corporateTokens.headline16,
                fontFamily
            ),
            bodyLarge = TypographyComposeAdapter.toTextStyle(corporateTokens.body1, fontFamily),
            bodyMedium = TypographyComposeAdapter.toTextStyle(corporateTokens.body2, fontFamily),
            bodySmall = TypographyComposeAdapter.toTextStyle(corporateTokens.caption, fontFamily),
            labelLarge = TypographyComposeAdapter.toTextStyle(corporateTokens.buttons, fontFamily),
            labelSmall = TypographyComposeAdapter.toTextStyle(corporateTokens.legal, fontFamily)
        )

    }

}