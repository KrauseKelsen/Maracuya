package cruxui.android.lib.ui.components.buttons

import android.graphics.Bitmap
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.viewinterop.AndroidView
import androidx.test.platform.app.InstrumentationRegistry
import cruxui.android.lib.R
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.buttons.simple.Button
import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.ui.components.buttons.simple.tokens.ButtonTokensOverride
import cruxui.android.lib.ui.previews.*
import java.io.File
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonInteractionTest valida eventos Compose, tamaño durante carga y capturas de las
 * galerías.
 *
 * Justificación: El dispositivo verifica gestos y semántica reales; guarda evidencia del mismo
 * layout XML y Compose.
 *
 * Se comunica con: MyLibraryTheme, Button y galerías públicas se ejecutan con el reloj controlado
 * de las pruebas.
 *
 * Uso:
 * ```shell
 * ./gradlew :maracuya:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=cruxui.android.lib.ui.components.buttons.ButtonInteractionTest
 * ```
 *
 * Objetivos:
 * - Comprobar carga indefinida controlada, acciones bloqueadas y galerías de referencia.
 *
 * Flujo: tema y entrada del host → resolución compartida → adaptador de plataforma → render.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
class ButtonInteractionTest {
    @get:Rule val rule = createComposeRule()

    /** Press no inicia carga; el host controla la duración y el texto conserva su medida. */
    @Test
    fun loadingBlocksActionsPreservesSizeAndWaitsForHost() {
        val loading = mutableStateOf(false)
        val disabled = mutableStateOf(false)
        var clicks = 0
        var longs = 0
        rule.setContent {
            MyLibraryTheme {
                Button(
                    text = "Guardar todos",
                    modifier = Modifier.testTag("button"),
                    onClick = { clicks++ },
                    onLongClick = { longs++ },
                    enabled = !disabled.value,
                    behavior = ButtonBehavior(isLoading = loading.value),
                    tokensOverride =
                        ButtonTokensOverride(
                            iconStart = LibraryTheme.current.icons.alerts.info,
                            iconEnd = LibraryTheme.current.icons.alerts.warning,
                        ),
                )
            }
        }
        val button = rule.onNodeWithTag("button")
        button.performTouchInput { down(center) }
        rule.runOnIdle { assertFalse(loading.value) }
        button.performTouchInput { up() }
        rule.runOnIdle { assertEquals(1, clicks) }
        val bounds = button.fetchSemanticsNode().boundsInRoot
        rule.runOnIdle { loading.value = true }
        button.assertIsNotEnabled()
        button.performClick()
        button.performTouchInput { longClick() }
        rule.mainClock.advanceTimeBy(5000)
        rule.runOnIdle {
            assertTrue(loading.value)
            assertEquals(1, clicks)
            assertEquals(0, longs)
        }
        assertEquals(bounds.size, button.fetchSemanticsNode().boundsInRoot.size)
        rule.runOnIdle {
            loading.value = false
            disabled.value = true
        }
        button.assertIsNotEnabled().performClick()
        rule.runOnIdle { disabled.value = false }
        button.performClick()
        rule.runOnIdle { assertEquals(2, clicks) }
    }

