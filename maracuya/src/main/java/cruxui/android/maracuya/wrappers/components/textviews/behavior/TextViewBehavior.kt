package cruxui.android.maracuya.wrappers.components.textviews.behavior

import android.view.View

/** Full declarative event configuration; familiar native listeners are kept at the Android edge. */
data class TextViewBehavior(
    val allowClick: Boolean = true,
    val allowLongClick: Boolean = true,
    val onClick: View.OnClickListener? = null,
    val onLongClick: View.OnLongClickListener? = null,
    val iconStart: TextViewIconBehavior = TextViewIconBehavior(),
    val iconEnd: TextViewIconBehavior = TextViewIconBehavior(),
)

data class TextViewIconBehavior(
    val allowClick: Boolean = true,
    val allowLongClick: Boolean = true,
    val onClick: View.OnClickListener? = null,
    val onLongClick: View.OnLongClickListener? = null,
    val contentDescription: CharSequence? = null,
)
