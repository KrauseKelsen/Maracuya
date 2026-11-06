package cruxui.android.lib.wrappers.capabilities.compounddrawables

import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.widget.TextView

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CompoundDrawableRender transporta el asset y el tinte resueltos de un slot relativo.
 *
 * Justificación:
 * Permite aplicar iconos sin que el renderer consulte recetas, temas o callbacks del componente.
 *
 * Uso:
 * Modelo interno del DS, producido por adaptadores de tokens y consumido por CompoundDrawableRenderer.
 * El host declara IconToken o recursos mediante el API del componente.
 *
 * Objetivos:
 * - Separar valores visuales y propiedad del slot de la política de interacción.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param managed true indica que el DS controla este slot, incluso para retirar su drawable.
 * @param drawable Asset resuelto o null para ausencia.
 * @param tint Color ARGB explícito; null utiliza el color actual del texto.
 */
internal data class CompoundDrawableRender(val managed: Boolean, val drawable: Drawable?, val tint: Int?)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CompoundDrawableRenderer aplica assets, celdas y tintes a los iconos relativos de un widget textual.
 *
 * Justificación:
 * Reutiliza la escritura nativa sin incluir decisiones de marca o callbacks. El componente entrega
 * el tamaño mínimo ya elegido; la política de disposición aporta un desplazamiento opcional.
 *
 * Uso:
 * Interno del DS. Llamar render con drawables resueltos y color al actualizar el estado visual.
 * managed indica si el DS controla el slot; al liberarlo se restaura su drawable anterior.
 * El host usa los setters y tokens del componente, no este renderer.
 *
 * Objetivos:
 * - Mantener el asset centrado sin escalarlo para alcanzar el mínimo táctil.
 * - Separar geometría y apariencia de detección de gestos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param endOffset Desplazamiento de dibujo del icono final; null conserva el anclaje Android.
 */
internal class CompoundDrawableRenderer(private val endOffset: (() -> Int)? = null) {
    private data class Slot(var managed: Boolean = false, var previous: Drawable? = null, var tint: Int? = null)
    /** Conserva las dimensiones del asset para retirar la celda al eliminar su última acción. */
    private class Cell(val asset: Drawable, val assetWidth: Int, val assetHeight: Int, horizontal: Int, vertical: Int) :
        InsetDrawable(asset, horizontal / 2, vertical / 2, horizontal - horizontal / 2, vertical - vertical / 2)
    private val start = Slot()
    private val end = Slot()
    private var previousPadding: Int? = null

    /**
     * Aplica los dos slots conservando drawables superior/inferior y el padding previo no gestionado.
     * @param view Widget receptor.
     * @param iconStart Asset inicial y propiedad de su slot.
     * @param iconEnd Asset final y propiedad de su slot.
     * @param paddingPx Separación resuelta; null restaura la separación anterior.
     * @param startMinimumSizePx Mínimo de la celda inicial, cero para el tamaño del asset.
     * @param endMinimumSizePx Mínimo de la celda final, cero para el tamaño del asset.
     */
    fun render(
        view: TextView,
        iconStart: CompoundDrawableRender,
        iconEnd: CompoundDrawableRender,
        paddingPx: Int?,
        startMinimumSizePx: Int = 0,
        endMinimumSizePx: Int = 0,
    ) {
        val drawables = view.compoundDrawablesRelative
        /** Alterna la propiedad del slot y conserva el drawable nativo que debe restaurarse. */
        fun apply(slot: Slot, requested: CompoundDrawableRender, current: Drawable?): Drawable? {
            if (requested.managed) {
                if (!slot.managed) slot.previous = current
                slot.managed = true
                slot.tint = requested.tint
                return requested.drawable
            }
            if (!slot.managed) return current
            slot.managed = false
            slot.tint = null
            return slot.previous
        }
        val nextStart = cell(apply(start, iconStart, asset(drawables[0])), startMinimumSizePx)
        val endCell = cell(apply(end, iconEnd, asset(drawables[2])), endMinimumSizePx)
        val nextEnd = if (endCell != null && endOffset != null && endCell !is OffsetCompoundDrawable)
            OffsetCompoundDrawable(endCell, endOffset) else endCell
        if (nextStart !== drawables[0] || nextEnd !== drawables[2]) {
            view.setCompoundDrawablesRelative(nextStart, drawables[1], nextEnd, drawables[3])
        }
        if (paddingPx != null) {
            if (previousPadding == null) previousPadding = view.compoundDrawablePadding
            view.compoundDrawablePadding = paddingPx
        } else previousPadding?.let { view.compoundDrawablePadding = it; previousPadding = null }
    }

    /** Reserva una celda sin escalar el asset; Android utiliza sus límites para medir y centrar el texto. */
    private fun cell(drawable: Drawable?, minimum: Int): Drawable? {
        if (drawable == null || minimum <= 0) return drawable
        val width = drawable.bounds.width()
        val height = drawable.bounds.height()
        val horizontal = (minimum - width).coerceAtLeast(0)
        val vertical = (minimum - height).coerceAtLeast(0)
        if (horizontal == 0 && vertical == 0) return drawable
        return Cell(drawable, width, height, horizontal, vertical).apply {
            setBounds(0, 0, width + horizontal, height + vertical)
        }
    }

    /** Retira solo decoradores de esta capacidad; conserva los drawables nativos del consumidor. */
    private fun asset(drawable: Drawable?): Drawable? = when (drawable) {
        is OffsetCompoundDrawable -> asset(drawable.drawable)
        is Cell -> drawable.asset.apply { setBounds(0, 0, drawable.assetWidth, drawable.assetHeight) }
        else -> drawable
    }

    /** Aplica el color resuelto también al asset alojado dentro de una celda interactiva. */
    fun color(view: TextView) {
        val drawables = view.compoundDrawablesRelative
        if (start.managed) drawables[0]?.setTint(start.tint ?: view.currentTextColor)
        if (end.managed) drawables[2]?.setTint(end.tint ?: view.currentTextColor)
    }
}
