package cruxui.android.lib.wrappers.core.tokens

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.tokens.base.ColorToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextColor: Describe una fuente de valores nativos sin resolver recursos ni acceder a vistas.
 *
 * Justificación:
 * Los modelos conservan su API pública. La conversión se delega a core.resources; los nombres Text describen capacidades de texto, no dependencia del componente DS TextView.
 *
 * Uso:
 * API pública para los overrides nativos del host; cada componente admite las capacidades que expone.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 * `view.setColorRole(LibraryColorRole.InfoText)` selecciona cualquier color del tema actual.
 * `TextColorRole` conserva el nombre anterior como alias del mismo enum generado; Primary equivale
 * a BrandPrimary y los cinco nombres históricos continúan disponibles. Para una selección propia usar
 * `TextColor.Semantic { it.infoText }`; Token fija un valor y States permite un selector Android.
 * En XML usar `android:textColor="?attr/libraryColorInfoText"` dentro del overlay de marca.
 * Agregar un ColorToken a Library y compilar incorpora automáticamente su rol y atributo.
 *
 * Objetivos:
 * - Conservar fuentes de color tipadas sin dependencias de componentes concretos.
 * - Resolver roles contra la paleta vigente en lugar de congelar colores al registrar una receta.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */

sealed interface TextColor {
    /** Color ARGB fijo. */
    data class Value(@ColorInt val argb: Int) : TextColor
    /** Color o selector Android del Context del componente. */
    data class Resource(@ColorRes val id: Int) : TextColor
    /** Rol del catálogo completo; consulta el tema al resolver el componente. */
    data class Role(val role: TextColorRole) : TextColor
    /** Colores para los estados nativos de la View. */
    data class States(val colors: ColorStateList) : TextColor
    /** Token fijo seleccionado explícitamente. */
    data class Token(val token: ColorToken) : TextColor
    /** Selección diferida sobre la paleta Library vigente. */
    data class Semantic(val select: (LibraryColorTokens) -> ColorToken) : TextColor
}
