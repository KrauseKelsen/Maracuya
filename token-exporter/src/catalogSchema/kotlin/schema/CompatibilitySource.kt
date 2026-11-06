package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CompatibilitySource conserva nombres públicos anteriores como aliases de las colecciones Library.
 *
 * Justificación:
 * Permite retirar la responsabilidad de semantics y tokens.icons sin obligar a migrar todos los imports
 * Kotlin del host. No crea colecciones alternativas ni vuelve a mapear cada icono.
 *
 * Uso:
 * Interno del build. Genera compatibilidad de fuentes Kotlin; no promete compatibilidad binaria ni Java.
 * Los consumidores deben recompilarse. El código nuevo utiliza theme.collections y theme.catalog.
 *
 * Objetivos:
 * - Facilitar la migración sin mantener dos implementaciones.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object CompatibilitySource {
    /** Genera un alias documentado; el tipo real y todas sus propiedades permanecen en Library. */
    fun alias(packageName: String, old: String, target: String): String = buildString {
        appendLine("package $packageName")
        appendLine(schemaDocumentation(
            "$old es un alias Kotlin de compatibilidad para $target.",
            "No añade valores ni una capa de ejecución; conserva imports históricos tras retirar las colecciones duplicadas.",
            "El host existente puede conservar $old al recompilar. En código nuevo usar $target. No editar el alias generado ni añadir lógica en semantics.",
            "Mantener compatibilidad de fuentes mientras Library es la única fuente de datos.",
        ))
        appendLine("typealias $old = $target")
    }

    /** Conserva el adaptador histórico como acceso directo a la categoría, sin construir otra instancia. */
    fun iconGroup(group: IconGroupSchema): String = alias(
        "cruxui.android.lib.tokens.icons", group.legacyType, group.type,
    ) + "\n/** Devuelve la categoría Library original; no copia ni convierte iconos. */\n" +
        "fun get${group.legacyType}(atoms: cruxui.android.lib.atoms.LibraryIconTokens): ${group.legacyType} = atoms.${group.property}\n"
}
