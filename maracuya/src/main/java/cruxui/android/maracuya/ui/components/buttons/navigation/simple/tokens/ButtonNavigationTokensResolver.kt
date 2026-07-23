package cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariantResolver
import cruxui.android.maracuya.ui.components.buttons.simple.ButtonTokensResolver

/**
 * Resuelve los tokens del ButtonNavigation desde los tokens visuales de ButtonMrcy.
 *
 * Mantiene la estructura estandar de resolvers: resolve, fromLibrary y fromMaterial.
 */
object ButtonNavigationTokensResolver {

    @get:Composable
    val primary: ButtonNavigationTokens
        get() = resolve(variant = ButtonNavigationVariant.PRIMARY)

    @get:Composable
    val secondary: ButtonNavigationTokens
        get() = resolve(variant = ButtonNavigationVariant.SECONDARY)

    /**
     * Resuelve tokens visuales para la variante indicada y aplica overrides opcionales.
     */
    @Composable
    fun resolve(
        variant: ButtonNavigationVariant = ButtonNavigationVariant.PRIMARY,
        tokens: ButtonNavigationTokens? = null,
        override: ButtonNavigationTokensOverride? = null,
    ): ButtonNavigationTokens {
        val base = tokens ?: when {
            hasLibraryTokens() -> fromLibrary(variant)
            else -> fromMaterial(variant)
        }

        return base.merge(override)
    }

