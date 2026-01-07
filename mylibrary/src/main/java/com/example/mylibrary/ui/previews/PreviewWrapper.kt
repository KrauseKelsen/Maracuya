package com.example.mylibrary.ui.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.theme.LibraryThemes
import com.example.mylibrary.theme.ThemeStyle
import com.example.mylibrary.theme.LibraryThemeStyles

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
