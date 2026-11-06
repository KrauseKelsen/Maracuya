package exporter.android

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ColorResourceWriter: Exporta las paletas claro/oscuro conservando los roles del catálogo.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object ColorResourceWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val values = StringBuilder("<resources>\n")
        for (theme in catalog.themes) for ((mode, palette) in listOf("light" to theme.light, "dark" to theme.dark)) {
            for ((name, color) in palette.colors) {
                values.appendLine("    <color name=\"library_${snake(theme.name)}_${mode}_${snake(name)}\">${hex(color)}</color>")
            }
        }
        values.appendLine("</resources>")
        output.resource("values/library_colors.xml", values.toString())
    }
}
