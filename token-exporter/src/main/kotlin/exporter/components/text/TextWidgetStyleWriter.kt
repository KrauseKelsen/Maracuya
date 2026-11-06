package exporter.components.text

import exporter.android.appendParagraphAlignment
import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextWidgetStyleWriter: Adaptación opcional de tipografía a widgets Android con texto; no participa en los tokens del DS.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object TextWidgetStyleWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val values = StringBuilder("<resources>\n")
        for (theme in catalog.themes) {
            val styleName = styleName(theme.name)
            for ((name, token) in theme.light.typography) {
                values.appendLine("    <style name=\"Widget.$styleName.Typography.${upper(name)}\" parent=\"\">")
                values.appendLine("        <item name=\"android:textAppearance\">?attr/${typeAttr(name)}</item>")
                values.appendLine("        <item name=\"android:lineHeight\">?attr/libraryLineHeight${upper(name)}</item>")
                values.appendLine("        <item name=\"android:includeFontPadding\">false</item>")
                values.appendParagraphAlignment(token)
                values.appendLine("    </style>")
            }
        }
        values.appendLine("</resources>")
        output.resource("values/library_text_widgets.xml", values.toString())
    }
}
