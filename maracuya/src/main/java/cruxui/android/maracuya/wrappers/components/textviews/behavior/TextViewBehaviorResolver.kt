package cruxui.android.maracuya.wrappers.components.textviews.behavior

import cruxui.android.maracuya.core.text.TextInteraction
import cruxui.android.maracuya.core.text.TextInteractionState
import cruxui.android.maracuya.wrappers.core.behavior.CompoundDrawableSlot

/** Events use the same enabled policy; visual overrides never grant interaction. */
internal object TextViewBehaviorResolver {
    fun canClick(behavior: TextViewBehavior, state: TextInteractionState) = TextInteraction.canActivate(state, behavior.allowClick)
    fun canLongClick(behavior: TextViewBehavior, state: TextInteractionState) = TextInteraction.canActivate(state, behavior.allowLongClick)
    fun icon(behavior: TextViewBehavior, slot: CompoundDrawableSlot) =
        if (slot == CompoundDrawableSlot.Start) behavior.iconStart else behavior.iconEnd
    fun canIconClick(behavior: TextViewBehavior, state: TextInteractionState, slot: CompoundDrawableSlot) =
        TextInteraction.canActivate(state, icon(behavior, slot).allowClick)
    fun canIconLongClick(behavior: TextViewBehavior, state: TextInteractionState, slot: CompoundDrawableSlot) =
        TextInteraction.canActivate(state, icon(behavior, slot).allowLongClick)
}
