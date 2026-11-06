package exporter.kotlin

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AssetBindingsWriter: Genera enlaces estáticos a R.font y R.drawable sin reflexión en ejecución.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object AssetBindingsWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val drawableNames = catalog.drawableNames
        val fontNames = catalog.fontNames
        val bindings = buildString {
            appendLine("package cruxui.android.lib.theme")
            appendLine("import cruxui.android.lib.R")
            appendLine("import cruxui.android.lib.tokens.base.IconToken")
            appendLine(typeDocumentation(
                "LibraryAssetResources",
                "Enlaza nombres simbólicos del catálogo con IDs estáticos de recursos Android.",
                "API pública Android. Para iconos usar LibraryAssetResources.icon(token); respeta filledResId del host " +
                    "y resuelve resourceName en los iconos incluidos. drawable(name) y font(name) admiten únicamente nombres del catálogo. " +
                    "No editar este archivo: modificar los catálogos fuente y compilar. No realiza reflexión ni getIdentifier durante el render.",
            ))
            appendLine("object LibraryAssetResources {")
            appendLine("    /**")
            appendLine("     * Resuelve un token de icono a @DrawableRes; prioriza filledResId del host.")
            appendLine("     * @param token Icono semántico, del catálogo incluido o del host.")
            appendLine("     * @return ID del drawable.")
            appendLine("     * @throws IllegalArgumentException Si faltan tanto el ID como el nombre simbólico.")
            appendLine("     * @throws IllegalStateException Si el nombre simbólico no pertenece al catálogo.")
            appendLine("     */")
            appendLine("    fun icon(token: IconToken): Int = if (token.filledResId != 0) token.filledResId else drawable(requireNotNull(token.resourceName))")
            for ((type, names) in listOf("drawable" to drawableNames, "font" to fontNames)) {
                appendLine("    /**")
                appendLine("     * Enlaza un nombre incluido a R.$type para integraciones Android.")
                appendLine("     * @param name Nombre del catálogo, sin extensión ni prefijo de recurso.")
                appendLine("     * @return ID Android del recurso.")
                appendLine("     * @throws IllegalStateException Si el nombre no está incluido.")
                appendLine("     */")
                appendLine("    fun $type(name: String): Int = when (name) {")
                for (name in names) appendLine("        \"$name\" -> R.$type.$name")
                appendLine("        else -> error(\"Unknown bundled $type: \$name\")")
                appendLine("    }")
            }
            appendLine("}")
        }
        output.source("cruxui/android/lib/theme/LibraryAssetResources.kt", bindings)

    }
}
