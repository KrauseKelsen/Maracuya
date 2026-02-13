package com.example.mylibrary.wrappers.theme

import com.example.mylibrary.theme.ThemeStyle

internal data class MyLibraryThemeConfig(
    val themeName: String,
    val themeStyle: ThemeStyle,
    val useMaterial: Boolean,
)