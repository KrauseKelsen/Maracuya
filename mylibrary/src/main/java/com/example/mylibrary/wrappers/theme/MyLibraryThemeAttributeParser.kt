package com.example.mylibrary.wrappers.theme

import android.content.Context
import android.util.AttributeSet
import com.example.mylibrary.R

internal object MyLibraryThemeAttributeParser {

    private const val DEFAULT_THEME_NAME = "Maracuya"

    fun parse(context: Context, attrs: AttributeSet?): MyLibraryThemeConfig {
        if (attrs == null) {
            return MyLibraryThemeConfig(
                themeName = DEFAULT_THEME_NAME,
                themeStyle = MyLibraryThemeWrpThemeMapper.toThemeStyle(null),
                useMaterial = false,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLibraryThemeWrp)
        return try {
            MyLibraryThemeConfig(
                themeName = typedArray.getString(R.styleable.MyLibraryThemeWrp_themeName)
                    ?: DEFAULT_THEME_NAME,
                themeStyle = MyLibraryThemeWrpThemeMapper.toThemeStyle(
                    typedArray.getString(R.styleable.MyLibraryThemeWrp_themeStyle),
                ),
                useMaterial = typedArray.getBoolean(R.styleable.MyLibraryThemeWrp_useMaterial, false),
            )
        } finally {
            typedArray.recycle()
        }
    }
}