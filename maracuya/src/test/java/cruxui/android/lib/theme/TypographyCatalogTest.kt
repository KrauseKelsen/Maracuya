package cruxui.android.lib.theme

import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import cruxui.android.lib.R
import cruxui.android.lib.atoms.LibraryTypographyTokens
import cruxui.android.lib.semantics.CorporateTypography
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.utils.composeadapters.TypographyComposeAdapter
import cruxui.android.lib.wrappers.components.textviews.TextView
import cruxui.android.lib.wrappers.core.resources.resolve
import cruxui.android.lib.wrappers.core.tokens.TextTypography
import cruxui.android.lib.wrappers.core.tokens.TextTypographyRole
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.Config

/** Recorre el catálogo real completo para detectar omisiones en cualquiera de sus consumidores. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class TypographyCatalogTest {
    private val context = ContextThemeWrapper(RuntimeEnvironment.getApplication(), androidx.appcompat.R.style.Theme_AppCompat_Light)
        .apply { theme.applyStyle(R.style.ThemeOverlay_Maracuya, true) }

    @Test fun `Library es la fuente de todas las propiedades y roles generados`() {
        val defaults = LibraryTypographyTokens::class.java.declaredFields
            .filter { it.type == TypographyToken::class.java }
            .associate { it.isAccessible = true; it.name to it.get(null) as TypographyToken }
        val typography = CorporateTypography()
        assertEquals(defaults.keys, typography.asMap().keys)
        defaults.forEach { (name, token) -> assertSame(name, token, typography[name]) }
        assertEquals(defaults.keys, TextTypographyRole.entries.map { it.catalogName }.toSet())
        val library = LibraryThemes.Maracuya.resolve(false)
        for (role in TextTypographyRole.entries) {
            assertSame(library.typography[role.catalogName], TextTypography.Role(role).resolve(library))
        }
    }

    @Test fun `todos los estilos son consumibles como TextAppearance y mediante roles nativos`() {
        val library = LibraryThemes.Maracuya.resolve(false)
        for (role in TextTypographyRole.entries) {
            val token = library.typography[role.catalogName]
            val attr = LibraryXmlReferences.typography.javaClass.getDeclaredField(role.catalogName).apply { isAccessible = true }.getInt(null)
            val a = context.obtainStyledAttributes(intArrayOf(attr))
            val style = try { a.getResourceId(0, 0) } finally { a.recycle() }
            assertNotEquals(role.name, 0, style)
            val xml = TextView(context).apply { setTextAppearance(style) }
            val native = TextView(context).apply { setTypography(role) }
            val expectedPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, token.fontSize, context.resources.displayMetrics)
            assertEquals(role.name, expectedPx, xml.textSize, 0.01f)
            assertEquals(role.name, xml.textSize, native.textSize, 0.01f)
            assertEquals(role.name, token.italic, xml.typeface.isItalic)
            assertEquals(role.name, token.italic, native.typeface.isItalic)
            val compose = TypographyComposeAdapter.toTextStyle(token, FontFamily.Default)
            assertEquals(role.name, token.fontSize, compose.fontSize.value, 0f)
            assertEquals(role.name, if (token.italic) FontStyle.Italic else FontStyle.Normal, compose.fontStyle)
        }
    }

    @Test fun `un rol consulta el tema personalizado y el alias Body conserva body1`() {
        val base = LibraryThemes.Maracuya.resolve(false)
        val custom = base.copy(typography = base.typography.copy(
            headline24Medium = base.typography.headline24Medium.copy(fontSize = 33f),
        ))
        val role = TextTypography.Role(TextTypographyRole.Headline24Medium)
        assertEquals(33f, role.resolve(custom).fontSize, 0f)
        assertSame(base.typography.headline24Medium, role.resolve(base))
        assertSame(TextTypography.Role(TextTypographyRole.Body).resolve(base),
            TextTypography.Role(TextTypographyRole.Body1).resolve(base))
        assertThrows(IllegalArgumentException::class.java) { base.typography["inexistente"] }
    }

    @Test fun `XML infla encabezados cursivas y legales sin registrar recetas`() {
        val layout = LayoutInflater.from(context).inflate(R.layout.typography_preview, null)
        val title = layout.findViewById<TextView>(R.id.typographyHeadlinePreview)
        val body = layout.findViewById<TextView>(R.id.typographyBodyItalicPreview)
        val legal = layout.findViewById<TextView>(R.id.typographyLegalPreview)
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, LibraryTypographyTokens.headline24Medium.fontSize,
            context.resources.displayMetrics), title.textSize, 0.01f)
        assertTrue(body.typeface.isItalic)
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, LibraryTypographyTokens.legalBold.fontSize,
            context.resources.displayMetrics), legal.textSize, 0.01f)
    }
}
