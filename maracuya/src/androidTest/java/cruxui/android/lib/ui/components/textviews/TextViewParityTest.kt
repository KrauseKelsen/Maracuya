package cruxui.android.lib.ui.components.textviews

import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableGeometry
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableSlot
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariant
import cruxui.android.lib.wrappers.components.textviews.TextView as NativeTextView
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextViewParityTest compara medidas y posición de iconos entre Compose y el TextView nativo real.
 *
 * Justificación:
 * Los mismos tokens no garantizan igual medición: la prueba detecta padding de fuente, celdas
 * interactivas y anclaje de iconos diferentes. Compose conserva la referencia esperada.
 *
 * Uso:
 * Prueba interna instrumentada con AndroidJUnitRunner. No forma parte del API del host.
 * Reutiliza la View al cambiar casos para verificar también transiciones de estado e iconos.
 *
 * Objetivos:
 * - Verificar ancho, alto, línea base y geometría final con tolerancia de redondeo de dos píxeles.
 * - Cubrir estados, caption, multilínea, ancho fijo y RTL.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
class TextViewParityTest {
    @get:Rule val rule = createComposeRule()

    private data class Case(
        val text: String,
        val icons: Int = 0,
        val interactive: Boolean = false,
        val enabled: Boolean = true,
        val error: Boolean = false,
        val link: Boolean = false,
        val fullWidth: Boolean = false,
        val rtl: Boolean = false,
        val caption: Boolean = false,
    )

    /** Recorre configuraciones equivalentes y compara sus resultados de layout. */
    @Test fun defaultsIconCellsAndBaselinesMatchCompose() {
        val selected = mutableStateOf(Case("TextView basico"))
        lateinit var native: NativeTextView
        var composeBaseline = 0
        rule.setContent {
            MyLibraryTheme {
                val library = LibraryTheme.current
                val case = selected.value
                val tokens = TextViewTokensOverride(
                    typography = if (case.caption) library.typography.caption else library.typography.label,
                    iconStart = if (case.icons == 2) library.icons.general.link else null,
                    iconEnd = if (case.icons > 0) library.icons.alerts.info else null,
                )
                val width = if (case.fullWidth) Modifier.width(300.dp) else Modifier
                CompositionLocalProvider(LocalLayoutDirection provides if (case.rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
                    Column {
                        TextView(case.text, modifier = width.testTag("compose").onGloballyPositioned { composeBaseline = it[FirstBaseline] },
                            tokensOverride = tokens, enabled = case.enabled, error = case.error,
                            variant = if (case.link) TextViewVariant.HIPERTEXT else TextViewVariant.DEFAULT,
                            onIconStartClick = if (case.interactive && case.icons == 2) ({}) else null,
                            onIconEndClick = if (case.interactive && case.icons > 0) ({}) else null,
                            iconStartContentDescription = "Inicial", iconEndContentDescription = "Final")
                        AndroidView(modifier = width.testTag("native"), factory = {
                            NativeTextView(ContextThemeWrapper(it, androidx.appcompat.R.style.Theme_AppCompat_Light)).also { native = it }
                        }, update = { view ->
                            // AndroidView conserva un minimumWidth impuesto por restricciones anteriores.
                            // El montaje restaura el mínimo del fixture antes de aplicar el siguiente caso.
                            view.minimumWidth = 0
                            view.minimumHeight = 0
                            view.layoutDirection = if (case.rtl) android.view.View.LAYOUT_DIRECTION_RTL else android.view.View.LAYOUT_DIRECTION_LTR
                            view.setThemeTokens(library)
                            view.setTypography(tokens.typography!!)
                            view.text = case.text
                            view.setIconStartContentDescription("Inicial")
                            view.setIconEndContentDescription("Final")
                            view.setIconStart(tokens.iconStart)
                            view.setIconEnd(tokens.iconEnd)
                            view.setOnIconStartClickListener(if (case.interactive && case.icons == 2) android.view.View.OnClickListener {} else null)
                            view.setOnIconEndClickListener(if (case.interactive && case.icons > 0) android.view.View.OnClickListener {} else null)
                            view.isEnabled = case.enabled
                            view.setErrorState(case.error)
                            view.setVariant(if (case.link) TextViewVariant.HIPERTEXT else TextViewVariant.DEFAULT)
                        })
                    }
                }
            }
        }
        val cases = listOf(Case("TextView basico"), Case("Estado de error", error = true),
            Case("Estado deshabilitado", enabled = false), Case("Hipervinculo", link = true),
            Case("Label", icons = 1), Case("Label", icons = 1, interactive = true),
            Case("Iconos del DS", icons = 2, interactive = true),
            Case("Iconos del DS", icons = 2, interactive = true, enabled = false),
            Case("Primera línea\nSegunda línea", icons = 2), Case("Label", icons = 1, interactive = true, fullWidth = true),
            Case("Texto secundario", caption = true),
            Case("مرحبا", icons = 2, interactive = true, fullWidth = true, rtl = true),
            Case("Texto largo que debe ocupar más de una línea junto a dos iconos", icons = 2, interactive = true, fullWidth = true))
        for (case in cases) {
            rule.runOnIdle { selected.value = case }
            val bounds = rule.onNodeWithTag("compose").fetchSemanticsNode().boundsInRoot
            val end = if (case.icons > 0) rule.onNodeWithContentDescription("Final").fetchSemanticsNode().boundsInRoot else null
            rule.runOnIdle {
                assertEquals("Dirección del host $case",
                    if (case.rtl) android.view.View.LAYOUT_DIRECTION_RTL else android.view.View.LAYOUT_DIRECTION_LTR,
                    native.layoutDirection)
                if (end != null) {
                    val target = requireNotNull(CompoundDrawableGeometry(native, 0f).target(CompoundDrawableSlot.End)) { "Icono final ausente: $case" }
                    assertEquals("Icono final X $case", end.left - bounds.left, target.left, 2f)
                    assertEquals("Icono final Y $case", end.top - bounds.top, target.top, 2f)
                }
                assertEquals("Ancho $case min=${native.minimumWidth} textMin=${native.minWidth} layout=${native.layout.width} padding=${native.compoundPaddingLeft},${native.compoundPaddingRight}", bounds.width, native.width.toFloat(), 2f)
                assertEquals("Alto $case", bounds.height, native.height.toFloat(), 2f)
                assertEquals("Línea base $case", composeBaseline.toFloat(), native.baseline.toFloat(), 2f)
            }
        }
    }
}
