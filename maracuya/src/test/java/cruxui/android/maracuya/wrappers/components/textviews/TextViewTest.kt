package cruxui.android.maracuya.wrappers.components.textviews

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.MotionEvent
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import android.widget.LinearLayout
import cruxui.android.maracuya.R
import cruxui.android.maracuya.theme.ThemeStyle
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokensOverride
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokensResolver
import cruxui.android.maracuya.wrappers.components.textviews.behavior.TextViewBehavior
import cruxui.android.maracuya.wrappers.core.tokens.TextColor
import cruxui.android.maracuya.wrappers.core.tokens.TextColorRole
import cruxui.android.maracuya.wrappers.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.maracuya.wrappers.core.tokens.TextTypography
import cruxui.android.maracuya.wrappers.core.tokens.ViewIcon
import cruxui.android.maracuya.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.maracuya.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TextViewTest {
    private val context = ContextThemeWrapper(RuntimeEnvironment.getApplication(), androidx.appcompat.R.style.Theme_AppCompat_Light)
    private fun sp(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)

    @Test fun `default is native and resolves theme without a registry`() {
        val view = TextView(context)
        assertEquals(androidx.appcompat.widget.AppCompatTextView::class.java, view.javaClass.superclass)
        assertEquals(TextViewVariant.Default, view.getVariant())
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgDefault), view.currentTextColor)
        assertEquals(sp(view.getThemeTokens().typography.label.fontSize), view.textSize, 0.01f)
        assertEquals("", view.text.toString())
    }

    @Test fun `optional supplies localized text and preserves explicit empty text`() {
        val view = TextView(context)
        view.setVariant(TextViewVariant.Optional)
        assertEquals(context.getString(R.string.text_view_optional), view.text.toString())
        view.text = ""
        view.setVariant(TextViewVariant.Default)
        view.setVariant(TextViewVariant.Optional)
        assertEquals("", view.text.toString())
        view.clearTextOverride()
        assertEquals(context.getString(R.string.text_view_optional), view.text.toString())
    }

    @Test fun `real layout inflates styles variants error and native disabled state`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val normal = root.findViewById<TextView>(R.id.nativeTextDefault)
        val optional = root.findViewById<TextView>(R.id.nativeTextOptional)
        val error = root.findViewById<TextView>(R.id.nativeTextError)
        val disabled = root.findViewById<TextView>(R.id.nativeTextDisabled)
        val styled = root.findViewById<TextView>(R.id.nativeTextStyled)
        assertEquals(context.getString(R.string.text_view_demo_name), normal.text.toString())
        assertEquals(TextViewVariant.Optional, optional.getVariant())
        assertTrue(error.hasError())
        assertNotNull(error.compoundDrawablesRelative[2])
        assertFalse(disabled.isEnabled)
        assertEquals(AndroidTokenAdapter.color(disabled.getThemeTokens().colors.fgSubtle), disabled.currentTextColor)
        assertEquals(sp(18f), styled.textSize, 0.01f)
    }

    @Test fun `XML preview measures every showcase row and renders state icons`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val width = View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY)
        root.measure(width, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        root.layout(0, 0, root.measuredWidth, root.measuredHeight)

        val column = (root as FrameLayout).getChildAt(0) as LinearLayout
        var previousBottom = 0
        for (index in 0 until column.childCount) {
            val child = column.getChildAt(index)
            assertTrue("Preview row $index has no height", child.measuredHeight > 0)
            assertTrue("Preview rows overlap at $index", child.top >= previousBottom)
            previousBottom = child.bottom
        }
        assertTrue(column.measuredHeight <= root.measuredHeight)

        val semanticError = root.findViewById<TextView>(R.id.nativeTextError)
        assertTrue(semanticError.hasError())
        assertEquals(AndroidTokenAdapter.color(semanticError.getThemeTokens().colors.fgError),
            semanticError.currentTextColor)
        assertNotNull(semanticError.compoundDrawablesRelative[2])

        val icons = root.findViewById<TextView>(R.id.nativeTextIcons)
        assertNotNull(icons.compoundDrawablesRelative[0])
        assertNotNull(icons.compoundDrawablesRelative[2])

        val styledError = root.findViewById<TextView>(R.id.nativeTextStyledError)
        assertTrue(styledError.hasError())
        assertEquals(context.getColor(R.color.text_view_demo_error), styledError.currentTextColor)
        assertNotNull(styledError.compoundDrawablesRelative[2])
    }

    @Test fun `XML selector and Kotlin resource setter produce identical state colors`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val xml = root.findViewById<TextView>(R.id.nativeTextStyled)
        val kotlin = TextView(context).apply { setColor(R.color.text_view_demo_states) }
        for ((enabled, error) in listOf(true to false, true to true, false to true, false to false)) {
            listOf(xml, kotlin).forEach { it.isEnabled = enabled; it.setErrorState(error) }
            assertEquals(xml.currentTextColor, kotlin.currentTextColor)
            val expected = when {
                !enabled -> R.color.text_view_demo_disabled
                error -> R.color.text_view_demo_error
                else -> R.color.text_view_demo_normal
            }
            assertEquals(context.getColor(expected), kotlin.currentTextColor)
        }
    }

    @Test fun `partial normal override preserves error and disabled tokens`() {
        val view = TextView(context)
        view.setTokensOverride(TextViewTokensOverride(foregroundDefault = TextColor.Value(Color.MAGENTA)))
        assertEquals(Color.MAGENTA, view.currentTextColor)
        view.setErrorState(true)
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgError), view.currentTextColor)
        view.isEnabled = false
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgSubtle), view.currentTextColor)
    }

    @Test fun `solid native setter applies to all states but never enables interaction`() {
        val view = TextView(context)
        var calls = 0
        view.setOnClickListener { calls++ }
        view.setTextColor(Color.BLUE)
        view.setErrorState(true)
        view.isEnabled = false
        assertEquals(Color.BLUE, view.currentTextColor)
        assertFalse(view.performClick())
        assertFalse(view.callOnClick())
        assertEquals(0, calls)
        view.isEnabled = true
        assertTrue(view.performClick())
        assertEquals(1, calls)
        view.setBehavior(view.getBehavior().copy(allowClick = false))
        assertFalse(view.performClick())
        assertFalse(view.callOnClick())
        assertEquals(1, calls)
    }

    @Test fun `long click policy also covers coordinate based native dispatch`() {
        val view = TextView(context)
        var calls = 0
        view.setOnLongClickListener { calls++; true }
        view.setBehavior(view.getBehavior().copy(allowLongClick = false))
        assertFalse(view.performLongClick(1f, 1f))
        assertEquals(0, calls)
        view.setBehavior(view.getBehavior().copy(allowLongClick = true))
        assertTrue(view.performLongClick(1f, 1f))
        assertEquals(1, calls)
        view.isEnabled = false
        assertFalse(view.performLongClick())
        assertEquals(1, calls)
    }

    @Test fun `pressed selector remains reactive on native drawable state`() {
        val view = TextView(context)
        val colors = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf()), intArrayOf(Color.RED, Color.GREEN))
        view.setTextColor(colors)
        assertSame(colors, view.textColors)
        assertEquals(Color.GREEN, view.currentTextColor)
        view.isPressed = true
        assertEquals(Color.RED, view.currentTextColor)
        view.isPressed = false
        assertEquals(Color.GREEN, view.currentTextColor)
    }

    @Test fun `native setError is connected to semantic state`() {
        val view = TextView(context)
        view.error = "Invalid name"
        assertTrue(view.hasError())
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgError), view.currentTextColor)
        view.error = null
        assertFalse(view.hasError())
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgDefault), view.currentTextColor)
    }

    @Test
    @GraphicsMode(GraphicsMode.Mode.NATIVE)
    fun `native typography setters survive state variant and theme refresh`() {
        val view = TextView(context)
        view.setTextSizeSp(23f)
        view.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC)
        assertEquals(700, view.getTokensOverride().weight)
        assertEquals(true, view.getTokensOverride().italic)
        view.letterSpacing = 0.12f
        view.setVariant(TextViewVariant.Optional)
        view.setErrorState(true)
        view.refreshTheme()
        assertEquals(sp(23f), view.textSize, 0.01f)
        assertTrue(view.typeface.isItalic)
        assertTrue(view.typeface.isBold)
        assertEquals(0.12f, view.letterSpacing, 0.001f)
    }

    @Test fun `clear runtime color restores XML style`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val view = root.findViewById<TextView>(R.id.nativeTextStyled)
        val xmlColor = view.currentTextColor
        view.setTextColor(Color.YELLOW)
        assertEquals(Color.YELLOW, view.currentTextColor)
        view.clearColorOverride()
        assertEquals(xmlColor, view.currentTextColor)
    }

    @Test fun `direct XML attributes override style fields`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val view = root.findViewById<TextView>(R.id.nativeTextLocalStyled)
        assertEquals(sp(21f), view.textSize, 0.01f)
        view.setTextSizeSp(27f)
        assertEquals(sp(27f), view.textSize, 0.01f)
        view.clearOverrides()
        assertEquals(sp(21f), view.textSize, 0.01f)
    }

    @Test fun `theme host updates descendants and respects nested scope`() {
        val outer = MyLibraryThemeWrp(context)
        val nested = MyLibraryThemeWrp(context)
        val view = TextView(context)
        val inner = TextView(context)
        val container = FrameLayout(context)
        outer.addView(container); container.addView(view)
        outer.addView(nested); nested.addView(inner)
        nested.themeStyle = ThemeStyle.LIGHT
        inner.refreshTheme()
        val innerColor = inner.currentTextColor
        view.setColorRole(TextColorRole.Foreground)
        outer.themeStyle = ThemeStyle.DARK
        assertEquals(AndroidTokenAdapter.color(view.getThemeTokens().colors.fgDefault), view.currentTextColor)
        assertEquals(innerColor, inner.currentTextColor)
        assertNotEquals(innerColor, view.currentTextColor)
    }

    @Test fun `font and icon resources are converted to Android values`() {
        val view = TextView(context)
        view.setFont(R.font.mulish_regular)
        view.setShowIconEnd(true)
        assertNotNull(view.typeface)
        assertNotNull(view.compoundDrawablesRelative[2])
        assertTrue(view.compoundDrawablesRelative[2].bounds.width() > 0)
        view.setShowIconEnd(false)
        assertNull(view.compoundDrawablesRelative[2])
    }

    @Test fun `native transformations do not turn variant content into an override`() {
        val transformations: List<(TextView) -> Unit> = listOf(
            { it.setAllCaps(true) },
            { it.setTextIsSelectable(true) },
            { it.setSingleLine(true) },
        )
        for (transform in transformations) {
            val view = TextView(context)
            transform(view)
            view.setVariant(TextViewVariant.Optional)
            assertEquals(context.getString(R.string.text_view_optional), view.text.toString())
            view.setVariant(TextViewVariant.Default)
            assertEquals("", view.text.toString())
        }
    }

    @Test fun `native line spacing survives updates and latest spacing policy wins`() {
        val view = TextView(context)
        view.setLineSpacing(8f, 1.3f)
        view.setTextColor(Color.BLUE)
        view.setVariant(TextViewVariant.Optional)
        view.refreshTheme()
        assertEquals(8f, view.lineSpacingExtra, 0.01f)
        assertEquals(1.3f, view.lineSpacingMultiplier, 0.01f)
        view.setLineHeight(50)
        assertNull(view.getTokensOverride().lineSpacing)
        assertEquals(1f, view.lineSpacingMultiplier, 0.01f)
        view.setLineSpacing(4f, 1.1f)
        assertNull(view.getTokensOverride().lineHeight)
        assertEquals(4f, view.lineSpacingExtra, 0.01f)
        assertEquals(1.1f, view.lineSpacingMultiplier, 0.01f)
        view.setLineHeight(TypedValue.COMPLEX_UNIT_SP, 40f)
        view.refreshTheme()
        assertNull(view.getTokensOverride().lineSpacing)
        assertEquals(40f, view.getTokensOverride().lineHeight!!.value, 0.01f)
    }

    @Test fun `XML line spacing remains after setting color and clearing runtime spacing`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val view = root.findViewById<TextView>(R.id.nativeTextLocalStyled)
        val extra = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, context.resources.displayMetrics)
        view.setTextColor(Color.BLUE)
        assertEquals(extra, view.lineSpacingExtra, 0.01f)
        assertEquals(1.2f, view.lineSpacingMultiplier, 0.01f)
        view.setLineHeight(50)
        view.clearTypographyOverrides()
        assertEquals(extra, view.lineSpacingExtra, 0.01f)
        assertEquals(1.2f, view.lineSpacingMultiplier, 0.01f)
    }

    @Test fun `native variants consume the same Label tokens as Compose`() {
        val view = TextView(context)
        val shared = LabelTokensResolver.resolve(view.getThemeTokens())
        assertEquals(AndroidTokenAdapter.color(shared.foregroundDefault), view.currentTextColor)
        assertEquals(sp(shared.labelTypography.fontSize), view.textSize, 0.01f)
        view.setVariant(TextViewVariant.Optional)
        assertEquals(AndroidTokenAdapter.color(shared.foregroundDefault), view.currentTextColor)
        assertEquals(sp(shared.optionalTypography.fontSize), view.textSize, 0.01f)
        view.setErrorState(true)
        assertEquals(AndroidTokenAdapter.color(shared.foregroundError), view.currentTextColor)
    }

    @Test fun `host can reuse Label tokens and component overrides without rebuilding semantics`() {
        val view = TextView(context)
        val original = LabelTokensResolver.resolve(view.getThemeTokens())
        val custom = original.copy(
            foregroundDefault = ColorToken(0xFF123456),
            labelTypography = original.labelTypography.copy(fontSize = 25f),
        )
        view.setTokens(custom)
        assertEquals(AndroidTokenAdapter.color(custom.foregroundDefault), view.currentTextColor)
        assertEquals(sp(25f), view.textSize, 0.01f)
        view.setComponentTokensOverride(LabelTokensOverride(foregroundError = ColorToken(0xFF654321)))
        view.setErrorState(true)
        assertEquals(0xFF654321.toInt(), view.currentTextColor)
        view.setTextColor(Color.CYAN)
        assertEquals(Color.CYAN, view.currentTextColor)
        view.clearColorOverride()
        assertEquals(0xFF654321.toInt(), view.currentTextColor)
        view.setComponentTokensOverride(null)
        assertEquals(AndroidTokenAdapter.color(original.foregroundError), view.currentTextColor)
        view.setErrorState(false)
        view.setTokens(null)
        assertEquals(AndroidTokenAdapter.color(original.foregroundDefault), view.currentTextColor)
        assertEquals(sp(original.labelTypography.fontSize), view.textSize, 0.01f)
    }

    @Test fun `full semantic selectors are resolved again after a theme update`() {
        val view = TextView(context)
        view.setTokensOverride(TextViewTokensOverride(
            textColor = TextColor.Semantic { it.brandPrimary },
            typography = TextTypography.Semantic { it.headline24 },
            iconStart = ViewIcon.Semantic { it.alerts.info },
            iconEnd = ViewIcon.Semantic { it.alerts.warning },
        ))
        val original = view.getThemeTokens()
        assertEquals(AndroidTokenAdapter.color(original.colors.brandPrimary), view.currentTextColor)
        assertEquals(sp(original.typography.headline24.fontSize), view.textSize, 0.01f)
        assertNotNull(view.compoundDrawablesRelative[0])
        assertNotNull(view.compoundDrawablesRelative[2])
        val custom = original.copy(
            colors = original.colors.copy(brandPrimary = ColorToken(0xFF135790)),
            icons = original.icons.copy(alerts = original.icons.alerts.copy(
                info = original.icons.alerts.info.copy(iconSize = 32),
            )),
        )
        view.setThemeTokens(custom)
        assertSame(custom, view.getThemeTokens())
        assertEquals(0xFF135790.toInt(), view.currentTextColor)
        val iconSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, context.resources.displayMetrics).toInt()
        assertEquals(iconSize, view.compoundDrawablesRelative[0].bounds.width())
        view.setThemeTokens(null)
        assertEquals(AndroidTokenAdapter.color(original.colors.brandPrimary), view.currentTextColor)
    }

    @Test fun `both DS icon slots can be assigned hidden and removed independently`() {
        val view = TextView(context)
        val icons = view.getThemeTokens().icons
        view.setIconStart(icons.alerts.info)
        view.setIconEnd(icons.alerts.warning)
        assertNotNull(view.compoundDrawablesRelative[0])
        assertNotNull(view.compoundDrawablesRelative[2])
        view.setShowIconStart(false)
        assertNull(view.compoundDrawablesRelative[0])
        assertNotNull(view.compoundDrawablesRelative[2])
        view.setShowIconStart(true)
        assertNotNull(view.compoundDrawablesRelative[0])
        view.setIconEnd(null as IconToken?)
        assertNotNull(view.compoundDrawablesRelative[0])
        assertNull(view.compoundDrawablesRelative[2])
        view.setIconStart(ViewIcon.None)
        assertNull(view.compoundDrawablesRelative[0])
        assertNull(view.compoundDrawablesRelative[2])
    }

    @Test fun `native listener setters and declarative behavior share the same callbacks`() {
        val view = TextView(context)
        FrameLayout(context).addView(view)
        var clicks = 0
        var longClicks = 0
        val nativeListener = View.OnClickListener { clicks++ }
        view.setOnClickListener(nativeListener)
        assertSame(nativeListener, view.getBehavior().onClick)
        assertTrue(view.performClick())
        assertEquals(1, clicks)
        val behavior = TextViewBehavior(
            onClick = View.OnClickListener { clicks += 10 },
            onLongClick = View.OnLongClickListener { longClicks++; true },
        )
        view.setBehavior(behavior)
        assertSame(behavior.onClick, view.getBehavior().onClick)
        assertTrue(view.performClick())
        assertTrue(view.performLongClick())
        assertEquals(11, clicks)
        assertEquals(1, longClicks)
        view.setBehavior(TextViewBehavior())
        assertNull(view.getBehavior().onClick)
        assertNull(view.getBehavior().onLongClick)
        assertFalse(view.performClick())
        assertFalse(view.performLongClick())
        assertEquals(11, clicks)
        assertEquals(1, longClicks)
    }

    @Test fun `TextView routes icon gestures and accessibility separately from text`() {
        val root = LayoutInflater.from(context).inflate(R.layout.text_view_preview, null)
        val view = root.findViewById<TextView>(R.id.nativeTextIcons)
        var body = 0
        var start = 0
        var end = 0
        var held = 0
        view.setOnClickListener { body++ }
        view.setOnIconStartClickListener { start++ }
        view.setOnIconEndClickListener { end++ }
        view.setOnIconEndLongClickListener { held++; true }
        view.measure(View.MeasureSpec.makeMeasureSpec(240, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(80, View.MeasureSpec.EXACTLY))
        view.layout(0, 0, 240, 80)
        val now = SystemClock.uptimeMillis()
        for (action in listOf(MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP)) {
            val event = MotionEvent.obtain(now, now, action, 9f, 40f, 0)
            assertTrue(view.onTouchEvent(event))
            event.recycle()
        }
        assertEquals(1, start)
        assertEquals(0, body)
        assertEquals(0, end)
        val info = AccessibilityNodeInfo.obtain()
        view.onInitializeAccessibilityNodeInfo(info)
        assertEquals(context.getString(R.string.text_view_demo_icon_end_description),
            info.actionList.single { it.id == R.id.action_icon_end_click }.label)
        assertTrue(view.performAccessibilityAction(R.id.action_icon_end_click, null))
        assertTrue(view.performAccessibilityAction(R.id.action_icon_end_long_click, null))
        assertEquals(1, end)
        assertEquals(1, held)
        view.isEnabled = false
        assertFalse(view.performIconStartClick())
        assertFalse(view.performIconEndClick())
        assertFalse(view.performIconEndLongClick())
        assertFalse(view.performClick())
        assertEquals(0, body)
        info.recycle()
    }
}