    /**
     * El reloj nativo se detiene al ocultar/desmontar; volver a mostrar no finaliza la solicitud
     * del host.
     */
    @Test
    fun nativeLoadingAnimationFollowsVisibilityAndAttachment() {
        val attached = mutableStateOf(true)
        lateinit var button: cruxui.android.lib.wrappers.components.buttons.simple.Button
        rule.setContent {
            if (attached.value)
                AndroidView(
                    modifier = Modifier.testTag("nativeButton"),
                    factory = {
                        cruxui.android.lib.wrappers.components.buttons.simple
                            .Button(
                                ContextThemeWrapper(
                                    it,
                                    androidx.appcompat.R.style.Theme_AppCompat_Light,
                                )
                            )
                            .apply {
                                text = "Guardar"
                                setIconStart(getThemeTokens().icons.alerts.info)
                                setIconEnd(getThemeTokens().icons.alerts.info)
                                isLoading = true
                                button = this
                            }
                    },
                )
        }
        rule.waitUntil(5000) { button.getChildAt(1).rotation > 1f }
        rule.runOnIdle {
            button.visibility = android.view.View.INVISIBLE
            assertEquals(0f, button.getChildAt(1).rotation, 0f)
            assertTrue(button.isLoading)
            button.visibility = android.view.View.VISIBLE
        }
        rule.waitUntil(5000) { button.getChildAt(1).rotation > 1f }
        rule.runOnIdle { button.isLoading = false }
        rule.waitForIdle()
        val files = InstrumentationRegistry.getInstrumentation().targetContext.filesDir
        val image = rule.onNodeWithTag("nativeButton").captureToImage().asAndroidBitmap()
        File(files, "button-icons-xml.png").outputStream().use {
            image.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        rule.runOnIdle {
            button.isLoading = true
            attached.value = false
        }
        rule.waitForIdle()
        rule.runOnIdle { assertEquals(0f, button.getChildAt(1).rotation, 0f) }
    }

    /** Los parámetros de icono se entregan al TextView y solo activan la acción del botón. */
    @Test
    fun optionalIconsForwardDescriptionsAndOnlyClickTheButton() {
        val showStart = mutableStateOf(true)
        var clicks = 0
        rule.setContent {
            MyLibraryTheme {
                val icon = LibraryTheme.current.icons.alerts.info
                Button(
                    text = "Guardar",
                    onClick = { clicks++ },
                    tokensOverride = ButtonTokensOverride(iconStart = icon),
                    iconStart = icon.takeIf { showStart.value },
                    iconEnd = icon,
                    iconStartContentDescription = "Información inicial",
                    iconEndContentDescription = "Información final",
                )
            }
        }
        val start = rule.onNodeWithContentDescription("Información inicial", useUnmergedTree = true)
        val end = rule.onNodeWithContentDescription("Información final", useUnmergedTree = true)
        start.assertHasNoClickAction()
        end.assertHasNoClickAction()
        val files = InstrumentationRegistry.getInstrumentation().targetContext.filesDir
        val image = rule.onNodeWithText("Guardar").captureToImage().asAndroidBitmap()
        File(files, "button-icons-compose.png").outputStream().use {
            image.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        start.performTouchInput { click() }
        end.performTouchInput { click() }
        rule.runOnIdle {
            assertEquals(2, clicks)
            showStart.value = false
        }
        rule
            .onNodeWithContentDescription("Información inicial", useUnmergedTree = true)
            .assertDoesNotExist()
        end.assertExists()
    }

    /** El adaptador de navegación conserva su fallback sin requerir los locals del Button DS. */
    @Test
    fun navigationFallbackDoesNotRequireLibraryColorLocals() {
        val variants =
            cruxui.android.lib.ui.components.buttons.navigation.simple.variant
                .ButtonNavigationVariant
                .entries
        var count = 0
        rule.setContent {
            androidx.compose.runtime.CompositionLocalProvider(
                cruxui.android.lib.compositions.LocalFontFamily provides
                    cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(false).fontFamily
            ) {
                androidx.compose.material3.MaterialTheme {
                    for (variant in variants) {
                        val tokens =
                            cruxui.android.lib.ui.components.buttons.navigation.simple.tokens
                                .ButtonNavigationTokensResolver
                                .resolve(variant)
                        assertTrue(tokens.typographyToken.fontSize > 0)
                    }
                    androidx.compose.runtime.SideEffect { count = variants.size }
                }
            }
        }
        rule.runOnIdle { assertEquals(4, count) }
    }

    /** Captura las tres variantes en ambos motores bajo idéntico tema y dimensiones de galería. */
    @Test
    fun captureReferenceGalleries() {
        val selection = mutableStateOf(0)
        rule.mainClock.autoAdvance = false
        rule.setContent {
            Box(Modifier.testTag("gallery")) {
                when (selection.value) {
                    0 -> ButtonPrimaryPreview()
                    1 -> ButtonSecondaryPreview()
                    2 -> ButtonTerciaryPreview()
                    else ->
                        key(selection.value) {
                            AndroidView(
                                factory = {
                                    val context =
                                        ContextThemeWrapper(
                                            it,
                                            androidx.appcompat.R.style.Theme_AppCompat_Light,
                                        )
                                    val layout =
                                        listOf(
                                            R.layout.button_primary_preview,
                                            R.layout.button_secondary_preview,
                                            R.layout.button_terciary_preview,
                                        )[selection.value - 3]
                                    LayoutInflater.from(context).inflate(layout, null)
                                }
                            )
                        }
                }
            }
        }
        val files = InstrumentationRegistry.getInstrumentation().targetContext.filesDir
        val sizes = mutableListOf<androidx.compose.ui.geometry.Size>()
        for (index in 0..5) {
            rule.runOnIdle { selection.value = index }
            rule.mainClock.advanceTimeByFrame()
            rule.waitForIdle()
            val node = rule.onNodeWithTag("gallery")
            sizes += node.fetchSemanticsNode().boundsInRoot.size
            val image = node.captureToImage().asAndroidBitmap()
            File(
                    files,
                    "button-${listOf("primary", "secondary", "terciary")[index % 3]}-${if (index < 3) "compose" else "xml"}.png",
                )
                .outputStream()
                .use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
        for (index in 0..2) {
            assertEquals(sizes[index].width, sizes[index + 3].width, 2f)
            assertEquals(sizes[index].height, sizes[index + 3].height, 4f)
        }
    }
}
