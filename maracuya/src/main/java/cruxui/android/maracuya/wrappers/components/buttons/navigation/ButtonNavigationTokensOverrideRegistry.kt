package cruxui.android.maracuya.wrappers.components.buttons.navigation

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.buttons.navigation.ButtonNavigationTokensOverride
import java.util.concurrent.ConcurrentHashMap

/**
 * Registro global para que XML pueda referenciar overrides de `ButtonNavigationMrcy`.
 *
 * XML no puede construir objetos Kotlin ni leer CompositionLocals directamente, por eso
 * la app registra un override por nombre y el layout lo consume con
 * `app:buttonNavigationTokensOverride`.
 */
object ButtonNavigationTokensOverrideRegistry {

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
