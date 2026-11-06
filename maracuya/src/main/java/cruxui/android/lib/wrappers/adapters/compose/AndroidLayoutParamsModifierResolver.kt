package cruxui.android.lib.wrappers.adapters.compose

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AndroidLayoutParamsModifierResolver adapta las medidas del contenedor Android a Compose.
 *
 * Justificación:
 * MrcyXmlComposeView recibe medidas ya resueltas por el padre; este adaptador las traduce sin
 * interpretar atributos XML ni conocer componentes o temas.
 *
 * Uso:
 * Interno del DS. Importar fromLayoutParams y aplicarlo sobre el Modifier que se desea conservar.
 * El host configura layout_width y layout_height de su wrapper mediante el API Android habitual.
 *
 * Objetivos:
 * - Respetar wrap_content y el espacio asignado por Android.
 * - Conservar la cadena de modificadores recibida.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object AndroidLayoutParamsModifierResolver {

    /**
     * Usa los params genéricos de `ViewGroup`, no atributos XML ni clases de layouts.
     */
    fun Modifier.fromLayoutParams(params: ViewGroup.LayoutParams?): Modifier {
        if (params == null) return this

        return fillAxis(params.width, isWidth = true)
            .fillAxis(params.height, isWidth = false)
    }

    /** Respeta wrap_content y ocupa el espacio ya medido para el resto de tamaños. */
    private fun Modifier.fillAxis(size: Int, isWidth: Boolean): Modifier {
        if (size == ViewGroup.LayoutParams.WRAP_CONTENT) return this
        return if (isWidth) fillMaxWidth() else fillMaxHeight()
    }
}
