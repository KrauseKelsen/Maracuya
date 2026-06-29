package cruxui.android.maracuya.wrappers.components.labels

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import java.util.concurrent.ConcurrentHashMap

/**
 * Registro global para resolver `LabelTokensOverride` por nombre desde XML.
 *
 * XML no puede construir objetos Kotlin complejos. Por eso la app host registra un
 * provider composable con un nombre estable y el layout lo referencia mediante
 * `app:labelTokensOverride`.
 */
object LabelTokensOverrideRegistry {

    private val providers = ConcurrentHashMap<String, @Composable () -> LabelTokensOverride>()

    /**
     * Registra o reemplaza un provider de tokens override para labels.
     *
     * El provider es composable para que pueda leer CompositionLocals del tema actual
     * justo cuando `LabelWrp` se renderiza.
     */
    fun register(
        name: String,
        tokensOverride: LabelTokensOverride,
    ) {
        providers[name.normalizedKey()] = { tokensOverride }
    }

    /**
     * Elimina un provider previamente registrado por nombre.
     *
     * Úsalo al finalizar previews, pantallas o pruebas que sean dueñas del override.
     */
    fun unregister(name: String) {
        providers.remove(name.normalizedKey())
    }

    /**
     * Limpia todos los providers registrados.
     *
     * Está pensado principalmente para pruebas o demos controladas que requieren un
     * estado global limpio antes de registrar nuevos overrides.
     */
    fun clear() {
        providers.clear()
    }

    /**
     * Resuelve el override asociado al nombre indicado, si existe.
     */
    @Composable
    fun resolve(name: String?): LabelTokensOverride? {
        val key = name?.normalizedKey().orEmpty()
        if (key.isEmpty()) return null
        return providers[key]?.invoke()
    }

    private fun String.normalizedKey(): String = trim().lowercase()
}
