package cruxui.android.lib.wrappers.core.registry

import cruxui.android.lib.theme.LibraryThemeSnapshot

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TokensOverrideSource: Contrato de lectura de recetas tipadas por componente.
 *
 * Justificación:
 * Los consumidores dependen de consulta y resolución, sin permisos para registrar o borrar recetas.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
interface TokensOverrideSource<T : Any> {
    /** Resuelve el nombre en la biblioteca indicada; null o vacío representan ausencia. */
    fun resolve(name: String?, library: LibraryThemeSnapshot): T?
    /** Consulta si existe una receta normalizando su nombre. */
    fun contains(name: String): Boolean
}

/** Permite previews sin inicialización del host, manteniendo errores explícitos en ejecución normal. */
internal fun <T : Any> TokensOverrideSource<T>.resolveRecipe(
    name: String?, library: LibraryThemeSnapshot, preview: Boolean = false,
): T? = if (preview && !name.isNullOrBlank() && !contains(name)) null else resolve(name, library)
