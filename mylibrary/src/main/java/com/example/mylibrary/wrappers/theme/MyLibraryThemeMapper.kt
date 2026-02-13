package com.example.mylibrary.wrappers.theme

import com.example.mylibrary.theme.ThemeStyle
import java.util.Locale

internal object MyLibraryThemeWrpThemeMapper {

    fun toThemeStyle(rawValue: String?): ThemeStyle {
        return when (rawValue?.trim()?.uppercase(Locale.ROOT)) {
            ThemeStyle.LIGHT.name -> ThemeStyle.LIGHT
            ThemeStyle.DARK.name -> ThemeStyle.DARK
            else -> ThemeStyle.AUTO
        }
    }
}