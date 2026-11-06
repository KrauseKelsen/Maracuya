package cruxui.android.lib.wrappers.capabilities.text

import android.widget.TextView
import androidx.core.widget.TextViewCompat

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextStyleRenderer aplica las métricas de NativeTextStyle a un widget Android con texto.
 *
 * Justificación:
 * Reutiliza el render tipográfico en TextView, Button y EditText sin decidir estados o interacciones.
 * Delega la colocación horizontal a TextAlignmentRenderer.
 *
 * Uso:
 * Interno del DS. Cada componente conserva una instancia y protege sus setters mientras llama render,
 * para evitar convertir los valores resueltos en nuevos overrides. El host usa el API del componente.
 *
 * Objetivos:
 * - Aplicar fuente, tamaño, tracking y altura de línea.
 * - Conservar la colocación vertical del widget.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal class NativeTextStyleRenderer {
    private val alignment = TextAlignmentRenderer()
    /** Aplica un modelo resuelto y conserva la colocación vertical original. */
    fun render(view: TextView, style: NativeTextStyle) {
        view.typeface = style.typeface
        view.setTextSize(style.textSize.unit, style.textSize.value)
        view.letterSpacing = style.letterSpacingEm
        alignment.render(view, style.alignment)
        if (style.lineSpacing != null) view.setLineSpacing(style.lineSpacing.extraPx, style.lineSpacing.multiplier)
        else {
            view.setLineSpacing(0f, 1f)
            TextViewCompat.setLineHeight(view, style.lineHeight.unit, style.lineHeight.value)
        }
    }
}
