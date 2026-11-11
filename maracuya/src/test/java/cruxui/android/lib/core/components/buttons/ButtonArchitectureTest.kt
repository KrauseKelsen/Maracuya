package cruxui.android.lib.core.components.buttons

import cruxui.android.lib.core.components.buttons.behavior.*
import cruxui.android.lib.core.components.buttons.tokens.*
import cruxui.android.lib.core.components.buttons.tokensrender.ButtonTokensRenderResolver
import cruxui.android.lib.core.components.buttons.variant.ButtonVariant
import cruxui.android.lib.theme.LibraryThemes
import org.junit.Assert.*
import org.junit.Test

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonArchitectureTest verifica contratos compartidos sin levantar una UI.
 *
 * Justificación: Las combinaciones de estado y las recetas por modo deben coincidir en ambos
 * frontends.
 *
 * Se comunica con: Ejercita LibraryThemes, defaults, behavior y tokensrender reales.
 *
 * Uso:
 * ```shell
 * ./gradlew :maracuya:testDebugUnitTest
 * ```
 *
 * Objetivos:
 * - Detectar regresiones de prioridad, tinte y preservación de colores.
 *
 * Flujo: tema y entrada del host → resolución compartida → adaptador de plataforma → render.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
class ButtonArchitectureTest {
    /** Cubre todas las combinaciones de enabled, loading, disabled y pressed. */
    @Test
    fun prioritiesAndClickGating() {
        for (enabled in listOf(false, true)) for (loading in listOf(false, true)) for (disabled in
            listOf(false, true)) for (pressed in listOf(false, true)) {
            val state =
                ButtonBehaviorResolver.resolve(ButtonBehavior(loading, disabled, pressed), enabled)
            assertEquals(!enabled || disabled, state.isDisabled)
            assertEquals(enabled && !disabled && loading, state.isLoading)
            assertEquals(enabled && !disabled && !loading && pressed, state.isPressed)
            assertEquals(enabled && !disabled && !loading, state.canClick)
        }
    }

    /** Protege las recetas históricas en claro y oscuro; loading no añade otra paleta. */
    @Test
    fun threeSurfaceVariantsAndLoadingDefaultsWorkInBothModes() {
        for (dark in listOf(false, true)) {
            val library = LibraryThemes.Maracuya.resolve(dark)
            for (variant in ButtonVariant.entries) {
                val tokens = ButtonTokenDefaults.resolve(library, variant)
                val normal =
                    ButtonTokensRenderResolver.resolve(tokens, ButtonBehaviorResolver.resolve())
                val loading =
                    ButtonTokensRenderResolver.resolve(
                        tokens,
                        ButtonBehaviorResolver.resolve(ButtonBehavior(isLoading = true)),
                    )
                assertEquals(normal, loading)
                assertEquals(library.typography.buttons, tokens.typography)
                assertNull(tokens.iconStart)
                assertNull(tokens.iconEnd)
                assertEquals(library.colors.bgMuted, tokens.disabledBackground)
                assertEquals(library.colors.fgSubtle, tokens.disabledTextColor)
                val secondary = variant == ButtonVariant.SECONDARY
                assertEquals(
                    if (secondary) library.colors.fgDefault else library.colors.brandPrimary,
                    tokens.border,
                )
                assertEquals(
                    if (secondary) library.colors.fgDefault else library.colors.brandPrimaryHover,
                    tokens.pressedBackground,
                )
                assertEquals(normal.textColor, normal.iconStartColor)
                assertEquals(normal.textColor, normal.iconEndColor)
            }
            assertEquals(
                library.colors.brandPrimary,
                ButtonTokenDefaults.resolve(library).background,
            )
            assertEquals(
                library.colors.fgDefault,
                ButtonTokenDefaults.resolve(library, ButtonVariant.SECONDARY).textColor,
            )
            assertEquals(
                library.colors.bgBase,
                ButtonTokenDefaults.resolve(library, ButtonVariant.SECONDARY).background,
            )
            assertEquals(
                library.colors.fgOnInverseAux,
                ButtonTokenDefaults.resolve(library, ButtonVariant.TERCIARY).textColor,
            )
            assertEquals(
                library.colors.bgBase,
                ButtonTokenDefaults.resolve(library, ButtonVariant.TERCIARY).background,
            )
        }
    }

    /**
     * El borde pressed tiene token propio y el TextView recibe un tinte común para texto/iconos.
     */
    @Test
    fun pressedBorderIsIndependentAndTextViewOwnsIconTint() {
        val library = LibraryThemes.Maracuya.resolve(false)
        val tokens =
            ButtonTokenDefaults.resolve(
                library,
                override =
                    ButtonTokensOverride(
                        pressedBorder = library.colors.borderFocus,
                        pressedBackground = library.colors.bgBase,
                        iconStart = library.icons.alerts.info,
                        iconEnd = library.icons.alerts.warning,
                    ),
            )
        val render =
            ButtonTokensRenderResolver.resolve(
                tokens,
                ButtonBehaviorResolver.resolve(pressed = true),
            )
        assertEquals(library.colors.borderFocus, render.border)
        assertEquals(library.colors.bgBase, render.background)
        assertEquals(render.text.textColor, render.text.disabledColor)
        assertEquals(tokens.iconStart, render.iconStart)
        assertEquals(render.iconStart, render.text.iconStart)
        assertEquals(tokens.iconEnd, render.iconEnd)
        assertEquals(render.iconEnd, render.text.iconEnd)
    }
}
