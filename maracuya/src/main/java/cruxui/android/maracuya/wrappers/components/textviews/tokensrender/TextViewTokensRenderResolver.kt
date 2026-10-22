package cruxui.android.maracuya.wrappers.components.textviews.tokensrender

import android.content.Context
import cruxui.android.maracuya.wrappers.core.render.CompoundDrawableRender
import android.util.TypedValue
import cruxui.android.maracuya.core.text.TextVisualState
import cruxui.android.maracuya.theme.LibraryThemeSnapshot
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.wrappers.components.textviews.core.TextViewDefaults
import cruxui.android.maracuya.wrappers.components.textviews.tokens.TextViewResolvedTokens
import cruxui.android.maracuya.wrappers.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.maracuya.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.maracuya.wrappers.core.tokens.*
import kotlin.math.roundToInt

/** Converts final semantic decisions into native parameters, without reading CompositionLocals. */
internal object TextViewTokensRenderResolver {
    fun resolve(context: Context, theme: LibraryThemeSnapshot, tokens: TextViewResolvedTokens): TextViewTokensRender {
        val patch = tokens.override
        val typography = tokens.typography
        val size = (patch.textSize ?: TextDimension(typography.fontSize)).pixels(context)
        val spacingPx = TextDimension(kotlin.math.abs(typography.letterSpacing)).pixels(context) *
            if (typography.letterSpacing < 0) -1 else 1
        val weight = patch.weight ?: typography.weight
        val italic = patch.italic ?: typography.italic
        val start = icon(context, theme, patch.iconStart, patch.showIconStart, tokens.label.infoIcon)
        val end = icon(context, theme, patch.iconEnd, patch.showIconEnd, tokens.label.infoIcon)
        return TextViewTokensRender(
            typeface = patch.font?.resolve(context, weight, italic)
                ?: AndroidTokenAdapter.font(context, tokens.label.fontFamily, weight, italic),
            textSizePx = size,
            lineHeightPx = (patch.lineHeight ?: TextDimension(typography.lineHeight)).pixels(context).roundToInt(),
            lineSpacing = patch.lineSpacing,
            letterSpacingEm = patch.letterSpacingEm ?: if (size == 0f) 0f else spacingPx / size,
            iconStart = start,
            iconEnd = end,
            iconPaddingPx = (patch.iconPadding ?: if (start.managed || end.managed)
                TextDimension(TextViewDefaults.IconPaddingDp, TypedValue.COMPLEX_UNIT_DIP) else null)?.pixels(context)?.roundToInt(),
        )
    }

    private fun icon(context: Context, theme: LibraryThemeSnapshot, source: ViewIcon?, show: Boolean?, default: IconToken): CompoundDrawableRender {
        val token = if (show == false || source == ViewIcon.None) null
            else source?.resolve(theme) ?: if (show == true) default else null
        return CompoundDrawableRender(source != null || show != null,
            token?.let { AndroidTokenAdapter.icon(context, it) }, token?.iconColor?.let(AndroidTokenAdapter::color))
    }

    fun color(context: Context, theme: LibraryThemeSnapshot, tokens: TextViewResolvedTokens,
        state: TextVisualState, drawableState: IntArray, xml: TextViewTokensOverride, runtime: TextViewTokensOverride,
    ): TextViewColorRender {
        fun TextViewTokensOverride.source(): TextColor? = when (state) {
            TextVisualState.Disabled -> foregroundDisabled
            TextVisualState.Error -> foregroundError
            TextVisualState.Normal -> foregroundDefault
        } ?: textColor
        val source = runtime.source() ?: xml.source()
        val colors = source?.resolve(context, theme)
        val color = colors?.getColorForState(drawableState, colors.defaultColor) ?: AndroidTokenAdapter.color(when (state) {
            TextVisualState.Disabled -> tokens.foregroundDisabled
            TextVisualState.Error -> tokens.label.foregroundError
            TextVisualState.Normal -> tokens.label.foregroundDefault
        })
        fun TextViewTokensOverride.hasStateOverrides() = foregroundDefault != null || foregroundError != null || foregroundDisabled != null
        val native = when {
            runtime.hasStateOverrides() -> null
            runtime.textColor != null -> runtime.textColor.resolve(context, theme)
            xml.hasStateOverrides() -> null
            else -> xml.textColor?.resolve(context, theme)
        }
        return TextViewColorRender(color, native)
    }
}
