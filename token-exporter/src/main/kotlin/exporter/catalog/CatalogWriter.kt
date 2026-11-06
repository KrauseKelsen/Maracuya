package exporter.catalog

import exporter.output.ExportOutput

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CatalogWriter: Contrato mínimo para una salida independiente del catálogo.
 *
 * Justificación y uso:
 * Agregar un formato o adaptación no modifica los escritores existentes. No incluye tokens de componentes.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal fun interface CatalogWriter {
    /** Emite los archivos propios del escritor sobre una salida inyectada. */
    fun write(catalog: ExportCatalog, output: ExportOutput)
}
