package exporter.android

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * FontResourceWriter: Construye familias XML a partir de las caras físicas declaradas por el DS.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object FontResourceWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        for ((family, faces) in catalog.fonts) {
            val xml = buildString {
                appendLine("<font-family xmlns:android=\"http://schemas.android.com/apk/res/android\" xmlns:app=\"http://schemas.android.com/apk/res-auto\">")
                for (face in faces) {
                    val style = if (face.italic) "italic" else "normal"
                    appendLine("    <font android:font=\"@font/${face.resourceName}\" android:fontWeight=\"${face.weight}\" android:fontStyle=\"$style\" app:font=\"@font/${face.resourceName}\" app:fontWeight=\"${face.weight}\" app:fontStyle=\"$style\" />")
                }
                appendLine("</font-family>")
            }
            output.resource("font/library_${snake(family.name)}.xml", xml)
        }

    }
}
