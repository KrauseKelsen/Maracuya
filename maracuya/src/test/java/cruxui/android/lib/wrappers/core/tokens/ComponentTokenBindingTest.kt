package cruxui.android.lib.wrappers.core.tokens

import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.ui.components.buttons.simple.ButtonTokensOverride
import cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry
import org.junit.Assert.*
import org.junit.Test

/** Comprueba independencia de componente, registro y ámbito sin instanciar TextView ni Compose. */
class ComponentTokenBindingTest {
    @Test fun `Button resuelve recetas por ambito y recupera herencia al retirar tema explicito`() {
        val registry = TokensOverrideRegistry<ButtonTokensOverride>()
        registry.register("principal") { library: LibraryThemeSnapshot ->
            ButtonTokensOverride(contentColor = library.colors.fgDefault, textTypography = library.typography.headline24)
        }
        val light = LibraryThemes.Maracuya.resolve(false)
        val dark = LibraryThemes.Maracuya.resolve(true)
        var inherited = light
        val first = ComponentTokenBinding(registry) { inherited }
        val second = ComponentTokenBinding(registry) { light }
        inherited = dark
        first.refresh()
        assertEquals(dark.colors.fgDefault, first.recipe("principal")!!.contentColor)
        assertEquals(light.colors.fgDefault, second.recipe("principal")!!.contentColor)
        val custom = light.copy(typography = light.typography.copy(headline24 = light.typography.headline24.copy(fontSize = 35f)))
        first.explicitTheme = custom
        first.refresh()
        assertEquals(35f, first.recipe("principal")!!.textTypography!!.fontSize, 0f)
        first.explicitTheme = null
        first.refresh()
        assertSame(dark, first.library)
    }

    @Test fun `registros diferentes admiten el mismo nombre sin mezclar recetas`() {
        val buttons = TokensOverrideRegistry<ButtonTokensOverride>()
        val counters = TokensOverrideRegistry<Int>()
        buttons.register("principal", ButtonTokensOverride(textTypography = LibraryThemes.Maracuya.typography.body1))
        counters.register("principal", 63)
        val library = LibraryThemes.Maracuya.resolve(false)
        assertEquals(63, ComponentTokenBinding(counters) { library }.recipe("principal"))
        assertNotNull(ComponentTokenBinding(buttons) { library }.recipe("principal")!!.textTypography)
        buttons.clear()
        assertTrue(counters.contains("principal"))
    }

    @Test fun `preview tolera recetas no registradas y ejecucion conserva el error`() {
        val binding = ComponentTokenBinding(TokensOverrideRegistry<Int>()) { LibraryThemes.Maracuya.resolve(false) }
        assertNull(binding.recipe("ausente", preview = true))
        assertNull(binding.recipe("  "))
        assertThrows(IllegalArgumentException::class.java) { binding.recipe("ausente") }
    }
}
