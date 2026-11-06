package cruxui.android.lib.wrappers.components.textviews

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.os.SystemClock
import android.text.SpannableString
import android.text.Spanned
import android.text.Layout
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Gravity
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import cruxui.android.lib.R
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
import cruxui.android.lib.theme.*
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.fonts.TextAlignTokens
import cruxui.android.lib.wrappers.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.wrappers.components.textviews.tokens.*
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.lib.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.lib.wrappers.core.tokens.*
import cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TextViewTest {
    private val context = ContextThemeWrapper(RuntimeEnvironment.getApplication(), androidx.appcompat.R.style.Theme_AppCompat_Light)
    private fun sp(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)
    @After fun cleanRegistry() { TextViewTokensOverrideRegistry.clear() }

    @Test fun `default native view consumes exactly the new Compose semantic defaults`() {
        val view = TextView(context)
        val shared = TextViewTokenDefaults.resolve(view.getThemeTokens())
        assertEquals(androidx.appcompat.widget.AppCompatTextView::class.java, view.javaClass.superclass)
        assertEquals(TextViewVariant.DEFAULT, view.getVariant())
        assertEquals(AndroidTokenAdapter.color(shared.textColor), view.currentTextColor)
        assertEquals(sp(shared.typography.fontSize), view.textSize, 0.01f)
        assertEquals("", view.text.toString())
        assertNull(view.compoundDrawablesRelative[0])
        assertNull(view.compoundDrawablesRelative[2])
        assertEquals(shared.backgroundColor.argb.toInt(), (view.background as ColorDrawable).color)
        assertEquals(shared.foregroundColor.argb.toInt(), (view.foreground as ColorDrawable).color)
    }

    @Test fun `hipertext preserves text and paint flags and uses the link color`() {
        val view = TextView(context)
        view.text = "Condiciones"
        view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        view.setVariant(TextViewVariant.HIPERTEXT)
        assertEquals(view.getThemeTokens().colors.linkDefault.argb.toInt(), view.currentTextColor)
        assertTrue(view.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0)
        view.setVariant(TextViewVariant.DEFAULT)
        assertFalse(view.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0)
        assertTrue(view.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG != 0)
        assertEquals("Condiciones", view.text.toString())
    }

    @Test fun `real layout inflates native styles states and explicit icons`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val link = root.findViewById<TextView>(R.id.nativeTextHipertext)
        val error = root.findViewById<TextView>(R.id.nativeTextError)
        val disabled = root.findViewById<TextView>(R.id.nativeTextDisabled)
        val styled = root.findViewById<TextView>(R.id.nativeTextCustom)
        assertEquals(TextViewVariant.HIPERTEXT, link.getVariant())
        assertTrue(error.hasError())
        assertNull(error.compoundDrawablesRelative[2])
        assertFalse(disabled.isEnabled)
        assertEquals(disabled.getThemeTokens().colors.fgSubtle.argb.toInt(), disabled.currentTextColor)
        assertEquals(sp(styled.getThemeTokens().typography.caption.fontSize), styled.textSize, 0.01f)
    }

    @Test fun `registered recipe receives the current library and all semantic fields reach Android`() {
        TextViewTokensOverrideRegistry.register("sectionTitle") { library: LibraryThemeSnapshot ->
            TextViewTokensOverride(
                typography = library.typography.headline24,
                textColor = library.colors.brandPrimary,
                errorColor = ColorToken(0xff102030),
                disabledColor = ColorToken(0xff405060),
                backgroundColor = ColorToken(0xffaabbcc),
                foregroundColor = ColorToken(0x10010203),
                fontFamily = library.fontFamily,
                iconStart = library.icons.alerts.info,
                iconEnd = library.icons.alerts.warning,
            )
        }
        val view = TextView(context)
        view.setTokensOverrideName("SECTIONTITLE")
        assertEquals(sp(view.getThemeTokens().typography.headline24.fontSize), view.textSize, 0.01f)
        assertEquals(view.getThemeTokens().colors.brandPrimary.argb.toInt(), view.currentTextColor)
        assertEquals(0xffaabbcc.toInt(), (view.background as ColorDrawable).color)
        assertEquals(0x10010203, (view.foreground as ColorDrawable).color)
        assertNotNull(view.compoundDrawablesRelative[0])
        assertNotNull(view.compoundDrawablesRelative[2])
        view.setErrorState(true)
        assertEquals(0xff102030.toInt(), view.currentTextColor)
        view.isEnabled = false
        assertEquals(0xff405060.toInt(), view.currentTextColor)
        view.isEnabled = true
        view.setErrorState(false)
        val updated = view.getThemeTokens().copy(colors = view.getThemeTokens().colors.copy(brandPrimary = ColorToken(0xff135790)))
        view.setThemeTokens(updated)
        assertEquals(0xff135790.toInt(), view.currentTextColor)
        view.setTokensOverrideName(null)
        assertEquals(updated.colors.fgDefault.argb.toInt(), view.currentTextColor)
        assertNull(view.compoundDrawablesRelative[0])
    }

    @Test fun `XML selects registry recipe and local attributes override it`() {
        TextViewTokensOverrideRegistry.register("sectionTitle") { library: LibraryThemeSnapshot ->
            TextViewTokensOverride(typography = library.typography.headline24, textColor = ColorToken(0xff123456))
        }
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_registry_test, null)
        val view = root.findViewById<TextView>(R.id.registeredTitle)
        assertEquals("sectionTitle", view.getTokensOverrideName())
        assertEquals(sp(21f), view.textSize, 0.01f)
        assertEquals(0xff123456.toInt(), view.currentTextColor)
        view.setTokensOverride(TextViewTokensOverride(typography = view.getThemeTokens().typography.headline36))
        assertEquals(sp(36f), view.textSize, 0.01f)
        view.setTextSizeSp(40f)
        view.refreshTheme()
        assertEquals(sp(40f), view.textSize, 0.01f)
        view.setTypography(view.getThemeTokens().typography.headline24)
        assertEquals(sp(24f), view.textSize, 0.01f)
        view.clearOverrides()
        assertEquals(sp(21f), view.textSize, 0.01f)
    }

    @Test fun `native setters have precedence and clearing restores recipe and XML`() {
        TextViewTokensOverrideRegistry.register("title", TextViewTokensOverride(textColor = ColorToken(0xff123456)))
        val view = TextView(context)
        view.setTokensOverrideName("title")
        view.setTextColor(Color.YELLOW)
        view.setErrorState(true)
        view.isEnabled = false
        assertEquals(Color.YELLOW, view.currentTextColor)
        view.clearColorOverride()
        assertEquals(view.getThemeTokens().colors.fgSubtle.argb.toInt(), view.currentTextColor)
        view.isEnabled = true
        view.setErrorState(false)
        assertEquals(0xff123456.toInt(), view.currentTextColor)
        val styled = LayoutInflater.from(context).inflate(R.layout.text_view_native_style_test, null).findViewById<TextView>(R.id.nativeTextStyled)
        val xmlColor = styled.currentTextColor
        styled.setTextColor(Color.YELLOW)
        styled.clearColorOverride()
        assertEquals(xmlColor, styled.currentTextColor)
    }

    @Test fun `semantic text color changes normal state while native color applies to all states`() {
        val view = TextView(context)
        view.setTokensOverride(TextViewTokensOverride(textColor = ColorToken(0xffff00ff)))
        assertEquals(Color.MAGENTA, view.currentTextColor)
        view.setErrorState(true)
        assertEquals(view.getThemeTokens().colors.fgError.argb.toInt(), view.currentTextColor)
        view.setTextColor(Color.BLUE)
        view.setErrorState(false)
        assertEquals(Color.BLUE, view.currentTextColor)
        view.isEnabled = false
        assertEquals(Color.BLUE, view.currentTextColor)
    }

    @Test fun `pressed disabled and custom error selectors remain native state lists`() {
        val view = TextView(context)
        val colors = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf(R.attr.state_error), intArrayOf(android.R.attr.state_pressed), intArrayOf()),
            intArrayOf(Color.GRAY, Color.RED, Color.BLUE, Color.GREEN))
        view.setTextColor(colors)
        assertSame(colors, view.textColors)
        view.isPressed = true
        assertEquals(Color.BLUE, view.currentTextColor)
        view.setErrorState(true)
        assertEquals(Color.RED, view.currentTextColor)
        view.isEnabled = false
        assertEquals(Color.GRAY, view.currentTextColor)
    }

    @Test @GraphicsMode(GraphicsMode.Mode.NATIVE)
    fun `native font size weight italic and spacing survive variant and theme refresh`() {
        val view = TextView(context)
        view.setTextSizeSp(23f)
        view.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC)
        view.letterSpacing = 0.12f
        view.setVariant(TextViewVariant.HIPERTEXT)
        view.setErrorState(true)
        view.refreshTheme()
        assertEquals(sp(23f), view.textSize, 0.01f)
        assertTrue(view.typeface.isItalic)
        assertTrue(view.typeface.isBold)
        assertEquals(0.12f, view.letterSpacing, 0.001f)
    }

    @Test fun `line height and spacing are mutually exclusive and survive rerender`() {
        val view = TextView(context)
        view.setLineSpacing(8f, 1.3f)
        view.setTextColor(Color.BLUE)
        view.refreshTheme()
        assertEquals(8f, view.lineSpacingExtra, 0.01f)
        assertEquals(1.3f, view.lineSpacingMultiplier, 0.01f)
        view.setLineHeight(50)
        assertNull(view.getNativeOverrides().lineSpacing)
        assertEquals(1f, view.lineSpacingMultiplier, 0.01f)
        view.setLineSpacing(4f, 1.1f)
        assertNull(view.getNativeOverrides().lineHeight)
        view.setLineHeight(TypedValue.COMPLEX_UNIT_SP, 40f)
        assertEquals(40f, view.getNativeOverrides().lineHeight!!.value, 0.01f)
    }

    @Test fun `XML line spacing is restored when runtime typography is cleared`() {
        val view = LayoutInflater.from(context).inflate(R.layout.text_view_native_style_test, null).findViewById<TextView>(R.id.nativeTextLocalStyled)
        view.setLineHeight(50)
        view.setTypography(view.getThemeTokens().typography.headline36)
        view.clearTypographyOverrides()
        assertNull(view.getNativeOverrides().typography)
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, context.resources.displayMetrics), view.lineSpacingExtra, 0.01f)
        assertEquals(1.2f, view.lineSpacingMultiplier, 0.01f)
    }

    @Test fun `DS alignment and native paragraph setters preserve vertical gravity and reset`() {
        context.applicationInfo.flags = context.applicationInfo.flags or android.content.pm.ApplicationInfo.FLAG_SUPPORTS_RTL
        val view = TextView(context)
        val original = view.textAlignment
        val typography = view.getThemeTokens().typography.body1
        view.setTypography(typography.copy(textAlign = TextAlignTokens.Center))
        assertEquals(View.TEXT_ALIGNMENT_CENTER, view.textAlignment)
        view.gravity = Gravity.END or Gravity.BOTTOM
        view.refreshTheme()
        assertEquals(View.TEXT_ALIGNMENT_GRAVITY, view.textAlignment)
        assertEquals(Gravity.END or Gravity.BOTTOM, view.gravity)
        view.setTypography(typography.copy(textAlign = TextAlignTokens.Justify))
        assertEquals(Layout.JUSTIFICATION_MODE_INTER_WORD, view.justificationMode)
        view.justificationMode = Layout.JUSTIFICATION_MODE_NONE
        view.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        view.refreshTheme()
        assertEquals(View.TEXT_ALIGNMENT_TEXT_END, view.textAlignment)
        assertEquals(Layout.JUSTIFICATION_MODE_NONE, view.justificationMode)
        view.clearTypographyOverrides()
        assertEquals(original, view.textAlignment)
        assertEquals(Layout.JUSTIFICATION_MODE_NONE, view.justificationMode)
    }

    @Test fun `DS alignment also renders when the host does not support RTL`() {
        context.applicationInfo.flags = context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SUPPORTS_RTL.inv()
        val view = TextView(context)
        val vertical = view.gravity and Gravity.VERTICAL_GRAVITY_MASK
        view.setTypography(view.getThemeTokens().typography.body1.copy(textAlign = TextAlignTokens.Center))
        assertEquals(Gravity.CENTER_HORIZONTAL, view.gravity and Gravity.HORIZONTAL_GRAVITY_MASK)
        assertEquals(vertical, view.gravity and Gravity.VERTICAL_GRAVITY_MASK)
    }

    @Test fun `theme provider updates descendants without leaking across nested scopes`() {
        val outer = MyLibraryThemeWrp(context)
        val nested = MyLibraryThemeWrp(context)
        val view = TextView(context)
        val inner = TextView(context)
        outer.addView(view)
        outer.addView(nested)
        nested.addView(inner)
        nested.themeStyle = ThemeStyle.LIGHT
        val light = inner.currentTextColor
        outer.themeStyle = ThemeStyle.DARK
        assertEquals(view.getThemeTokens().colors.fgDefault.argb.toInt(), view.currentTextColor)
        assertEquals(light, inner.currentTextColor)
        assertNotEquals(light, view.currentTextColor)
    }

    @Test fun `native setText keeps spans and selection without variant content injection`() {
        val view = TextView(context)
        val text = SpannableString("Copiar información")
        val span = StyleSpan(Typeface.BOLD)
        text.setSpan(span, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.text = text
        view.setBehavior(TextViewBehavior.Selectable)
        view.setVariant(TextViewVariant.HIPERTEXT)
        view.refreshTheme()
        assertEquals(text.toString(), view.text.toString())
        assertTrue(view.isTextSelectable)
        assertEquals(1, (view.text as Spanned).getSpans(0, 6, StyleSpan::class.java).size)
    }

    @Test fun `selection owns long and double gestures while listeners survive toggling`() {
        val view = TextView(context)
        FrameLayout(context).addView(view)
        var clicks = 0; var held = 0; var doubles = 0
        view.setOnClickListener { clicks++ }
        view.setOnLongClickListener { held++; true }
        view.setOnDoubleClickListener { doubles++ }
        view.setBehavior(TextViewBehavior.Selectable)
        view.performClick()
        view.performLongClick()
        assertFalse(view.performDoubleClick())
        assertEquals(1, clicks)
        assertEquals(0, held)
        view.setBehavior(TextViewBehavior.Default)
        assertTrue(view.performLongClick())
        assertTrue(view.performDoubleClick())
        assertEquals(1, held)
        assertEquals(1, doubles)
        view.isEnabled = false
        assertFalse(view.performClick())
        assertFalse(view.callOnClick())
        assertFalse(view.performLongClick())
        assertFalse(view.performDoubleClick())
    }

    @Test fun `pointer double tap does not also dispatch single click`() {
        val view = TextView(context)
        var singles = 0; var doubles = 0
        view.setOnClickListener { singles++ }
        view.setOnDoubleClickListener { doubles++ }
        view.layout(0, 0, 300, 80)
        tap(view, 100f, 40f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(60))
        tap(view, 100f, 40f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(400))
        assertEquals(0, singles)
        assertEquals(1, doubles)
        tap(view, 100f, 40f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(400))
        assertEquals(1, singles)
    }

    @Test fun `double only publishes accessible activation and disabled removes it`() {
        val view = TextView(context)
        var calls = 0
        view.setOnDoubleClickListener { calls++ }
        assertTrue(view.performAccessibilityAction(AccessibilityNodeInfo.ACTION_CLICK, null))
        assertEquals(1, calls)
        view.isEnabled = false
        assertFalse(view.performAccessibilityAction(AccessibilityNodeInfo.ACTION_CLICK, null))
        assertEquals(1, calls)
    }

    @Test fun `icons can be hidden removed and activated independently from text`() {
        val view = TextView(context)
        val library = view.getThemeTokens()
        view.setIconStart(library.icons.alerts.info)
        view.setIconEnd(library.icons.alerts.warning)
        var body = 0; var start = 0; var end = 0; var held = 0
        view.setOnClickListener { body++ }
        view.setOnIconStartClickListener { start++ }
        view.setOnIconEndClickListener { end++ }
        view.setOnIconEndLongClickListener { held++; true }
        view.setIconStartContentDescription("Ayuda")
        view.setIconEndContentDescription("Información")
        view.measure(View.MeasureSpec.makeMeasureSpec(240, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(80, View.MeasureSpec.EXACTLY))
        view.layout(0, 0, 240, 80)
        tap(view, 9f, 40f)
        assertEquals(1, start)
        assertEquals(0, body)
        val info = AccessibilityNodeInfo.obtain()
        view.onInitializeAccessibilityNodeInfo(info)
        assertEquals("Información", info.actionList.single { it.id == R.id.action_icon_end_click }.label)
        assertTrue(view.performAccessibilityAction(R.id.action_icon_end_click, null))
        assertTrue(view.performAccessibilityAction(R.id.action_icon_end_long_click, null))
        assertEquals(1, end)
        assertEquals(1, held)
        view.setShowIconStart(false)
        assertFalse(view.performIconStartClick())
        view.setTokensOverride(TextViewTokensOverride(iconStart = library.icons.alerts.info))
        assertNotNull(view.compoundDrawablesRelative[0])
        view.setShowIconStart(true)
        assertNotNull(view.compoundDrawablesRelative[0])
        view.setIconEnd(null as IconToken?)
        assertNull(view.compoundDrawablesRelative[2])
        view.isEnabled = false
        assertFalse(view.performIconStartClick())
        assertFalse(view.performClick())
        info.recycle()
    }

    @Test fun `background foreground and textAppearance setters survive theme refresh`() {
        val view = TextView(context)
        view.setBackgroundColor(Color.BLUE)
        val foreground = ColorDrawable(Color.GREEN)
        view.foreground = foreground
        view.setTextAppearance(R.style.TextAppearance_Maracuya_Headline24)
        view.refreshTheme()
        assertEquals(Color.BLUE, (view.background as ColorDrawable).color)
        assertSame(foreground, view.foreground)
        assertEquals(sp(24f), view.textSize, 0.01f)
        view.error = "Inválido"
        assertTrue(view.hasError())
        view.error = null
        assertFalse(view.hasError())
    }


    @Test fun `la celda interactiva conserva padding y se retira al quitar la accion`() {
        val view = TextView(context)
        view.text = "Etiqueta"
        view.setPaddingRelative(7, 3, 11, 5)
        view.setIconEnd(view.getThemeTokens().icons.alerts.info)
        val assetSize = view.compoundDrawablesRelative[2]!!.bounds.width()
        view.setIconEndContentDescription("Información")
        view.setOnIconEndClickListener {}
        val cellSize = (48 * context.resources.displayMetrics.density).toInt()
        assertEquals(cellSize, view.compoundDrawablesRelative[2]!!.bounds.width())
        view.measure(View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.AT_MOST))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        assertEquals(7, view.paddingStart)
        assertEquals(11, view.paddingEnd)
        assertFalse(view.includeFontPadding)
        view.isEnabled = false
        assertEquals(cellSize, view.compoundDrawablesRelative[2]!!.bounds.width())
        view.setOnIconEndClickListener(null)
        assertEquals(assetSize, view.compoundDrawablesRelative[2]!!.bounds.width())
        view.setIconEnd(null as IconToken?)
        assertNull(view.compoundDrawablesRelative[2])
        view.setTypography(view.getThemeTokens().typography.caption)
        view.measure(View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.AT_MOST))
        assertTrue("Recupera el ancho intrínseco tras un ancho fijo", view.measuredWidth < 500)
    }

    @Test fun `la galeria conserva los nueve casos y no agrega iconos de error`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val ids = listOf(R.id.nativeTextDefault, R.id.nativeTextCustom, R.id.nativeTextError,
            R.id.nativeTextDisabled, R.id.nativeTextIcons, R.id.nativeTextLabel,
            R.id.nativeTextGestures, R.id.nativeTextSelectable, R.id.nativeTextHipertext)
        ids.forEach { assertNotNull(root.findViewById<TextView>(it)) }
        assertTrue(root.findViewById<TextView>(R.id.nativeTextSelectable).isTextSelectable)
        assertTrue(root.findViewById<TextView>(R.id.nativeTextIcons).performIconStartClick())
        assertTrue(root.findViewById<TextView>(R.id.nativeTextLabel).performIconEndClick())
        assertNull(root.findViewById<TextView>(R.id.nativeTextError).compoundDrawablesRelative[2])
    }

    @Test(expected = IllegalArgumentException::class)
    fun `un icono interactivo exige descripcion antes de medir`() {
        val view = TextView(context)
        view.setIconEnd(view.getThemeTokens().icons.alerts.info)
        view.setOnIconEndClickListener {}
        view.measure(0, 0)
    }

    @Test fun `el espacio vacio despues del texto no activa su listener`() {
        val view = TextView(context)
        view.text = "Texto"
        var clicks = 0
        view.setOnClickListener { clicks++ }
        view.measure(View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(200, View.MeasureSpec.AT_MOST))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 590f, 5f, 0)
        assertFalse(view.onTouchEvent(event))
        assertEquals(0, clicks)
        event.recycle()
    }

    private fun tap(view: TextView, x: Float, y: Float) {
        val down = SystemClock.uptimeMillis()
        for (action in listOf(MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP)) {
            val event = MotionEvent.obtain(down, SystemClock.uptimeMillis(), action, x, y, 0)
            assertTrue(view.onTouchEvent(event))
            event.recycle()
            shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(20))
        }
    }
}
