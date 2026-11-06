package schema

import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.atoms.LibraryIconTokens
import cruxui.android.lib.semantics.CorporateIcons
import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.theme.catalog.*
import cruxui.android.lib.theme.collections.LibraryIcons
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.icons.getAlertsIcons
import org.junit.Assert.*
import org.junit.Test

/** Verifica cobertura del catálogo real, identidad de grupos y compatibilidad sin listas de tokens en tests. */
class LibraryCatalogSchemaTest {
    @Test fun `todos los colores descubiertos se consultan por nombre y rol sin congelar el tema`() {
        val colors = LibraryColorTokens.DefaultDark.copy(infoText = ColorToken(0xff123456))
        assertEquals(LibraryCatalogSchema.colors().toSet(), colors.asMap().keys)
        assertEquals(colors.asMap().keys, LibraryColorRole.entries.map { it.catalogName }.toSet())
        LibraryColorRole.entries.forEach { assertEquals(colors.asMap()[it.catalogName], colors[it]) }
        assertEquals(ColorToken(0xff123456), colors[LibraryColorRole.InfoText])
        assertEquals(colors.fgDefault, colors[LibraryColorRole.Foreground])
        assertThrows(IllegalArgumentException::class.java) { colors["ausente"] }
    }

    @Test fun `ningun grupo ni icono se pierde y los alias conservan las instancias Library`() {
        val atoms = LibraryIconTokens.Default.copy(alertsIconsLibrary = LibraryIconTokens.Default.alertsIconsLibrary.copy(
            info = LibraryIconTokens.Default.alertsIconsLibrary.warning.copy(iconSize = 37),
        ))
        val icons: CorporateIcons = LibraryIcons.fromAtoms(atoms)
        assertSame(atoms.alertsIconsLibrary, icons.alerts)
        assertSame(atoms.alertsIconsLibrary, getAlertsIcons(atoms))
        assertSame(atoms.alertsIconsLibrary.info, icons[LibraryIconRole.AlertsInfo])
        val schema = LibraryCatalogSchema.icons().associate { it.name to it.icons.toSet() }
        assertEquals(schema, icons.asMap().mapValues { it.value.keys })
        val paths = schema.flatMap { (group, names) -> names.map { "$group.$it" } }.toSet()
        assertEquals(paths, LibraryIconRole.entries.map { it.catalogName }.toSet())
        LibraryIconRole.entries.forEach { role ->
            val (group, name) = role.catalogName.split('.')
            assertSame(icons.asMap().getValue(group).getValue(name), icons[role])
        }
        assertThrows(IllegalArgumentException::class.java) { icons["ausente.icono"] }
    }

    @Test fun `el tema conserva Library sin transformar cada icono`() {
        val theme = LibraryThemes.Maracuya
        assertSame(theme.iconAtoms.generalIconsLibrary, theme.resolve(false).icons.general)
        assertSame(theme.iconAtoms.alertsIconsLibrary, theme.resolve(true).icons.alerts)
    }

    @Test fun `el esquema rechaza colisiones antes de escribir codigo`() {
        assertThrows(IllegalArgumentException::class.java) { LibraryCatalogSchema.validateNames(listOf("color", "Color")) }
        assertThrows(IllegalArgumentException::class.java) { LibraryCatalogSchema.validateNames(listOf("icon", "icon")) }
        assertThrows(IllegalArgumentException::class.java) { LibraryCatalogSchema.validateNames(listOf("invalid-name")) }
        assertThrows(IllegalArgumentException::class.java) {
            RolesSource.render("LibraryIconRole", listOf("AlertsInfo" to "alerts.info", "AlertsInfo" to "alertsInfo.icon"), "")
        }
    }
}
