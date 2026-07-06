package cruxui.android.maracuya.wrappers.components.labels

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.atoms.LibraryColorTokens
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.semantics.CorporateIcons
import cruxui.android.maracuya.semantics.CorporateTypography
import cruxui.android.maracuya.tokens.base.FontFamilyToken
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
     * Usa esta variante cuando el override ya fue construido y no necesita leer
     * `CompositionLocal`.
     */
    fun register(
        name: String,
        tokensOverride: LabelTokensOverride,
    ) {
        providers[name.normalizedKey()] = { tokensOverride }
    }

    /**
'     * Registra o reemplaza un provider de tokens override para labels.
     *
     * Usa esta variante cuando el override necesita leer CompositionLocals del tema
     * actual. El provider se invoca desde `LabelWrp`, dentro de Compose y del scope de
     * `MyLibraryThemeWrp`.
     */

    /**
     * Registra un override declarativo para hosts XML que no deben leer CompositionLocals.
     *
     * El builder es una lambda normal. El registry lee los tokens activos dentro de Compose
     * y se los entrega al builder para construir el override final.
     */
    fun register(
        name: String,
        builder: (
            colors: LibraryColorTokens,
            typography: CorporateTypography,
            icons: CorporateIcons,
            fontFamily: FontFamilyToken,
        ) -> LabelTokensOverride,
    ) {
        providers[name.normalizedKey()] = {
            builder(
                LocalLibraryColorTokens.current,
                LocalLibraryTypography.current,
                LocalLibraryIcons.current,
                LocalFontFamily.current,
            )
        }
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
