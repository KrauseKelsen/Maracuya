package cruxui.android.lib.ui.components.textviews

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.click
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import cruxui.android.lib.compositions.LocalLibraryIcons
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensOverride
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextViewInteractionTest verifica las acciones táctiles y accesibles del TextView Compose real.
 *
 * Justificación:
 * Los resolutores de tokens no prueban la entrega de eventos ni la prioridad de la selección.
 * Esta suite conecta TextView, MyLibraryTheme y el reloj de entrada de Compose en un dispositivo.
 *
 * Uso:
 * Prueba instrumentada interna; no forma parte del API del host. Se ejecuta con AndroidJUnitRunner.
 * Los gestos independientes dejan transcurrir la ventana de doble toque entre secuencias.
 *
 * Objetivos:
 * - Conservar acciones de texto e iconos independientes.
 * - Verificar disabled, selección y combinaciones de callbacks.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
class TextViewInteractionTest {

    @get:Rule
    val composeRule = createComposeRule()

    /** Comprueba click, pulsación larga y doble toque sobre el mismo componente. */
    @Test
    fun textSupportsClickLongClickAndDoubleClick() {
        var clicks = 0
        var longClicks = 0
        var doubleClicks = 0
        composeRule.setContent {
            MyLibraryTheme {
                TextView(
                    text = "Interactivo",
                    onClick = { clicks++ },
                    onLongClick = { longClicks++ },
                    onDoubleClick = { doubleClicks++ },
                )
            }
        }

        composeRule.onNodeWithText("Interactivo").performTouchInput {
            click()
            // Son tres gestos independientes; no deben compartir la ventana de doble toque.
            advanceEventTime(viewConfiguration.doubleTapTimeoutMillis + 1)
            longClick()
            advanceEventTime(viewConfiguration.doubleTapTimeoutMillis + 1)
            doubleClick()
        }

        composeRule.runOnIdle {
            assertEquals(1, clicks)
            assertEquals(1, longClicks)
            assertEquals(1, doubleClicks)
        }
    }

    /** Verifica que las acciones de ambos iconos no activen el callback del texto. */
    @Test
    fun iconInteractionsDoNotInvokeTextInteraction() {
        var textClicks = 0
        var startClicks = 0
        var startLongClicks = 0
        var endClicks = 0
        var endLongClicks = 0
        composeRule.setContent {
            MyLibraryTheme {
                val icons = LocalLibraryIcons.current
                TextView(
                    text = "Con iconos",
                    tokensOverride = TextViewTokensOverride(
                        iconStart = icons.general.link,
                        iconEnd = icons.alerts.info,
                    ),
                    onClick = { textClicks++ },
                    onIconStartClick = { startClicks++ },
                    onIconStartLongClick = { startLongClicks++ },
                    onIconEndClick = { endClicks++ },
                    onIconEndLongClick = { endLongClicks++ },
                    iconStartContentDescription = "Icono inicial",
                    iconEndContentDescription = "Icono final",
                )
            }
        }

        composeRule.onNodeWithContentDescription("Icono inicial").performClick()
        composeRule.onNodeWithContentDescription("Icono inicial").performTouchInput { longClick() }
        composeRule.onNodeWithContentDescription("Icono final").performClick()
        composeRule.onNodeWithContentDescription("Icono final").performTouchInput { longClick() }

        composeRule.runOnIdle {
            assertEquals(0, textClicks)
            assertEquals(1, startClicks)
            assertEquals(1, startLongClicks)
            assertEquals(1, endClicks)
            assertEquals(1, endLongClicks)
        }
    }

    /** Comprueba bloqueo táctil y semántica accesible del estado deshabilitado. */
    @Test
    fun disabledBlocksInteractionsAndExposesDisabledSemantics() {
        var clicks = 0
        var iconClicks = 0
        composeRule.setContent {
            MyLibraryTheme {
                TextView(
                    text = "Deshabilitado",
                    modifier = Modifier.testTag("disabled-text-view"),
                    enabled = false,
                    tokensOverride = TextViewTokensOverride(
                        iconStart = LocalLibraryIcons.current.general.link,
                    ),
                    onClick = { clicks++ },
                    onIconStartClick = { iconClicks++ },
                    iconStartContentDescription = "Icono deshabilitado",
                )
            }
        }

        composeRule.onNodeWithTag("disabled-text-view").assertIsNotEnabled()
        composeRule.onNodeWithText("Deshabilitado").performTouchInput { click() }
        composeRule.onNodeWithContentDescription("Icono deshabilitado").performTouchInput { click() }

        composeRule.runOnIdle {
            assertEquals(0, clicks)
            assertEquals(0, iconClicks)
        }
    }

    /** El texto sin callbacks no anuncia una acción de click vacía. */
    @Test
    fun basicTextHasNoClickAction() {
        composeRule.setContent {
            MyLibraryTheme {
                TextView(text = "Solo texto")
            }
        }

        composeRule.onNodeWithText("Solo texto").assertHasNoClickAction()
    }

    /** Prueba acciones largas y dobles sin exigir un callback de click sencillo. */
    @Test
    fun longAndDoubleOnlyDoNotCreateNoOpPointerClicks() {
        var longClicks = 0
        var doubleClicks = 0
        composeRule.setContent {
            MyLibraryTheme {
                Column {
                    TextView(
                        text = "Solo long",
                        onLongClick = { longClicks++ },
                    )
                    TextView(
                        text = "Solo doble",
                        onDoubleClick = { doubleClicks++ },
                    )
                }
            }
        }

        composeRule.onNodeWithText("Solo long").assertHasNoClickAction()
        composeRule.onNodeWithText("Solo long").performTouchInput { click() }
        composeRule.onNodeWithText("Solo long").performTouchInput { longClick() }
        composeRule.onNodeWithText("Solo doble").performTouchInput { click() }
        composeRule.onNodeWithText("Solo doble").performTouchInput { doubleClick() }

        composeRule.runOnIdle {
            assertEquals(1, longClicks)
            assertEquals(1, doubleClicks)
        }
    }

    /** La selección conserva prioridad sobre los callbacks de pulsación larga y doble toque. */
    @Test
    fun selectableTextReservesLongPressAndDoubleTap() {
        var longClicks = 0
        var doubleClicks = 0
        composeRule.setContent {
            MyLibraryTheme {
                TextView(
                    text = "Seleccionable",
                    behavior = TextViewBehavior.Selectable,
                    onLongClick = { longClicks++ },
                    onDoubleClick = { doubleClicks++ },
                )
            }
        }

        composeRule.onNodeWithText("Seleccionable").performTouchInput { longClick() }
        composeRule.onNodeWithText("Seleccionable").performTouchInput { doubleClick() }

        composeRule.runOnIdle {
            assertEquals(0, longClicks)
            assertEquals(0, doubleClicks)
        }
    }
}
