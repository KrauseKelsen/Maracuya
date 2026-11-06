package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ColorCatalogSource genera consultas sobre LibraryColorTokens sin crear una segunda paleta.
 *
 * Justificación:
 * Colores ya es una colección configurable; necesita una proyección de sus propiedades, no otro objeto
 * Corporate. ThemeCatalogReader y los resolutores usan la misma consulta generada.
 *
 * Uso:
 * Interno del build. Recibe todos los nombres del esquema y genera extensiones sin reflexión en ejecución.
 * El host importa theme.catalog.get para acceder mediante LibraryColorRole; el acceso directo no cambia.
 *
 * Objetivos:
 * - Conservar valores y copy de Library y eliminar el when manual de colores admitidos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object ColorCatalogSource {
    /** Proyecta propiedades efectivas, de forma que las sustituciones del tema también se exportan. */
    fun render(names: List<String>): String = buildString {
        appendLine("package cruxui.android.lib.theme.catalog")
        appendLine("import cruxui.android.lib.atoms.LibraryColorTokens")
        appendLine("import cruxui.android.lib.tokens.base.ColorToken")
        appendLine(schemaDocumentation(
            "Consulta un color de LibraryColorTokens por su identidad semántica.",
            "Conserva la colección recibida y sus sustituciones. Se comparte entre resolutores y token-exporter.",
            "API del host: importar theme.catalog.get y usar library.colors[LibraryColorRole.BrandPrimary]. En Compose obtener library de LibraryTheme.current; en una receta usar el snapshot recibido por register. En XML usar android:textColor=\"?attr/libraryColorBrandPrimary\" con ThemeOverlay.Maracuya.",
            "Resolver todas las propiedades Library sin reflection ni otra paleta.",
        ))
        appendLine("operator fun LibraryColorTokens.get(role: LibraryColorRole): ColorToken = get(role.catalogName)")
        appendLine("/** Consulta por nombre exacto; un nombre desconocido se rechaza explícitamente. */")
        appendLine("operator fun LibraryColorTokens.get(name: String): ColorToken = when (name) {")
        names.forEach { appendLine("    \"$it\" -> $it") }
        appendLine("    else -> throw IllegalArgumentException(\"Color no registrado: \$name\")")
        appendLine("}")
        appendLine("/** Recorre todos los colores efectivos del tema en orden estable; no almacena valores alternativos. */")
        appendLine("fun LibraryColorTokens.asMap(): Map<String, ColorToken> = linkedMapOf(")
        names.forEach { appendLine("    \"$it\" to $it,") }
        appendLine(")")
    }
}
