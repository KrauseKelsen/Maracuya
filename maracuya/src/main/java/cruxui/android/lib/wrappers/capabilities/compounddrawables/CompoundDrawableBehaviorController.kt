package cruxui.android.lib.wrappers.capabilities.compounddrawables

import cruxui.android.lib.tokens.spacings.InteractionSpacings
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
import cruxui.android.lib.R

/** Alias compatible de las posiciones relativas utilizadas por Android. */
typealias CompoundDrawableSlot = cruxui.android.lib.wrappers.core.behavior.CompoundDrawableSlot

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Añade acciones independientes a los drawables relativos de un widget Android con texto.
 *
 * Justificación:
 * Button, EditText y TextView pueden incorporar esta capacidad sin compartir variantes ni tokens.
 * CompoundDrawableGeometry calcula los límites; el controlador conserva la secuencia del gesto.
 *
 * Uso:
 * El componente delega eventos táctiles/accesibles antes de su implementación nativa, llama
 * onDrawableStateChanged tras actualizar estados y cancel al separarse de la ventana.
 * Las políticas se evalúan al despachar para evitar acciones obsoletas. Los iconos decorativos
 * sin listeners conservan el comportamiento táctil nativo. La herencia existe por compatibilidad
 * con la fachada histórica; los componentes nuevos usan composición de esta capacidad.
 *
 * @param minimumTargetSizeDp Ampliación táctil mínima; usar cero si el renderer ya reserva la celda.
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
open class CompoundDrawableBehaviorController(
    private val view: TextView,
    private val canClick: (CompoundDrawableSlot) -> Boolean = { true },
    private val canLongClick: (CompoundDrawableSlot) -> Boolean = { true },
    minimumTargetSizeDp: Float = InteractionSpacings.MinimumTargetSizeDp,
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
    private val geometry = CompoundDrawableGeometry(view, minimumTargetSizeDp * view.resources.displayMetrics.density)
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

    /** Ejecuta el listener con sonido y evento accesible estándar de Android. */
    fun performClick(slot: CompoundDrawableSlot): Boolean {
        val listener = actions(slot).click ?: return false
        if (!view.isEnabled || !canClick(slot) || drawable(slot) == null) return false
        view.playSoundEffect(SoundEffectConstants.CLICK)
        listener.onClick(view)
        view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED)
        return true
    }

    /** Si el listener devuelve false, permite el click sencillo al levantar el dedo. */
    fun performLongClick(slot: CompoundDrawableSlot): Boolean {
        val listener = actions(slot).longClick ?: return false
        if (!view.isEnabled || !canLongClick(slot) || drawable(slot) == null) return false
        if (!listener.onLongClick(view)) return false
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED)
        return true
    }

    /** true consume toda la secuencia del icono; no debe reenviarse al widget nativo. */
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
                // Cancela acciones del icono si la secuencia incorpora más de un puntero.
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

    /** Restaura el estado pressed del icono después de actualizar los estados nativos. */
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

    /** Cancela pulsaciones pendientes y limpia pressed, también al separar la View. */
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
        .mapNotNull { slot -> geometry.target(slot)?.let { slot to it } }
        .filter { (_, rect) -> rect.contains(x, y) }
        .minByOrNull { (_, rect) -> kotlin.math.abs(rect.centerX() - x) }
        ?.first

    private fun withinActiveTarget(x: Float, y: Float): Boolean {
        val rect = activeSlot?.let(geometry::target) ?: return false
        rect.inset(-touchSlop, -touchSlop)
        return rect.contains(x, y)
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
