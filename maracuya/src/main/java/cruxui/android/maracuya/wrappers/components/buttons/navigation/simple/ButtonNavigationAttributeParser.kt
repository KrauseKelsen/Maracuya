package cruxui.android.maracuya.wrappers.components.buttons.navigation.simple

import android.content.Context
import android.util.AttributeSet
import cruxui.android.maracuya.R
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Resuelve atributos XML de `ButtonNavigationWrp` y los convierte en un modelo tipado.
 */
internal object ButtonNavigationAttributeParser {

    private const val VARIANT_PRIMARY = 0
    private const val VARIANT_SECONDARY = 1
    private const val VARIANT_PRIMARY_SQUARE = 2
    private const val VARIANT_SECONDARY_SQUARE = 3

    fun parse(context: Context, attrs: AttributeSet?): ButtonNavigationAttributes {
        if (attrs == null) {
            return ButtonNavigationAttributes(
                label = null,
                enabled = true,
                variant = ButtonNavigationVariant.PRIMARY,
                buttonNavigationBehavior = ButtonNavigationBehavior.Default,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonNavigationWrp)
        return try {
            val variant = when (
                typedArray.getInt(
                    R.styleable.ButtonNavigationWrp_buttonNavigationVariant,
                    VARIANT_PRIMARY,
                )
            ) {
                VARIANT_SECONDARY -> ButtonNavigationVariant.SECONDARY
                VARIANT_PRIMARY_SQUARE -> ButtonNavigationVariant.PRIMARYSQUARE
                VARIANT_SECONDARY_SQUARE -> ButtonNavigationVariant.SECONDARYSQUARE
                else -> ButtonNavigationVariant.PRIMARY
            }

            val behavior = ButtonNavigationBehavior(
                isLoading = typedArray.getBoolean(
                    R.styleable.ButtonNavigationWrp_buttonNavigationIsLoading,
                    false,
                ),
                startsLoadingOnClick = typedArray.getBoolean(
                    R.styleable.ButtonNavigationWrp_buttonNavigationStartsLoadingOnClick,
                    false,
                ),
                showIcon = typedArray.getBoolean(
                    R.styleable.ButtonNavigationWrp_buttonNavigationShowIcon,
                    true,
                ),
            )

            ButtonNavigationAttributes(
                label = typedArray.getString(R.styleable.ButtonNavigationWrp_buttonNavigationLabel),
                enabled = typedArray.getBoolean(R.styleable.ButtonNavigationWrp_isEnabled, true),
                variant = variant,
                buttonNavigationBehavior = behavior,
                buttonNavigationTokensOverrideName = typedArray.getString(
                    R.styleable.ButtonNavigationWrp_buttonNavigationTokensOverride,
                ),
            )
        } finally {
            typedArray.recycle()
        }
    }
}
