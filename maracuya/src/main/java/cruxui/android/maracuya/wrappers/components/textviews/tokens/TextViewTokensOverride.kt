package cruxui.android.maracuya.wrappers.components.textviews.tokens

import cruxui.android.maracuya.wrappers.core.tokens.ViewIcon
import cruxui.android.maracuya.wrappers.core.tokens.TextColor
import cruxui.android.maracuya.wrappers.core.tokens.TextDimension
import cruxui.android.maracuya.wrappers.core.tokens.TextFont
import cruxui.android.maracuya.wrappers.core.tokens.TextLineSpacing
import cruxui.android.maracuya.wrappers.core.tokens.TextTypography

/** Independent XML/View contract. Null means absent, including for boolean overrides. */
data class TextViewTokensOverride(
    val textColor: TextColor? = null,
    val foregroundDefault: TextColor? = null,
    val foregroundError: TextColor? = null,
    val foregroundDisabled: TextColor? = null,
    val typography: TextTypography? = null,
    val font: TextFont? = null,
    val textSize: TextDimension? = null,
    val lineHeight: TextDimension? = null,
    val lineSpacing: TextLineSpacing? = null,
    val letterSpacingEm: Float? = null,
    val weight: Int? = null,
    val italic: Boolean? = null,
    val iconStart: ViewIcon? = null,
    val iconEnd: ViewIcon? = null,
    val showIconStart: Boolean? = null,
    val showIconEnd: Boolean? = null,
    val iconPadding: TextDimension? = null,
) {
    init {
        require(lineHeight == null || lineSpacing == null) { "Choose lineHeight or native lineSpacing" }
        require(weight == null || weight in 1..1000)
        require(letterSpacingEm == null || letterSpacingEm.isFinite())
    }

    fun merge(next: TextViewTokensOverride) = TextViewTokensOverride(
        textColor = next.textColor ?: textColor,
        foregroundDefault = next.foregroundDefault ?: foregroundDefault,
        foregroundError = next.foregroundError ?: foregroundError,
        foregroundDisabled = next.foregroundDisabled ?: foregroundDisabled,
        typography = next.typography ?: typography,
        font = next.font ?: font,
        textSize = next.textSize ?: textSize,
        lineHeight = if (next.lineSpacing != null) null else next.lineHeight ?: lineHeight,
        lineSpacing = if (next.lineHeight != null) null else next.lineSpacing ?: lineSpacing,
        letterSpacingEm = next.letterSpacingEm ?: letterSpacingEm,
        weight = next.weight ?: weight,
        italic = next.italic ?: italic,
        iconStart = next.iconStart ?: iconStart,
        iconEnd = next.iconEnd ?: iconEnd,
        showIconStart = next.showIconStart ?: showIconStart,
        showIconEnd = next.showIconEnd ?: showIconEnd,
        iconPadding = next.iconPadding ?: iconPadding,
    )

    fun withoutColors() = copy(textColor = null, foregroundDefault = null, foregroundError = null, foregroundDisabled = null)
    fun withoutTypography() = copy(font = null, textSize = null, lineHeight = null, lineSpacing = null, letterSpacingEm = null, weight = null, italic = null)
}
