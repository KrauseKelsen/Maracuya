package exporter.validation

import exporter.catalog.ExportCatalog
import exporter.output.*
import java.io.File

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CatalogValidator: Valida el contrato de recursos antes de escribir archivos.
 *
 * Justificación y uso:
 * Comprueba nombres, compatibilidad de roles y assets; falla antes de producir una exportación parcial.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object CatalogValidator {
    /** Valida el catálogo completo contra los recursos físicos del DS. */
    fun validate(catalog: ExportCatalog, assets: File) {
        val themes = catalog.themes
        require(themes.isNotEmpty()) { "El catálogo no puede estar vacío" }
        require(themes.map { snake(it.name) }.distinct().size == themes.size) { "Colisión de nombres de recursos" }
        require(themes.map { styleName(it.name) }.distinct().size == themes.size) { "Colisión de nombres de estilos" }
        val attributes = catalog.colorNames.map(::colorAttr) + catalog.typographyNames.flatMap {
            listOf(typeAttr(it), "libraryLineHeight${upper(it)}")
        } + "libraryFontPrimary" + catalog.iconGroups.flatMap { (group, icons) -> icons.keys.flatMap {
            listOf(iconAttr(group, it), "${iconAttr(group, it)}Size", "${iconAttr(group, it)}Tint")
        } }
        require(attributes.distinct().size == attributes.size) { "Colisión de atributos semánticos" }
        for (theme in themes) {
            require(styleName(theme.name).isNotEmpty()) { "Nombre de tema no exportable" }
            for (palette in listOf(theme.light, theme.dark)) {
                require(palette.colors.keys == catalog.colorNames) { "Roles de color incompatibles: ${theme.name}" }
                require(palette.typography.keys == catalog.typographyNames) { "Roles tipográficos incompatibles: ${theme.name}" }
                require(palette.icons.mapValues { it.value.keys } == catalog.iconGroups.mapValues { it.value.keys }) {
                    "Roles de iconos incompatibles: ${theme.name}"
                }
                palette.typography.values.forEach {
                    require(it.fontSize > 0f && it.fontSize.isFinite() && it.lineHeight >= 0 && it.lineHeight.isFinite())
                }
                palette.icons.forEach { (group, icons) -> icons.forEach { (name, icon) ->
                    require(icon.iconSize > 0) { "Tamaño de icono inválido: $group.$name" }
                    require(icon.filledResId == 0 && icon.resourceName != null) {
                        "El icono exportable $group.$name requiere resourceName y filledResId = 0"
                    }
                } }
            }
        }
        checkAssets(assets, "font", catalog.fontNames)
        checkAssets(assets, "drawable", catalog.drawableNames)
    }
    /** Comprueba nombres Android y presencia de archivos sin cargar recursos en ejecución. */
    private fun checkAssets(root: File, type: String, names: Set<String>) {
        val available = File(root, type).listFiles().orEmpty().map { it.nameWithoutExtension }.toSet()
        for (name in names) {
            require(name.matches(Regex("[a-z][a-z0-9_]*"))) { "Nombre de recurso inválido: $type/$name" }
            require(name in available) { "Falta $type/$name" }
        }
    }
}
