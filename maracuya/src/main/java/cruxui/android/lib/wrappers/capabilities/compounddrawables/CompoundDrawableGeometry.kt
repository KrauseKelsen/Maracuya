package cruxui.android.lib.wrappers.capabilities.compounddrawables

import android.graphics.RectF
import android.view.View
import android.widget.TextView
import kotlin.math.max

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CompoundDrawableGeometry calcula áreas de iconos relativos en widgets de texto Android.
 *
 * Justificación:
 * Separa geometría RTL y tamaño mínimo de los gestos y acciones accesibles. Comparte el
 * desplazamiento de OffsetCompoundDrawable para que la acción siga al dibujo.
 *
 * Uso:
 * Interno del DS. El controlador consulta target al despachar un evento. Si el renderer reserva
 * una celda interactiva completa, pasar minimumTargetSize=0 para no invadir el texto contiguo.
 * El host configura iconos y listeners; no utiliza esta clase directamente.
 *
 * Objetivos:
 * - Unificar límites visibles y táctiles.
 * - Evitar dependencia de tokens, variantes o componentes concretos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param view Widget receptor.
 * @param minimumTargetSize Ampliación mínima en píxeles, limitada al área de la View.
 */
internal class CompoundDrawableGeometry(private val view: TextView, private val minimumTargetSize: Float) {
    /** Devuelve el área visible ampliada; las coordenadas coinciden con MotionEvent. */
    fun target(slot: CompoundDrawableSlot): RectF? {
        val icon = view.compoundDrawablesRelative[if (slot == CompoundDrawableSlot.Start) 0 else 2] ?: return null
        val bounds = icon.bounds
        if (bounds.isEmpty || view.width <= 0 || view.height <= 0) return null
        val isLeft = (slot == CompoundDrawableSlot.Start) != (view.layoutDirection == View.LAYOUT_DIRECTION_RTL)
        val left = if (isLeft) view.paddingLeft.toFloat()
            else (view.width - view.paddingRight - bounds.width()).toFloat()
        val space = view.height - view.compoundPaddingTop - view.compoundPaddingBottom
        val top = view.compoundPaddingTop + (space - bounds.height()) / 2f
        return RectF(left + bounds.left, top + bounds.top, left + bounds.right, top + bounds.bottom).apply {
            offset((icon as? OffsetCompoundDrawable)?.offsetX?.toFloat() ?: 0f, 0f)
            // Amplía iconos pequeños al área táctil mínima, limitada a la región visible.
            inset(-max(0f, (minimumTargetSize - width()) / 2f), -max(0f, (minimumTargetSize - height()) / 2f))
            intersect(0f, 0f, view.width.toFloat(), view.height.toFloat())
        }
    }

}
