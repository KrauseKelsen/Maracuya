package cruxui.android.maracuya.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import cruxui.android.maracuya.semantics.CorporateIcons


/**
 * Exponer los icons semánticos al árbol de Compose.
 *
 * NO debe ser nullable:
 *  - El DS garantiza iconos.
 *  - Si falta el provider → error claro y temprano.
 */
val LocalLibraryIcons = staticCompositionLocalOf<CorporateIcons> {
    error(
        "LocalLibraryIcons not provided. " +
                "Wrap your composable with CorporateTheme() to load semantic icons."
    )
}
