package cruxui.android.lib.wrappers.capabilities.text

import android.graphics.RectF
import android.widget.TextView

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextTouchTarget localiza el bloque textual dentro de una View con espacios e iconos.
 *
 * Justificación:
 * Un listener nativo actúa sobre toda la View; esta capacidad permite restringir el inicio del
 * gesto al texto, como un nodo Text en Compose, sin reemplazar la selección o los gestos nativos.
 *
 * Uso:
 * Interno del DS. Consultar contains al iniciar el gesto, después de despachar acciones de iconos.
 * El componente aporta la medida mínima; esta clase no conoce tokens, callbacks ni variantes.
 *
 * Objetivos:
 * - Evitar activaciones del texto en espacio libre del contenedor.
 * - Respetar alineación, líneas, fuente y desplazamiento del widget.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param view Widget que aporta el layout textual.
 * @param minimumSizeDp Mínimo táctil aplicado alrededor del bloque de texto.
 */
internal class NativeTextTouchTarget(private val view: TextView, private val minimumSizeDp: Float) {
    /** Comprueba coordenadas locales; antes de disponer del layout conserva el despacho nativo. */
    fun contains(x: Float, y: Float): Boolean {
        val layout = view.layout ?: return true
        val left = (0 until layout.lineCount).minOfOrNull(layout::getLineLeft) ?: 0f
        val right = (0 until layout.lineCount).maxOfOrNull(layout::getLineRight) ?: 0f
        val originX = view.compoundPaddingLeft - view.scrollX
        val top = view.baseline - layout.getLineBaseline(0) - view.scrollY
        val target = RectF(originX + left, top.toFloat(), originX + right, (top + layout.height).toFloat())
        val minimum = minimumSizeDp * view.resources.displayMetrics.density
        target.inset(-((minimum - target.width()) / 2).coerceAtLeast(0f),
            -((minimum - target.height()) / 2).coerceAtLeast(0f))
        return target.contains(x, y)
    }
}
