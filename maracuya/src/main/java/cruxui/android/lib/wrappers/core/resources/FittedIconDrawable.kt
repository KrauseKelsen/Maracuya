package cruxui.android.lib.wrappers.core.resources

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * FittedIconDrawable centra un asset conservando su proporción dentro de los límites recibidos.
 *
 * Justificación:
 * Los insets fijos dejan de representar el ajuste al cambiar los límites en Android o el editor XML.
 * AndroidIconLoader utiliza este decorador para recalcular el ajuste y anunciar el tamaño del token.
 *
 * Uso:
 * Interno del DS. El cargador entrega este drawable a los renderers; el host continúa usando iconos
 * Library y atributos XML. DrawableWrapper delega tinte, estado y dibujo al asset original.
 *
 * Objetivos:
 * - Mantener el ajuste equivalente a ContentScale.Fit al redimensionar.
 * - Separar la proporción del asset del tamaño de su celda interactiva.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param asset Drawable independiente cargado por AndroidIconLoader.
 * @param size Tamaño intrínseco cuadrado del token, en píxeles.
 */
internal class FittedIconDrawable(asset: Drawable, private val size: Int) : DrawableWrapper(asset) {
    private val assetWidth = asset.intrinsicWidth.coerceAtLeast(1)
    private val assetHeight = asset.intrinsicHeight.coerceAtLeast(1)

    /** Expone el ancho del token, no el ancho original del recurso más márgenes. */
    override fun getIntrinsicWidth(): Int = size

    /** Expone la misma caja cuadrada utilizada por Compose. */
    override fun getIntrinsicHeight(): Int = size

    /** Recalcula escala uniforme y centrado para cada cambio de límites, incluidos los del editor. */
    override fun onBoundsChange(bounds: Rect) {
        val scale = minOf(bounds.width().toFloat() / assetWidth, bounds.height().toFloat() / assetHeight)
        val width = (assetWidth * scale).roundToInt()
        val height = (assetHeight * scale).roundToInt()
        val left = bounds.left + (bounds.width() - width) / 2
        val top = bounds.top + (bounds.height() - height) / 2
        drawable?.setBounds(left, top, left + width, top + height)
    }
}
