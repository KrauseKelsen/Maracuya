package cruxui.android.lib.wrappers.core.tokens

import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.wrappers.core.registry.TokensOverrideSource
import cruxui.android.lib.wrappers.core.registry.resolveRecipe

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ComponentTokenBinding: Vincula un tema por instancia con una fuente de recetas de cualquier componente.
 *
 * Justificación:
 * Se inyectan consulta de tema y registro; no depende de View, Compose, estado visual ni comportamiento. El adaptador llama refresh en cambios de ámbito/configuración.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal class ComponentTokenBinding<T : Any>(
    private val recipes: TokensOverrideSource<T>,
    private val inheritedTheme: () -> LibraryThemeSnapshot,
) {
    /** Tema explícito de esta instancia; null permite heredar el ámbito. */
    var explicitTheme: LibraryThemeSnapshot? = null
    /** Biblioteca resuelta; permanece estable hasta refresh. */
    var library: LibraryThemeSnapshot = inheritedTheme()
        private set

    /** Reevalúa el tema; el componente decide qué valores y vistas debe actualizar. */
    fun refresh() { library = explicitTheme ?: inheritedTheme() }

    /** Omite recetas ausentes únicamente en preview; ejecución normal falla explícitamente. */
    fun recipe(name: String?, preview: Boolean = false): T? =
        recipes.resolveRecipe(name, library, preview)
}
