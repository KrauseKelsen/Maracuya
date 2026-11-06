package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/** Token tipográfico junto con la fuente Android cargada; no representa estado de componente. */
data class AndroidTypography(val token: TypographyToken, val typeface: Typeface)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AndroidTokenAdapter: Fachada compatible de conversión de tokens a valores Android.
 *
 * Justificación:
 * Delega fuentes e iconos a cargadores independientes. Los nuevos resolutores pueden utilizar la capacidad necesaria sin incorporar comportamiento.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
object AndroidTokenAdapter {
    /** Proyecta ARGB a la representación entera de Android. */
    fun color(token: ColorToken): Int = token.argb.toInt()
    /** Delega selección y carga de la familia tipográfica. */
    fun font(context: Context, family: FontFamilyToken, weight: Int = 400, italic: Boolean = false): Typeface =
        AndroidFontLoader.load(context, family, weight, italic)
    /** Combina un token tipográfico con su fuente; no impone estilos de widget. */
    fun typography(context: Context, token: TypographyToken, family: FontFamilyToken): AndroidTypography =
        AndroidTypography(token, font(context, family, token.weight, token.italic))
    /** Delega la carga aislada del drawable. */
    fun icon(context: Context, token: IconToken): Drawable = AndroidIconLoader.load(context, token)
}
