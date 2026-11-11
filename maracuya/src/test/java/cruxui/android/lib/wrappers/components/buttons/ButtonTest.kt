package cruxui.android.lib.wrappers.components.buttons

import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import cruxui.android.lib.R
import cruxui.android.lib.theme.*
import cruxui.android.lib.wrappers.components.buttons.simple.Button
import cruxui.android.lib.wrappers.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.wrappers.components.buttons.simple.tokens.*
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.wrappers.components.textviews.TextView
import cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTest prueba el control nativo, sus estados, XML, callbacks y alcance de tema.
 *
 * Justificación: Verifica integración real de TextView y View; una prueba de tokens no detectaría
 * listeners o medidas perdidos.
 *
 * Se comunica con: Infla las galerías públicas y utiliza el registro por instancia con Robolectric.
 *
 * Uso:
 * ```shell
 * ./gradlew :maracuya:testDebugUnitTest --tests "*buttons*"
 * ```
 *
 * Objetivos:
 * - Detectar regresiones en compatibilidad Android y carga controlada.
 *
 * Flujo: tema y entrada del host → resolución compartida → adaptador de plataforma → render.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ButtonTest {
    private val context =
        ContextThemeWrapper(
            RuntimeEnvironment.getApplication(),
            androidx.appcompat.R.style.Theme_AppCompat_Light,
        )

    @After
    fun clearRecipes() {
        ButtonTokensOverrideRegistry.clear()
    }

    /** El padre ejecuta la acción y los flags no borran listeners previamente registrados. */
    @Test
    fun listenersAreBlockedUntilHostCompletesLoading() {
        val view = Button(context)
        var clicks = 0
        var longs = 0
        view.setOnClickListener {
            clicks++
            view.isLoading = true
        }
        view.setOnLongClickListener {
            longs++
            true
        }
        assertTrue(view.performClick())
        assertTrue(view.isLoading)
        assertFalse(view.performClick())
        assertFalse(view.callOnClick())
        assertFalse(view.performLongClick())
        view.isEnabled = false
        assertFalse(view.callOnClick())
        view.isEnabled = true
        assertTrue(view.isLoading)
        view.isLoading = false
        assertTrue(view.performLongClick())
        assertTrue(view.callOnClick())
        assertEquals(2, clicks)
        assertEquals(1, longs)
        view.behavior = ButtonBehavior(isDisabled = true)
        assertFalse(view.performLongClick(1f, 1f))
    }

    /** Texto, iconos y medidas sobreviven a una carga; no reaparece un icono retirado con null. */
    @Test
    fun loadingPreservesLabelIconsAndMeasuredSize() {
        val view = Button(context)
        view.text = "Guardar todos los cambios"
        view.setIconStart(view.getThemeTokens().icons.alerts.info)
        view.setIconEnd(view.getThemeTokens().icons.alerts.warning)
        val label = view.getChildAt(0) as TextView
        measure(view)
        val size = view.measuredWidth to view.measuredHeight
        val color = (view.background as GradientDrawable).color!!.defaultColor
        view.isLoading = true
        measure(view)
        assertEquals(size, view.measuredWidth to view.measuredHeight)
        assertEquals(View.INVISIBLE, label.visibility)
        assertEquals(View.VISIBLE, view.getChildAt(1).visibility)
        assertEquals(color, (view.background as GradientDrawable).color!!.defaultColor)
        assertEquals("Guardar todos los cambios", view.text.toString())
        view.isLoading = false
        assertNotNull(label.compoundDrawablesRelative[0])
        assertNotNull(label.compoundDrawablesRelative[2])
        view.setIconEnd(null)
        view.isLoading = true
        view.isLoading = false
        assertNull(label.compoundDrawablesRelative[2])
    }

    /** La galería infla todas las variantes y ambos modos usando clases nativas reales. */
    @Test
    fun xmlGalleriesAndHostExampleInflate() {
        val root = LayoutInflater.from(context).inflate(R.layout.button_wrp_preview, null)
        val primary = root.findViewById<Button>(R.id.button_primary_light_default)
        val dark = root.findViewById<Button>(R.id.button_primary_dark_disabled)
        val loading = root.findViewById<Button>(R.id.button_secondary_light_loading)
        assertEquals(ButtonVariant.PRIMARY, primary.variant)
        assertEquals(
            primary.getThemeTokens().colors.brandPrimary.argb.toInt(),
            (primary.background as GradientDrawable).color!!.defaultColor,
        )
        assertTrue(loading.isLoading)
        assertFalse(dark.isEnabled)
        val link =
            root.findViewById<Button>(R.id.button_terciary_light_default).getChildAt(0) as TextView
        assertEquals(0, link.paintFlags and Paint.UNDERLINE_TEXT_FLAG)
        val start = root.findViewById<Button>(R.id.buttonPreviewStart)
        start.performClick()
        assertTrue(start.isLoading)
        root.findViewById<Button>(R.id.buttonPreviewFinish).performClick()
        assertFalse(start.isLoading)
    }

    /** Cambiar el proveedor reevalúa las recetas y conserva los overrides de la instancia. */
    @Test
    fun themeScopeAndInstanceOverridesStayIndependent() {
        val provider = MyLibraryThemeWrp(context)
        val first = Button(context)
        val second = Button(context)
        provider.addView(first)
        provider.addView(second)
        ButtonTokensOverrideRegistry.register("label") { library: LibraryThemeSnapshot ->
            ButtonTokensOverride(textColor = library.colors.fgDefault)
        }
        first.tokensOverrideName = "label"
        first.variant = ButtonVariant.SECONDARY
        second.variant = ButtonVariant.SECONDARY
        provider.themeStyle = ThemeStyle.DARK
        assertEquals(
            first.getThemeTokens().colors.fgDefault.argb.toInt(),
            (first.getChildAt(0) as TextView).currentTextColor,
        )
        val custom = first.getThemeTokens().colors.borderFocus
        first.setTokensOverride(ButtonTokensOverride(textColor = custom))
        provider.themeStyle = ThemeStyle.LIGHT
        assertEquals(custom.argb.toInt(), (first.getChildAt(0) as TextView).currentTextColor)
        assertEquals(
            second.getThemeTokens().colors.fgDefault.argb.toInt(),
            (second.getChildAt(0) as TextView).currentTextColor,
        )
        first.setTokensOverride(null)
        assertEquals(
            first.getThemeTokens().colors.fgDefault.argb.toInt(),
            (first.getChildAt(0) as TextView).currentTextColor,
        )
    }

    /** Accessibility conserva el nombre de la acción aunque solo sea visible el spinner. */
    @Test
    fun accessibilityNamesButtonAndRemovesBlockedActions() {
        val view = Button(context)
        view.text = "Guardar"
        view.isLoading = true
        val info = AccessibilityNodeInfo.obtain()
        view.onInitializeAccessibilityNodeInfo(info)
        assertEquals("android.widget.Button", info.className)
        assertEquals("Guardar", info.text)
        assertFalse(info.isEnabled)
        assertFalse(info.isClickable)
        assertFalse(info.actionList.any { it.id == AccessibilityNodeInfo.ACTION_CLICK })
        info.recycle()
    }

    /**
     * El parche de ejecución prevalece sobre iconos/tamaños XML; clearOverrides recupera el layout.
     */
    @Test
    fun runtimeTextOverrideWinsOverXml() {
        val root = LayoutInflater.from(context).inflate(R.layout.button_wrp_preview, null)
        val button = root.findViewById<Button>(R.id.buttonPreviewStart)
        val label = button.getChildAt(0) as TextView
        val library = button.getThemeTokens()
        val enlarged = library.icons.alerts.warning.copy(iconSize = 32)
        button.setTokensOverride(
            ButtonTokensOverride(iconEnd = enlarged, typography = library.typography.headline24)
        )
        val expected = (32 * context.resources.displayMetrics.density).toInt()
        assertEquals(expected, label.compoundDrawablesRelative[2]!!.bounds.width())
        button.setIconEnd(null)
        button.refreshTheme()
        assertNull(label.compoundDrawablesRelative[2])
        button.clearOverrides()
        assertNotNull(label.compoundDrawablesRelative[2])
        assertNotEquals(expected, label.compoundDrawablesRelative[2]!!.bounds.width())
    }

    /** Entrar en loading durante un gesto no deja pressed atascado cuando termina la operación. */
    @Test
    fun loadingClearsAnOngoingNativePress() {
        val button = Button(context)
        button.isPressed = true
        assertTrue(button.isPressed)
        button.isLoading = true
        assertFalse(button.isPressed)
        button.isLoading = false
        assertFalse(button.isPressed)
    }

    /** El modo editor conserva medidas y recursos reales sin iniciar el animator. */
    @Test
    fun editModeRendersRealContentAndOptionalIcons() {
        val button =
            object : Button(context) {
                override fun isInEditMode(): Boolean = true
            }
        button.text = "Button"
        button.setIconStart(
            cruxui.android.lib.wrappers.core.tokens.ViewIcon.Semantic { it.alerts.info }
        )
        button.setIconEnd(button.getThemeTokens().icons.alerts.warning)
        button.setIconStartContentDescription("Información")
        button.setIconEndContentDescription("Aviso")
        measure(button)
        val label = button.getChildAt(0) as TextView
        assertTrue(button.measuredHeight >= (48 * context.resources.displayMetrics.density).toInt())
        assertNotNull(label.compoundDrawablesRelative[0])
        assertNotNull(label.compoundDrawablesRelative[2])
        button.setIconStart(null)
        assertNull(label.compoundDrawablesRelative[0])
        button.setIconStart(button.getThemeTokens().icons.alerts.info)
        assertNotNull(label.compoundDrawablesRelative[0])
        val info = AccessibilityNodeInfo.obtain()
        button.onInitializeAccessibilityNodeInfo(info)
        assertEquals("Información Button Aviso", info.contentDescription)
        assertFalse(
            info.actionList.any {
                it.id == R.id.action_icon_start_click || it.id == R.id.action_icon_end_click
            }
        )
        info.recycle()
        button.isLoading = true
        assertEquals(View.INVISIBLE, label.visibility)
        assertEquals(0f, button.getChildAt(1).rotation, 0f)
    }

    /** El lector compartido no traslada a Button los controles de visibilidad de TextView. */
    @Test
    fun xmlIconsDependOnlyOnTheirSources() {
        val attrs =
            org.robolectric.Robolectric.buildAttributeSet()
                .addAttribute(R.attr.iconStart, "@drawable/ic_info")
                .addAttribute(R.attr.iconEnd, "@drawable/ic_info")
                .addAttribute(R.attr.showIconStart, "false")
                .addAttribute(R.attr.showIconEnd, "false")
                .build()
        val button = Button(context, attrs)
        val label = button.getChildAt(0) as TextView
        assertNotNull(label.compoundDrawablesRelative[0])
        assertNotNull(label.compoundDrawablesRelative[2])

        button.setIconStart(null)
        button.setIconEnd(null)
        button.refreshTheme()
        assertNull(label.compoundDrawablesRelative[0])
        assertNull(label.compoundDrawablesRelative[2])

        button.clearOverrides()
        assertNotNull(label.compoundDrawablesRelative[0])
        assertNotNull(label.compoundDrawablesRelative[2])
    }

    private fun measure(view: View) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(1200, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(1200, View.MeasureSpec.AT_MOST),
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
