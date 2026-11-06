package exporter.catalog

import cruxui.android.lib.theme.FontAssetFace
import cruxui.android.lib.tokens.base.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ExportCatalog: Modelo tipado de exportación, derivado del catálogo existente del DS.
 *
 * Justificación y uso:
 * No define valores de marca. Los escritores comparten este resultado y no usan reflexión.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal data class ExportCatalog(
    val themes: List<ExportTheme>,
    val fonts: Map<FontFamilyToken, List<FontAssetFace>>,
) {
    val colorNames get() = themes.first().light.colors.keys
    val typographyNames get() = themes.first().light.typography.keys
    val iconGroups get() = themes.first().light.icons
    val fontNames get() = fonts.values.flatten().map { it.resourceName }.toSortedSet()
    val drawableNames get() = themes.flatMap { listOf(it.light, it.dark) }
        .flatMap { it.icons.values.flatMap { group -> group.values } }
        .map { requireNotNull(it.resourceName) }.toSortedSet()
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ExportTheme: Agrupa los modos exportables de una marca.
 *
 * Justificación y uso:
 * Lo produce ThemeCatalogReader; los escritores consumen ambos modos sin reevaluar el tema.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal data class ExportTheme(val name: String, val light: ExportPalette, val dark: ExportPalette)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ExportPalette: Representa los roles semánticos de un modo mediante tipos puros.
 *
 * Justificación y uso:
 * Aísla el descubrimiento de propiedades de los formatos Android y Kotlin.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal data class ExportPalette(
    val colors: Map<String, ColorToken>,
    val typography: Map<String, TypographyToken>,
    val icons: Map<String, Map<String, IconToken>>,
    val fontFamily: FontFamilyToken,
)