    /**
     * Detecta si el Design System esta correctamente inyectado.
     */
    @Composable
    private fun hasLibraryTokens(): Boolean {
        return runCatching {
            LocalLibraryColorTokens.current
            LocalLibraryTypography.current
            LocalLibraryIcons.current
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    /**
     * Resuelve tokens desde el Design System y mapea la variante visual al ButtonMrcy base.
     */
    @Composable
    private fun fromLibrary(variant: ButtonNavigationVariant): ButtonNavigationTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current
        val icons = LocalLibraryIcons.current

        if (ButtonNavigationVariantResolver.isSquare(variant)) {
            return when (variant) {
                ButtonNavigationVariant.PRIMARYSQUARE -> ButtonNavigationTokens(
                    contentColor = colors.fgOnInverse,
                    contentPressColor = colors.fgOnInverse,
                    containerColor = colors.brandPrimary,
                    disabledContentColor = colors.fgSubtle,
                    disabledContainerColor = colors.bgMuted,
                    hoverContainerColor = colors.brandPrimaryHover,
                    borderContainerColor = colors.brandPrimary,
                    borderDisabledColor = colors.borderDefault,
                    iconToken = icons.arrows.keyboardBackspaceRight,
                    loadingIconToken = icons.arrows.sync,
                    iconColor = colors.fgOnInverse,
                    typographyToken = typography.legal,
                    fontFamilyToken = fontFamily,
                )

                ButtonNavigationVariant.SECONDARYSQUARE -> ButtonNavigationTokens(
                    contentColor = colors.brandPrimary,
                    contentPressColor = colors.fgOnInverse,
                    containerColor = colors.errorFillSoft,
                    disabledContentColor = colors.fgSubtle,
                    disabledContainerColor = colors.bgMuted,
                    hoverContainerColor = colors.brandPrimaryHover,
                    borderContainerColor = colors.brandPrimary,
                    borderDisabledColor = colors.borderDefault,
                    iconToken = icons.arrows.keyboardBackspaceLeft,
                    loadingIconToken = icons.arrows.sync,
                    iconColor = colors.brandPrimary,
                    typographyToken = typography.legal,
                    fontFamilyToken = fontFamily,
                )

                ButtonNavigationVariant.PRIMARY,
                ButtonNavigationVariant.SECONDARY -> error("Unsupported square variant: $variant")
            }
        }

        val buttonTokens = ButtonTokensResolver.resolve(
            variant = ButtonNavigationVariantResolver.resolve(variant),
        )

        val iconToken = when (variant) {
            ButtonNavigationVariant.PRIMARY -> icons.arrows.keyboardBackspaceRight

            ButtonNavigationVariant.SECONDARY -> icons.arrows.keyboardBackspaceLeft

            ButtonNavigationVariant.PRIMARYSQUARE,
            ButtonNavigationVariant.SECONDARYSQUARE -> error("Unsupported label variant: $variant")
        }

        return ButtonNavigationTokens(
            contentColor = buttonTokens.contentColor,
            contentPressColor = buttonTokens.contentPressColor,
            containerColor = buttonTokens.containerColor,
            disabledContentColor = buttonTokens.disabledContentColor,
            disabledContainerColor = buttonTokens.disabledContainerColor,
            hoverContainerColor = buttonTokens.hoverContainerColor,
            borderContainerColor = buttonTokens.borderContainerColor,
            borderDisabledColor = buttonTokens.borderDisabledColor,
            iconToken = iconToken,
            loadingIconToken = icons.arrows.sync,
            iconColor = buttonTokens.contentColor,
            typographyToken = buttonTokens.textTypography,
            fontFamilyToken = buttonTokens.fontFamilyToken,
        )
    }

    /**
     * Fallback Material Design para uso fuera del tema del DS.
     */
    @Composable
    private fun fromMaterial(variant: ButtonNavigationVariant): ButtonNavigationTokens {
        val buttonTokens = ButtonTokensResolver.resolve(
            variant = ButtonNavigationVariantResolver.resolve(variant),
        )
        val scheme = MaterialTheme.colorScheme
        val backRightIcon = IconToken("ic_keyboard_backspace_right", R.drawable.ic_keyboard_backspace_right)
        val backLeftIcon = IconToken("ic_keyboard_backspace_left", R.drawable.ic_keyboard_backspace_left)
        val syncIcon = IconToken("ic_sync", R.drawable.ic_sync)

        fun colorToken(value: Color): ColorToken =
            ColorToken(value.value.toLong())

        fun pressedPrimary(): ColorToken {
            val overlay = scheme.onPrimary.copy(alpha = 0.12f)
            val mixed = lerp(scheme.primary, overlay, 0.5f)
            return colorToken(mixed)
        }

        if (ButtonNavigationVariantResolver.isSquare(variant)) {
            val legalTypography = TypographyToken(
                fontSize = 12f,
                lineHeight = 18f,
                letterSpacing = 0f,
                weight = 400,
                italic = false,
            )

            return when (variant) {
                ButtonNavigationVariant.PRIMARYSQUARE -> ButtonNavigationTokens(
                    contentColor = colorToken(scheme.onPrimary),
                    contentPressColor = colorToken(scheme.onPrimary),
                    containerColor = colorToken(scheme.primary),
                    disabledContentColor = colorToken(scheme.onSurfaceVariant),
                    disabledContainerColor = colorToken(scheme.surfaceVariant),
                    hoverContainerColor = pressedPrimary(),
                    borderContainerColor = colorToken(scheme.primary),
                    borderDisabledColor = colorToken(scheme.outline),
                    iconToken = backRightIcon,
                    loadingIconToken = syncIcon,
                    iconColor = colorToken(scheme.onPrimary),
                    typographyToken = legalTypography,
                    fontFamilyToken = buttonTokens.fontFamilyToken,
                )

                ButtonNavigationVariant.SECONDARYSQUARE -> ButtonNavigationTokens(
                    contentColor = colorToken(scheme.primary),
                    contentPressColor = colorToken(scheme.onPrimary),
                    containerColor = colorToken(scheme.primaryContainer),
                    disabledContentColor = colorToken(scheme.onSurfaceVariant),
                    disabledContainerColor = colorToken(scheme.surfaceVariant),
                    hoverContainerColor = pressedPrimary(),
                    borderContainerColor = colorToken(scheme.primary),
                    borderDisabledColor = colorToken(scheme.outline),
                    iconToken = backLeftIcon,
                    loadingIconToken = syncIcon,
                    iconColor = colorToken(scheme.primary),
                    typographyToken = legalTypography,
                    fontFamilyToken = buttonTokens.fontFamilyToken,
                )

                ButtonNavigationVariant.PRIMARY,
                ButtonNavigationVariant.SECONDARY -> error("Unsupported square variant: $variant")
            }
        }

        val iconToken = when (variant) {
            ButtonNavigationVariant.PRIMARY -> backRightIcon
            ButtonNavigationVariant.SECONDARY -> backLeftIcon
            ButtonNavigationVariant.PRIMARYSQUARE,
            ButtonNavigationVariant.SECONDARYSQUARE -> error("Unsupported label variant: $variant")
        }

        return ButtonNavigationTokens(
            contentColor = buttonTokens.contentColor,
            contentPressColor = buttonTokens.contentPressColor,
            containerColor = buttonTokens.containerColor,
            disabledContentColor = buttonTokens.disabledContentColor,
            disabledContainerColor = buttonTokens.disabledContainerColor,
            hoverContainerColor = buttonTokens.hoverContainerColor,
            borderContainerColor = buttonTokens.borderContainerColor,
            borderDisabledColor = buttonTokens.borderDisabledColor,
            iconToken = iconToken,
            loadingIconToken = syncIcon,
            iconColor = buttonTokens.contentColor,
            typographyToken = buttonTokens.textTypography,
            fontFamilyToken = buttonTokens.fontFamilyToken,
        )
    }
}
