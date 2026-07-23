package cruxui.android.maracuya.wrappers.components.buttons.navigation.group

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.ButtonNavigationGroupMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.wrappers.core.MrcyXmlComposeView

/**
 * Wrapper Android View que expone `ButtonNavigationGroupMrcy` para layouts XML tradicionales.
 *
 * Mantiene una API mutable equivalente a los parametros funcionales de Compose:
 * variante, labels, anchos, estado enabled del grupo, estado enabled por boton y comportamiento.
 */
class ButtonNavigationGroupWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MrcyXmlComposeView(context, attrs, defStyleAttr) {

    private var variantState by mutableStateOf(ButtonNavigationGroupVariant.BACK_CONTINUE)
    private var leadingLabelState by mutableStateOf<String?>(null)
    private var trailingLabelState by mutableStateOf<String?>(null)
    private var leadingButtonWidthState by mutableStateOf(ButtonNavigationGroupDefaults.LeadingButtonWidth)
    private var trailingButtonWidthState by mutableStateOf(ButtonNavigationGroupDefaults.TrailingButtonWidth)
    private var buttonSpacingState by mutableStateOf(ButtonNavigationGroupDefaults.ButtonSpacing)
    private var enabledState by mutableStateOf(isEnabled)
    private var leadingEnabledOverrideState by mutableStateOf<Boolean?>(null)
    private var trailingEnabledOverrideState by mutableStateOf<Boolean?>(null)
    private var buttonNavigationGroupBehaviorState by mutableStateOf(ButtonNavigationGroupBehavior.Default)
    private var onLeadingButtonClickState by mutableStateOf<(() -> Unit)?>(null)
    private var onTrailingButtonClickState by mutableStateOf<(() -> Unit)?>(null)

    init {
        val initialValues = ButtonNavigationGroupAttributeParser.parse(context, attrs)
        modifier = initialValues.modifier
        variantState = initialValues.variant
        leadingLabelState = initialValues.leadingLabel
        trailingLabelState = initialValues.trailingLabel
        leadingButtonWidthState = initialValues.leadingButtonWidth
        trailingButtonWidthState = initialValues.trailingButtonWidth
        buttonSpacingState = initialValues.buttonSpacing
        enabledState = initialValues.enabled
        leadingEnabledOverrideState = initialValues.leadingEnabledOverride
        trailingEnabledOverrideState = initialValues.trailingEnabledOverride
        buttonNavigationGroupBehaviorState = initialValues.buttonNavigationGroupBehavior
    }

    var variant: ButtonNavigationGroupVariant
        get() = variantState
        set(value) {
            variantState = value
        }

    var leadingLabel: String?
        get() = leadingLabelState
        set(value) {
            leadingLabelState = value
        }

    var trailingLabel: String?
        get() = trailingLabelState
        set(value) {
            trailingLabelState = value
        }

    var leadingButtonWidth: Dp
        get() = leadingButtonWidthState
        set(value) {
            leadingButtonWidthState = value
        }

    var trailingButtonWidth: Dp
        get() = trailingButtonWidthState
        set(value) {
            trailingButtonWidthState = value
        }

    var buttonSpacing: Dp
        get() = buttonSpacingState
        set(value) {
            buttonSpacingState = value
        }

    var leadingEnabled: Boolean
        get() = leadingEnabledOverrideState ?: enabledState
        set(value) {
            leadingEnabledOverrideState = value
        }

    var trailingEnabled: Boolean
        get() = trailingEnabledOverrideState ?: enabledState
        set(value) {
            trailingEnabledOverrideState = value
        }

    var buttonNavigationGroupBehavior: ButtonNavigationGroupBehavior
        get() = buttonNavigationGroupBehaviorState
        set(value) {
            buttonNavigationGroupBehaviorState = value
        }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    /** Define la accion del boton inicial del grupo. */
    fun setOnLeadingButtonClickListener(listener: (() -> Unit)?) {
        onLeadingButtonClickState = listener
    }

    /** Define la accion del boton final del grupo. */
    fun setOnTrailingButtonClickListener(listener: (() -> Unit)?) {
        onTrailingButtonClickState = listener
    }

    /** Elimina el override y vuelve a heredar `isEnabled` desde el grupo. */
    fun clearLeadingEnabledOverride() {
        leadingEnabledOverrideState = null
    }

    /** Elimina el override y vuelve a heredar `isEnabled` desde el grupo. */
    fun clearTrailingEnabledOverride() {
        trailingEnabledOverrideState = null
    }

    @Composable
    override fun Content() {
        WithXmlTheme {
            ButtonNavigationGroupContent()
        }
    }

    @Composable
    private fun ButtonNavigationGroupContent() {
        ButtonNavigationGroupMrcy(
            modifier = modifierState,
            variant = variantState,
            onLeadingButtonClick = ::dispatchLeadingButtonClick,
            onTrailingButtonClick = ::dispatchTrailingButtonClick,
            leadingLabel = leadingLabelState,
            trailingLabel = trailingLabelState,
            leadingButtonWidth = leadingButtonWidthState,
            trailingButtonWidth = trailingButtonWidthState,
            buttonSpacing = buttonSpacingState,
            enabled = enabledState,
            leadingEnabled = leadingEnabledOverrideState ?: enabledState,
            trailingEnabled = trailingEnabledOverrideState ?: enabledState,
            buttonNavigationGroupBehavior = buttonNavigationGroupBehaviorState,
        )
    }

    private fun dispatchLeadingButtonClick() {
        onLeadingButtonClickState?.invoke() ?: performClick()
    }

    private fun dispatchTrailingButtonClick() {
        onTrailingButtonClickState?.invoke() ?: performClick()
    }
}
