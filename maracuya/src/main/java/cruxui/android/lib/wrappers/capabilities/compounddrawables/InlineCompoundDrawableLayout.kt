package cruxui.android.lib.wrappers.capabilities.compounddrawables

import android.text.Layout
import android.view.View
import android.widget.TextView
import kotlin.math.ceil

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * InlineCompoundDrawableLayout mantiene los iconos relativos junto al bloque de texto aunque
 * el padre asigne a la View un ancho mayor que su contenido.
 *
 * Justificación:
 * Android ancla el drawable final al borde; una fila con texto de ancho intrínseco lo coloca
 * después del texto. Esta capacidad adapta la medición sin cambiar el padding explícito del host.
 * Se comunica con los getters de compoundPadding y con el desplazamiento del renderer de iconos.
 *
 * Uso:
 * Interno del DS. Antes de medir, llamar reset; después de la primera medición, resolve.
 * Si resolve devuelve true, repetir la medición con los insets calculados. Los widgets que
 * prefieran iconos anclados a sus bordes no incorporan esta capacidad.
 *
 * Objetivos:
 * - Separar disposición, dibujo y comportamiento.
 * - Conservar texto, spans, selección y padding nativos.
 * - Resolver posiciones relativas tanto en LTR como en RTL.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param view Widget cuyo bloque textual se mide.
 */
internal class InlineCompoundDrawableLayout(private val view: TextView) {
    private var endInset = 0
    private val rtl: Boolean get() = view.layoutDirection == View.LAYOUT_DIRECTION_RTL
    /** Espacio de contenido libre a la izquierda; no modifica android:paddingLeft. */
    val leftInset: Int get() = if (rtl) endInset else 0
    /** Espacio de contenido libre a la derecha; no modifica android:paddingRight. */
    val rightInset: Int get() = if (rtl) 0 else endInset
    /** Traslación horizontal del icono final hacia el texto. */
    val endOffset: Int get() = if (rtl) endInset else -endInset

    /** Inicia una medición nueva; evita acumular espacios al cambiar texto o dimensiones. */
    fun reset() { endInset = 0 }

    /** Calcula el sobrante a partir del texto transformado, incluyendo spans y fuentes resueltas. */
    fun resolve(): Boolean {
        val icons = view.compoundDrawablesRelative
        if (icons[0] == null && icons[2] == null) return false
        val layout = view.layout ?: return false
        val available = (view.measuredWidth - view.compoundPaddingLeft - view.compoundPaddingRight).coerceAtLeast(0)
        val intrinsic = ceil(Layout.getDesiredWidth(layout.text, view.paint).toDouble()).toInt()
        endInset = (available - intrinsic).coerceAtLeast(0)
        return endInset > 0
    }
}
