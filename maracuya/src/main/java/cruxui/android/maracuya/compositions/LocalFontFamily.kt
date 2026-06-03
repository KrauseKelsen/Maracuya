package cruxui.android.maracuya.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import cruxui.android.maracuya.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * CompositionLocal encargado de proveer la familia tipográfica base utilizada en toda
 * la librería. Su valor por defecto es `FontFamilies.Mulish`.
 *
 * Este CompositionLocal permite que la librería determine la fuente principal de forma
 * centralizada, asegurando consistencia tipográfica sin exponer directamente la lógica
 * interna al host.
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 * El acceso del host debe darse únicamente a través de:
 *    - LocalLibraryTypographyTokens (scope controlado)
 *
 * Ejemplo conceptual:
 *
 *     val providedTypographyTokens = LocalLibraryTypographyTokens.current
 *
 *     val resolvedFontFamily = when {
 *         // 1) Si el host pasa fuentes personalizadas:
 *         typographyTokens != null -> typographyTokens.fontFamily
 *
 *         // 2) Si existe un typographyTokens corporativo:
 *         providedTypographyTokens != null ->
 *             providedTypographyTokens.fontFamily
 *
 *         // 3) Fallback: derivar desde la fuente por defecto interna
 *         else -> LocalFontFamily.current
 *     }
 * ```
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.3
 * @see cruxui.android.maracuya.compositions.LocalFontFamily
 */

val LocalFontFamily = staticCompositionLocalOf {
    FontFamilyToken.MULISH
}