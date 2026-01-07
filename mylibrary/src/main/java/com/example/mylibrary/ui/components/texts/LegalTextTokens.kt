package com.example.mylibrary.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.TypographyToken

//TODO cambiar el token para que reciba TokenAdaper en vez de TextStyle, es decir no compose
data class LegalTextTokens(
    val typographyToken: TypographyToken,
    val colorToken: ColorToken
) {
    companion object {

        /**
         * Deriva desde la semántica corporativa de la librería.
         */
        @Composable
        fun fromCorporate(): LegalTextTokens {
            val colors = LocalLibraryColorTokens.current!!
            val typographyTokens = LocalLibraryTypography.current

            return remember(colors, typographyTokens) {
                LegalTextTokens(
                    typographyToken = typographyTokens.legal,
                    colorToken = colors.fgDefault
                )
            }
        }

        /**
         * Fallback desde MaterialTheme (cuando el host pide useMaterial = true).
         */
        @Composable
        fun fromMaterial(): LegalTextTokens {
            val mt = MaterialTheme.typography
            val scheme = MaterialTheme.colorScheme

            return remember(scheme) {
                LegalTextTokens(
                    typographyToken = TypographyToken(
                        fontSize = mt.bodySmall.fontSize.value,
                        lineHeight = mt.bodySmall.lineHeight.value,
                        letterSpacing = mt.bodySmall.letterSpacing.value,
                        weight = mt.bodySmall.fontWeight?.weight ?: 400,
                        italic = false
                    ),
                    colorToken = ColorToken(scheme.onSurface.value.toLong())
                )
            }
        }

        /**
         * Custom explícito del host.
         */
        fun custom(
            typography: TypographyToken,
            color: ColorToken
        ): LegalTextTokens = LegalTextTokens(typography, color)
    }
}