package cruxui.android.maracuya.wrappers.components.textviews.tokensrender

import android.content.res.ColorStateList
import android.graphics.Typeface
import cruxui.android.maracuya.wrappers.core.render.CompoundDrawableRender
import cruxui.android.maracuya.wrappers.core.tokens.TextLineSpacing

internal data class TextViewTokensRender(
    val typeface: Typeface,
    val textSizePx: Float,
    val lineHeightPx: Int,
    val lineSpacing: TextLineSpacing?,
    val letterSpacingEm: Float,
    val iconStart: CompoundDrawableRender,
    val iconEnd: CompoundDrawableRender,
    val iconPaddingPx: Int?,
)

internal data class TextViewColorRender(val color: Int, val nativeColors: ColorStateList?)
