package exporter.output

import java.io.File

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ExportOutput: Contrato de salida separado del formato y de la lectura de tokens.
 *
 * Justificación y uso:
 * Los escritores se prueban con una salida en memoria; Gradle usa DirectoryExportOutput.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal interface ExportOutput {
    /** Escribe un recurso relativo al directorio res. */
    fun resource(path: String, content: String)
    /** Escribe una fuente relativa al directorio Kotlin. */
    fun source(path: String, content: String)
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * DirectoryExportOutput: Persiste archivos generados y añade su documentación de procedencia.
 *
 * Justificación y uso:
 * Solo esta implementación conoce el sistema de archivos; rechaza rutas duplicadas.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal class DirectoryExportOutput(private val resources: File, private val sources: File) : ExportOutput {
    private val written = mutableSetOf<File>()
    /** Guarda XML documentado, sin decidir su contenido. */
    override fun resource(path: String, content: String) = write(resources, path, resourceDocumentation(path) + content)
    /** Guarda Kotlin ya documentado por el escritor. */
    override fun source(path: String, content: String) = write(sources, path, content)
    /** Evita que dos escritores se sobrescriban accidentalmente. */
    private fun write(root: File, path: String, content: String) {
        val file = File(root, path)
        check(written.add(file)) { "Salida duplicada: $path" }
        file.parentFile.mkdirs()
        file.writeText(content)
    }
}
