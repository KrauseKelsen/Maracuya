package cruxui.android.lib.wrappers.core.behavior

import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ViewGestureController: Despacha gestos opcionales sobre cualquier View.
 *
 * Justificación:
 * La política active se inyecta desde behavior del componente. No consulta tokens, variantes ni selección de texto.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal class ViewGestureController(
    private val view: View,
    private val active: () -> Boolean,
    private val hasClick: () -> Boolean,
    private val doubleClick: () -> Boolean,
) {
    private var tracking = false
    private var canceled = false
    private val detector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean { canceled = false; return true }
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (!active() || !view.isEnabled || !hasClick()) return false
            return view.performClick()
        }
        override fun onDoubleTap(e: MotionEvent): Boolean = true
        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            if (e.actionMasked == MotionEvent.ACTION_UP && !canceled && active() && view.isEnabled) doubleClick()
            return true
        }
        override fun onLongPress(e: MotionEvent) {
            if (!canceled && active() && view.isEnabled) view.performLongClick()
        }
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, dx: Float, dy: Float): Boolean {
            canceled = true
            view.isPressed = false
            return true
        }
    })

    /** Consume la secuencia solo cuando la política del componente habilita esta capacidad. */
    fun onTouchEvent(event: MotionEvent): Boolean {
        if (!active()) { if (tracking) cancel(); return false }
        if (event.actionMasked == MotionEvent.ACTION_DOWN) tracking = true
        if (!tracking) return false
        if (event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            cancel()
            return true
        }
        detector.onTouchEvent(event)
        view.isPressed = view.isEnabled && !canceled && event.actionMasked != MotionEvent.ACTION_UP &&
            event.actionMasked != MotionEvent.ACTION_CANCEL
        if (event.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_CANCEL) tracking = false
        return true
    }

    /** Cancela eventos diferidos al cambiar de política o separar la View. */
    fun cancel() {
        val now = SystemClock.uptimeMillis()
        MotionEvent.obtain(now, now, MotionEvent.ACTION_CANCEL, 0f, 0f, 0).also {
            detector.onTouchEvent(it)
            it.recycle()
        }
        tracking = false
        canceled = true
        view.isPressed = false
    }
}
