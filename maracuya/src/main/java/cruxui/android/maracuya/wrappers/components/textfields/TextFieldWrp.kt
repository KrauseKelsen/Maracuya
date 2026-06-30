package cruxui.android.maracuya.wrappers.components.textfields

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.textfields.TextFieldMrcy
import cruxui.android.maracuya.ui.components.textfields.TextFieldTokens
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant
import cruxui.android.maracuya.wrappers.core.MrcyXmlComposeView

/**
 * Wrapper Android View que expone `TextFieldMrcy` para layouts XML tradicionales.
 *
 * Soporta las dos sobrecargas del composable: las variantes de texto usan `String` y la
 * variante `PIN` usa `List<String>`. El wrapper conserva estado propio y re-renderiza
 * Compose cuando XML o código actualizan sus propiedades públicas.
 */
class TextFieldWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MrcyXmlComposeView(context, attrs, defStyleAttr) {

    private var valueState by mutableStateOf("")
    private var pinValueState by mutableStateOf(emptyList<String>())
    private var labelState by mutableStateOf("Label")
    private var placeholderState by mutableStateOf("Placeholder")
    private var lengthState by mutableIntStateOf(4)
    private var enabledState by mutableStateOf(isEnabled)
    private var readOnlyState by mutableStateOf(false)
    private var hasErrorState by mutableStateOf(false)
    private var optionalTextState by mutableStateOf(false)
    private var showLabelIconState by mutableStateOf(false)
    private var bottomTextState by mutableStateOf<String?>(null)
    private var showBottomIconState by mutableStateOf(false)
    private var textFieldVariantState by mutableStateOf(TextFieldVariant.DEFAULT)
    private var pinIconTokenState by mutableStateOf<IconToken?>(null)
    private var textFieldTokensState by mutableStateOf<TextFieldTokens?>(null)
    private var textFieldTokensOverrideNameState by mutableStateOf<String?>(null)
    private var enableImplicitTrailingClearState by mutableStateOf(true)
    private var onStringValueChangeState by mutableStateOf<((String) -> Unit)?>(null)
    private var onPinValueChangeState by mutableStateOf<((List<String>) -> Unit)?>(null)
    private var onTrailingIconClickState by mutableStateOf<(() -> Unit)?>(null)

    init {
        val initialValues = TextFieldAttributeParser.parse(context, attrs)
        valueState = initialValues.value
        pinValueState = initialValues.pinValue
        labelState = initialValues.label
        placeholderState = initialValues.placeholder
        lengthState = initialValues.length
        modifier = initialValues.modifier
        enabledState = initialValues.enabled
        readOnlyState = initialValues.readOnly
        hasErrorState = initialValues.hasError
        optionalTextState = initialValues.optionalText
        showLabelIconState = initialValues.showLabelIcon
        bottomTextState = initialValues.bottomText
        showBottomIconState = initialValues.showBottomIcon
        textFieldVariantState = initialValues.textFieldVariant
        pinIconTokenState = initialValues.pinIconToken
        textFieldTokensState = initialValues.textFieldTokens
        textFieldTokensOverrideNameState = initialValues.textFieldTokensOverrideName
        enableImplicitTrailingClearState = initialValues.enableImplicitTrailingClear
    }

    var value: String
        get() = valueState
        set(value) {
            valueState = value
            if (textFieldVariantState == TextFieldVariant.PIN) {
                pinValueState = value.toPinValue(lengthState)
            }
        }

    var pinValue: List<String>
        get() = pinValueState
        set(value) {
            pinValueState = value.normalizedPin(lengthState)
        }

    var label: String
        get() = labelState
        set(value) {
            labelState = value
        }

    var placeholder: String
        get() = placeholderState
        set(value) {
            placeholderState = value
        }

    var length: Int
        get() = lengthState
        set(value) {
            lengthState = value.coerceToSupportedPinLength()
            pinValueState = pinValueState.normalizedPin(lengthState)
        }

    var readOnly: Boolean
        get() = readOnlyState
        set(value) {
            readOnlyState = value
        }

    var hasError: Boolean
        get() = hasErrorState
        set(value) {
            hasErrorState = value
        }

    var optionalText: Boolean
        get() = optionalTextState
        set(value) {
            optionalTextState = value
        }

    var showLabelIcon: Boolean
        get() = showLabelIconState
        set(value) {
            showLabelIconState = value
        }

