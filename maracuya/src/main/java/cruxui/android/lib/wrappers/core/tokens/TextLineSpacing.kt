package cruxui.android.lib.wrappers.core.tokens


/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextLineSpacing: Describe una fuente de valores nativos sin resolver recursos ni acceder a vistas.
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
/** Espaciado nativo: píxeles adicionales con signo y multiplicador de altura de fuente. */
data class TextLineSpacing(val extraPx: Float = 0f, val multiplier: Float = 1f) {
    init {
        require(extraPx.isFinite() && multiplier.isFinite() && multiplier >= 0)
    }
}
