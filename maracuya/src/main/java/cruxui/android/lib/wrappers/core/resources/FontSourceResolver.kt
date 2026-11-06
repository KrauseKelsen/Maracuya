package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import cruxui.android.lib.wrappers.core.resources.AndroidTokenAdapter

import cruxui.android.lib.wrappers.core.tokens.*

/** Resuelve la fuente nativa contra el contexto o biblioteca recibidos. */
internal fun TextFont.resolve(context: Context, weight: Int, italic: Boolean): Typeface = when (this) {
    is TextFont.Resource -> Typeface.create(requireNotNull(ResourcesCompat.getFont(context, id)), weight, italic)
    is TextFont.Value -> Typeface.create(typeface, weight, italic)
    is TextFont.Family -> AndroidTokenAdapter.font(context, family, weight, italic)
    is TextFont.System -> Typeface.create(Typeface.create(name, Typeface.NORMAL), weight, italic)
}
