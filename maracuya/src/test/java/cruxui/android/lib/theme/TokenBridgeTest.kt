package cruxui.android.lib.theme

import android.content.Context
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cruxui.android.lib.R
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.utils.composeadapters.TypographyComposeAdapter
import cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.lang.reflect.Modifier
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TokenBridgeTest {
    private fun context(dark: Boolean): Context = ContextThemeWrapper(RuntimeEnvironment.getApplication(),
        if (dark) R.style.ThemeOverlay_Maracuya_Dark else R.style.ThemeOverlay_Maracuya_Light)

    @Test fun `all exported color roles match the active Kotlin palette in both modes`() {
        for (dark in listOf(false, true)) {
            val colors = LibraryThemes.Maracuya.resolve(dark).colors
            for ((name, argb) in fields(colors)) {
                val attr = reference(LibraryXmlReferences.colors, name)
                val a = context(dark).obtainStyledAttributes(intArrayOf(attr))
                try { assertEquals("$name dark=$dark", (argb as Long).toInt(), a.getColor(0, 0)) }
                finally { a.recycle() }
            }
        }
    }

    @Test fun `every generated TextAppearance retains typography metrics weight and italic`() {
        val ctx = context(false)
        for ((name, value) in fields(LibraryThemes.Maracuya.typography)) {
            val token = value as TypographyToken
            val theme = ctx.obtainStyledAttributes(intArrayOf(reference(LibraryXmlReferences.typography, name)))
            val style = try { theme.getResourceId(0, 0) } finally { theme.recycle() }
            assertNotEquals(name, 0, style)
            val a = ctx.obtainStyledAttributes(style, intArrayOf(android.R.attr.textSize, android.R.attr.lineHeight,
                android.R.attr.textFontWeight, android.R.attr.letterSpacing, android.R.attr.textStyle, android.R.attr.fontFamily))
            try {
                assertEquals(name, sp(ctx, token.fontSize), a.getDimension(0, 0f), 0.02f)
                assertEquals(name, sp(ctx, token.lineHeight), a.getDimension(1, 0f), 0.02f)
                assertEquals(name, token.weight, a.getInt(2, 0))
                assertEquals(name, token.letterSpacing / token.fontSize, a.getFloat(3, 0f), 0.0001f)
                assertEquals(name, token.italic, a.getInt(4, 0) and 2 != 0)
                assertNotEquals(name, 0, a.getResourceId(5, 0))
            } finally { a.recycle() }
        }
    }

    @Test fun `icon bindings and generated references use the same actual drawables`() {
        val ctx = context(false)
        for ((group, icons) in fields(LibraryThemes.Maracuya.icons)) for ((name, value) in fields(icons)) {
            val token = value as IconToken
            val nested = LibraryXmlReferences.icons.javaClass.declaredClasses.single { it.simpleName == group }
            val field = nested.getDeclaredField(name).apply { isAccessible = true }
            val attr = field.getInt(null)
            val a = ctx.obtainStyledAttributes(intArrayOf(attr))
            try { assertEquals("$group.$name", LibraryAssetResources.icon(token), a.getResourceId(0, 0)) }
            finally { a.recycle() }
        }
        assertEquals(123, LibraryAssetResources.icon(IconToken("host", 123)))
    }

    @Test fun `font catalog exposes physical weights and italic faces for both adapters`() {
        for (family in FontFamilyToken.entries) {
            val faces = FontResourceCatalog.faces(family)
            assertTrue(faces.any { it.italic })
            for (asset in FontAssetCatalog.faces(family)) {
                assertTrue(faces.any { it.resourceId == LibraryAssetResources.font(asset.resourceName) && it.weight == asset.weight && it.italic == asset.italic })
            }
        }
        val style = TypographyComposeAdapter.toTextStyle(TypographyToken(24f, 30f, weight = 900), FontFamily.Default)
        assertEquals(FontWeight(900), style.fontWeight)
    }

    @Test fun `themes reuse collections and resolve AUTO once for either frontend`() {
        val theme = LibraryThemes.Maracuya
        assertSame(theme.resolve(false), theme.resolve(ThemeStyle.LIGHT, true))
        assertSame(theme.resolve(true), theme.resolve(ThemeStyle.AUTO, true))
        assertSame(theme.resolve(false).typography, theme.resolve(true).typography)
        assertSame(theme.resolve(false).icons, theme.resolve(true).icons)
        val custom = theme.copy(name = "Custom", typography = theme.typography.copy(
            headline24 = theme.typography.headline24.copy(fontSize = 26f)), fontFamily = FontFamilyToken.DAVIVIENDA)
        assertEquals(26f, custom.resolve(false).typography.headline24.fontSize, 0f)
        assertEquals(FontFamilyToken.DAVIVIENDA, custom.resolve(false).fontFamily)
    }

    @Test fun `registry receives each scope and never freezes theme when registering`() {
        val registry = TokensOverrideRegistry<TextViewTokensOverride>()
        registry.register("Title") { library: LibraryThemeSnapshot ->
            TextViewTokensOverride(textColor = library.colors.fgDefault, typography = library.typography.headline24)
        }
        val light = LibraryThemes.Maracuya.resolve(false)
        val dark = LibraryThemes.Maracuya.resolve(true)
        assertEquals(light.colors.fgDefault, registry.resolve(" title ", light)!!.textColor)
        assertEquals(dark.colors.fgDefault, registry.resolve("TITLE", dark)!!.textColor)
        val custom = light.copy(typography = light.typography.copy(headline24 = light.typography.headline24.copy(fontSize = 31f)))
        assertEquals(31f, registry.resolve("title", custom)!!.typography!!.fontSize, 0f)
        registry.unregister("title")
        assertThrows(IllegalArgumentException::class.java) { registry.resolve("title", light) }
        assertNull(registry.resolve(null, light))
    }

    @Test fun `legacy builders and key normalization do not depend on system locale`() {
        val registry = TokensOverrideRegistry<TextViewTokensOverride>()
        val locale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"))
            registry.register("TITLE") { colors, typography, _, _ ->
                TextViewTokensOverride(textColor = colors.brandPrimary, typography = typography.headline24)
            }
            assertNotNull(registry.resolve("title", LibraryThemes.Maracuya.resolve(false)))
            registry.register("literal", TextViewTokensOverride(textColor = ColorToken(0xff112233)))
            assertEquals(ColorToken(0xff112233), registry.resolve("literal", LibraryThemes.Maracuya.resolve(true))!!.textColor)
        } finally { Locale.setDefault(locale) }
    }

    private fun reference(group: Any, name: String): Int = group.javaClass.getDeclaredField(name).let {
        it.isAccessible = true
        it.getInt(null)
    }
    private fun fields(value: Any) = value.javaClass.declaredFields.filter { !it.isSynthetic && !Modifier.isStatic(it.modifiers) }
        .associate { it.isAccessible = true; it.name to requireNotNull(it.get(value)) }
    private fun sp(ctx: Context, value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, ctx.resources.displayMetrics)
}
