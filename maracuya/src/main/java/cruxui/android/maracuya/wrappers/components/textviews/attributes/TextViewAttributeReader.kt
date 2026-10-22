package cruxui.android.maracuya.wrappers.components.textviews.attributes

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import cruxui.android.maracuya.R
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.wrappers.components.textviews.tokens.*
import cruxui.android.maracuya.wrappers.core.tokens.*
import cruxui.android.maracuya.wrappers.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.maracuya.wrappers.components.textviews.variant.TextViewVariant

internal data class TextViewAttributes(
    val variant: TextViewVariant,
    val error: Boolean,
    val hasText: Boolean,
    val iconStartDescription: CharSequence?,
    val iconEndDescription: CharSequence?,
    val tokens: TextViewTokensOverride,
)

/** Android owns style precedence; this reader only converts the resulting typed values. */
internal object TextViewAttributeReader {
    fun read(context: Context, attrs: AttributeSet?, defStyleAttr: Int): TextViewAttributes {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.DesignTextView, defStyleAttr, R.style.Widget_Maracuya_TextView,
        )
        return try {
            val appearance = a.getResourceId(R.styleable.DesignTextView_android_textAppearance, 0)
            TextViewAttributes(
                variant = when (a.getInt(R.styleable.DesignTextView_variant, 0)) {
                    0 -> TextViewVariant.Default
                    1 -> TextViewVariant.Optional
                    else -> error("Unknown TextView variant")
                },
                error = a.getBoolean(R.styleable.DesignTextView_error, false),
                hasText = a.hasValue(R.styleable.DesignTextView_android_text),
                iconStartDescription = a.getText(R.styleable.DesignTextView_iconStartContentDescription),
                iconEndDescription = a.getText(R.styleable.DesignTextView_iconEndContentDescription),
                tokens = (if (appearance != 0) readAppearance(context, appearance) else TextViewTokensOverride())
                    .merge(readTokens(a)),
            )
        } finally { a.recycle() }
    }

    fun readAppearance(context: Context, style: Int): TextViewTokensOverride {
        val a = context.obtainStyledAttributes(style, R.styleable.DesignTextView)
        return try { readTokens(a) } finally { a.recycle() }
    }

    private fun readTokens(a: TypedArray): TextViewTokensOverride {
        val style = a.optionalInt(R.styleable.DesignTextView_android_textStyle)
        val fontIndex = R.styleable.DesignTextView_android_fontFamily
        val fontRes = a.getResourceId(fontIndex, 0).takeIf {
            it != 0 && a.resources.getResourceTypeName(it) == "font"
        } ?: 0
        val lineHeight = a.dimension(R.styleable.DesignTextView_android_lineHeight)
        val hasLineSpacing = a.hasValue(R.styleable.DesignTextView_android_lineSpacingExtra) ||
            a.hasValue(R.styleable.DesignTextView_android_lineSpacingMultiplier)
        return TextViewTokensOverride(
            textColor = a.color(R.styleable.DesignTextView_android_textColor)
                ?: a.optionalInt(R.styleable.DesignTextView_textColorRole)?.let {
                    TextColor.Role(TextColorRole.entries.getOrNull(it) ?: error("Unknown color role"))
                },
            foregroundError = a.color(R.styleable.DesignTextView_errorTextColor),
            foregroundDisabled = a.color(R.styleable.DesignTextView_disabledTextColor),
            typography = a.optionalInt(R.styleable.DesignTextView_typography)?.let {
                TextTypography.Role(when (it) {
                    0 -> TextTypographyRole.Label
                    1 -> TextTypographyRole.Caption
                    2 -> TextTypographyRole.Body
                    else -> error("Unknown typography role")
                })
            },
            font = if (fontRes != 0) TextFont.Resource(fontRes)
                else a.getString(fontIndex)?.let(TextFont::System),
            textSize = a.dimension(R.styleable.DesignTextView_android_textSize),
            lineHeight = lineHeight,
            lineSpacing = if (lineHeight == null && hasLineSpacing) TextLineSpacing(
                a.getDimension(R.styleable.DesignTextView_android_lineSpacingExtra, 0f),
                a.getFloat(R.styleable.DesignTextView_android_lineSpacingMultiplier, 1f),
            ) else null,
            letterSpacingEm = if (a.hasValue(R.styleable.DesignTextView_android_letterSpacing))
                a.getFloat(R.styleable.DesignTextView_android_letterSpacing, 0f) else null,
            weight = a.optionalInt(R.styleable.DesignTextView_android_textFontWeight)?.takeIf { it > 0 }
                ?: style?.let { if (it and Typeface.BOLD != 0) 700 else 400 },
            italic = style?.let { it and Typeface.ITALIC != 0 },
            iconStart = a.icon(R.styleable.DesignTextView_iconStart, R.styleable.DesignTextView_android_drawableStart),
            iconEnd = a.icon(R.styleable.DesignTextView_iconEnd, R.styleable.DesignTextView_android_drawableEnd),
            showIconStart = a.optionalBoolean(R.styleable.DesignTextView_showIconStart),
            showIconEnd = a.optionalBoolean(R.styleable.DesignTextView_showIconEnd),
            iconPadding = a.dimension(R.styleable.DesignTextView_iconPadding)
                ?: a.dimension(R.styleable.DesignTextView_android_drawablePadding),
        )
    }

    private fun TypedArray.icon(custom: Int, native: Int): ViewIcon? {
        val index = if (hasValueOrEmpty(custom)) custom else native
        if (!hasValueOrEmpty(index)) return null
        val resource = getResourceId(index, 0)
        return if (resource == 0) ViewIcon.None else ViewIcon.Token(IconToken("xml", resource))
    }

    private fun TypedArray.optionalBoolean(index: Int): Boolean? = if (hasValue(index)) getBoolean(index, false) else null

    private fun TypedArray.optionalInt(index: Int): Int? = if (hasValue(index)) getInt(index, 0) else null

    private fun TypedArray.color(index: Int): TextColor? {
        if (!hasValue(index)) return null
        val id = getResourceId(index, 0)
        return if (id != 0) TextColor.Resource(id) else getColorStateList(index)?.let(TextColor::States)
    }

    private fun TypedArray.dimension(index: Int): TextDimension? {
        if (!hasValue(index)) return null
        val typed = peekValue(index)
        // Keep the original unit so font scaling can be applied on a configuration change.
        return if (typed?.type == TypedValue.TYPE_DIMENSION) TextDimension(
            TypedValue.complexToFloat(typed.data), typed.data and TypedValue.COMPLEX_UNIT_MASK,
        ) else TextDimension(getDimension(index, 0f), TypedValue.COMPLEX_UNIT_PX)
    }
}
