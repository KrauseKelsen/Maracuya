package cruxui.android.lib.wrappers.core.tokens

import android.graphics.drawable.Drawable

/** Conserva la identidad de recursos. Value(null) retira explícitamente la superficie. */
sealed interface ViewSurface {
    data class Color(val color: TextColor) : ViewSurface
    data class Resource(val id: Int) : ViewSurface
    data class Value(val drawable: Drawable?) : ViewSurface
}
