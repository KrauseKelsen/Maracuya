package cruxui.android.maracuya.wrappers.core.behavior

import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.os.SystemClock
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import cruxui.android.maracuya.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LooperMode(LooperMode.Mode.PAUSED)
class CompoundDrawableBehaviorControllerTest {
    private fun view(rtl: Boolean = false) = TextView(RuntimeEnvironment.getApplication()).apply {
        context.applicationInfo.flags = context.applicationInfo.flags or ApplicationInfo.FLAG_SUPPORTS_RTL
        layoutDirection = if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        setPadding(12, 6, 14, 10)
        compoundDrawablePadding = 8
        setCompoundDrawablesRelative(
            ColorDrawable(Color.RED).apply { setBounds(0, 0, 24, 24) }, null,
            ColorDrawable(Color.BLUE).apply { setBounds(0, 0, 24, 24) }, null,
        )
        text = "Native text"
        measure(View.MeasureSpec.makeMeasureSpec(240, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(80, View.MeasureSpec.EXACTLY))
        layout(0, 0, 240, 80)
    }

    private fun touch(controller: CompoundDrawableBehaviorController, action: Int, x: Float,
        y: Float = 38f, down: Long = SystemClock.uptimeMillis()): Boolean {
        val event = MotionEvent.obtain(down, SystemClock.uptimeMillis(), action, x, y, 0)
        return try { controller.onTouchEvent(event) } finally { event.recycle() }
    }

    @Test fun `start and end taps dispatch independent native listeners and own the gesture`() {
        val view = view()
        val controller = CompoundDrawableBehaviorController(view)
        var startCalls = 0
        var endCalls = 0
        var bodyCalls = 0
        view.setOnClickListener { bodyCalls++ }
        controller.setOnClickListener(CompoundDrawableSlot.Start, { assertSame(view, it); startCalls++ })
        controller.setOnClickListener(CompoundDrawableSlot.End, { endCalls++ })
        assertTrue(touch(controller, MotionEvent.ACTION_DOWN, 24f))
        assertTrue(view.compoundDrawablesRelative[0].state.contains(android.R.attr.state_pressed))
        assertFalse(view.compoundDrawablesRelative[2].state.contains(android.R.attr.state_pressed))
        assertTrue(touch(controller, MotionEvent.ACTION_UP, 24f))
        assertFalse(view.compoundDrawablesRelative[0].state.contains(android.R.attr.state_pressed))
        touch(controller, MotionEvent.ACTION_DOWN, 214f)
        touch(controller, MotionEvent.ACTION_UP, 214f)
        assertEquals(1, startCalls)
        assertEquals(1, endCalls)
        assertEquals(0, bodyCalls)
        assertFalse(touch(controller, MotionEvent.ACTION_DOWN, 120f))
    }

    @Test fun `relative hit testing accounts for RTL padding scrolling and text gravity`() {
        val view = view(rtl = true)
        assertEquals(View.LAYOUT_DIRECTION_RTL, view.layoutDirection)
        view.gravity = Gravity.BOTTOM or Gravity.END
        view.scrollTo(60, 25)
        val controller = CompoundDrawableBehaviorController(view)
        var starts = 0
        var ends = 0
        controller.setOnClickListener(CompoundDrawableSlot.Start, { starts++ })
        controller.setOnClickListener(CompoundDrawableSlot.End, { ends++ })
        touch(controller, MotionEvent.ACTION_DOWN, 214f)
        touch(controller, MotionEvent.ACTION_UP, 214f)
        touch(controller, MotionEvent.ACTION_DOWN, 24f)
        touch(controller, MotionEvent.ACTION_UP, 24f)
        assertEquals(1, starts)
        assertEquals(1, ends)
    }

    @Test fun `long press consumes release click and cancel removes pending callback`() {
        val controller = CompoundDrawableBehaviorController(view())
        var clicks = 0
        var longs = 0
        controller.setOnClickListener(CompoundDrawableSlot.End, { clicks++ })
        controller.setOnLongClickListener(CompoundDrawableSlot.End, { longs++; true })
        val down = SystemClock.uptimeMillis()
        touch(controller, MotionEvent.ACTION_DOWN, 214f, down = down)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(ViewConfiguration.getLongPressTimeout().toLong() + 100))
        touch(controller, MotionEvent.ACTION_UP, 214f, down = down)
        assertEquals(1, longs)
        assertEquals(0, clicks)
        touch(controller, MotionEvent.ACTION_DOWN, 214f)
        controller.cancel()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(1))
        assertEquals(1, longs)
    }

    @Test fun `moving outside cancels click and long press`() {
        val controller = CompoundDrawableBehaviorController(view())
        var calls = 0
        controller.setOnClickListener(CompoundDrawableSlot.Start, { calls++ })
        controller.setOnLongClickListener(CompoundDrawableSlot.Start, { calls++; true })
        val down = SystemClock.uptimeMillis()
        touch(controller, MotionEvent.ACTION_DOWN, 24f, down = down)
        touch(controller, MotionEvent.ACTION_MOVE, 120f, down = down)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(1))
        touch(controller, MotionEvent.ACTION_UP, 24f, down = down)
        assertEquals(0, calls)
    }

    @Test fun `state changes during a gesture prevent action without falling through to text`() {
        val view = view()
        var allowed = true
        var calls = 0
        val controller = CompoundDrawableBehaviorController(view, canClick = { allowed })
        controller.setOnClickListener(CompoundDrawableSlot.Start, { calls++ })
        touch(controller, MotionEvent.ACTION_DOWN, 24f)
        allowed = false
        assertTrue(touch(controller, MotionEvent.ACTION_UP, 24f))
        assertEquals(0, calls)
        allowed = true
        touch(controller, MotionEvent.ACTION_DOWN, 24f)
        view.isEnabled = false
        controller.onDrawableStateChanged()
        assertTrue(touch(controller, MotionEvent.ACTION_UP, 24f))
        assertEquals(0, calls)
    }

    @Test fun `host labels become independent accessibility actions with same policy checks`() {
        val view = view()
        val controller = CompoundDrawableBehaviorController(view)
        var clicks = 0
        var longs = 0
        controller.setOnClickListener(CompoundDrawableSlot.Start, { clicks++ }, "Show help")
        controller.setOnLongClickListener(CompoundDrawableSlot.End, { longs++; true }, "Explain status")
        val info = AccessibilityNodeInfo.obtain()
        controller.onInitializeAccessibilityNodeInfo(info)
        assertEquals("Show help", info.actionList.single { it.id == R.id.action_icon_start_click }.label)
        assertEquals("Explain status", info.actionList.single { it.id == R.id.action_icon_end_long_click }.label)
        assertTrue(controller.performAccessibilityAction(R.id.action_icon_start_click, null))
        assertTrue(controller.performAccessibilityAction(R.id.action_icon_end_long_click, null))
        assertEquals(1, clicks)
        assertEquals(1, longs)
        view.isEnabled = false
        assertFalse(controller.performAccessibilityAction(R.id.action_icon_start_click, null))
        val disabledInfo = AccessibilityNodeInfo.obtain()
        controller.onInitializeAccessibilityNodeInfo(disabledInfo)
        assertTrue(disabledInfo.actionList.isEmpty())
        info.recycle()
        disabledInfo.recycle()
    }
}
