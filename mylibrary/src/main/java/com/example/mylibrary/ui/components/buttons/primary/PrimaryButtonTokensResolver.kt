package com.example.mylibrary.ui.components.buttons.primary

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.TypographyToken

object PrimaryButtonTokensResolver {

    @Composable
    fun resolve(
        override: PrimaryButtonTokens? = null
    ): PrimaryButtonTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary()
            else -> fromMaterial()
        }
    }

    @Composable
    private fun hasLibraryTokens(): Boolean {
        return runCatching {
            LocalLibraryColorTokens.current
            LocalLibraryTypography.current
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    /**
     * Deriva ButtonTokens desde LibraryColorTokens (semántica corporativa).
     * Mantener SRP: esta función solo mapea semántica -> tokens del componente.
     */
    @Composable
    fun fromLibrary(): PrimaryButtonTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current
        return PrimaryButtonTokens(
            containerColor = colors.brandPrimary,
            contentColor = colors.fgOnInverse,
            hoverContainerColor = colors.brandPrimaryHover,
            disabledContainerColor = colors.bgMuted,
            disabledContentColor = colors.fgMuted,
            borderContainerColor = colors.brandPrimary,
            borderDisabledColor = colors.borderDefault,
            textTypography = typography.buttons, // semántica corporativa para botones
            fontFamilyToken = fontFamily
        )
    }

    /**
     * Deriva ButtonTokens desde el ColorScheme activo (Material).
     * Útil cuando la librería host ha solicitado `useMaterial = true`.
     */
    @Composable
    fun fromMaterial(): PrimaryButtonTokens {
        val scheme = MaterialTheme.colorScheme
        val fontFamilyToken = LocalFontFamily.current

        // Helpers para aplicar “pressed/hover” sin inventar un color completamente distinto
        fun pressedColor(): ColorToken {
            // Oscurece o aclara levemente el primary usando onPrimary como overlay
            val base = scheme.primary
            val overlay = scheme.onPrimary.copy(alpha = 0.12f)
            val mixed = androidx.compose.ui.graphics.lerp(base, overlay, 0.5f)
            return ColorToken(mixed.value.toLong())
        }

        fun disabledContainer(): ColorToken {
            // Surface con onSurface tenue encima (look Material)
            val base = scheme.surface
            val overlay = scheme.onSurface.copy(alpha = 0.12f)
            val mixed = androidx.compose.ui.graphics.lerp(base, overlay, 1f)
            return ColorToken(mixed.value.toLong())
        }

        fun disabledContent(): ColorToken =
            ColorToken(scheme.onSurface.copy(alpha = 0.38f).value.toLong())

        return remember(scheme, fontFamilyToken) {
            PrimaryButtonTokens(
                containerColor = ColorToken(scheme.primary.value.toLong()),
                contentColor = ColorToken(scheme.onPrimary.value.toLong()),

                // “pressed/hover” con sentido (variación del primary)
                hoverContainerColor = pressedColor(),

                disabledContainerColor = disabledContainer(),
                disabledContentColor = disabledContent(),

                // Diferenciar del DS corporativo: outline en vez de brandPrimary
                borderContainerColor = ColorToken(scheme.outline.value.toLong()),
                borderDisabledColor = ColorToken(scheme.outlineVariant.value.toLong()),

                textTypography = TypographyToken(
                    fontSize = 14f,
                    lineHeight = 20f,
                    letterSpacing = 0.1f,
                    weight = 500,
                    italic = false
                ),
                fontFamilyToken = fontFamilyToken
            )
        }
    }

//    @Composable
//    fun fromMaterial(): PrimaryButtonTokens = fromLibrary()
}