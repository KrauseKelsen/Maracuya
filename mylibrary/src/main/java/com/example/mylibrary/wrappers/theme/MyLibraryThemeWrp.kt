package com.example.mylibrary.wrappers.theme

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.mylibrary.theme.ThemeStyle

/**
 * Contenedor XML para definir el tema de la librería una sola vez por jerarquía de Views.
 *
 * Los wrappers hijos (ej: [com.example.mylibrary.wrappers.components.buttons.primary.PrimaryButtonWrp]) consultan este host para aplicar
 * [com.example.mylibrary.theme.MyLibraryTheme] sin duplicar configuración por componente.
 */
class MyLibraryThemeWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var configState: MyLibraryThemeConfig =
        MyLibraryThemeAttributeParser.parse(context, attrs)

    var themeName: String
        get() = configState.themeName
        set(value) {
            configState = configState.copy(themeName = value)
        }

    var themeStyle: ThemeStyle
        get() = configState.themeStyle
        set(value) {
            configState = configState.copy(themeStyle = value)
        }

    var useMaterial: Boolean
        get() = configState.useMaterial
        set(value) {
            configState = configState.copy(useMaterial = value)
        }

    internal fun currentConfig(): MyLibraryThemeConfig = configState
}