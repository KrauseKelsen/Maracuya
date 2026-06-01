package com.example.mylibrary.ui.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.mylibrary.atoms.LibraryColorTokens
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.TypographyToken

// TODO el color del boton disable debe cambiar pero desde el textstyle (4 modos)
// modo 1 y 2: blanco, modo 3: gris oscurso, modo 4: gris claro
// PD es mejor crear otro button llamado secondary y manejar eso independiente
data class ButtonTokens(
    val containerColor: ColorToken, // DefaultColors.Brand.Red.c500: Rojo
    val contentColor: ColorToken, // DefaultColors.Core.white: Blanco en ambos modos
    val hoverContainerColor: ColorToken, // DefaultColors.Brand.Red.c600: Rojo oscuro
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken
) {
    companion object {
        /**
         * Deriva ButtonTokens desde LibraryColorTokens (semántica corporativa).
         * Mantener SRP: esta función solo mapea semántica -> tokens del componente.
         */
        @Composable
        fun fromLibraryTokens(tokens: LibraryColorTokens): ButtonTokens {
            val typographyTokens = LocalLibraryTypography.current
            val fontFamilyToken = LocalFontFamily.current
            return ButtonTokens(
                containerColor = tokens.brandPrimary,
                contentColor = tokens.fgOnInverse,
                hoverContainerColor = tokens.brandPrimaryHover,
                disabledContainerColor = tokens.bgMuted,
                disabledContentColor = tokens.fgMuted,
                textTypography = typographyTokens.buttons, // semántica corporativa para botones
                fontFamilyToken = fontFamilyToken
            )
        }

        /**
         * Deriva ButtonTokens desde el ColorScheme activo (Material).
         * Útil cuando la librería host ha solicitado `useMaterial = true`.
         */
        @Composable
        fun fromMaterialTheme(): ButtonTokens {
            val scheme = MaterialTheme.colorScheme
            val typography = MaterialTheme.typography
            val fontFamilyToken = LocalFontFamily.current

            return remember(scheme) {
                ButtonTokens(
                    containerColor = ColorToken(scheme.primary.value.toLong()),
                    contentColor = ColorToken(scheme.onPrimary.value.toLong()),
                    hoverContainerColor = ColorToken(scheme.primaryContainer.value.toLong()),
                    disabledContainerColor = ColorToken(scheme.surfaceVariant.value.toLong()),
                    disabledContentColor = ColorToken((scheme.onSurface.copy(alpha = 0.38f)).value.toLong()),
                    textTypography = TypographyToken(
                        fontSize = 14f,
                        lineHeight = 20f,
                        letterSpacing = 0.1f,
                        weight = 500,
                        italic = false
                    ), // semántica Material
                    fontFamilyToken = fontFamilyToken
                )
            }
        }
    }
}
