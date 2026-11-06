package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.util.TypedValue

import cruxui.android.lib.wrappers.core.tokens.*

/** Convierte la unidad original con la densidad actual, sin congelar el escalado. */
internal fun TextDimension.pixels(context: Context): Float =
    TypedValue.applyDimension(unit, value, context.resources.displayMetrics)
