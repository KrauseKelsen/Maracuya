package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import cruxui.android.lib.theme.FontResourceCatalog
import cruxui.android.lib.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AndroidFontLoader: Carga familias y selecciona pesos/cursivas del catálogo físico.
 *
 * Justificación:
 * No conoce colores, iconos ni componentes. AndroidTokenAdapter conserva la fachada pública.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object AndroidFontLoader {
    /** Carga la cara más cercana y sintetiza únicamente las diferencias solicitadas. */
    fun load(
        context: Context,
        family: FontFamilyToken,
        weight: Int = 400,
        italic: Boolean = false,
    ): Typeface {
        val face = FontResourceCatalog.closest(family, weight, italic)
        val loaded = requireNotNull(ResourcesCompat.getFont(context, face.resourceId)) {
            "The theme font could not be loaded: $family"
        }
        return if (face.weight == weight && face.italic == italic) loaded
        else Typeface.create(loaded, weight, italic)
    }

}
