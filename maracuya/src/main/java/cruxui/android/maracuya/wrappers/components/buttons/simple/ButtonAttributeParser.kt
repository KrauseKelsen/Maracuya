package cruxui.android.maracuya.wrappers.components.buttons.simple

import android.content.Context
import android.util.AttributeSet
import cruxui.android.maracuya.R
import cruxui.android.maracuya.ui.components.buttons.button.ButtonVariant

/**
 * Resuelve atributos XML del wrapper `ButtonWrp` y los transforma en un objeto tipado
 * (`ButtonAttributes`).
 */
internal object ButtonAttributeParser {

    private const val DEFAULT_TEXT = "Button"
    private const val VARIANT_PRIMARY = 0
    private const val VARIANT_SECONDARY = 1

    fun parse(context: Context, attrs: AttributeSet?): ButtonAttributes {
        if (attrs == null) {
            return ButtonAttributes(
                text = DEFAULT_TEXT,
                enabled = true,
                buttonVariant = ButtonVariant.PRIMARY,
                showProgressOnPress = true,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonWrp)
        return try {
            val text = typedArray.getString(R.styleable.ButtonWrp_text)
                ?: DEFAULT_TEXT

            val showProgressOnPress = typedArray.getBoolean(
                R.styleable.ButtonWrp_showProgressOnPress,
                true,
            )

            val enabled = typedArray.getBoolean(
                R.styleable.ButtonWrp_isEnabled,
                true,
            )

            val buttonVariant = when (typedArray.getInt(R.styleable.ButtonWrp_buttonVariant, VARIANT_PRIMARY)) {
                VARIANT_SECONDARY -> ButtonVariant.SECONDARY
                else -> ButtonVariant.PRIMARY
            }

            ButtonAttributes(
                text = text,
                enabled = enabled,
                buttonVariant = buttonVariant,
                buttonTokensOverrideName = typedArray.getString(R.styleable.ButtonWrp_buttonTokensOverride),
                showProgressOnPress = showProgressOnPress,
            )
        } finally {
            typedArray.recycle()
        }
    }
}
