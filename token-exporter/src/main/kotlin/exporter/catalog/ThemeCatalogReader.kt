package exporter.catalog

import cruxui.android.lib.theme.*
import cruxui.android.lib.tokens.base.*
import cruxui.android.lib.theme.catalog.asMap

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ThemeCatalogReader: Adapta las colecciones efectivas de cada tema al modelo de exportación.
 *
 * Justificación y uso:
 * Usa las consultas generadas desde Library, sin reflexión ni listas manuales. Recibe temas explícitos para probar catálogos alternativos.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object ThemeCatalogReader {
    /** Lee una vez cada modo y conserva los nombres públicos ordenados. */
    fun read(themes: List<LibraryThemeSet>): ExportCatalog = ExportCatalog(
        themes.map { ExportTheme(it.name, palette(it.resolve(false)), palette(it.resolve(true))) },
        FontFamilyToken.entries.associateWith(FontAssetCatalog::faces),
    )
    /** Convierte una biblioteca existente al modelo de exportación sin valores alternativos. */
    private fun palette(value: LibraryThemeSnapshot) = ExportPalette(
        value.colors.asMap(),
        value.typography.asMap(),
        value.icons.asMap(),
        value.fontFamily,
    )
}
