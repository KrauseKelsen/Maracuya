package cruxui.android.maracuya.wrappers.components.textviews.render

import android.util.TypedValue
import android.widget.TextView
import cruxui.android.maracuya.wrappers.components.textviews.tokensrender.TextViewTokensRender
import cruxui.android.maracuya.wrappers.components.textviews.tokensrender.TextViewColorRender
import cruxui.android.maracuya.wrappers.core.render.CompoundDrawableRenderer

/** Applies final native parameters; semantic choices belong to the resolvers. */
internal class TextViewRenderer {
    private val icons = CompoundDrawableRenderer()

    fun render(view: TextView, tokens: TextViewTokensRender) {
        view.typeface = tokens.typeface
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, tokens.textSizePx)
        view.letterSpacing = tokens.letterSpacingEm
        if (tokens.lineSpacing != null) view.setLineSpacing(tokens.lineSpacing.extraPx, tokens.lineSpacing.multiplier)
        else {
            view.setLineSpacing(0f, 1f)
            view.lineHeight = tokens.lineHeightPx
        }
        icons.render(view, tokens.iconStart, tokens.iconEnd, tokens.iconPaddingPx)
    }

    fun color(view: TextView, tokens: TextViewColorRender) {
        if (tokens.nativeColors != null) view.setTextColor(tokens.nativeColors) else view.setTextColor(tokens.color)
        icons.color(view)
    }
}
