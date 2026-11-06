package cruxui.android.lib.wrappers.core.resources

import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.theme.LibraryThemeSnapshot

import cruxui.android.lib.wrappers.core.tokens.*

/** Selecciona el token de icono sin cargar el drawable. */
internal fun ViewIcon.resolve(theme: LibraryThemeSnapshot): IconToken? = when (this) {
    ViewIcon.None -> null
    is ViewIcon.Token -> token
    is ViewIcon.Role -> theme.icons[role]
    is ViewIcon.Semantic -> select(theme.icons)
}
