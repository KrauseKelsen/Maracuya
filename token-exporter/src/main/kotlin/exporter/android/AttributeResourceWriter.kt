package exporter.android

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AttributeResourceWriter: Declara los atributos semánticos de colores, tipografías, fuentes e iconos.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object AttributeResourceWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val colorNames = catalog.colorNames
        val typographyNames = catalog.typographyNames
        val iconGroups = catalog.iconGroups
        val attrs = buildString {
            appendLine("<resources>")
            for (name in colorNames) appendLine("    <attr name=\"${colorAttr(name)}\" format=\"color|reference\" />")
            for (name in typographyNames) {
                appendLine("    <attr name=\"${typeAttr(name)}\" format=\"reference\" />")
                appendLine("    <attr name=\"libraryLineHeight${upper(name)}\" format=\"dimension\" />")
            }
            appendLine("    <attr name=\"libraryFontPrimary\" format=\"reference\" />")
            for ((group, icons) in iconGroups) for (name in icons.keys) {
                appendLine("    <attr name=\"${iconAttr(group, name)}\" format=\"reference\" />")
                appendLine("    <attr name=\"${iconAttr(group, name)}Size\" format=\"dimension\" />")
                appendLine("    <attr name=\"${iconAttr(group, name)}Tint\" format=\"color|reference\" />")
            }
            appendLine("</resources>")
        }
        output.resource("values/library_attrs.xml", attrs)

    }
}
