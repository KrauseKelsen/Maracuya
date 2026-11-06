package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TypographyRolesSource genera los roles nativos completos desde el esquema de Library.
 *
 * Justificación:
 * Elimina el enum cerrado y el when mantenidos manualmente en wrappers. Cada rol conserva el nombre
 * semántico que LibraryTypography resuelve contra el tema activo; no almacena métricas congeladas.
 *
 * Uso:
 * Interno de construcción. El alias Body conserva la API histórica y se dirige a body1.
 * Los estilos nuevos no requieren tocar esta compatibilidad ni el resolutor nativo.
 *
 * Objetivos:
 * - Exponer todos los estilos y mantener compatibilidad con los roles existentes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object TypographyRolesSource {
    /** Genera los roles tipográficos y conserva las primeras entradas históricas. */
    fun render(names: List<String>): String = RolesSource.render("LibraryTypographyRole", RolesSource.entries(
        names, listOf("Label" to "label", "Caption" to "caption", "Body" to "body1")),
        "API del host: library.typography[LibraryTypographyRole.Headline24Medium] o view.setTypography(TextTypographyRole.Headline24Medium). Body equivale a Body1. En XML usar android:textAppearance=\"?attr/libraryTypographyHeadline24Medium\" con el overlay de marca.")
}
