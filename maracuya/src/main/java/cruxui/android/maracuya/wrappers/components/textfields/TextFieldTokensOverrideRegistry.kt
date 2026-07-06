package cruxui.android.maracuya.wrappers.components.textfields

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.atoms.LibraryColorTokens
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.semantics.CorporateIcons
import cruxui.android.maracuya.semantics.CorporateTypography
import cruxui.android.maracuya.tokens.base.FontFamilyToken
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
        builder: (
            colors: LibraryColorTokens,
            typography: CorporateTypography,
            icons: CorporateIcons,
            fontFamily: FontFamilyToken,
        ) -> TextFieldTokens,
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
