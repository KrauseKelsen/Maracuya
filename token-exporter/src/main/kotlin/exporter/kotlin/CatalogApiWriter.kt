package exporter.kotlin

import exporter.catalog.CatalogWriter
import exporter.catalog.ExportCatalog
import exporter.output.ExportOutput

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CatalogApiWriter entrega a Android las fuentes compartidas generadas en la primera fase.
 *
 * Justificación:
 * El exportador y la librería compilan exactamente las colecciones producidas desde Library.
 * Este escritor transporta código; no vuelve a descubrir nombres ni reconstruye métricas o roles.
 *
 * Uso:
 * Interno de construcción. Consume los recursos catalog-api del propio exportador y escribe en
 * el directorio Kotlin administrado por generateLibraryTokens. El host recibe las clases en el AAR.
 *
 * Objetivos:
 * - Transportar la API generada sin acoplar el exportador a componentes de interfaz.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object CatalogApiWriter : CatalogWriter {
    /** Copia todas las fuentes declaradas en el manifiesto, sin una lista por familia o componente. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val paths = requireNotNull(javaClass.getResourceAsStream("/catalog-api/manifest.txt")) {
            "Falta el manifiesto de APIs Library"
        }.bufferedReader().use { it.readLines() }
        require(paths.isNotEmpty() && paths.distinct().size == paths.size)
        for (path in paths) {
            require(path.startsWith("cruxui/android/lib/") && path.endsWith(".kt") && ".." !in path)
            val content = requireNotNull(javaClass.getResourceAsStream("/catalog-api/$path")) {
                "Falta la API Library generada: $path"
            }.bufferedReader().use { it.readText() }
            output.source(path, content)
        }
    }
}
