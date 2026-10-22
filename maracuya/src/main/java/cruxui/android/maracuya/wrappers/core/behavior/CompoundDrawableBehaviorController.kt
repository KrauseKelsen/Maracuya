package cruxui.android.maracuya.wrappers.core.behavior

import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.view.GestureDetector
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewConfiguration
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import cruxui.android.maracuya.R
import kotlin.math.max

/** Logical positions; Android maps them to physical sides according to layoutDirection. */
enum class CompoundDrawableSlot { Start, End }

/**
 * Adds independent actions to a native TextView's relative compound drawables.
 *
 * The host delegates touch and accessibility callbacks here before its native implementation,
 * calls [onDrawableStateChanged] after updating drawables, and [cancel] when detached. Policies
 * are evaluated at dispatch time, so a state change during a gesture cannot trigger a stale action.
 * Decorative drawables without listeners retain TextView's normal touch behavior.
 */
class CompoundDrawableBehaviorController(
    private val view: TextView,
    private val canClick: (CompoundDrawableSlot) -> Boolean = { true },
    private val canLongClick: (CompoundDrawableSlot) -> Boolean = { true },
) {
    private class Actions {
        var click: View.OnClickListener? = null
        var longClick: View.OnLongClickListener? = null
        var clickLabel: CharSequence? = null
        var longClickLabel: CharSequence? = null
    }

    private val start = Actions()
    private val end = Actions()
    private var tracking = false
    private var activeSlot: CompoundDrawableSlot? = null
    private var activePointer = MotionEvent.INVALID_POINTER_ID
    private var canceled = false
    private var longPressHandled = false
    private val touchSlop = ViewConfiguration.get(view.context).scaledTouchSlop.toFloat()
    private val minimumTargetSize = 48f * view.resources.displayMetrics.density
    private val detector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean = true

        override fun onScroll(first: MotionEvent?, current: MotionEvent, dx: Float, dy: Float): Boolean {
            canceled = true
            updatePressedState()
            return true
        }

        override fun onLongPress(event: MotionEvent) {
            val slot = activeSlot ?: return
            if (tracking && !canceled) longPressHandled = performLongClick(slot)
        }
    })

    fun setOnClickListener(
        slot: CompoundDrawableSlot,
        listener: View.OnClickListener?,
        accessibilityLabel: CharSequence? = null,
    ) {
        actions(slot).apply { click = listener; clickLabel = accessibilityLabel }
        notifyAccessibilityChanged()
    }

    fun setOnLongClickListener(
        slot: CompoundDrawableSlot,
        listener: View.OnLongClickListener?,
        accessibilityLabel: CharSequence? = null,
    ) {
        actions(slot).apply { longClick = listener; longClickLabel = accessibilityLabel }
        notifyAccessibilityChanged()
    }

    /** Uses standard Android listener, sound and accessibility event semantics. */
    fun performClick(slot: CompoundDrawableSlot): Boolean {
        val listener = actions(slot).click ?: return false
        if (!view.isEnabled || !canClick(slot) || drawable(slot) == null) return false
        view.playSoundEffect(SoundEffectConstants.CLICK)
        listener.onClick(view)
        view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED)
        return true
    }

    /** Returning false from the listener permits the ordinary click when the finger is lifted. */
    fun performLongClick(slot: CompoundDrawableSlot): Boolean {
        val listener = actions(slot).longClick ?: return false
        if (!view.isEnabled || !canLongClick(slot) || drawable(slot) == null) return false
        if (!listener.onLongClick(view)) return false
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED)
        return true
    }

    /** True owns the complete icon gesture; the host must not pass it to TextView.onTouchEvent. */
    fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            cancel()
            activeSlot = hit(event.x, event.y) ?: return false
            tracking = true
            activePointer = event.getPointerId(0)
            updatePressedState()
            detector.onTouchEvent(event)
            return true
        }
        if (!tracking) return false
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                val pointer = event.findPointerIndex(activePointer)
                if (pointer < 0 || !withinActiveTarget(event.getX(pointer), event.getY(pointer))) {
                    canceled = true
                    cancelDetector()
                } else detector.onTouchEvent(event)
                updatePressedState()
            }
            MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                // A multi-touch gesture belongs to the surrounding UI, never to an icon action.
                canceled = true
                cancelDetector()
                updatePressedState()
            }
            MotionEvent.ACTION_UP -> {
                detector.onTouchEvent(event)
                val slot = activeSlot
                val activate = !canceled && !longPressHandled &&
                    withinActiveTarget(event.x, event.y)
                cancel()
                if (activate && slot != null) performClick(slot)
            }
            MotionEvent.ACTION_CANCEL -> cancel()
        }
        return true
    }

    /** Reapplies the active icon's pressed state after the host refreshes drawable states. */
    fun onDrawableStateChanged() {
        if (!view.isEnabled) {
            canceled = true
            cancelDetector()
        }
        updatePressedState()
    }

    fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        for (slot in CompoundDrawableSlot.entries) {
            if (drawable(slot) == null || !view.isEnabled) continue
            val actions = actions(slot)
            if (actions.click != null && canClick(slot)) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction(
                    clickAction(slot), actions.clickLabel ?: view.context.getString(clickLabel(slot)),
                ))
                info.isScreenReaderFocusable = true
            }
            if (actions.longClick != null && canLongClick(slot)) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction(
                    longClickAction(slot), actions.longClickLabel ?: view.context.getString(longClickLabel(slot)),
                ))
                info.isScreenReaderFocusable = true
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean = when (action) {
        R.id.action_icon_start_click -> performClick(CompoundDrawableSlot.Start)
        R.id.action_icon_end_click -> performClick(CompoundDrawableSlot.End)
        R.id.action_icon_start_long_click -> performLongClick(CompoundDrawableSlot.Start)
        R.id.action_icon_end_long_click -> performLongClick(CompoundDrawableSlot.End)
        else -> false
    }

    /** Cancels pending long presses and removes pressed state, including on detach. */
    fun cancel() {
        cancelDetector()
        tracking = false
        activeSlot = null
        activePointer = MotionEvent.INVALID_POINTER_ID
        canceled = false
        longPressHandled = false
        updatePressedState()
    }

    private fun cancelDetector() {
        val now = SystemClock.uptimeMillis()
        val cancel = MotionEvent.obtain(now, now, MotionEvent.ACTION_CANCEL, 0f, 0f, 0)
        detector.onTouchEvent(cancel)
        cancel.recycle()
    }

    private fun actions(slot: CompoundDrawableSlot) = if (slot == CompoundDrawableSlot.Start) start else end

    private fun drawable(slot: CompoundDrawableSlot): Drawable? =
        view.compoundDrawablesRelative[if (slot == CompoundDrawableSlot.Start) 0 else 2]

    private fun hit(x: Float, y: Float): CompoundDrawableSlot? = CompoundDrawableSlot.entries
        .filter { actions(it).let { actions -> actions.click != null || actions.longClick != null } }
        .mapNotNull { slot -> target(slot)?.let { slot to it } }
        .filter { (_, rect) -> rect.contains(x, y) }
        .minByOrNull { (_, rect) -> kotlin.math.abs(rect.centerX() - x) }
        ?.first

    private fun withinActiveTarget(x: Float, y: Float): Boolean {
        val rect = activeSlot?.let(::target) ?: return false
        rect.inset(-touchSlop, -touchSlop)
        return rect.contains(x, y)
    }

    /**
     * Matches TextView's compound drawable placement, independent of text gravity. Android
     * centers side drawables between compound top/bottom padding; its draw translation cancels
     * scrollX/scrollY, so MotionEvent and these bounds both use viewport coordinates.
     */
    private fun target(slot: CompoundDrawableSlot): RectF? {
        val icon = drawable(slot) ?: return null
        val bounds = icon.bounds
        if (bounds.isEmpty || view.width <= 0 || view.height <= 0) return null
        val isLeft = (slot == CompoundDrawableSlot.Start) != (view.layoutDirection == View.LAYOUT_DIRECTION_RTL)
        val left = if (isLeft) view.paddingLeft.toFloat()
            else (view.width - view.paddingRight - bounds.width()).toFloat()
        val space = view.height - view.compoundPaddingTop - view.compoundPaddingBottom
        val top = view.compoundPaddingTop + (space - bounds.height()) / 2f
        return RectF(left + bounds.left, top + bounds.top, left + bounds.right, top + bounds.bottom).apply {
            // Extend small icons to a useful touch target, clipped to the host's visible bounds.
            inset(-max(0f, (minimumTargetSize - width()) / 2f), -max(0f, (minimumTargetSize - height()) / 2f))
            intersect(0f, 0f, view.width.toFloat(), view.height.toFloat())
        }
    }

    private fun updatePressedState() {
        val nativeState = view.drawableState.filter { it != android.R.attr.state_pressed }.toIntArray()
        for (slot in CompoundDrawableSlot.entries) {
            val pressed = tracking && !canceled && activeSlot == slot && view.isEnabled &&
                ((actions(slot).click != null && canClick(slot)) ||
                    (actions(slot).longClick != null && canLongClick(slot)))
            drawable(slot)?.state = if (pressed) nativeState + android.R.attr.state_pressed else nativeState
        }
        view.invalidate()
    }

    private fun notifyAccessibilityChanged() {
        view.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
    }

    private fun clickAction(slot: CompoundDrawableSlot) = if (slot == CompoundDrawableSlot.Start)
        R.id.action_icon_start_click else R.id.action_icon_end_click

    private fun longClickAction(slot: CompoundDrawableSlot) = if (slot == CompoundDrawableSlot.Start)
        R.id.action_icon_start_long_click else R.id.action_icon_end_long_click

    private fun clickLabel(slot: CompoundDrawableSlot) = if (slot == CompoundDrawableSlot.Start)
        R.string.action_icon_start_click else R.string.action_icon_end_click

    private fun longClickLabel(slot: CompoundDrawableSlot) = if (slot == CompoundDrawableSlot.Start)
        R.string.action_icon_start_long_click else R.string.action_icon_end_long_click
}
