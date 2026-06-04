package cruxui.android.maracuya.wrappers.components.buttons.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.theme.LibraryThemes
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.ui.components.buttons.button.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokens
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.ui.components.buttons.button.ButtonVariant
import cruxui.android.maracuya.wrappers.theme.MyLibraryThemeProvider

/**
 * Wrapper Android View que expone `ButtonMrcy` para layouts XML tradicionales.
 *
 * Mantiene una API mutable equivalente a los parámetros funcionales de Compose:
 * texto, enabled, shape, interactionSource, variante, tokens, overrides y progreso en press.
 */
class ButtonWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var buttonTextState by mutableStateOf("Button")
    private var modifierState by mutableStateOf(Modifier)
    private var enabledState by mutableStateOf(isEnabled)
    private var shapeState by mutableStateOf<Shape>(RoundedCornerShape(100.dp))
    private var interactionSourceState by mutableStateOf(MutableInteractionSource())
    private var buttonVariantState by mutableStateOf(ButtonVariant.PRIMARY)
    private var buttonTokensState by mutableStateOf<ButtonTokens?>(null)
    private var buttonTokensOverrideState by mutableStateOf<ButtonTokensOverride?>(null)
    private var buttonTokensOverrideNameState by mutableStateOf<String?>(null)
    private var showProgressOnPressState by mutableStateOf(true)

    init {
        val initialValues = ButtonAttributeParser.parse(context, attrs)
        buttonTextState = initialValues.text
        modifierState = initialValues.modifier as Modifier.Companion
        enabledState = initialValues.enabled
        shapeState = initialValues.shape
        interactionSourceState = initialValues.interactionSource
        buttonVariantState = initialValues.buttonVariant
        buttonTokensState = initialValues.buttonTokens
        buttonTokensOverrideState = initialValues.buttonTokensOverride
        buttonTokensOverrideNameState = initialValues.buttonTokensOverrideName
        showProgressOnPressState = initialValues.showProgressOnPress
    }

    var text: String
        get() = buttonTextState
        set(value) {
            buttonTextState = value
        }

    var modifier: Modifier
        get() = modifierState
        set(value) {
            modifierState = value as Modifier.Companion
        }

    var shape: Shape
        get() = shapeState
        set(value) {
            shapeState = value
        }

    var interactionSource: MutableInteractionSource
        get() = interactionSourceState
        set(value) {
            interactionSourceState = value
        }

    var buttonVariant: ButtonVariant
        get() = buttonVariantState
        set(value) {
            buttonVariantState = value
        }

    var buttonTokens: ButtonTokens?
        get() = buttonTokensState
        set(value) {
            buttonTokensState = value
        }

    var buttonTokensOverride: ButtonTokensOverride?
        get() = buttonTokensOverrideState
        set(value) {
            buttonTokensOverrideState = value
        }

    var buttonTokensOverrideName: String?
        get() = buttonTokensOverrideNameState
        set(value) {
            buttonTokensOverrideNameState = value
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
            ButtonContent()
            return
        }

        MyLibraryTheme(
            theme = LibraryThemes.fromName(themeConfig.themeName),
            style = themeConfig.themeStyle,
            useMaterial = themeConfig.useMaterial,
        ) {
            ButtonContent()
        }
    }

    @Composable
    private fun ButtonContent() {
        val resolvedButtonTokensOverride = buttonTokensOverrideState
            ?: ButtonTokensOverrideRegistry.resolve(buttonTokensOverrideNameState)

        ButtonMrcy(
            text = buttonTextState,
            onClick = ::performClick,
            modifier = modifierState,
            enabled = enabledState,
            shape = shapeState,
            interactionSource = interactionSourceState,
            buttonVariant = buttonVariantState,
            buttonTokens = buttonTokensState,
            buttonTokensOverride = resolvedButtonTokensOverride,
            showProgressOnPress = showProgressOnPressState,
        )
    }
}
