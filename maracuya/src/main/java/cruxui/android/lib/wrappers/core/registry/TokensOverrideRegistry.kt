package cruxui.android.lib.wrappers.core.registry

import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.theme.collections.LibraryIcons
import cruxui.android.lib.theme.collections.LibraryTypography
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.FontFamilyToken
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TokensOverrideRegistry: Almacena recetas tipadas sin mantener un tema activo global.
 *
 * Justificación:
 * API pública: registrar antes de inflar; cada componente declara su propio registro. Una receta recibe LibraryThemeSnapshot y devuelve T. Resolver de nuevo tras sustituirla. No capturar Context ni Views.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
open class TokensOverrideRegistry<T : Any> : TokensOverrideSource<T> {
    private val recipes = ConcurrentHashMap<String, (LibraryThemeSnapshot) -> T>()

    /** Registra o sustituye una receta dependiente del tema del consumidor. */
    fun register(name: String, builder: (library: LibraryThemeSnapshot) -> T) {
        val key = name.key()
        require(key.isNotEmpty()) { "An override recipe needs a non-blank name" }
        recipes[key] = builder
    }

    /** Registra un valor fijo; no se recalcula al cambiar de tema. */
    fun register(name: String, tokensOverride: T) = register(name) { _: LibraryThemeSnapshot -> tokensOverride }

    /** Conserva la API de recetas de cuatro colecciones sin acoplarla a Compose. */
    fun register(name: String, builder: (LibraryColorTokens, LibraryTypography, LibraryIcons, FontFamilyToken) -> T) =
        register(name) { library: LibraryThemeSnapshot ->
            builder(library.colors, library.typography, library.icons, library.fontFamily)
        }

    /** Resuelve una receta o falla si el nombre solicitado no está registrado. */
    override fun resolve(name: String?, library: LibraryThemeSnapshot): T? {
        val key = name?.key().orEmpty()
        if (key.isEmpty()) return null
        return requireNotNull(recipes[key]) { "Unregistered tokens override: $name" }(library)
    }

    /** Comprueba la existencia usando la misma normalización que register. */
    override fun contains(name: String): Boolean = recipes.containsKey(name.key())
    /** Retira una receta; el host debe retirar antes las referencias activas. */
    fun unregister(name: String) { recipes.remove(name.key()) }
    /** Vacía exclusivamente este registro, sin afectar los demás componentes. */
    fun clear() { recipes.clear() }
    /** Normaliza claves independientemente del idioma del dispositivo. */
    private fun String.key() = trim().lowercase(Locale.ROOT)
}
