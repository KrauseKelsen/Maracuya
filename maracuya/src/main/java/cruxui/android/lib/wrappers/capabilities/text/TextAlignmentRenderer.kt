package cruxui.android.lib.wrappers.capabilities.text

import android.annotation.SuppressLint
import android.graphics.text.LineBreaker
import android.view.Gravity
import android.view.View
import android.widget.TextView
import cruxui.android.lib.tokens.fonts.TextAlignTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextAlignmentRender reúne la alineación semántica y las opciones nativas explícitas.
 *
 * Justificación:
 * Permite que TextAlignmentRenderer aplique prioridades sin conocer variantes ni temas.
 *
 * Uso:
 * Valor interno del DS; el componente lo construye después de resolver XML y overrides.
 * El host configura la alineación mediante el API público del componente.
 *
 * Objetivos:
 * - Separar la resolución de valores de su aplicación sobre la View.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param token Alineación semántica del DS.
 * @param textAlignment Alineación Android explícita o null para resolverla desde el token.
 * @param gravity Gravedad Android explícita o null para conservar la base vertical.
 * @param justificationMode Justificación explícita o null para resolverla desde el token.
 */
internal data class TextAlignmentRender(
    val token: Int,
    val textAlignment: Int?,
    val gravity: Int?,
    val justificationMode: Int?,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextAlignmentRenderer adapta la alineación del DS a las propiedades de un TextView Android.
 *
 * Justificación:
 * Conserva la colocación vertical y restaura la base nativa al retirar la alineación del DS.
 * TextAlignmentRender contiene los valores resueltos; el renderer solo aplica la presentación.
 *
 * Uso:
 * Capacidad interna; cada widget conserva su propia instancia. El host usa los setters de su componente.
 *
 * Objetivos:
 * - Respetar las propiedades Android explícitas.
 * - Mantener compatibilidad con la versión mínima de Android del DS.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal class TextAlignmentRenderer {
    private var baseline: TextAlignmentRender? = null

    /**
     * Aplica la alineación efectiva y conserva la configuración nativa de la primera llamada.
     * JUSTIFICATION_MODE_INTER_WORD se compila como el entero 1, admitido por TextView desde API 26;
     * la anotación del SDK actual exige el nombre de LineBreaker, introducido en API 29.
     * Usar esta constante no carga LineBreaker en dispositivos anteriores.
     */
    @SuppressLint("InlinedApi")
    fun render(view: TextView, values: TextAlignmentRender) {
        val original = baseline ?: TextAlignmentRender(TextAlignTokens.Unspecified,
            view.textAlignment, view.gravity, view.justificationMode).also { baseline = it }
        val horizontal = when (values.token) {
            TextAlignTokens.Left -> Gravity.LEFT
            TextAlignTokens.Right -> Gravity.RIGHT
            TextAlignTokens.Center -> Gravity.CENTER_HORIZONTAL
            TextAlignTokens.Start, TextAlignTokens.Justify -> Gravity.START
            TextAlignTokens.End -> Gravity.END
            else -> null
        }
        val gravity = values.gravity ?: original.gravity!!
        view.gravity = if (values.textAlignment == null && values.gravity == null && horizontal != null)
            (gravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK.inv()) or horizontal else gravity
        view.textAlignment = values.textAlignment ?: if (values.gravity != null) View.TEXT_ALIGNMENT_GRAVITY else when (values.token) {
            TextAlignTokens.Left, TextAlignTokens.Right -> View.TEXT_ALIGNMENT_GRAVITY
            TextAlignTokens.Center -> View.TEXT_ALIGNMENT_CENTER
            TextAlignTokens.Start, TextAlignTokens.Justify -> View.TEXT_ALIGNMENT_TEXT_START
            TextAlignTokens.End -> View.TEXT_ALIGNMENT_TEXT_END
            else -> original.textAlignment!!
        }
        view.justificationMode = values.justificationMode ?: if (values.token == TextAlignTokens.Justify)
            LineBreaker.JUSTIFICATION_MODE_INTER_WORD else original.justificationMode!!
    }
}
