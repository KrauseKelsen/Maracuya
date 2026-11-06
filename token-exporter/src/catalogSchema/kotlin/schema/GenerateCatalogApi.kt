package schema

import java.io.File

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Compone la generación de APIs de colores, tipografías e iconos antes de resolver temas.
 *
 * Justificación:
 * Separa descubrimiento de estructura, escritores por familia y compatibilidad. Esta primera fase solo
 * depende de Library y tokens puros; evita el ciclo entre la API del tema y el exportador de recursos.
 *
 * Uso:
 * Gradle ejecuta generateCatalogApi antes de compilar token-exporter. Los dos directorios pertenecen
 * exclusivamente a esta tarea: se limpian para que eliminar un grupo también elimine sus clases antiguas.
 * CatalogApiWriter consume un manifiesto de fuentes para entregar a Android exactamente la misma API.
 * El host no ejecuta esta función ni edita sus salidas.
 *
 * Objetivos:
 * - Regenerar todas las proyecciones a partir de una declaración por token.
 * - Mantener independientes los recursos de familias y las adaptaciones de componentes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param args Directorios de fuentes Kotlin y recursos que empaquetan esas mismas fuentes.
 */
fun main(args: Array<String>) {
    require(args.size == 2)
    val typography = TypographySchema.names()
    val colors = LibraryCatalogSchema.colors()
    val groups = LibraryCatalogSchema.icons()
    val sources = linkedMapOf<String, String>()
    val root = "cruxui/android/lib"
    sources["$root/theme/collections/LibraryTypography.kt"] = LibraryTypographySource.render(typography)
    sources["$root/theme/collections/LibraryIcons.kt"] = LibraryIconsSource.render(groups)
    sources["$root/theme/catalog/LibraryColorCatalog.kt"] = ColorCatalogSource.render(colors)
    sources["$root/theme/catalog/LibraryTypographyRole.kt"] = TypographyRolesSource.render(typography)
    sources["$root/theme/catalog/LibraryColorRole.kt"] = RolesSource.render("LibraryColorRole", RolesSource.entries(
        colors, listOf("Foreground" to "fgDefault", "Muted" to "fgMuted", "Disabled" to "fgSubtle",
            "Error" to "fgError", "Primary" to "brandPrimary")),
        "API del host: library.colors[LibraryColorRole.BrandPrimary], importando theme.catalog.get. Para un override nativo usar TextColor.Role(LibraryColorRole.BrandPrimary). En XML: android:textColor=\"?attr/libraryColorBrandPrimary\" con el overlay de marca.")
    sources["$root/theme/catalog/LibraryIconRole.kt"] = RolesSource.render("LibraryIconRole", groups.flatMap { group ->
        group.icons.map { (group.name.replaceFirstChar(Char::uppercaseChar) + it.replaceFirstChar(Char::uppercaseChar)) to "${group.name}.$it" }
    }, "API del host: library.icons[LibraryIconRole.AlertsInfo] o ViewIcon.Role(LibraryIconRole.AlertsInfo). En XML: android:drawableStart=\"?attr/libraryIconAlertsInfo\" con el overlay de marca.")
    for ((old, new) in listOf("CorporateTypography" to "LibraryTypography", "CorporateIcons" to "LibraryIcons")) {
        sources["$root/semantics/$old.kt"] = CompatibilitySource.alias(
            "cruxui.android.lib.semantics", old, "cruxui.android.lib.theme.collections.$new")
    }
    for ((old, new) in listOf("TextTypographyRole" to "LibraryTypographyRole", "TextColorRole" to "LibraryColorRole")) {
        sources["$root/wrappers/core/tokens/$old.kt"] = CompatibilitySource.alias(
            "cruxui.android.lib.wrappers.core.tokens", old, "cruxui.android.lib.theme.catalog.$new")
    }
    groups.filter { it.legacyType.isNotEmpty() }.forEach {
        sources["$root/tokens/icons/${it.legacyType}.kt"] = CompatibilitySource.iconGroup(it)
    }
    val kotlinRoot = File(args[0])
    val resourceRoot = File(args[1])
    kotlinRoot.deleteRecursively()
    resourceRoot.deleteRecursively()
    sources.forEach { (path, source) ->
        write(kotlinRoot, path, source)
        write(resourceRoot, "catalog-api/$path", source)
    }
    write(resourceRoot, "catalog-api/manifest.txt", sources.keys.joinToString("\n"))
}

/** Guarda una salida administrada por la tarea creando sus directorios contenedores. */
private fun write(root: File, path: String, content: String) {
    File(root, path).apply { parentFile.mkdirs(); writeText(content) }
}
