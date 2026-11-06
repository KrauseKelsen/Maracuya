package cruxui.android.lib.wrappers.capabilities.compounddrawables

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import androidx.core.graphics.withTranslation

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * OffsetCompoundDrawable desplaza el dibujo de un icono sin alterar sus dimensiones de medición.
 *
 * Justificación:
 * Permite que una política de layout coloque el icono junto al texto conservando el renderer
 * nativo. CompoundDrawableGeometry consume el mismo desplazamiento para localizar sus acciones.
 *
 * Uso:
 * Interno del DS; CompoundDrawableRenderer lo compone alrededor de la celda final. El host usa
 * tokens o drawables normales. No conoce colores de marca, listeners ni componentes concretos.
 *
 * Objetivos:
 * - Mantener dibujo y área táctil sincronizados.
 * - Preservar delegación de tintes, estados y callbacks al drawable original.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param drawable Celda o asset que debe dibujarse.
 * @param offset Proveedor del desplazamiento horizontal actual en píxeles.
 * @param initialBounds Límites preservados antes de que DrawableWrapper adopte el asset.
 */
internal class OffsetCompoundDrawable(
    drawable: Drawable,
    private val offset: () -> Int,
    initialBounds: Rect = Rect(drawable.bounds),
) : DrawableWrapper(drawable) {
    /** Desplazamiento consultado también por la geometría de interacción. */
    val offsetX: Int get() = offset()

    init { bounds = initialBounds }

    /** Traslada únicamente el icono y restaura el canvas del widget. */
    override fun draw(canvas: Canvas) {
        canvas.withTranslation(x = offsetX.toFloat()) { super.draw(this) }
    }
}
