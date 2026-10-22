package cruxui.android.maracuya.wrappers.core.theme

import android.content.res.Configuration
import android.view.View
import cruxui.android.maracuya.theme.LibraryThemeSnapshot
import cruxui.android.maracuya.theme.LibraryThemes
import cruxui.android.maracuya.theme.ThemeStyle
import cruxui.android.maracuya.wrappers.theme.MyLibraryThemeProvider

/** The XML entry point exposes the complete shared DS model, without copying semantic roles. */
typealias AndroidThemeSnapshot = LibraryThemeSnapshot

object AndroidThemeResolver {
    fun resolve(view: View): LibraryThemeSnapshot {
        val config = MyLibraryThemeProvider.findFrom(view)?.currentConfig()
        val systemDark = view.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        val dark = when (config?.themeStyle) {
            ThemeStyle.DARK -> true
            ThemeStyle.LIGHT -> false
            else -> systemDark
        }
        return LibraryThemeSnapshot.resolve(LibraryThemes.fromName(config?.themeName), dark)
    }
}

/** Scoped notification through the View tree; no global subscription. */
internal interface AndroidThemeAware {
    fun refreshTheme()
}
