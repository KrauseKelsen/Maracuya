package cruxui.android.maracuya.wrappers.components.textfields

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.textfields.TextFieldTokens
import java.util.concurrent.ConcurrentHashMap

/**
 * Registro global para que un XML pueda referenciar tokens de `TextFieldMrcy` por nombre.
 *
 * Android XML no puede construir objetos Kotlin ni leer `CompositionLocal` directamente.
 * Por eso la app registra un provider composable y el XML apunta al mismo nombre mediante
 * `app:textFieldTokensOverride="miOverride"`.
 */
object TextFieldTokensOverrideRegistry {

    private val providers = ConcurrentHashMap<String, @Composable () -> TextFieldTokens>()

    /** Registra o reemplaza un provider de tokens para un nombre estable. */
    fun register(
        name: String,
        tokensOverride: TextFieldTokens,
    ) {
        providers[name.normalizedKey()] = { tokensOverride }
    }

    /** Elimina un provider previamente registrado por la pantalla, preview o test dueño. */
    fun unregister(name: String) {
        providers.remove(name.normalizedKey())
    }

    /** Limpia el registro completo; útil para tests o demos aisladas. */
    fun clear() {
        providers.clear()
    }

    /** Resuelve los tokens asociados al nombre recibido desde XML, si existen. */
    @Composable
    fun resolve(name: String?): TextFieldTokens? {
        val key = name?.normalizedKey().orEmpty()
        if (key.isEmpty()) return null
        return providers[key]?.invoke()
    }

    private fun String.normalizedKey(): String = trim().lowercase()
}
