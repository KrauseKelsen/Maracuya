package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.appcompat.content.res.AppCompatResources
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.wrappers.core.tokens.ViewSurface

/** Convierte una superficie para cualquier View sin decidir su estado ni su uso visual. */
internal fun ViewSurface.resolve(context: Context, library: LibraryThemeSnapshot, state: IntArray): Drawable? = when (this) {
    is ViewSurface.Value -> drawable
    is ViewSurface.Resource -> AppCompatResources.getDrawable(context, id)?.mutate()
    is ViewSurface.Color -> color.resolve(context, library).let {
        it.getColorForState(state, it.defaultColor).toDrawable()
    }
}
