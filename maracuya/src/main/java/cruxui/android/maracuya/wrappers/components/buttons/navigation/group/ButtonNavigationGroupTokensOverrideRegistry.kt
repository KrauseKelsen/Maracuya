package cruxui.android.maracuya.wrappers.components.buttons.navigation.group

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import java.util.concurrent.ConcurrentHashMap

/**
 * Registro de overrides para botones hijos usados por wrappers XML del grupo.
 *
 * `ButtonNavigationGroupMrcy` no expone tokens visuales propios porque compone
 * `ButtonNavigationMrcy`. Este registro mantiene una extension simetrica para XML
 * cuando una pantalla necesite declarar overrides reutilizables de los botones hijos.
 */
object ButtonNavigationGroupTokensOverrideRegistry {

    private val providers = ConcurrentHashMap<String, @Composable () -> ButtonNavigationTokensOverride>()

    /** Registra o reemplaza un override estable para usar desde XML. */
    fun register(
        name: String,
        tokensOverride: ButtonNavigationTokensOverride,
    ) {
        providers[name.normalizedKey()] = { tokensOverride }
    }

    /** Elimina un override previamente registrado por una pantalla, preview o test. */
    fun unregister(name: String) {
        providers.remove(name.normalizedKey())
    }

    /** Limpia el registro completo; util para pruebas o demos aisladas. */
    fun clear() {
        providers.clear()
    }

    /** Resuelve el override asociado al nombre recibido desde XML, si existe. */
    @Composable
    fun resolve(name: String?): ButtonNavigationTokensOverride? {
        val key = name?.normalizedKey().orEmpty()
        if (key.isEmpty()) return null
        return providers[key]?.invoke()
    }

    private fun String.normalizedKey(): String = trim().lowercase()
}
