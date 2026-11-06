package cruxui.android.lib.wrappers.core.theme

import android.content.res.Configuration
import android.view.View
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.theme.ThemeStyle
import cruxui.android.lib.wrappers.theme.MyLibraryThemeProvider

object AndroidThemeResolver {
    fun resolve(view: View): LibraryThemeSnapshot {
        val config = MyLibraryThemeProvider.findFrom(view)?.currentConfig()
        val systemDark = view.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        return LibraryThemes.fromName(config?.themeName).resolve(config?.themeStyle ?: ThemeStyle.AUTO, systemDark)
    }
}

/** Scoped notification through the View tree; no global subscription. */
internal interface AndroidThemeAware {
    fun refreshTheme()
}
