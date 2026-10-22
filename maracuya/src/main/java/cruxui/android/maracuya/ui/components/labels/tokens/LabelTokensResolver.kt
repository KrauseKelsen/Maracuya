package cruxui.android.maracuya.ui.components.labels.tokens

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.theme.LibraryThemeSnapshot

/** Shared component semantics. Composition only supplies the current theme. */
object LabelTokensResolver {

    @Composable
    fun resolve(
        tokens: LabelTokens? = null,
        override: LabelTokensOverride? = null,
    ): LabelTokens {
        if (tokens != null) return tokens.merge(override)
        return resolve(
            theme = LibraryThemeSnapshot(
                colors = LocalLibraryColorTokens.current,
                typography = LocalLibraryTypography.current,
                icons = LocalLibraryIcons.current,
                fontFamily = LocalFontFamily.current,
            ),
            override = override,
        )
    }

    /** Also used by XML components; needs neither a composition nor Android View values. */
    fun resolve(
        theme: LibraryThemeSnapshot,
        tokens: LabelTokens? = null,
        override: LabelTokensOverride? = null,
    ): LabelTokens {
        val base = tokens ?: LabelTokens(
            foregroundDefault = theme.colors.fgDefault,
            foregroundError = theme.colors.fgError,
            labelTypography = theme.typography.label,
            optionalTypography = theme.typography.caption,
            infoIcon = theme.icons.alerts.info,
            fontFamily = theme.fontFamily,
        )
        return base.merge(override)
    }

    private fun LabelTokens.merge(override: LabelTokensOverride?): LabelTokens {
        if (override == null) return this
        return copy(
            foregroundDefault = override.foregroundDefault ?: foregroundDefault,
            foregroundError = override.foregroundError ?: foregroundError,
            labelTypography = override.labelTypography ?: labelTypography,
            optionalTypography = override.optionalTypography ?: optionalTypography,
            infoIcon = override.infoIcon ?: infoIcon,
            fontFamily = override.fontFamily ?: fontFamily,
        )
    }
}
