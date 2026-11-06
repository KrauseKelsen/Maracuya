package cruxui.android.lib.ui.previews.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.theme.ThemeStyle
import cruxui.android.lib.theme.LibraryThemeStyles

@Composable
fun PreviewWrapper(
    useMaterial: Boolean = false,
    style: ThemeStyle = LibraryThemeStyles.AUTO,
    content: @Composable () -> Unit
) {
    MyLibraryTheme(
        theme = LibraryThemes.Maracuya,
        style = style,
        useMaterial = useMaterial
    ) {
        // El background lo controla MyLibraryTheme (bgBase)
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}
