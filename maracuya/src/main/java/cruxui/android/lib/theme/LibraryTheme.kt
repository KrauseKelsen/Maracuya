package cruxui.android.lib.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cruxui.android.lib.compositions.LocalFontFamily
import cruxui.android.lib.compositions.LocalLibraryColorTokens
import cruxui.android.lib.compositions.LocalLibraryIcons
import cruxui.android.lib.compositions.LocalLibraryTypography

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryTheme` ofrece una entrada agrupada al tema activo dentro de Compose.
 *
 * Justificación:
 * Permite escribir recetas con un solo parámetro library sin sustituir los CompositionLocals existentes. Respeta
 * modificaciones anidadas de cualquiera de los cuatro locals.
 *
 * Se comunica con:
 * Lee `LocalLibraryColorTokens`, `LocalLibraryTypography`, `LocalLibraryIcons` y `LocalFontFamily`; recuerda un
 * `LibraryThemeSnapshot` por combinación de valores.
 *
 * Uso:
 * API pública del host, exclusivamente dentro de una composición con el tema provisto:
 * ```kotlin
 * MyLibraryTheme {
 *     val library = LibraryTheme.current
 *     TextView(
 *         text = "Título",
 *         tokensOverride = TextViewTokensOverride(
 *             typography = library.typography.headline24,
 *             textColor = library.colors.brandPrimary,
 *         ),
 *     )
 * }
 * ```
 * No leer current desde Application, una View o una función ordinaria. Allí usar el snapshot recibido por el
 * registro, `view.getThemeTokens()` o `LibraryThemeSet.resolve`.
 *
 * Objetivos:
 * - Simplificar recetas Compose.
 * - Mantener los alcances de los locals.
 * - Evitar un tema global mutable.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.MyLibraryTheme
 */
object LibraryTheme {
    /**
     * Obtiene el snapshot de los cuatro locals activos.
     *
     * Uso público solo composable: `val library = LibraryTheme.current`. La ausencia de los proveedores
     * requeridos produce el error de esos locals. El resultado se recuerda mientras sus cuatro valores no
     * cambien.
     */
    val current: LibraryThemeSnapshot
        @Composable get() {
            val colors = LocalLibraryColorTokens.current
            val typography = LocalLibraryTypography.current
            val icons = LocalLibraryIcons.current
            val fontFamily = LocalFontFamily.current
            return remember(colors, typography, icons, fontFamily) {
                LibraryThemeSnapshot(colors, typography, icons, fontFamily)
            }
        }
}
