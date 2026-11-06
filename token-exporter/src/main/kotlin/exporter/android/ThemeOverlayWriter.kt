package exporter.android

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ThemeOverlayWriter: Enlaza atributos semánticos con recursos de cada marca y modo.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object ThemeOverlayWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val colorNames = catalog.colorNames
        val typographyNames = catalog.typographyNames
        val values = StringBuilder("<resources>\n")
        val night = StringBuilder("<resources>\n")
        for (theme in catalog.themes) {
            val id = snake(theme.name)
            val styleName = styleName(theme.name)
            for (dark in listOf(false, true)) {
                val mode = if (dark) "Dark" else "Light"
                val resolved = if (dark) theme.dark else theme.light
                values.appendLine("    <style name=\"ThemeOverlay.$styleName.$mode\" parent=\"\">")
                for (name in colorNames) values.appendLine("        <item name=\"${colorAttr(name)}\">@color/library_${id}_${mode.lowercase()}_${snake(name)}</item>")
                for (name in typographyNames) {
                    values.appendLine("        <item name=\"${typeAttr(name)}\">@style/TextAppearance.$styleName.${upper(name)}</item>")
                    values.appendLine("        <item name=\"libraryLineHeight${upper(name)}\">@dimen/library_${id}_${snake(name)}_line_height</item>")
                }
                values.appendLine("        <item name=\"libraryFontPrimary\">@font/library_${snake(resolved.fontFamily.name)}</item>")
                for ((group, icons) in resolved.icons) for ((name, asset) in icons) {
                    val token = asset
                    val resourceName = requireNotNull(token.resourceName) { "El icono $group.$name requiere resourceName" }
                    values.appendLine("        <item name=\"${iconAttr(group, name)}\">@drawable/$resourceName</item>")
                    values.appendLine("        <item name=\"${iconAttr(group, name)}Size\">${token.iconSize}dp</item>")
                    values.appendLine("        <item name=\"${iconAttr(group, name)}Tint\">${token.iconColor?.let(::hex) ?: "@null"}</item>")
                }
                values.appendLine("    </style>")
            }
            values.appendLine("    <style name=\"ThemeOverlay.$styleName\" parent=\"ThemeOverlay.$styleName.Light\" />")
            night.appendLine("    <style name=\"ThemeOverlay.$styleName\" parent=\"ThemeOverlay.$styleName.Dark\" />")
        }
        values.appendLine("</resources>")
        night.appendLine("</resources>")
        output.resource("values/library_tokens.xml", values.toString())
        output.resource("values-night/library_tokens.xml", night.toString())

    }
}
