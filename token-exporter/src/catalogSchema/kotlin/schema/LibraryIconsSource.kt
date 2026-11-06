package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryIconsSource genera la colección temática de iconos como referencias a las categorías Library.
 *
 * Justificación:
 * Sustituye los objetos Corporate y las copias manuales de cada icono por un contenedor inmutable mínimo.
 * Recibe el esquema descubierto, sin decidir iconos, colores, tamaños ni políticas de componentes.
 *
 * Uso:
 * Interno del build. El host recibe LibraryIcons; las categorías mantienen los tipos originales de Library.
 * fromAtoms conserva la identidad de los grupos, incluso después de personalizarlos con copy.
 *
 * Objetivos:
 * - Generar el acceso completo conservando la API general/alerts/etc. y sin duplicar catálogos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object LibraryIconsSource {
    /** Produce el contenedor de grupos y las consultas directas, sin reflexión en la app. */
    fun render(groups: List<IconGroupSchema>): String = buildString {
        appendLine("package cruxui.android.lib.theme.collections")
        appendLine("import cruxui.android.lib.atoms.LibraryIconTokens")
        appendLine("import cruxui.android.lib.tokens.base.IconToken")
        appendLine("import cruxui.android.lib.theme.catalog.LibraryIconRole")
        appendLine(schemaDocumentation(
            "LibraryIcons agrupa las categorías Library del tema bajo sus nombres semánticos públicos.",
            "Conserva referencias a los grupos originales, sin convertir ni copiar cada IconToken. LibraryThemeSet, Compose, recetas y token-exporter comparten esta colección.",
            "API pública: LibraryTheme.current.icons.alerts.info en Compose; library.icons.alerts.info en register. Personalizar con icons.copy(alerts = icons.alerts.copy(info = otroToken)). También admite icons[LibraryIconRole.AlertsInfo]. En XML usar android:drawableStart=\"?attr/libraryIconAlertsInfo\" con el overlay de marca. Agregar un IconToken al grupo Library y compilar genera automáticamente su API y atributo; nunca editar este archivo.",
            "Compartir los iconos del tema entre plataformas y eliminar listas por componente.",
        ))
        appendLine("data class LibraryIcons(")
        groups.forEach {
            appendLine("    /** Referencia a la categoría ${it.name} del tema; no copia sus iconos. */")
            appendLine("    val ${it.name}: ${it.type} = LibraryIconTokens.Default.${it.property},")
        }
        appendLine(") {")
        appendLine("    /** Resuelve un rol contra los iconos efectivos del tema. */")
        appendLine("    operator fun get(role: LibraryIconRole): IconToken = get(role.catalogName)")
        appendLine("    /** Consulta una ruta grupo.icono exacta; rechaza rutas ajenas al catálogo. */")
        appendLine("    operator fun get(path: String): IconToken = when (path) {")
        groups.forEach { group -> group.icons.forEach { name ->
            appendLine("        \"${group.name}.$name\" -> ${group.name}.$name")
        } }
        appendLine("        else -> throw IllegalArgumentException(\"Icono no registrado: \$path\")")
        appendLine("    }")
        appendLine("    /** Recorre todas las categorías y sus tokens efectivos; utilizado por el exportador. */")
        appendLine("    fun asMap(): Map<String, Map<String, IconToken>> = linkedMapOf(")
        groups.sortedBy { it.name }.forEach { group ->
            appendLine("        \"${group.name}\" to linkedMapOf(")
            group.icons.forEach { appendLine("            \"$it\" to ${group.name}.$it,") }
            appendLine("        ),")
        }
        appendLine("    )")
        appendLine("    companion object {")
        appendLine("        /** Agrupa las categorías ya construidas por Library, conservando su identidad y personalizaciones. */")
        appendLine("        fun fromAtoms(atoms: LibraryIconTokens): LibraryIcons = LibraryIcons(")
        groups.forEach { appendLine("            ${it.name} = atoms.${it.property},") }
        appendLine("        )")
        appendLine("    }")
        appendLine("}")
    }
}
