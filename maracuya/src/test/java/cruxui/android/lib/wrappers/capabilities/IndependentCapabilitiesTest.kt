package cruxui.android.lib.wrappers.capabilities

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.wrappers.capabilities.text.*
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableRender
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableRenderer
import cruxui.android.lib.wrappers.core.behavior.ViewGestureController
import cruxui.android.lib.wrappers.core.tokens.*
import cruxui.android.lib.wrappers.core.resources.AndroidIconLoader
import android.graphics.drawable.DrawableWrapper
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** Usa capacidades reales fuera de TextView del DS, sin registros ni tokens de ese componente. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class IndependentCapabilitiesTest {
    /** Los iconos horizontales, verticales y cuadrados conservan proporción al cambiar límites. */
    @Test fun `iconos Library conservan proporcion y tamano intrinseco al redimensionarse`() {
        val context = RuntimeEnvironment.getApplication()
        val icons = LibraryThemes.Maracuya.resolve(false).icons
        listOf(icons.general.link, icons.arrows.navigateNext, icons.alerts.info).forEach { token ->
            val fitted = AndroidIconLoader.load(context, token) as DrawableWrapper
            val asset = requireNotNull(fitted.drawable)
            assertEquals(fitted.bounds.width(), fitted.intrinsicWidth)
            assertEquals(fitted.bounds.height(), fitted.intrinsicHeight)
            listOf(40 to 40, 96 to 48, 24 to 72).forEach { (width, height) ->
                fitted.setBounds(5, 7, 5 + width, 7 + height)
                val scale = minOf(width.toFloat() / asset.intrinsicWidth, height.toFloat() / asset.intrinsicHeight)
                assertEquals(asset.intrinsicWidth * scale, asset.bounds.width().toFloat(), 1f)
                assertEquals(asset.intrinsicHeight * scale, asset.bounds.height().toFloat(), 1f)
                assertEquals(fitted.bounds.exactCenterX(), asset.bounds.exactCenterX(), 0.5f)
                assertEquals(fitted.bounds.exactCenterY(), asset.bounds.exactCenterY(), 0.5f)
            }
        }
    }

    /** La capacidad de celdas funciona en Button y restaura el tamaño de un drawable no gestionado. */
    @Test fun `Button recupera el asset decorativo al retirar su celda interactiva`() {
        val button = Button(RuntimeEnvironment.getApplication())
        val asset = ColorDrawable().apply { setBounds(0, 0, 24, 24) }
        button.setCompoundDrawablesRelative(null, null, asset, null)
        val renderer = CompoundDrawableRenderer()
        val unmanaged = CompoundDrawableRender(false, null, null)
        renderer.render(button, unmanaged, unmanaged, 8, endMinimumSizePx = 48)
        assertEquals(48, button.compoundDrawablesRelative[2]!!.bounds.width())
        assertEquals(24, asset.bounds.width())
        renderer.render(button, unmanaged, unmanaged, 8)
        assertSame(asset, button.compoundDrawablesRelative[2])
        assertEquals(24, asset.bounds.width())
    }

    @Test fun `Button utiliza las mismas metricas y conserva su listener`() {
        val context = RuntimeEnvironment.getApplication()
        val library = LibraryThemes.Maracuya.resolve(false)
        val button = Button(context)
        var clicks = 0
        button.setOnClickListener { clicks++ }
        val lower = NativeTextStyleOverrides(textSize = TextDimension(12f), weight = 900)
        val merged = lower.merge(NativeTextStyleOverrides(
            typography = TextTypography.Value(library.typography.headline24),
            textSize = TextDimension(29f), font = TextFont.Value(Typeface.DEFAULT),
        ))
        assertNull(merged.weight)
        val style = NativeTextStyleResolver.resolve(context, library, library.typography.body1, library.fontFamily, merged)
        NativeTextStyleRenderer().render(button, style)
        assertEquals(29f * context.resources.displayMetrics.scaledDensity, button.textSize, 0.01f)
        button.performClick()
        assertEquals(1, clicks)
    }

    @Test fun `gestos funcionan sobre View sin texto y respetan la politica al completar el gesto`() {
        val view = View(RuntimeEnvironment.getApplication())
        var active = true
        var doubles = 0
        val controller = ViewGestureController(view, { active }, { false }, { doubles++; true })
        val start = SystemClock.uptimeMillis()
        fun touch(action: Int, delay: Long): Boolean {
            val event = MotionEvent.obtain(start, start + delay, action, 10f, 10f, 0)
            return try { controller.onTouchEvent(event) } finally { event.recycle() }
        }
        assertTrue(touch(MotionEvent.ACTION_DOWN, 0))
        touch(MotionEvent.ACTION_UP, 30)
        touch(MotionEvent.ACTION_DOWN, 100)
        touch(MotionEvent.ACTION_UP, 130)
        assertEquals(1, doubles)
        touch(MotionEvent.ACTION_DOWN, 500)
        active = false
        assertFalse(touch(MotionEvent.ACTION_UP, 530))
        assertEquals(1, doubles)
        assertFalse(view.isPressed)
    }
}
