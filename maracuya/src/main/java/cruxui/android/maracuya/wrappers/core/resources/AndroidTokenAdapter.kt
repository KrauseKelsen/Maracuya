package cruxui.android.maracuya.wrappers.core.resources

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import cruxui.android.maracuya.theme.FontResourceCatalog
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import kotlin.math.roundToInt

/** A render value, created only at the boundary to an Android View. */
data class AndroidTypography(val token: TypographyToken, val typeface: Typeface)

/** Converts existing DS values to Android values; never reads a CompositionLocal. */
object AndroidTokenAdapter {
    fun color(token: ColorToken): Int = token.argb.toInt()

    fun font(
        context: Context,
        family: FontFamilyToken,
        weight: Int = 400,
        italic: Boolean = false,
    ): Typeface {
        val face = FontResourceCatalog.closest(family, weight, italic)
        val loaded = requireNotNull(ResourcesCompat.getFont(context, face.resourceId)) {
            "The theme font could not be loaded: $family"
        }
        return if (face.weight == weight && face.italic == italic) loaded
        else Typeface.create(loaded, weight, italic)
    }

    fun typography(context: Context, token: TypographyToken, family: FontFamilyToken): AndroidTypography =
        AndroidTypography(token, font(context, family, token.weight, token.italic))

    fun icon(context: Context, token: IconToken): Drawable =
        requireNotNull(AppCompatResources.getDrawable(context, token.filledResId)) {
            "Missing icon resource: ${token.name}"
        }.mutate().apply {
            val size = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, token.iconSize.toFloat(), context.resources.displayMetrics,
            ).roundToInt()
            setBounds(0, 0, size, size)
            token.iconColor?.let { setTint(color(it)) }
        }
}
