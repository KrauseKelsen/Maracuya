package cruxui.android.lib.wrappers.core.tokens

import android.graphics.Typeface
import androidx.annotation.FontRes
import cruxui.android.lib.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextFont: Describe una fuente de valores nativos sin resolver recursos ni acceder a vistas.
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
sealed interface TextFont {
    data class Resource(@FontRes val id: Int) : TextFont
    data class Value(val typeface: Typeface) : TextFont
    data class Family(val family: FontFamilyToken) : TextFont
    data class System(val name: String) : TextFont
}
