package cruxui.android.maracuya.ui.previews.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.theme.LibraryThemes
import cruxui.android.maracuya.theme.ThemeStyle
import cruxui.android.maracuya.theme.LibraryThemeStyles

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
