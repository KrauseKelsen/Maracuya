package exporter

import cruxui.android.lib.theme.LibraryThemes
import exporter.android.*
import exporter.catalog.*
import exporter.components.text.TextWidgetStyleWriter
import exporter.kotlin.*
import exporter.output.DirectoryExportOutput
import exporter.validation.CatalogValidator
import java.io.File

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * main: Punto de composición de la herramienta de construcción.
 *
 * Justificación y uso:
 * Conecta lectura, validación y escritores independientes. Para otro componente, añadir solo su adaptación opcional; los recursos semánticos existentes se reutilizan.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
fun main(args: Array<String>) {
    require(args.size == 3) { "Se esperan directorios res generado, Kotlin generado y res fuente" }
    val catalog = ThemeCatalogReader.read(LibraryThemes.all)
    CatalogValidator.validate(catalog, File(args[2]))
    val output = DirectoryExportOutput(File(args[0]), File(args[1]))
    val writers: List<CatalogWriter> = listOf(
        AttributeResourceWriter, ColorResourceWriter, TypographyResourceWriter,
        FontResourceWriter, ThemeOverlayWriter, AssetBindingsWriter, XmlReferencesWriter, CatalogApiWriter,
        TextWidgetStyleWriter,
    )
    writers.forEach { it.write(catalog, output) }
    println("Exportados ${catalog.themes.size} temas, ${catalog.colorNames.size} colores, " +
        "${catalog.typographyNames.size} tipografías y ${catalog.drawableNames.size} iconos")
}
