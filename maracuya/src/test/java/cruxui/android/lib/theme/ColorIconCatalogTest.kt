package cruxui.android.lib.theme

import android.content.Context
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.ImageView
import cruxui.android.lib.R
import cruxui.android.lib.theme.catalog.*
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.utils.composeadapters.ColorComposeAdapter
import cruxui.android.lib.wrappers.components.textviews.TextView
import cruxui.android.lib.wrappers.core.resources.resolve
import cruxui.android.lib.wrappers.core.tokens.TextColor
import cruxui.android.lib.wrappers.core.tokens.ViewIcon
import androidx.compose.ui.graphics.toArgb
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** Comprueba el catálogo completo en recursos Android, adaptadores y roles nativos, incluyendo temas distintos. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ColorIconCatalogTest {
    private fun context(dark: Boolean): Context = ContextThemeWrapper(RuntimeEnvironment.getApplication(),
        androidx.appcompat.R.style.Theme_AppCompat_Light).apply {
        theme.applyStyle(if (dark) R.style.ThemeOverlay_Maracuya_Dark else R.style.ThemeOverlay_Maracuya_Light, true)
    }

    @Test fun `cada rol de color coincide en XML Compose y resolutor nativo para ambos modos`() {
        for (dark in listOf(false, true)) {
            val context = context(dark)
            val library = LibraryThemes.Maracuya.resolve(dark)
            for (role in LibraryColorRole.entries) {
                val token = library.colors[role]
                val attr = reference(LibraryXmlReferences.colors, role.catalogName)
                val values = context.obtainStyledAttributes(intArrayOf(attr))
                try {
                    assertEquals(role.name, token.argb.toInt(), values.getColor(0, 0))
                    assertEquals(role.name, token.argb.toInt(), TextColor.Role(role).resolve(context, library).defaultColor)
                    assertEquals(role.name, token.argb.toInt(), ColorComposeAdapter.toComposeColor(token).toArgb())
                } finally { values.recycle() }
            }
        }
    }

    @Test fun `todos los iconos tienen asset tamano y tinte exportados desde su token`() {
        val context = context(false)
        val library = LibraryThemes.Maracuya.resolve(false)
        for (role in LibraryIconRole.entries) {
            val token = library.icons[role]
            val (group, name) = role.catalogName.split('.')
            val values = context.obtainStyledAttributes(intArrayOf(
                nestedReference(LibraryXmlReferences.icons, group, name),
                nestedReference(LibraryXmlReferences.iconSizes, group, name),
                nestedReference(LibraryXmlReferences.iconTints, group, name),
            ))
            try {
                assertEquals(role.name, LibraryAssetResources.icon(token), values.getResourceId(0, 0))
                assertEquals(role.name, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, token.iconSize.toFloat(),
                    context.resources.displayMetrics), values.getDimension(1, 0f), 0.01f)
                if (token.iconColor == null) assertNull(values.getColorStateList(2))
                else assertEquals(token.iconColor.argb.toInt(), values.getColor(2, 0))
                assertSame(token, ViewIcon.Role(role).resolve(library))
            } finally { values.recycle() }
        }
    }

    @Test fun `los roles conservan sustituciones de tema despues de configurar una View`() {
        val base = LibraryThemes.Maracuya.resolve(false)
        val custom = base.copy(colors = base.colors.copy(infoText = ColorToken(0xff123456)),
            icons = base.icons.copy(alerts = base.icons.alerts.copy(info = base.icons.alerts.warning.copy(iconSize = 32))))
        val view = TextView(context(false)).apply {
            setColorRole(LibraryColorRole.InfoText)
            setIconStart(ViewIcon.Role(LibraryIconRole.AlertsInfo))
            setThemeTokens(custom)
        }
        assertEquals(custom.colors.infoText.argb.toInt(), view.currentTextColor)
        assertSame(custom.icons.alerts.info, ViewIcon.Role(LibraryIconRole.AlertsInfo).resolve(custom))
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, view.resources.displayMetrics).toInt(),
            view.compoundDrawablesRelative[0].bounds.width())
        view.setThemeTokens(base)
        assertEquals(base.colors.infoText.argb.toInt(), view.currentTextColor)
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base.icons.alerts.info.iconSize.toFloat(),
            view.resources.displayMetrics).toInt(), view.compoundDrawablesRelative[0].bounds.width())
    }

    @Test fun `XML consume colores e iconos en componentes diferentes sin registros`() {
        val layout = LayoutInflater.from(context(false)).inflate(R.layout.catalog_preview, null)
        val text = layout.findViewById<TextView>(R.id.catalogTextPreview)
        val image = layout.findViewById<ImageView>(R.id.catalogIconPreview)
        assertEquals(LibraryThemes.Maracuya.lightTokens.linkDefault.argb.toInt(), text.currentTextColor)
        assertNotNull(text.compoundDrawablesRelative[0])
        assertNotNull(image.drawable)
        assertNull(image.imageTintList)
        assertEquals(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LibraryThemes.Maracuya.icons.alerts.info.iconSize.toFloat(),
            image.resources.displayMetrics).toInt(), image.layoutParams.width)
    }

    @Test fun `nombres historicos de iconos conservan valores y recursos XML`() {
        val library = LibraryThemes.Maracuya.resolve(false)
        assertSame(library.icons.people.accessibleMenu, library.icons.people.accesibleMenu)
        assertSame(library.icons.extension.davibankHouseEmpty, library.icons.extension.lgDavibankHouseEmpty)
        val values = context(false).obtainStyledAttributes(intArrayOf(
            LibraryXmlReferences.icons.people.accessibleMenu, LibraryXmlReferences.icons.people.accesibleMenu,
            LibraryXmlReferences.icons.extension.davibankHouseEmpty, LibraryXmlReferences.icons.extension.lgDavibankHouseEmpty,
        ))
        try {
            assertEquals(values.getResourceId(0, 0), values.getResourceId(1, 0))
            assertEquals(values.getResourceId(2, 0), values.getResourceId(3, 0))
        } finally { values.recycle() }
    }

    private fun reference(group: Any, name: String): Int = group.javaClass.getDeclaredField(name)
        .apply { isAccessible = true }.getInt(null)

    private fun nestedReference(root: Any, group: String, name: String): Int = root.javaClass.declaredClasses
        .single { it.simpleName == group }.getDeclaredField(name).apply { isAccessible = true }.getInt(null)
}
