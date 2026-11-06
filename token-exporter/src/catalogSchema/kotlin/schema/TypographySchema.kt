package schema

import cruxui.android.lib.atoms.LibraryTypographyTokens
import cruxui.android.lib.tokens.base.TypographyToken
import java.lang.reflect.Modifier

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TypographySchema descubre las propiedades tipográficas declaradas en LibraryTypographyTokens.
 *
 * Justificación:
 * Es el único descubrimiento de nombres necesario para generar la API semántica y los roles nativos.
 * Se ejecuta antes de compilar LibraryTypography, evitando dependencias circulares o reflexión en la app.
 *
 * Uso:
 * Herramienta interna de construcción. Agregar un val de tipo TypographyToken al objeto Library;
 * generateCatalogApi incorpora su nombre sin editar este lector. No analiza texto de código Kotlin.
 *
 * Objetivos:
 * - Mantener una única declaración por estilo.
 * - Conservar el orden del constructor histórico para consumidores existentes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object TypographySchema {
    // Compatibilidad del constructor y componentN publicados: no agregar aquí los estilos nuevos.
    private val legacyOrder = listOf(
        "headline48", "headline36", "headline32", "headline28", "headline24", "headline21", "headline18", "headline16",
        "subtitle1", "subtitle2", "subtitle3", "body1", "body2", "introduction", "caption", "legal", "buttons",
        "label", "placeholder", "bottomText", "headline24Medium", "headline21Bold", "headline21Medium",
        "body1Italic", "body2Italic", "captionItalic", "legalItalic", "legalBold",
    )

    /** Obtiene nombres de tokens del catálogo; los nuevos se añaden en orden determinista. */
    fun names(): List<String> = orderedNames(LibraryTypographyTokens::class.java.declaredMethods
        .filter { Modifier.isPublic(it.modifiers) && !it.isSynthetic && it.parameterCount == 0 &&
            it.returnType == TypographyToken::class.java && it.name.startsWith("get") }
        .map { it.name.removePrefix("get").replaceFirstChar(Char::lowercaseChar) })

    /** Valida identificadores y preserva posiciones históricas, sin mantener una lista de estilos admitidos. */
    fun orderedNames(discovered: List<String>): List<String> {
        require(discovered.isNotEmpty()) { "El catálogo tipográfico está vacío" }
        require(discovered.all { it.matches(Regex("[a-z][a-zA-Z0-9]*")) }) { "Nombre tipográfico no exportable" }
        require(discovered.distinct().size == discovered.size) { "Nombres tipográficos duplicados" }
        return legacyOrder.filter { it in discovered } + (discovered - legacyOrder.toSet()).sorted()
    }
}
