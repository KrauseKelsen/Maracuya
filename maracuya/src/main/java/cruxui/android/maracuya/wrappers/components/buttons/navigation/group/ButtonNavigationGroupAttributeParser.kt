package cruxui.android.maracuya.wrappers.components.buttons.navigation.group

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.R
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant

/**
 * Resuelve atributos XML de `ButtonNavigationGroupWrp` y los convierte en un modelo tipado.
 */
internal object ButtonNavigationGroupAttributeParser {

    private const val VARIANT_BACK_CONTINUE = 0
    private const val VARIANT_BACK_SUBMIT = 1
    private const val VARIANT_SKIP_CONTINUE = 2
    private const val VARIANT_SKIP_SUBMIT = 3

    fun parse(context: Context, attrs: AttributeSet?): ButtonNavigationGroupAttributes {
        if (attrs == null) {
            return ButtonNavigationGroupAttributes(
                variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
                enabled = true,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonNavigationGroupWrp)
        return try {
            ButtonNavigationGroupAttributes(
                variant = typedArray.readVariant(),
                leadingLabel = typedArray.getString(
                    R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupLeadingLabel,
                ),
                trailingLabel = typedArray.getString(
                    R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupTrailingLabel,
                ),
                leadingButtonWidth = typedArray.readDimensionDpOrDefault(
                    index = R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupLeadingButtonWidth,
                    context = context,
                    defaultValue = ButtonNavigationGroupDefaults.LeadingButtonWidth,
                ),
                trailingButtonWidth = typedArray.readDimensionDpOrDefault(
                    index = R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupTrailingButtonWidth,
                    context = context,
                    defaultValue = ButtonNavigationGroupDefaults.TrailingButtonWidth,
                ),
                buttonSpacing = typedArray.readDimensionDpOrDefault(
                    index = R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupButtonSpacing,
                    context = context,
                    defaultValue = ButtonNavigationGroupDefaults.ButtonSpacing,
                ),
                enabled = typedArray.getBoolean(
                    R.styleable.ButtonNavigationGroupWrp_isEnabled,
                    true,
                ),
                leadingEnabledOverride = typedArray.readOptionalBoolean(
                    R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupLeadingEnabled,
                ),
                trailingEnabledOverride = typedArray.readOptionalBoolean(
                    R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupTrailingEnabled,
                ),
                buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior(
                    isTrailingLoading = typedArray.getBoolean(
                        R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupIsTrailingLoading,
                        false,
                    ),
                    startsTrailingLoadingOnClick = typedArray.getBoolean(
                        R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupStartsTrailingLoadingOnClick,
                        false,
                    ),
                ),
            )
        } finally {
            typedArray.recycle()
        }
    }

    private fun TypedArray.readVariant(): ButtonNavigationGroupVariant =
        when (
            getInt(
                R.styleable.ButtonNavigationGroupWrp_buttonNavigationGroupVariant,
                VARIANT_BACK_CONTINUE,
            )
        ) {
            VARIANT_BACK_SUBMIT -> ButtonNavigationGroupVariant.BACK_SUBMIT
            VARIANT_SKIP_CONTINUE -> ButtonNavigationGroupVariant.SKIP_CONTINUE
            VARIANT_SKIP_SUBMIT -> ButtonNavigationGroupVariant.SKIP_SUBMIT
            else -> ButtonNavigationGroupVariant.BACK_CONTINUE
        }

    private fun TypedArray.readOptionalBoolean(index: Int): Boolean? =
        if (hasValue(index)) {
            getBoolean(index, true)
        } else {
            null
        }

    private fun TypedArray.readDimensionDpOrDefault(
        index: Int,
        context: Context,
        defaultValue: Dp,
    ): Dp {
        if (!hasValue(index)) return defaultValue

        val density = context.resources.displayMetrics.density
        return (getDimension(index, 0f) / density).dp
    }
}
