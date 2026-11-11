package cruxui.android.lib.core.components.buttons.tokens

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokens contiene la receta visual completa, sin dependencias de UI.
 *
 * Justificación: Comparte nombres y valores entre Compose y XML; no contiene callbacks, estados ni
 * tintes independientes de iconos.
 *
 * Se comunica con: ButtonTokenDefaults combina la receta. ButtonTokensRenderResolver elige el
 * estado y entrega un override al TextView del DS.
 *
 * Uso:
 * ```kotlin
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(false)
 * val override = ButtonTokensOverride(iconEnd = library.icons.alerts.info)
 * val tokens = ButtonTokenDefaults.resolve(library, override = override)
 * ```
 *
 * Null en un override conserva la capa inferior. Para retirar iconos en Compose, recomponer sin
 * ellos; en Views usar setIconStart(null) o setIconEnd(null).
 *
 * Objetivos:
 * - Compartir apariencia sin duplicar las reglas de texto e iconos.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @param background Fondo normal del botón; por ejemplo library.colors.brandPrimary.
 * @param textColor Color normal de texto e iconos; ambos comparten siempre el tinte.
 * @param pressedTextColor Color del texto y sus iconos durante una pulsación habilitada.
 * @param pressedBackground Fondo de pulsación; loading conserva background.
 * @param disabledBackground Fondo cuando enabled es false o behavior.isDisabled es true.
 * @param disabledTextColor Tinte común de texto e iconos deshabilitados.
 * @param border Color del borde normal, independiente del fondo.
 * @param disabledBorder Color del borde deshabilitado.
 * @param pressedBorder Color del borde durante pressed; no deriva del fondo personalizado.
 * @param typography Métricas del TextView interno; por defecto library.typography.buttons.
 * @param fontFamily Familia del tema que usa el TextView para resolver peso y cursiva.
 * @param iconStart Icono decorativo inicial relativo a RTL; sin token no hay icono ni separación.
 * @param iconEnd Icono decorativo final relativo a RTL; loading lo oculta junto con el texto.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
data class ButtonTokens(
    val background: ColorToken,
    val textColor: ColorToken,
    val pressedTextColor: ColorToken,
    val pressedBackground: ColorToken,
    val disabledBackground: ColorToken,
    val disabledTextColor: ColorToken,
    val border: ColorToken,
    val disabledBorder: ColorToken,
    val pressedBorder: ColorToken,
    val typography: TypographyToken,
    val fontFamily: FontFamilyToken,
    val iconStart: IconToken? = null,
    val iconEnd: IconToken? = null,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensOverride contiene sustituciones parciales del contrato visual.
 *
 * Justificación: Comparte nombres y valores entre Compose y XML; no contiene callbacks, estados ni
 * tintes independientes de iconos.
 *
 * Se comunica con: ButtonTokenDefaults combina la receta. ButtonTokensRenderResolver elige el
 * estado y entrega un override al TextView del DS.
 *
 * Uso:
 * ```kotlin
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(false)
 * val override = ButtonTokensOverride(iconEnd = library.icons.alerts.info)
 * val tokens = ButtonTokenDefaults.resolve(library, override = override)
 * ```
 *
 * Null en un override conserva la capa inferior. Para retirar iconos en Compose, recomponer sin
 * ellos; en Views usar setIconStart(null) o setIconEnd(null).
 *
 * Objetivos:
 * - Compartir apariencia sin duplicar las reglas de texto e iconos.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @param background Fondo normal del botón; por ejemplo library.colors.brandPrimary. Null conserva
 *   la capa inferior.
 * @param textColor Color normal de texto e iconos; ambos comparten siempre el tinte. Null conserva
 *   la capa inferior.
 * @param pressedTextColor Color del texto y sus iconos durante una pulsación habilitada. Null
 *   conserva la capa inferior.
 * @param pressedBackground Fondo de pulsación; loading conserva background. Null conserva la capa
 *   inferior.
 * @param disabledBackground Fondo cuando enabled es false o behavior.isDisabled es true. Null
 *   conserva la capa inferior.
 * @param disabledTextColor Tinte común de texto e iconos deshabilitados. Null conserva la capa
 *   inferior.
 * @param border Color del borde normal, independiente del fondo. Null conserva la capa inferior.
 * @param disabledBorder Color del borde deshabilitado. Null conserva la capa inferior.
 * @param pressedBorder Color del borde durante pressed; no deriva del fondo personalizado. Null
 *   conserva la capa inferior.
 * @param typography Métricas del TextView interno; por defecto library.typography.buttons. Null
 *   conserva la capa inferior.
 * @param fontFamily Familia del tema que usa el TextView para resolver peso y cursiva. Null
 *   conserva la capa inferior.
 * @param iconStart Icono decorativo inicial relativo a RTL; sin token no hay icono ni separación.
 *   Null conserva la capa inferior.
 * @param iconEnd Icono decorativo final relativo a RTL; loading lo oculta junto con el texto. Null
 *   conserva la capa inferior.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
data class ButtonTokensOverride(
    val background: ColorToken? = null,
    val textColor: ColorToken? = null,
    val pressedTextColor: ColorToken? = null,
    val pressedBackground: ColorToken? = null,
    val disabledBackground: ColorToken? = null,
    val disabledTextColor: ColorToken? = null,
    val border: ColorToken? = null,
    val disabledBorder: ColorToken? = null,
    val pressedBorder: ColorToken? = null,
    val typography: TypographyToken? = null,
    val fontFamily: FontFamilyToken? = null,
    val iconStart: IconToken? = null,
    val iconEnd: IconToken? = null,
)

/**
 * Combina campos presentes sin mutación; null conserva los valores del receptor.
 *
 * @param override Parche parcial, o null para devolver este mismo objeto.
 * @return Receta completa con las sustituciones solicitadas.
 */
fun ButtonTokens.merge(override: ButtonTokensOverride?): ButtonTokens {
    if (override == null) return this
    return copy(
        background = override.background ?: background,
        textColor = override.textColor ?: textColor,
        pressedTextColor = override.pressedTextColor ?: pressedTextColor,
        pressedBackground = override.pressedBackground ?: pressedBackground,
        disabledBackground = override.disabledBackground ?: disabledBackground,
        disabledTextColor = override.disabledTextColor ?: disabledTextColor,
        border = override.border ?: border,
        disabledBorder = override.disabledBorder ?: disabledBorder,
        pressedBorder = override.pressedBorder ?: pressedBorder,
        typography = override.typography ?: typography,
        fontFamily = override.fontFamily ?: fontFamily,
        iconStart = override.iconStart ?: iconStart,
        iconEnd = override.iconEnd ?: iconEnd,
    )
}
