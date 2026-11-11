package cruxui.android.lib.core.components.buttons.tokens

import cruxui.android.lib.core.components.buttons.variant.ButtonVariant
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
import cruxui.android.lib.theme.LibraryThemeSnapshot

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokenDefaults resuelve las tres recetas del botón: relleno de marca, borde
 * neutro y borde de marca.
 *
 * Justificación: Es la fuente única para ambas plataformas. No mezcla colores Material ni fabrica
 * valores ARGB.
 *
 * Se comunica con: Hereda familia e iconos de TextViewTokenDefaults y usa typography.buttons del
 * tema activo.
 *
 * Uso:
 * ```kotlin
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(dark = true)
 * val tokens = ButtonTokenDefaults.resolve(library, ButtonVariant.SECONDARY)
 * ```
 *
 * Sin variante se obtiene PRIMARY. Loading no tiene receta distinta: usa los colores normales.
 *
 * Objetivos:
 * - Conservar las recetas de marca y compartir el borde neutro con XML.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
object ButtonTokenDefaults {
    /**
     * Selecciona receta y aplica el parche al final.
     *
     * @param library Snapshot de marca y modo, sin Context ni CompositionLocal.
     * @param variant PRIMARY relleno, SECONDARY borde fgDefault o TERCIARY borde brandPrimary.
     * @param override Campos que sustituyen la receta; null la conserva.
     * @return Tokens completos, todavía sin resolución de estado.
     */
    fun resolve(
        library: LibraryThemeSnapshot,
        variant: ButtonVariant = ButtonVariant.PRIMARY,
        override: ButtonTokensOverride? = null,
    ): ButtonTokens {
        val colors = library.colors
        val text = TextViewTokenDefaults.resolve(library)
        val isSecondary = variant == ButtonVariant.SECONDARY
        return ButtonTokens(
                background =
                    when (variant) {
                        ButtonVariant.PRIMARY -> colors.brandPrimary
                        ButtonVariant.SECONDARY -> colors.bgBase
                        ButtonVariant.TERCIARY -> colors.bgBase
                    },
                textColor =
                    when (variant) {
                        ButtonVariant.PRIMARY -> colors.fgOnInverse
                        ButtonVariant.SECONDARY -> colors.fgDefault
                        ButtonVariant.TERCIARY -> colors.fgOnInverseAux
                    },
                pressedTextColor = if (isSecondary) colors.bgBase else colors.fgOnInverse,
                pressedBackground = if (isSecondary) colors.fgDefault else colors.brandPrimaryHover,
                disabledBackground = colors.bgMuted,
                disabledTextColor = text.disabledColor,
                border = if (isSecondary) colors.fgDefault else colors.brandPrimary,
                disabledBorder = colors.borderDefault,
                pressedBorder = if (isSecondary) colors.fgDefault else colors.brandPrimaryHover,
                typography = library.typography.buttons,
                fontFamily = text.fontFamily,
                iconStart = text.iconStart,
                iconEnd = text.iconEnd,
            )
            .merge(override)
    }
}
