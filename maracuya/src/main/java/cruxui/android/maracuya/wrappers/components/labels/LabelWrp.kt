package cruxui.android.maracuya.wrappers.components.labels

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import cruxui.android.maracuya.theme.LibraryThemes
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.labels.LabelTokens
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import cruxui.android.maracuya.wrappers.theme.MyLibraryThemeProvider

/**
 * Wrapper Android View que permite usar `LabelMrcy` desde layouts XML.
 *
 * Expone propiedades mutables equivalentes a la API Compose del componente para que
 * pantallas híbridas puedan actualizar texto, estado de error, icono, optional text,
 * descripción de accesibilidad y tokens sin recrear la vista.
 */
class LabelWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var modifierState by mutableStateOf(Modifier)
    private var textState by mutableStateOf("Label")
    private var optionalTextState by mutableStateOf(false)
    private var showIconState by mutableStateOf(false)
    private var errorState by mutableStateOf(false)
    private var onClickContentDescriptionState by mutableStateOf<String?>(null)
    private var labelTokensState by mutableStateOf<LabelTokens?>(null)
    private var labelTokensOverrideState by mutableStateOf<LabelTokensOverride?>(null)
    private var labelTokensOverrideNameState by mutableStateOf<String?>(null)
    private var limitMaxLabelState by mutableStateOf(true)

    init {
        val initialValues = LabelAttributeParser.parse(context, attrs)
        modifierState = initialValues.modifier
        textState = initialValues.text
        optionalTextState = initialValues.optionalText
        showIconState = initialValues.showIcon
        errorState = initialValues.error
        onClickContentDescriptionState = initialValues.onClickContentDescription
        labelTokensState = initialValues.labelTokens
        labelTokensOverrideState = initialValues.labelTokensOverride
        labelTokensOverrideNameState = initialValues.labelTokensOverrideName
        limitMaxLabelState = initialValues.limitMaxLabel
    }

    var modifier: Modifier
        get() = modifierState
        set(value) {
            modifierState = value
        }

    var text: String
        get() = textState
        set(value) {
            textState = value
        }

    var optionalText: Boolean
        get() = optionalTextState
        set(value) {
            optionalTextState = value
        }

    var showIcon: Boolean
        get() = showIconState
        set(value) {
            showIconState = value
        }

    var error: Boolean
        get() = errorState
        set(value) {
            errorState = value
        }

    var onClickContentDescription: String?
        get() = onClickContentDescriptionState
        set(value) {
            onClickContentDescriptionState = value
        }

    var labelTokens: LabelTokens?
        get() = labelTokensState
        set(value) {
            labelTokensState = value
        }

    var labelTokensOverride: LabelTokensOverride?
        get() = labelTokensOverrideState
        set(value) {
            labelTokensOverrideState = value
        }

    var labelTokensOverrideName: String?
        get() = labelTokensOverrideNameState
        set(value) {
            labelTokensOverrideNameState = value
        }

    var limitMaxLabel: Boolean
        get() = limitMaxLabelState
        set(value) {
            limitMaxLabelState = value
        }

    /**
     * Renderiza el contenido Compose dentro del tema XML padre si existe.
     */
    @Composable
    override fun Content() {
        val themeHost = MyLibraryThemeProvider.findFrom(this)
        val themeConfig = themeHost?.currentConfig()

        if (themeConfig == null) {
            LabelContent()
            return
        }

        MyLibraryTheme(
            theme = LibraryThemes.fromName(themeConfig.themeName),
            style = themeConfig.themeStyle,
            useMaterial = themeConfig.useMaterial,
        ) {
            LabelContent()
        }
    }

    @Composable
    private fun LabelContent() {
        val resolvedLabelTokensOverride = labelTokensOverrideState
            ?: LabelTokensOverrideRegistry.resolve(labelTokensOverrideNameState)

        LabelMrcy(
            modifier = modifierState,
            text = textState,
            optionalText = optionalTextState,
            showIcon = showIconState,
            error = errorState,
            onClick = ::performClick,
            onClickContentDescription = onClickContentDescriptionState,
            labelTokens = labelTokensState,
            labelTokensOverride = resolvedLabelTokensOverride,
            limitMaxLabel = limitMaxLabelState,
        )
    }
}
