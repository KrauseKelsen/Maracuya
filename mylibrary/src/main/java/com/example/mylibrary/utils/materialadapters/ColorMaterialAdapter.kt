package com.example.mylibrary.utils.materialadapters

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.example.mylibrary.atoms.LibraryColorTokens
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter

/**
 * Adaptador que convierte LibraryColorTokens (sin Compose) a Material3 ColorScheme.
 * Es el único lugar donde la librería hace el puente hacia Material.
 *
 * Uso:
 * val scheme = LibraryColorToMaterialAdapter.toMaterialColorScheme(tokens, isDark)
 *
 * @since 2025-11-18
 * @version 1.5.4
 */

/**
 * Adapter: convierte LibraryColorTokens -> Material3 ColorScheme.
 * No es necesario crearlo si no se quiere puente semántico corporativo
 * Mapear tantos roles como tenga sentido. No borra semántica, solo convierte
 * en roles de color MD3.
 * Se crea un ToMaterialColorScheme por cada tema en su mode light/dark
 */
object ColorMaterialAdapter {

    fun toMaterialColorScheme(tokens: LibraryColorTokens, isDark: Boolean): ColorScheme {
        return if (!isDark) {
            lightColorScheme(
                primary = ColorComposeAdapter.toComposeColor(tokens.brandPrimary),
                onPrimary = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),
                primaryContainer = ColorComposeAdapter.toComposeColor(tokens.brandPrimaryHover),
                onPrimaryContainer = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),

                secondary = ColorComposeAdapter.toComposeColor(tokens.supportDefault),
                onSecondary = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),
                secondaryContainer = ColorComposeAdapter.toComposeColor(tokens.supportHover),
                onSecondaryContainer = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),

                tertiary = ColorComposeAdapter.toComposeColor(tokens.infoFillDefault),
                onTertiary = ColorComposeAdapter.toComposeColor(tokens.infoText),
                tertiaryContainer = ColorComposeAdapter.toComposeColor(tokens.infoFillSoft),
                onTertiaryContainer = ColorComposeAdapter.toComposeColor(tokens.infoText),

                background = ColorComposeAdapter.toComposeColor(tokens.bgBase),
                onBackground = ColorComposeAdapter.toComposeColor(tokens.fgDefault),

                surface = ColorComposeAdapter.toComposeColor(tokens.surfaceDefault),
                onSurface = ColorComposeAdapter.toComposeColor(tokens.fgDefault),
                surfaceVariant = ColorComposeAdapter.toComposeColor(tokens.surfaceSubtle),
                onSurfaceVariant = ColorComposeAdapter.toComposeColor(tokens.fgSubtle),

                inverseSurface = ColorComposeAdapter.toComposeColor(tokens.surfaceRaised),
                inverseOnSurface = ColorComposeAdapter.toComposeColor(tokens.fgOnInverse),

                error = ColorComposeAdapter.toComposeColor(tokens.errorFillDefault),
                onError = ColorComposeAdapter.toComposeColor(tokens.errorText),
                errorContainer = ColorComposeAdapter.toComposeColor(tokens.errorFillSoft),
                onErrorContainer = ColorComposeAdapter.toComposeColor(tokens.errorText),

                outline = ColorComposeAdapter.toComposeColor(tokens.borderDefault),
                outlineVariant = ColorComposeAdapter.toComposeColor(tokens.borderSubtle),
                scrim = ColorComposeAdapter.toComposeColor(tokens.borderEmphasis),

                surfaceTint = ColorComposeAdapter.toComposeColor(tokens.brandPrimary)
            )
        } else {
            darkColorScheme(
                primary = ColorComposeAdapter.toComposeColor(tokens.brandPrimary),
                onPrimary = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),
                primaryContainer = ColorComposeAdapter.toComposeColor(tokens.brandPrimaryHover),
                onPrimaryContainer = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),

                secondary = ColorComposeAdapter.toComposeColor(tokens.supportDefault),
                onSecondary = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),
                secondaryContainer = ColorComposeAdapter.toComposeColor(tokens.supportHover),
                onSecondaryContainer = ColorComposeAdapter.toComposeColor(tokens.fgOnPrimary),

                tertiary = ColorComposeAdapter.toComposeColor(tokens.infoFillDefault),
                onTertiary = ColorComposeAdapter.toComposeColor(tokens.infoText),
                tertiaryContainer = ColorComposeAdapter.toComposeColor(tokens.infoFillSoft),
                onTertiaryContainer = ColorComposeAdapter.toComposeColor(tokens.infoText),

                background = ColorComposeAdapter.toComposeColor(tokens.bgBase),
                onBackground = ColorComposeAdapter.toComposeColor(tokens.fgDefault),

                surface = ColorComposeAdapter.toComposeColor(tokens.surfaceDefault),
                onSurface = ColorComposeAdapter.toComposeColor(tokens.fgDefault),
                surfaceVariant = ColorComposeAdapter.toComposeColor(tokens.surfaceSubtle),
                onSurfaceVariant = ColorComposeAdapter.toComposeColor(tokens.fgSubtle),

                inverseSurface = ColorComposeAdapter.toComposeColor(tokens.surfaceRaised),
                inverseOnSurface = ColorComposeAdapter.toComposeColor(tokens.fgOnInverse),

                error = ColorComposeAdapter.toComposeColor(tokens.errorFillDefault),
                onError = ColorComposeAdapter.toComposeColor(tokens.errorText),
                errorContainer = ColorComposeAdapter.toComposeColor(tokens.errorFillSoft),
                onErrorContainer = ColorComposeAdapter.toComposeColor(tokens.errorText),

                outline = ColorComposeAdapter.toComposeColor(tokens.borderDefault)
            )
        }
    }
}