    var bottomText: String?
        get() = bottomTextState
        set(value) {
            bottomTextState = value
        }

    var showBottomIcon: Boolean
        get() = showBottomIconState
        set(value) {
            showBottomIconState = value
        }

    var textFieldVariant: TextFieldVariant
        get() = textFieldVariantState
        set(value) {
            textFieldVariantState = value
            if (value == TextFieldVariant.PIN && pinValueState.isEmpty()) {
                pinValueState = valueState.toPinValue(lengthState)
            }
        }

    var pinIconToken: IconToken?
        get() = pinIconTokenState
        set(value) {
            pinIconTokenState = value
        }

    var textFieldTokens: TextFieldTokens?
        get() = textFieldTokensState
        set(value) {
            textFieldTokensState = value
        }

    var textFieldTokensOverrideName: String?
        get() = textFieldTokensOverrideNameState
        set(value) {
            textFieldTokensOverrideNameState = value
        }

    var enableImplicitTrailingClear: Boolean
        get() = enableImplicitTrailingClearState
        set(value) {
            enableImplicitTrailingClearState = value
        }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    /** Define un callback programático para cambios de variantes basadas en `String`. */
    fun setOnValueChangeListener(listener: ((String) -> Unit)?) {
        onStringValueChangeState = listener
    }

    /** Define un callback programático para cambios de la variante `PIN`. */
    fun setOnPinValueChangeListener(listener: ((List<String>) -> Unit)?) {
        onPinValueChangeState = listener
    }

    /** Define la acción del ícono trailing para variantes como `FACE_ID` o `PASSWORD`. */
    fun setOnTrailingIconClickListener(listener: (() -> Unit)?) {
        onTrailingIconClickState = listener
    }

    @Composable
    override fun Content() {
        WithXmlTheme {
            TextFieldContent()
        }
    }

    @Composable
    private fun TextFieldContent() {
        val resolvedTokens = textFieldTokensState
            ?: TextFieldTokensOverrideRegistry.resolve(textFieldTokensOverrideNameState)

        if (textFieldVariantState == TextFieldVariant.PIN) {
            TextFieldMrcy(
                value = pinValueState.normalizedPin(lengthState),
                onValueChange = { next ->
                    pinValueState = next.normalizedPin(lengthState)
                    valueState = pinValueState.joinToString(separator = "")
                    onPinValueChangeState?.invoke(pinValueState)
                    onStringValueChangeState?.invoke(valueState)
                },
                label = labelState,
                length = lengthState,
                modifier = modifierState,
                enabled = enabledState,
                readOnly = readOnlyState,
                hasError = hasErrorState,
                optionalText = optionalTextState,
                showLabelIcon = showLabelIconState,
                bottomText = bottomTextState,
                showBottomIcon = showBottomIconState,
                textFieldVariant = TextFieldVariant.PIN,
                pinIconToken = pinIconTokenState,
                textFieldTokens = resolvedTokens,
            )
            return
        }

        TextFieldMrcy(
            value = valueState,
            onValueChange = { next ->
                valueState = next
                onStringValueChangeState?.invoke(next)
            },
            label = labelState,
            placeholder = placeholderState,
            modifier = modifierState,
            enabled = enabledState,
            readOnly = readOnlyState,
            hasError = hasErrorState,
            optionalText = optionalTextState,
            showLabelIcon = showLabelIconState,
            bottomText = bottomTextState,
            showBottomIcon = showBottomIconState,
            textFieldVariant = textFieldVariantState,
            onTrailingIconClick = onTrailingIconClickState,
            enableImplicitTrailingClear = enableImplicitTrailingClearState,
            textFieldTokens = resolvedTokens,
        )
    }

    private fun Int.coerceToSupportedPinLength(): Int = when (this) {
        6 -> 6
        else -> 4
    }

    private fun String.toPinValue(length: Int): List<String> {
        val digits = filter(Char::isDigit).take(length).map { it.toString() }
        return digits + List((length - digits.size).coerceAtLeast(0)) { "" }
    }

    private fun List<String>.normalizedPin(length: Int): List<String> {
        val digits = take(length).map { value -> value.takeLast(1).filter(Char::isDigit) }
        return digits + List((length - digits.size).coerceAtLeast(0)) { "" }
    }
}
