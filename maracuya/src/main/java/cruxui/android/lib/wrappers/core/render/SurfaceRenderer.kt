package cruxui.android.lib.wrappers.core.render

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * SurfaceRenderer: Aplica fondo y foreground a cualquier View.
 *
 * Justificación:
 * No elige colores ni estados: solo evita escrituras redundantes de superficies ya resueltas.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object SurfaceRenderer {
    /** Actualiza ambas superficies conservando drawables equivalentes. */
    fun render(view: View, background: Drawable?, foreground: Drawable?) {
        if (!same(view.background, background)) view.background = background
        if (!same(view.foreground, foreground)) view.foreground = foreground
    }
    /** Compara identidad y color sólido sin equiparar drawables con estado diferente. */
    private fun same(first: Drawable?, second: Drawable?) = first === second ||
        (first is ColorDrawable && second is ColorDrawable && first.color == second.color)
}
