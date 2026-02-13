package com.example.mylibrary.wrappers.components.buttons.primary

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import com.example.mylibrary.theme.LibraryThemes
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.ui.components.buttons.primary.PrimaryButtonMrcy
import com.example.mylibrary.wrappers.theme.MyLibraryThemeProvider

/**
 * Wrapper Android View para consumir [PrimaryButtonMrcy] desde layouts XML.
 *
 * SRP:
 * - Este wrapper solo expone API/atributos del botón y delega theming al host [com.example.mylibrary.wrappers.theme.MyLibraryThemeWrp].
 */
class PrimaryButtonWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var buttonTextState by mutableStateOf("Primary Button")
    private var showProgressOnPressState by mutableStateOf(true)
    private var enabledState by mutableStateOf(isEnabled)

    init {
        val initialValues = PrimaryButtonAttributeParser.parse(context, attrs)
        buttonTextState = initialValues.text
        showProgressOnPressState = initialValues.showProgressOnPress
        enabledState = initialValues.enabled
    }

    var text: String
        get() = buttonTextState
        set(value) {
            buttonTextState = value
        }

    var showProgressOnPress: Boolean
        get() = showProgressOnPressState
        set(value) {
            showProgressOnPressState = value
        }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    @Composable
    override fun Content() {
        val themeHost = MyLibraryThemeProvider.findFrom(this)
        val themeConfig = themeHost?.currentConfig()

        if (themeConfig == null) {
            PrimaryButtonMrcy(
                text = buttonTextState,
                onClick = ::performClick,
                modifier = Modifier,
                enabled = enabledState,
                showProgressOnPress = showProgressOnPressState,
            )
            return
        }

        MyLibraryTheme(
            theme = LibraryThemes.fromName(themeConfig.themeName),
            style = themeConfig.themeStyle,
            useMaterial = themeConfig.useMaterial,
        ) {
            PrimaryButtonMrcy(
                text = buttonTextState,
                onClick = ::performClick,
                modifier = Modifier,
                enabled = enabledState,
                showProgressOnPress = showProgressOnPressState,
            )
        }
    }
}