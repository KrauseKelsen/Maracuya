package cruxui.android.maracuya.wrappers.components.textviews.variant

import android.content.Context
import cruxui.android.maracuya.ui.components.labels.variant.LabelVariantResolver

internal object TextViewVariantResolver {
    fun defaultText(context: Context, variant: TextViewVariant): CharSequence =
        LabelVariantResolver.defaultTextResource(variant)?.let(context::getString).orEmpty()
}
