package cruxui.android.maracuya.theme

import cruxui.android.maracuya.atoms.LibraryColorTokens
import cruxui.android.maracuya.semantics.CorporateIcons
import cruxui.android.maracuya.semantics.CorporateTypography
import cruxui.android.maracuya.tokens.base.FontFamilyToken

/**
 * Complete DS semantics, shared by Compose and Android Views without a composition or registry.
 * Collections keep their original types; platform adapters convert individual tokens at render time.
 */
data class LibraryThemeSnapshot(
    val colors: LibraryColorTokens,
    val typography: CorporateTypography,
    val icons: CorporateIcons,
    val fontFamily: FontFamilyToken,
) {
    companion object {
        fun resolve(theme: LibraryThemeSet, dark: Boolean): LibraryThemeSnapshot =
            LibraryThemeSnapshot(
                colors = if (dark) theme.darkTokens else theme.lightTokens,
                typography = CorporateTypography(),
                icons = CorporateIcons.fromAtoms(theme.iconAtoms),
                fontFamily = FontResources.forTheme(theme.name),
            )
    }
}
