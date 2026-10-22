package cruxui.android.maracuya.wrappers.core.tokens

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import cruxui.android.maracuya.atoms.LibraryColorTokens
import cruxui.android.maracuya.semantics.CorporateTypography
import cruxui.android.maracuya.semantics.CorporateIcons
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.maracuya.wrappers.core.theme.AndroidThemeSnapshot

enum class TextColorRole { Foreground, Muted, Disabled, Error, Primary }
enum class TextTypographyRole { Label, Caption, Body }

/** Preserve sources instead of freezing theme roles or resource IDs into literal values. */
sealed interface TextColor {
    data class Value(@ColorInt val argb: Int) : TextColor
    data class Resource(@ColorRes val id: Int) : TextColor
    data class Role(val role: TextColorRole) : TextColor
    data class States(val colors: ColorStateList) : TextColor
    data class Token(val token: ColorToken) : TextColor
    data class Semantic(val select: (LibraryColorTokens) -> ColorToken) : TextColor
}

sealed interface TextFont {
    data class Resource(@FontRes val id: Int) : TextFont
    data class Value(val typeface: Typeface) : TextFont
    data class Family(val family: FontFamilyToken) : TextFont
    data class System(val name: String) : TextFont
}

sealed interface TextTypography {
    data class Role(val role: TextTypographyRole) : TextTypography
    data class Value(val token: TypographyToken) : TextTypography
    data class Semantic(val select: (CorporateTypography) -> TypographyToken) : TextTypography
}

/** Shared source for any View component accepting DS icons. None is an explicit removal. */
sealed interface ViewIcon {
    data object None : ViewIcon
    data class Token(val token: IconToken) : ViewIcon
    data class Semantic(val select: (CorporateIcons) -> IconToken) : ViewIcon
}

internal fun ViewIcon.resolve(theme: AndroidThemeSnapshot): IconToken? = when (this) {
    ViewIcon.None -> null
    is ViewIcon.Token -> token
    is ViewIcon.Semantic -> select(theme.icons)
}

data class TextDimension(val value: Float, val unit: Int = TypedValue.COMPLEX_UNIT_SP) {
    init {
        require(value.isFinite() && value >= 0) { "Text dimensions must be finite and non-negative" }
        require(unit in TypedValue.COMPLEX_UNIT_PX..TypedValue.COMPLEX_UNIT_MM)
    }

    internal fun pixels(context: Context): Float =
        TypedValue.applyDimension(unit, value, context.resources.displayMetrics)
}

/** Native line spacing: signed extra pixels plus a multiplier of the font height. */
data class TextLineSpacing(val extraPx: Float = 0f, val multiplier: Float = 1f) {
    init {
        require(extraPx.isFinite() && multiplier.isFinite() && multiplier >= 0)
    }
}

internal fun TextColor.resolve(context: Context, theme: AndroidThemeSnapshot): ColorStateList = when (this) {
    is TextColor.Value -> ColorStateList.valueOf(argb)
    is TextColor.Resource -> requireNotNull(AppCompatResources.getColorStateList(context, id))
    is TextColor.States -> colors
    is TextColor.Token -> ColorStateList.valueOf(AndroidTokenAdapter.color(token))
    is TextColor.Semantic -> ColorStateList.valueOf(AndroidTokenAdapter.color(select(theme.colors)))
    is TextColor.Role -> ColorStateList.valueOf(AndroidTokenAdapter.color(when (role) {
        TextColorRole.Foreground -> theme.colors.fgDefault
        TextColorRole.Muted -> theme.colors.fgMuted
        TextColorRole.Disabled -> theme.colors.fgSubtle
        TextColorRole.Error -> theme.colors.fgError
        TextColorRole.Primary -> theme.colors.brandPrimary
    }))
}

internal fun TextFont.resolve(context: Context, weight: Int, italic: Boolean): Typeface = when (this) {
    is TextFont.Resource -> Typeface.create(requireNotNull(ResourcesCompat.getFont(context, id)), weight, italic)
    is TextFont.Value -> Typeface.create(typeface, weight, italic)
    is TextFont.Family -> AndroidTokenAdapter.font(context, family, weight, italic)
    is TextFont.System -> Typeface.create(Typeface.create(name, Typeface.NORMAL), weight, italic)
}
