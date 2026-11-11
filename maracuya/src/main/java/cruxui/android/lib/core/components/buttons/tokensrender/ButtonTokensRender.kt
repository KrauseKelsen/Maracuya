package cruxui.android.lib.core.components.buttons.tokensrender

import cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensRender transporta superficies y el contrato del TextView interno ya
 * resueltos.
 *
 * Justificación: La herencia por composición evita duplicar color de iconos, métricas y layout de
 * TextView.
 *
 * Se comunica con: Lo produce ButtonTokensRenderResolver; los renders solo adaptan colores y
 * entregan text al DS.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS, tras resolver tokens y behavior:
 * val render = ButtonTokensRenderResolver.resolve(tokens, state)
 * val textOverride = render.text
 * ```
 *
 * El host utiliza ButtonTokensOverride; este resultado es interno.
 *
 * Objetivos:
 * - Mantener los renders libres de decisiones de estado.
 *
 * Flujo: Receta completa y estado efectivo → colores resueltos → apariencia del TextView y
 * superficies.
 *
 * @param background Fondo efectivo.
 * @param border Borde efectivo.
 * @param textColor Tinte efectivo del texto y de ambos iconos; nunca configura acciones de icono.
 * @param typography Métricas del TextView interno, resueltas desde la tipografía buttons del tema.
 * @param fontFamily Familia efectiva, independiente de las métricas.
 * @param iconStart Icono decorativo inicial o null; se conserva para medir y restaurar después de
 *   loading.
 * @param iconEnd Icono decorativo final o null; no tiene un callback independiente del botón.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal data class ButtonTokensRender(
    val background: ColorToken,
    val border: ColorToken,
    val textColor: ColorToken,
    val typography: TypographyToken,
    val fontFamily: FontFamilyToken,
    val iconStart: IconToken?,
    val iconEnd: IconToken?,
) {
    /** Tinte del icono inicial; comparte siempre el color efectivo del texto, como TextView. */
    val iconStartColor: ColorToken
        get() = textColor

    /** Tinte del icono final; no existe una personalización que lo separe del texto. */
    val iconEndColor: ColorToken
        get() = textColor

    /**
     * Contrato de TextView construido con los mismos tokens planos; no mantiene una segunda receta.
     */
    val text: TextViewTokensOverride
        get() =
            TextViewTokensOverride(
                textColor = textColor,
                disabledColor = textColor,
                typography = typography,
                fontFamily = fontFamily,
                iconStart = iconStart,
                iconEnd = iconEnd,
            )
}
