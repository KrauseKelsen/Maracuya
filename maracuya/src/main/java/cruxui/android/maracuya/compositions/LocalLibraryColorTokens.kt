package cruxui.android.maracuya.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import cruxui.android.maracuya.atoms.LibraryColorTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LocalLibraryColorTokens` es un CompositionLocal que expone los tokens de color
 * corporativos derivados del Design System. A diferencia de versiones previas,
 * este CompositionLocal es **nullable** para permitir que los componentes puedan
 * resolverse incluso cuando el host consume un componente fuera del scope de
 * `MyLibraryTheme`.
 *
 * Esto evita crashes por falta de provisión y habilita el uso de fallbacks seguros
 * (por ejemplo, derivando desde `MaterialTheme.colorScheme` si es necesario).
 *
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 *
 * El acceso seguro debe darse únicamente a través del resolver interno:
 *    - ButtonTokens.fromLibraryTokens(...)
 *    - InputTokens.fromLibraryTokens(...)
 *    - Etc.
 *
 * Ejemplo conceptual:
 *
 *     val providedLibraryColorTokens = LocalLibraryColorTokens.current
 *
 *     val resolvedTokens: ButtonTokens = when {
 *         // 1) Si el host pasa tokens personalizados
 *         buttonTokens != null -> buttonTokens
 *
 *         // 2) Si existen tokens de la librería (en CompositionLocal)
 *         providedLibraryColorTokens != null ->
 *             ButtonTokens.fromLibraryTokens(providedLibraryColorTokens)
 *
 *         // 3) Fallback hacia MaterialTheme
 *         else -> ButtonTokens.fromMaterialTheme()
 *     }
 * ```
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.3
 * @see cruxui.android.maracuya.compositions.LocalLibraryColorTokens
 */
val LocalLibraryColorTokens = staticCompositionLocalOf<LibraryColorTokens> {
    error(
        "LocalLibraryColor not provided. " +
                "Wrap your composable with CorporateTheme() to load semantic colors."
    )
}