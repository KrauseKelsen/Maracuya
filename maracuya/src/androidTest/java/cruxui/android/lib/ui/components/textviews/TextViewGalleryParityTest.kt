package cruxui.android.lib.ui.components.textviews

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.Color
import androidx.compose.ui.test.onAllNodesWithContentDescription
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableGeometry
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableSlot
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.test.platform.app.InstrumentationRegistry
import cruxui.android.lib.R
import cruxui.android.lib.ui.previews.TextViewGalleryPreview
import cruxui.android.lib.wrappers.components.textviews.TextView as NativeTextView
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.File

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Compara las galerías públicas completas y guarda capturas de ambos renders en el dispositivo.
 *
 * Justificación:
 * Las pruebas de tokens no detectan un padding de fuente, margen o ejemplo diferente en XML.
 * Esta prueba infla el layout real y mide sus nueve textos frente al preview Compose existente.
 *
 * Uso:
 * Prueba instrumentada interna; ejecutar con AndroidJUnitRunner. Las capturas se escriben en
 * filesDir del APK de pruebas como textview-compose.png y textview-xml.png.
 *
 * Objetivos:
 * - Verificar posiciones textuales y altura total de las galerías.
 * - Producir evidencia visual revisable sin añadir lógica al componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
class TextViewGalleryParityTest {
    @get:Rule val rule = createComposeRule()

    /** Compara la posición de cada ejemplo bajo el mismo ancho y configuración Android. */
    @Test fun lasGaleriasConservanPosicionesYAltura() {
        val showNative = mutableStateOf(false)
        lateinit var nativeRoot: View
        rule.setContent {
            Box(Modifier.width(360.dp).testTag("gallery")) {
                if (showNative.value) AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
                    val context = ContextThemeWrapper(it, androidx.appcompat.R.style.Theme_AppCompat_Light)
                    LayoutInflater.from(context).inflate(R.layout.text_view_preview, null).also { nativeRoot = it }
                }) else TextViewGalleryPreview()
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val rows = listOf(
            R.id.nativeTextDefault to R.string.text_view_preview_basic,
            R.id.nativeTextCustom to R.string.text_view_preview_custom,
            R.id.nativeTextError to R.string.text_view_preview_error,
            R.id.nativeTextDisabled to R.string.text_view_preview_disabled,
            R.id.nativeTextIcons to R.string.text_view_preview_icons,
            R.id.nativeTextLabel to R.string.text_view_preview_label,
            R.id.nativeTextGestures to R.string.text_view_preview_gestures,
            R.id.nativeTextSelectable to R.string.text_view_preview_selectable,
            R.id.nativeTextHipertext to R.string.text_view_preview_link,
        )
        val composeBounds = rule.onNodeWithTag("gallery").fetchSemanticsNode().boundsInRoot
        val textBounds = rows.map { (_, stringId) -> rule.onNodeWithText(context.getString(stringId), useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot }
        val startBounds = rule.onAllNodesWithContentDescription(context.getString(R.string.text_view_preview_start_description))
            .fetchSemanticsNodes().single().boundsInRoot
        val endBounds = rule.onAllNodesWithContentDescription(context.getString(R.string.text_view_preview_end_description))
            .fetchSemanticsNodes().map { it.boundsInRoot }.sortedBy { it.top }
        val icons = listOf(Triple(R.id.nativeTextIcons, CompoundDrawableSlot.Start, startBounds),
            Triple(R.id.nativeTextIcons, CompoundDrawableSlot.End, endBounds[0]),
            Triple(R.id.nativeTextLabel, CompoundDrawableSlot.End, endBounds[1]))
        val composeImage = capture("compose")
        rule.runOnIdle { showNative.value = true }
        rule.waitForIdle()
        val nativeImage = capture("xml")
        rule.runOnIdle {
            assertEquals("Ancho de la galería", composeBounds.width, nativeRoot.width.toFloat(), 2f)
            assertEquals("Altura de la galería", composeBounds.height, nativeRoot.height.toFloat(), 2f)
            val rootPosition = IntArray(2).also(nativeRoot::getLocationInWindow)
            icons.forEach { (id, slot, expected) ->
                val view = nativeRoot.findViewById<NativeTextView>(id)
                val location = IntArray(2).also(view::getLocationInWindow)
                val actual = requireNotNull(CompoundDrawableGeometry(view, 0f).target(slot))
                actual.offset((location[0] - rootPosition[0]).toFloat(), (location[1] - rootPosition[1]).toFloat())
                val composeRect = Rect((expected.left - composeBounds.left).toInt(), (expected.top - composeBounds.top).toInt(),
                    (expected.right - composeBounds.left).toInt(), (expected.bottom - composeBounds.top).toInt())
                val nativeRect = Rect(actual.left.toInt(), actual.top.toInt(), actual.right.toInt(), actual.bottom.toInt())
                val expectedInk = inkBounds(composeImage, composeRect, view.currentTextColor)
                val actualInk = inkBounds(nativeImage, nativeRect, view.currentTextColor)
                assertEquals("Ancho del asset $id $slot", expectedInk.width().toFloat(), actualInk.width().toFloat(), 2f)
                assertEquals("Alto del asset $id $slot", expectedInk.height().toFloat(), actualInk.height().toFloat(), 2f)
            }
            rows.zip(textBounds).forEach { (row, expected) ->
                val view = nativeRoot.findViewById<NativeTextView>(row.first)
                val position = IntArray(2).also(view::getLocationInWindow)
                val left = position[0] - rootPosition[0] + view.compoundPaddingLeft
                val top = position[1] - rootPosition[1] + view.baseline - view.layout.getLineBaseline(0)
                assertEquals("Posición X: ${view.text}", expected.left - composeBounds.left, left.toFloat(), 2f)
                assertEquals("Posición Y: ${view.text}", expected.top - composeBounds.top, top.toFloat(), 2f)
            }
        }
    }

    /** Localiza el asset pintado; tolera antialiasing sin aceptar iconos ausentes o deformados. */
    private fun inkBounds(bitmap: Bitmap, region: Rect, color: Int): Rect {
        val result = Rect()
        for (y in region.top.coerceAtLeast(0) until region.bottom.coerceAtMost(bitmap.height)) {
            for (x in region.left.coerceAtLeast(0) until region.right.coerceAtMost(bitmap.width)) {
                val pixel = bitmap.getPixel(x, y)
                val distance = kotlin.math.abs(Color.red(pixel) - Color.red(color)) +
                    kotlin.math.abs(Color.green(pixel) - Color.green(color)) + kotlin.math.abs(Color.blue(pixel) - Color.blue(color))
                if (Color.alpha(pixel) > 128 && distance < 90) result.union(x, y, x + 1, y + 1)
            }
        }
        check(!result.isEmpty) { "Asset ausente dentro de $region" }
        return result
    }

    /** Guarda el render completo para inspección visual del DS. */
    private fun capture(name: String): Bitmap {
        val bitmap = rule.onNodeWithTag("gallery").captureToImage().asAndroidBitmap()
        val directory = InstrumentationRegistry.getInstrumentation().targetContext.filesDir
        File(directory, "textview-$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        return bitmap
    }
}
