package cruxui.android.maracuya.ui.components.buttons.simple

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.lerp
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * ButtonTokensResolver
 *
 * - Punto único de resolución de tokens para ButtonMrcy.
 * - Orquesta tokens visuales de las variantes PRIMARY y SECONDARY.
 * - DS first, Material fallback.
 */
object ButtonTokensResolver {

    /**
     * Resolución pública de tokens.
     */
    @Composable
    fun resolve(
        variant: ButtonVariant = ButtonVariant.PRIMARY,
        tokens: ButtonTokens? = null,
        override: ButtonTokensOverride? = null,
    ): ButtonTokens {
        val base = tokens ?: when {
            hasLibraryTokens() -> fromLibrary(variant)
            else -> fromMaterial(variant)
        }

        return base.merge(override)
    }

    /**
     * Detecta si el Design System está correctamente inyectado.
     */
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
     * Resolución desde tokens corporativos (DS).
     */
    @Composable
    private fun fromLibrary(variant: ButtonVariant): ButtonTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current

        return when (variant) {
            ButtonVariant.PRIMARY -> ButtonTokens(
                containerColor = colors.brandPrimary,
                contentColor = colors.fgOnInverse,
                contentPressColor = colors.fgOnInverse,
                hoverContainerColor = colors.brandPrimaryHover,
                disabledContainerColor = colors.bgMuted,
                disabledContentColor = colors.fgSubtle,
                borderContainerColor = colors.brandPrimary,
                borderDisabledColor = colors.borderDefault,
                textTypography = typography.buttons,
                fontFamilyToken = fontFamily,
            )

            ButtonVariant.SECONDARY -> ButtonTokens(
                containerColor = colors.bgBase,
                contentColor = colors.fgOnInverseAux,
                contentPressColor = colors.fgOnInverse,
                hoverContainerColor = colors.brandPrimaryHover,
                disabledContainerColor = colors.bgMuted,
                disabledContentColor = colors.fgSubtle,
                borderContainerColor = colors.brandPrimary,
                borderDisabledColor = colors.borderDefault,
                textTypography = typography.buttons,
                fontFamilyToken = fontFamily,
            )
        }
    }

    /**
     * Fallback Material Design.
     */
    @Composable
    private fun fromMaterial(variant: ButtonVariant): ButtonTokens {
        val scheme = MaterialTheme.colorScheme
        val fontFamilyToken = LocalFontFamily.current

        fun pressedPrimary(): ColorToken {
            val overlay = scheme.onPrimary.copy(alpha = 0.12f)
            val mixed = lerp(scheme.primary, overlay, 0.5f)
            return ColorToken(mixed.value.toLong())
        }

        fun disabledContainer(): ColorToken {
            val overlay = scheme.onSurface.copy(alpha = 0.12f)
            val mixed = lerp(scheme.surface, overlay, 1f)
            return ColorToken(mixed.value.toLong())
        }

        fun disabledContent(): ColorToken =
            ColorToken(scheme.onSurface.copy(alpha = 0.38f).value.toLong())

        val typography = TypographyToken(
            fontSize = 14f,
            lineHeight = 20f,
            letterSpacing = 0.1f,
            weight = 500,
            italic = false,
        )

        return remember(scheme, fontFamilyToken, variant) {
            when (variant) {
                ButtonVariant.PRIMARY -> ButtonTokens(
                    containerColor = ColorToken(scheme.primary.value.toLong()),
                    contentColor = ColorToken(scheme.onPrimary.value.toLong()),
                    contentPressColor = ColorToken(scheme.onPrimary.value.toLong()),
                    hoverContainerColor = pressedPrimary(),
                    disabledContainerColor = disabledContainer(),
                    disabledContentColor = disabledContent(),
                    borderContainerColor = ColorToken(scheme.outline.value.toLong()),
                    borderDisabledColor = ColorToken(scheme.outlineVariant.value.toLong()),
                    textTypography = typography,
                    fontFamilyToken = fontFamilyToken,
                )

                ButtonVariant.SECONDARY -> ButtonTokens(
                    containerColor = ColorToken(scheme.surface.value.toLong()),
                    contentColor = ColorToken(scheme.primary.value.toLong()),
                    contentPressColor = ColorToken(scheme.onPrimary.value.toLong()),
                    hoverContainerColor = pressedPrimary(),
                    disabledContainerColor = disabledContainer(),
                    disabledContentColor = disabledContent(),
                    borderContainerColor = ColorToken(scheme.primary.value.toLong()),
                    borderDisabledColor = ColorToken(scheme.outlineVariant.value.toLong()),
                    textTypography = typography,
                    fontFamilyToken = fontFamilyToken,
                )
            }
        }
    }
}
