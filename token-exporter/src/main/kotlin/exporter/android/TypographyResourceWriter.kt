package exporter.android

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TypographyResourceWriter: Proyecta estilos semánticos a TextAppearance y dimensiones Android.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object TypographyResourceWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val values = StringBuilder("<resources>\n")
        for (theme in catalog.themes) {
            val id = snake(theme.name)
            val styleName = styleName(theme.name)
            val library = theme.light
            val typeTokens = library.typography
            for ((name, token) in typeTokens) {
                val prefix = "library_${id}_${snake(name)}"
                values.appendLine("    <dimen name=\"${prefix}_size\">${token.fontSize}sp</dimen>")
                values.appendLine("    <dimen name=\"${prefix}_line_height\">${token.lineHeight}sp</dimen>")
                values.appendLine("    <style name=\"TextAppearance.$styleName.${upper(name)}\" parent=\"TextAppearance.AppCompat\">")
                values.appendLine("        <item name=\"android:fontFamily\">@font/library_${snake(library.fontFamily.name)}</item>")
                values.appendLine("        <item name=\"fontFamily\">@font/library_${snake(library.fontFamily.name)}</item>")
                values.appendLine("        <item name=\"android:textSize\">@dimen/${prefix}_size</item>")
                values.appendLine("        <item name=\"android:lineHeight\">@dimen/${prefix}_line_height</item>")
                values.appendLine("        <item name=\"android:textFontWeight\">${token.weight}</item>")
                values.appendLine("        <item name=\"android:textStyle\">${if (token.italic) "italic" else "normal"}</item>")
                values.appendLine("        <item name=\"android:letterSpacing\">${token.letterSpacing / token.fontSize}</item>")
                values.appendParagraphAlignment(token)
                values.appendLine("    </style>")
            }
        }
        values.appendLine("</resources>")
        output.resource("values/library_typography.xml", values.toString())
    }
}
