package cruxui.android.lib.wrappers.core.tokens

import android.util.TypedValue

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextDimension: Describe una fuente de valores nativos sin resolver recursos ni acceder a vistas.
 *
 * Justificación:
 * Los modelos conservan su API pública. La conversión se delega a core.resources; los nombres Text describen capacidades de texto, no dependencia del componente DS TextView.
 *
 * Uso:
 * API pública para los overrides nativos del host; cada componente admite las capacidades que expone.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
data class TextDimension(val value: Float, val unit: Int = TypedValue.COMPLEX_UNIT_SP) {
    init {
        require(value.isFinite() && value >= 0) { "Text dimensions must be finite and non-negative" }
        require(unit in TypedValue.COMPLEX_UNIT_PX..TypedValue.COMPLEX_UNIT_MM)
    }

}
