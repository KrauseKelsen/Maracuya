package cruxui.android.lib.wrappers.components.textviews.render

import cruxui.android.lib.wrappers.core.render.SurfaceRenderer
import android.widget.TextView
import cruxui.android.lib.core.components.textviews.tokens.TextViewLayoutTokens
import kotlin.math.roundToInt
import cruxui.android.lib.wrappers.components.textviews.tokensrender.TextViewTokensRender
import cruxui.android.lib.wrappers.components.textviews.tokensrender.TextViewColorRender
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariantResolver
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableRenderer
import cruxui.android.lib.wrappers.capabilities.text.NativeTextStyleRenderer

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewRenderer` aplica los modelos de render preparados a una TextView Android.
 *
 * Justificación:
 * Mantiene las escrituras nativas separadas de las decisiones sobre marca, receta, variante y estado.
 *
 * Se comunica con:
 * TextView lo invoca con los modelos del resolutor; delega párrafos a TextAlignmentRenderer, iconos a
 * CompoundDrawableRenderer y subrayado a TextViewVariantResolver.
 *
 * Uso:
 * Clase interna del DS. El host no la instancia; utiliza la View pública.
 * Debe ejecutarse dentro del bloque de aplicación protegido de la View, para que sus llamadas a setters no se
 * registren como personalizaciones del host.
 *
 * Objetivos:
 * - Aplicar resultados ya resueltos.
 * - Reutilizar renderizadores comunes.
 * - Evitar reemplazos innecesarios de superficies equivalentes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param endOffset Desplazamiento del icono final calculado por la capacidad de disposición.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal class TextViewRenderer(endOffset: () -> Int) {
    private val icons = CompoundDrawableRenderer(endOffset)
    private val textStyle = NativeTextStyleRenderer()

    /**
     * Aplica fuente, métricas, párrafo, decoración e iconos. Uso interno bajo la protección de escritura de la
     * View.
     *
     * @param view TextView receptora.
     * @param tokens Modelo nativo de render.
     * @param variant Variante usada para aplicar el bit de subrayado.
     * @param startInteractive Reserva la celda mínima para la acción inicial.
     * @param endInteractive Reserva la celda mínima para la acción final.
     */
    fun render(view: TextView, tokens: TextViewTokensRender, variant: TextViewVariant, startInteractive: Boolean, endInteractive: Boolean) {
        textStyle.render(view, tokens.textStyle)
        view.paintFlags = TextViewVariantResolver.paintFlags(view.paintFlags, variant)
        val minimum = (TextViewLayoutTokens.MinimumInteractiveIconSizeDp * view.resources.displayMetrics.density).roundToInt()
        icons.render(view, tokens.iconStart, tokens.iconEnd, tokens.iconPaddingPx,
            if (startInteractive) minimum else 0, if (endInteractive) minimum else 0)
    }

    /**
     * Actualiza el selector de texto, superficies y tintes de iconos.
     *
     * @param view TextView receptora.
     * @param tokens Colores y drawables ya resueltos. No selecciona estados ni temas.
     */
    fun color(view: TextView, tokens: TextViewColorRender) {
        view.setTextColor(tokens.colors)
        SurfaceRenderer.render(view, tokens.background, tokens.foreground)
        icons.color(view)
    }

}
