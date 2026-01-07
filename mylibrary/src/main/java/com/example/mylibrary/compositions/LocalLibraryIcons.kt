package com.example.mylibrary.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.mylibrary.semantics.CorporateIcons


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
