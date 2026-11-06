package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.content.res.AppCompatResources
import cruxui.android.lib.theme.catalog.get
import cruxui.android.lib.theme.LibraryThemeSnapshot

import cruxui.android.lib.wrappers.core.tokens.*

/** Resuelve la fuente nativa contra el contexto o biblioteca recibidos. */
internal fun TextColor.resolve(context: Context, theme: LibraryThemeSnapshot): ColorStateList = when (this) {
    is TextColor.Value -> ColorStateList.valueOf(argb)
    is TextColor.Resource -> requireNotNull(AppCompatResources.getColorStateList(context, id))
    is TextColor.States -> colors
    is TextColor.Token -> ColorStateList.valueOf(AndroidTokenAdapter.color(token))
    is TextColor.Semantic -> ColorStateList.valueOf(AndroidTokenAdapter.color(select(theme.colors)))
    is TextColor.Role -> ColorStateList.valueOf(AndroidTokenAdapter.color(theme.colors[role]))
}
