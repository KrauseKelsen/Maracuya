package cruxui.android.lib.wrappers.core.attributes

import cruxui.android.lib.tokens.base.IconToken
import android.content.res.TypedArray
import android.util.TypedValue
import cruxui.android.lib.wrappers.core.tokens.*

/** Decodificación común: conserva ausencia y unidades ante cambios de configuración. */
internal fun TypedArray.surface(index: Int): ViewSurface? {
    if (!hasValueOrEmpty(index)) return null
    val id = getResourceId(index, 0)
    return if (id != 0) ViewSurface.Resource(id) else ViewSurface.Value(getDrawable(index))
}

internal fun TypedArray.optionalBoolean(index: Int): Boolean? = if (hasValue(index)) getBoolean(index, false) else null

internal fun TypedArray.optionalInt(index: Int): Int? = if (hasValue(index)) getInt(index, 0) else null

internal fun TypedArray.color(index: Int): TextColor? {
    if (!hasValue(index)) return null
    val id = getResourceId(index, 0)
    return if (id != 0) TextColor.Resource(id) else getColorStateList(index)?.let(TextColor::States)
}

internal fun TypedArray.dimension(index: Int): TextDimension? {
    if (!hasValue(index)) return null
    val typed = peekValue(index)
    // Conserva la unidad original para reevaluar el escalado al cambiar la configuración.
    return if (typed?.type == TypedValue.TYPE_DIMENSION) TextDimension(
        TypedValue.complexToFloat(typed.data), typed.data and TypedValue.COMPLEX_UNIT_MASK,
    ) else TextDimension(getDimension(index, 0f), TypedValue.COMPLEX_UNIT_PX)
}

/** Lee el atributo de icono propio antes del nativo; @null representa eliminación explícita. */
internal fun TypedArray.icon(custom: Int, native: Int): ViewIcon? {
    val index = if (hasValueOrEmpty(custom)) custom else native
    if (!hasValueOrEmpty(index)) return null
    val resource = getResourceId(index, 0)
    return if (resource == 0) ViewIcon.None else ViewIcon.Token(IconToken("xml", resource))
}
