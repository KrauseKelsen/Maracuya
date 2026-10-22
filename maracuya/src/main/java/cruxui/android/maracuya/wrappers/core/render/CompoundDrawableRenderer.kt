package cruxui.android.maracuya.wrappers.core.render

import android.graphics.drawable.Drawable
import android.widget.TextView

/** Native parameters reusable by TextView, EditText and other compound-drawable hosts. */
internal data class CompoundDrawableRender(val managed: Boolean, val drawable: Drawable?, val tint: Int?)

internal class CompoundDrawableRenderer {
    private data class Slot(var managed: Boolean = false, var previous: Drawable? = null, var tint: Int? = null)
    private val start = Slot()
    private val end = Slot()
    private var previousPadding: Int? = null

    fun render(view: TextView, iconStart: CompoundDrawableRender, iconEnd: CompoundDrawableRender, paddingPx: Int?) {
        val drawables = view.compoundDrawablesRelative
        fun apply(slot: Slot, requested: CompoundDrawableRender, current: Drawable?): Drawable? {
            if (requested.managed) {
                if (!slot.managed) slot.previous = current
                slot.managed = true
                slot.tint = requested.tint
                return requested.drawable
            }
            if (!slot.managed) return current
            slot.managed = false
            slot.tint = null
            return slot.previous
        }
        val nextStart = apply(start, iconStart, drawables[0])
        val nextEnd = apply(end, iconEnd, drawables[2])
        if (nextStart !== drawables[0] || nextEnd !== drawables[2]) {
            view.setCompoundDrawablesRelative(nextStart, drawables[1], nextEnd, drawables[3])
        }
        if (paddingPx != null) {
            if (previousPadding == null) previousPadding = view.compoundDrawablePadding
            view.compoundDrawablePadding = paddingPx
        } else previousPadding?.let { view.compoundDrawablePadding = it; previousPadding = null }
    }

    fun color(view: TextView) {
        val drawables = view.compoundDrawablesRelative
        if (start.managed) drawables[0]?.setTint(start.tint ?: view.currentTextColor)
        if (end.managed) drawables[2]?.setTint(end.tint ?: view.currentTextColor)
    }
}
