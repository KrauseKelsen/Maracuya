package cruxui.android.maracuya.wrappers.components.buttons.navigation.simple

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationLoadingScope
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationVariant
import cruxui.android.maracuya.wrappers.core.MrcyXmlComposeView

/**
 * Wrapper Android View que expone `ButtonNavigationMrcy` para layouts XML tradicionales.
 *
 * Mantiene una API mutable equivalente a los parametros funcionales de Compose y usa
 * `performClick()` para integrar el onClick con el sistema de Views.
 */
class ButtonNavigationWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MrcyXmlComposeView(context, attrs, defStyleAttr) {

    private var labelState by mutableStateOf<String?>(null)
    private var enabledState by mutableStateOf(isEnabled)
    private var shapeState by mutableStateOf<Shape>(RoundedCornerShape(100.dp))
    private var interactionSourceState by mutableStateOf(MutableInteractionSource())
    private var variantState by mutableStateOf(ButtonNavigationVariant.PRIMARY)
    private var buttonNavigationBehaviorState by mutableStateOf(ButtonNavigationBehavior.Default)
    private var buttonNavigationTokensState by mutableStateOf<ButtonNavigationTokens?>(null)
    private var buttonNavigationTokensOverrideState by mutableStateOf<ButtonNavigationTokensOverride?>(null)
    private var buttonNavigationTokensOverrideNameState by mutableStateOf<String?>(null)
    private var onTrailingClickState by mutableStateOf<(ButtonNavigationLoadingScope.() -> Unit)?>(null)

    init {
        val initialValues = ButtonNavigationAttributeParser.parse(context, attrs)
        labelState = initialValues.label
        modifier = initialValues.modifier
        enabledState = initialValues.enabled
        shapeState = initialValues.shape
        interactionSourceState = initialValues.interactionSource
        variantState = initialValues.variant
        buttonNavigationBehaviorState = initialValues.buttonNavigationBehavior
        buttonNavigationTokensState = initialValues.buttonNavigationTokens
        buttonNavigationTokensOverrideState = initialValues.buttonNavigationTokensOverride
        buttonNavigationTokensOverrideNameState = initialValues.buttonNavigationTokensOverrideName
        onTrailingClickState = initialValues.onTrailingClick
    }

    var label: String?
        get() = labelState
        set(value) {
            labelState = value
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

    var variant: ButtonNavigationVariant
        get() = variantState
        set(value) {
            variantState = value
        }

    var buttonNavigationBehavior: ButtonNavigationBehavior
        get() = buttonNavigationBehaviorState
        set(value) {
            buttonNavigationBehaviorState = value
        }

    var buttonNavigationTokens: ButtonNavigationTokens?
        get() = buttonNavigationTokensState
        set(value) {
            buttonNavigationTokensState = value
        }

    var buttonNavigationTokensOverride: ButtonNavigationTokensOverride?
        get() = buttonNavigationTokensOverrideState
        set(value) {
            buttonNavigationTokensOverrideState = value
        }

    var buttonNavigationTokensOverrideName: String?
        get() = buttonNavigationTokensOverrideNameState
        set(value) {
            buttonNavigationTokensOverrideNameState = value
        }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    /** Define la accion opcional que controla el ciclo de loading del icono. */
    fun setOnTrailingClickListener(listener: (ButtonNavigationLoadingScope.() -> Unit)?) {
        onTrailingClickState = listener
    }

    @Composable
    override fun Content() {
        WithXmlTheme {
            ButtonNavigationContent()
        }
    }

    @Composable
    private fun ButtonNavigationContent() {
        val resolvedTokensOverride = buttonNavigationTokensOverrideState
            ?: ButtonNavigationTokensOverrideRegistry.resolve(buttonNavigationTokensOverrideNameState)

        ButtonNavigationMrcy(
            label = labelState,
            onClick = ::performClick,
            modifier = modifierState,
            enabled = enabledState,
            shape = shapeState,
            interactionSource = interactionSourceState,
            variant = variantState,
            buttonNavigationBehavior = buttonNavigationBehaviorState,
            buttonNavigationTokens = buttonNavigationTokensState,
            buttonNavigationTokensOverride = resolvedTokensOverride,
            onTrailingClick = onTrailingClickState,
        )
    }
}
