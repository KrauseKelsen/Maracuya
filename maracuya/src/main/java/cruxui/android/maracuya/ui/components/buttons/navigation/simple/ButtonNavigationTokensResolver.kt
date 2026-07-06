package cruxui.android.maracuya.ui.components.buttons.navigation.simple

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.ui.components.buttons.simple.ButtonTokensResolver
import cruxui.android.maracuya.ui.components.buttons.simple.ButtonVariant

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
        val buttonTokens = ButtonTokensResolver.resolve(
            variant = variant.toButtonVariant(),
        )
        val icons = LocalLibraryIcons.current

        val iconToken = when (variant) {
            ButtonNavigationVariant.PRIMARY -> icons.arrows.keyboardBackspaceRight
            ButtonNavigationVariant.SECONDARY -> icons.arrows.keyboardBackspaceLeft
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
     * Fallback Material. Por ahora reutiliza DS para evitar divergencias visuales.
     */
    @Composable
    private fun fromMaterial(variant: ButtonNavigationVariant): ButtonNavigationTokens = fromLibrary(variant)

    private fun ButtonNavigationVariant.toButtonVariant(): ButtonVariant =
        when (this) {
            ButtonNavigationVariant.PRIMARY -> ButtonVariant.PRIMARY
            ButtonNavigationVariant.SECONDARY -> ButtonVariant.SECONDARY
        }
}
