package exporter

import cruxui.android.lib.theme.LibraryThemes
import exporter.android.*
import exporter.catalog.*
import exporter.components.text.TextWidgetStyleWriter
import exporter.kotlin.*
import exporter.output.ExportOutput
import exporter.validation.CatalogValidator
import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Files
import javax.xml.parsers.DocumentBuilderFactory

/** Prueba el contrato exportado y la independencia de adaptaciones de componentes. */
class CatalogExportTest {
    private class MemoryOutput : ExportOutput {
        val resources = linkedMapOf<String, String>()
        val sources = linkedMapOf<String, String>()
        override fun resource(path: String, content: String) { check(resources.put(path, content) == null) }
        override fun source(path: String, content: String) { check(sources.put(path, content) == null) }
    }
    private val writers: List<CatalogWriter> = listOf(
        AttributeResourceWriter, ColorResourceWriter, TypographyResourceWriter,
        FontResourceWriter, ThemeOverlayWriter, AssetBindingsWriter, XmlReferencesWriter, CatalogApiWriter,
    )
    private val catalog = ThemeCatalogReader.read(LibraryThemes.all)

    @Test fun `el catalogo completo se exporta sin adaptar widgets de texto`() {
        val output = MemoryOutput()
        writers.forEach { it.write(catalog, output) }
        assertFalse(output.resources.values.any { "<style name=\"Widget." in it })
        assertTrue(output.resources.values.any { "TextAppearance.Maracuya.Headline24" in it })
        assertTrue(output.sources.keys.any { it.endsWith("theme/collections/LibraryIcons.kt") })
        val before = output.resources.toMap()
        TextWidgetStyleWriter.write(catalog, output)
        assertEquals(before.size + 1, output.resources.size)
        before.forEach { (path, xml) -> assertEquals(xml, output.resources[path]) }
        output.resources.values.forEach {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it.byteInputStream())
        }
    }

    @Test fun `el orden de los escritores no altera su salida ni comparte estado mutable`() {
        val first = MemoryOutput()
        val second = MemoryOutput()
        writers.forEach { it.write(catalog, first) }
        writers.reversed().forEach { it.write(catalog, second) }
        assertEquals(first.resources, second.resources)
        assertEquals(first.sources, second.sources)
    }

    @Test fun `una marca adicional reutiliza todos los escritores`() {
        val custom = LibraryThemes.Maracuya.copy(name = "OtraMarca", typography = LibraryThemes.Maracuya.typography.copy(
            headline24 = LibraryThemes.Maracuya.typography.headline24.copy(fontSize = 31f),
        ))
        val output = MemoryOutput()
        val expanded = ThemeCatalogReader.read(LibraryThemes.all + custom)
        writers.forEach { it.write(expanded, output) }
        assertTrue(output.resources.getValue("values/library_typography.xml").contains(
            "<dimen name=\"library_otra_marca_headline24_size\">31.0sp</dimen>"))
        assertTrue(output.resources.getValue("values/library_tokens.xml").contains("ThemeOverlay.OtraMarca.Dark"))
    }

    @Test fun `sustituir valores Library actualiza colores e iconos exportados sin modificar escritores`() {
        val base = LibraryThemes.Maracuya
        val icon = base.iconAtoms.alertsIconsLibrary.warning.copy(iconSize = 29,
            iconColor = cruxui.android.lib.tokens.base.ColorToken(0xff123456))
        val custom = base.copy(name = "Personalizada", lightTokens = base.lightTokens.copy(
            infoText = cruxui.android.lib.tokens.base.ColorToken(0xff654321)),
            iconAtoms = base.iconAtoms.copy(alertsIconsLibrary = base.iconAtoms.alertsIconsLibrary.copy(info = icon)))
        val catalog = ThemeCatalogReader.read(listOf(custom))
        val output = MemoryOutput()
        writers.forEach { it.write(catalog, output) }
        val xml = output.resources.values.joinToString("\n")
        assertTrue(xml.contains("#FF654321"))
        assertTrue(xml.contains("<item name=\"libraryIconAlertsInfo\">@drawable/${icon.resourceName}</item>"))
        assertTrue(xml.contains("<item name=\"libraryIconAlertsInfoSize\">29dp</item>"))
        assertTrue(xml.contains("<item name=\"libraryIconAlertsInfoTint\">#FF123456</item>"))
        assertSame(icon, catalog.themes.single().dark.icons.getValue("alerts").getValue("info"))
    }

    @Test fun `la validacion rechaza roles incompatibles y assets ausentes`() {
        val root = Files.createTempDirectory("catalog-validation").toFile()
        try {
            val theme = catalog.themes.first()
            val invalid = catalog.copy(themes = listOf(theme.copy(dark = theme.dark.copy(colors = emptyMap()))))
            val roles = assertThrows(IllegalArgumentException::class.java) { CatalogValidator.validate(invalid, root) }
            assertTrue(roles.message!!.contains("Roles de color"))
            val assets = assertThrows(IllegalArgumentException::class.java) { CatalogValidator.validate(catalog, root) }
            assertTrue(assets.message!!.contains("Falta font/"))
        } finally { root.deleteRecursively() }
    }

    @Test fun `colisiones entre iconos y atributos de metadatos fallan antes de exportar`() {
        val theme = catalog.themes.first()
        val alerts = theme.light.icons.getValue("alerts")
        val icons = theme.light.icons + ("alerts" to (alerts + ("infoSize" to alerts.getValue("info"))))
        val invalid = catalog.copy(themes = listOf(theme.copy(
            light = theme.light.copy(icons = icons), dark = theme.dark.copy(icons = icons))))
        val error = assertThrows(IllegalArgumentException::class.java) {
            CatalogValidator.validate(invalid, java.io.File("assets-no-utilizados"))
        }
        assertTrue(error.message!!.contains("Colisión de atributos"))
    }

    @Test fun `un icono con ID de host no puede alterar silenciosamente la paridad XML`() {
        val theme = catalog.themes.first()
        val alerts = theme.light.icons.getValue("alerts")
        val icons = theme.light.icons + ("alerts" to (alerts + ("info" to alerts.getValue("info").copy(filledResId = 123))))
        val invalid = catalog.copy(themes = listOf(theme.copy(light = theme.light.copy(icons = icons))))
        val error = assertThrows(IllegalArgumentException::class.java) {
            CatalogValidator.validate(invalid, java.io.File("assets-no-utilizados"))
        }
        assertTrue(error.message!!.contains("filledResId = 0"))
    }
}
