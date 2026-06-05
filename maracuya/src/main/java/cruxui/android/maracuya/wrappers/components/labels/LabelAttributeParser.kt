package cruxui.android.maracuya.wrappers.components.labels

import android.content.Context
import android.util.AttributeSet
import cruxui.android.maracuya.R

/**
 * Convierte los atributos XML de `LabelWrp` en un modelo interno tipado.
 *
 * Centralizar el parseo evita duplicar defaults en la vista y permite extender nuevos
 * atributos del label sin modificar la lógica de composición.
 */
internal object LabelAttributeParser {

    private const val DEFAULT_TEXT = "Label"

    /**
     * Lee los atributos declarados en XML y aplica defaults seguros cuando no existen.
     */
    fun parse(context: Context, attrs: AttributeSet?): LabelAttributes {
        if (attrs == null) {
            return LabelAttributes(text = DEFAULT_TEXT)
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelWrp)
        return try {
            LabelAttributes(
                text = typedArray.getString(R.styleable.LabelWrp_text) ?: DEFAULT_TEXT,
                optionalText = typedArray.getBoolean(R.styleable.LabelWrp_optionalText, false),
                showIcon = typedArray.getBoolean(R.styleable.LabelWrp_showIcon, false),
                error = typedArray.getBoolean(R.styleable.LabelWrp_error, false),
                onClickContentDescription = typedArray.getString(
                    R.styleable.LabelWrp_onClickContentDescription,
                ),
                labelTokensOverrideName = typedArray.getString(
                    R.styleable.LabelWrp_labelTokensOverride,
                ),
                limitMaxLabel = typedArray.getBoolean(R.styleable.LabelWrp_limitMaxLabel, true),
            )
        } finally {
            typedArray.recycle()
        }
    }
}
