package cruxui.android.maracuya.ui.components.labels.variant

import cruxui.android.maracuya.R
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokens

/** A single recipe consumed by Compose and Views, without framework state. */
object LabelVariantResolver {
    fun typography(variant: LabelVariant, tokens: LabelTokens) = when (variant) {
        LabelVariant.Default -> tokens.labelTypography
        LabelVariant.Optional -> tokens.optionalTypography
    }

    fun defaultTextResource(variant: LabelVariant): Int? = when (variant) {
        LabelVariant.Default -> null
        LabelVariant.Optional -> R.string.text_view_optional
    }
}
