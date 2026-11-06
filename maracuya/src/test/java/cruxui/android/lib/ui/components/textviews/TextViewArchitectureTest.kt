package cruxui.android.lib.ui.components.textviews

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehaviorResolver
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokens
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.ui.components.textviews.tokens.merge
import cruxui.android.lib.ui.components.textviews.tokensrender.TextViewTokensRenderResolver
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariant
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariantResolver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class TextViewArchitectureTest {

    @Test
    fun mergeWithoutOverrideReturnsSameTokens() {
        val tokens = tokens()

        assertSame(tokens, tokens.merge(null))
    }

    @Test
    fun mergeChangesOnlyOverriddenFields() {
        val tokens = tokens()
        val startIcon = IconToken(name = "link", filledResId = 10)
        val overriddenTextColor = ColorToken(0xFF101010)

        val merged = tokens.merge(
            TextViewTokensOverride(
                textColor = overriddenTextColor,
                iconStart = startIcon,
            ),
        )

        assertEquals(overriddenTextColor, merged.textColor)
        assertEquals(startIcon, merged.iconStart)
        assertEquals(tokens.backgroundColor, merged.backgroundColor)
        assertEquals(tokens.errorColor, merged.errorColor)
        assertEquals(tokens.foregroundColor, merged.foregroundColor)
        assertEquals(tokens.disabledColor, merged.disabledColor)
        assertNull(merged.iconEnd)
        assertEquals(tokens.typography, merged.typography)
        assertEquals(tokens.fontFamily, merged.fontFamily)
    }

    @Test
    fun renderUsesNormalColorForTextAndIcons() {
        val tokens = tokens(
            iconStart = IconToken(name = "start", filledResId = 1),
            iconEnd = IconToken(name = "end", filledResId = 2),
        )

        val render = TextViewTokensRenderResolver.resolve(
            tokens = tokens,
            enabled = true,
            error = false,
        )

        assertEquals(tokens.textColor, render.textColor)
        assertEquals(tokens.textColor, render.iconStartColor)
        assertEquals(tokens.textColor, render.iconEndColor)
        assertEquals(tokens.backgroundColor, render.backgroundColor)
        assertEquals(tokens.foregroundColor, render.foregroundColor)
    }

    @Test
    fun renderUsesErrorColorWhenEnabledAndInError() {
        val tokens = tokens()

        val render = TextViewTokensRenderResolver.resolve(
            tokens = tokens,
            enabled = true,
            error = true,
        )

        assertEquals(tokens.errorColor, render.textColor)
        assertEquals(tokens.errorColor, render.iconStartColor)
        assertEquals(tokens.errorColor, render.iconEndColor)
    }

    @Test
    fun disabledColorHasPriorityOverError() {
        val tokens = tokens()

        val render = TextViewTokensRenderResolver.resolve(
            tokens = tokens,
            enabled = false,
            error = true,
        )

        assertEquals(tokens.disabledColor, render.textColor)
        assertEquals(tokens.disabledColor, render.iconStartColor)
        assertEquals(tokens.disabledColor, render.iconEndColor)
    }

    @Test
    fun hipertextAddsUnderlineAndDefaultKeepsStyle() {
        val source = TextStyle.Default

        assertSame(
            source,
            TextViewVariantResolver.resolveTextStyle(TextViewVariant.DEFAULT, source),
        )
        assertEquals(
            TextDecoration.Underline,
            TextViewVariantResolver.resolveTextStyle(TextViewVariant.HIPERTEXT, source).textDecoration,
        )
    }

    @Test
    fun behaviorMarksIconInteractionsAndDisablesSelection() {
        val state = TextViewBehaviorResolver.resolve(
            behavior = TextViewBehavior.Selectable,
            enabled = false,
            onClick = {},
            onLongClick = {},
            onDoubleClick = {},
            onIconStartClick = {},
            onIconStartLongClick = null,
            onIconEndClick = null,
            onIconEndLongClick = {},
            onClickLabel = null,
            onLongClickLabel = null,
            iconStartContentDescription = "Inicio",
            iconEndContentDescription = "Fin",
        )

        assertFalse(state.textSelectionEnabled)
        assertTrue(state.iconStartInteractive)
        assertTrue(state.iconEndInteractive)
        assertEquals("Inicio", state.iconStartContentDescription)
        assertEquals("Fin", state.iconEndContentDescription)
    }

    private fun tokens(
        iconStart: IconToken? = null,
        iconEnd: IconToken? = null,
    ) = TextViewTokens(
        textColor = ColorToken(0xFF000001),
        backgroundColor = ColorToken(0xFF000002),
        errorColor = ColorToken(0xFF000003),
        foregroundColor = ColorToken(0x00000000),
        disabledColor = ColorToken(0xFF000004),
        iconStart = iconStart,
        iconEnd = iconEnd,
        typography = TypographyToken(
            fontSize = 14f,
            lineHeight = 21f,
        ),
        fontFamily = FontFamilyToken.MULISH,
    )
}
