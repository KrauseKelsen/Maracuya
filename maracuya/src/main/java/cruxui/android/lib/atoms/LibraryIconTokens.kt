package cruxui.android.lib.atoms

import cruxui.android.lib.atoms.icons.LibraryAlertsIconsTokens
import cruxui.android.lib.atoms.icons.LibraryArrowIconsTokens
import cruxui.android.lib.atoms.icons.LibraryExtensionTokens
import cruxui.android.lib.atoms.icons.LibraryGeneralIconsTokens
import cruxui.android.lib.atoms.icons.LibraryMoneyIconsTokens
import cruxui.android.lib.atoms.icons.LibraryPeopleIconsTokens

import cruxui.android.lib.tokens.base.LibraryIconGroup

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryIconTokens es la fuente de categorías de iconos del Design System.
 *
 * Justificación:
 * Agrupa los catálogos Library sin depender de Android ni de Compose. LibraryIconGroup declara
 * el nombre público de cada categoría; token-exporter genera LibraryIcons y los atributos XML desde aquí.
 *
 * Uso:
 * API pública para configurar marcas. El host puede copiar Default y sustituir una categoría:
 * ```
 * val base = LibraryIconTokens.Default
 * val icons = base.copy(alertsIconsLibrary = base.alertsIconsLibrary.copy(info = otroIcono))
 * val theme = LibraryThemes.Maracuya.copy(iconAtoms = icons)
 * ```
 * En Compose, dentro de MyLibraryTheme(theme), consumir LibraryTheme.current.icons.alerts.info.
 * En una receta XML, usar library.icons.alerts.info desde el snapshot recibido por register.
 * En XML compilado usar android:drawableStart="?attr/libraryIconAlertsInfo" con el overlay de marca.
 * Para añadir un icono, declararlo en su categoría Library; para añadir una categoría, agregar aquí
 * su propiedad, @get:LibraryIconGroup("nombre") y valor en Default. No agregar registros al exportador.
 * Una marca permanente debe pertenecer a LibraryThemes.all para que el build genere su overlay.
 * Los cambios realizados en ejecución con copy no reescriben los recursos Android compilados.
 *
 * Objetivos:
 * - Centralizar la estructura y los valores de iconos.
 * - Incorporar automáticamente cada categoría y token en ambas plataformas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemes
 */
data class LibraryIconTokens(

    @get:LibraryIconGroup("general", legacyType = "GeneralIcons")
    val generalIconsLibrary: LibraryGeneralIconsTokens,
    @get:LibraryIconGroup("alerts", legacyType = "AlertsIcons")
    val alertsIconsLibrary: LibraryAlertsIconsTokens,
    @get:LibraryIconGroup("people", legacyType = "PeopleIcons")
    val peopleIconsLibrary: LibraryPeopleIconsTokens,
    @get:LibraryIconGroup("arrows", legacyType = "ArrowIcons")
    val arrowIconsLibrary: LibraryArrowIconsTokens,
    @get:LibraryIconGroup("money", legacyType = "MoneyIcons")
    val moneyIconsLibrary: LibraryMoneyIconsTokens,

    @get:LibraryIconGroup("extension", legacyType = "ExtensionIcons")
    val extensionLibrary: LibraryExtensionTokens,

) {
    companion object {
        /** Catálogo base; cada categoría conserva su propia definición de assets. */
        val Default = LibraryIconTokens(

            generalIconsLibrary = LibraryGeneralIconsTokens.general,
            alertsIconsLibrary = LibraryAlertsIconsTokens.alerts,
            peopleIconsLibrary = LibraryPeopleIconsTokens.people,
            arrowIconsLibrary = LibraryArrowIconsTokens.arrows,
            moneyIconsLibrary = LibraryMoneyIconsTokens.money,
            extensionLibrary = LibraryExtensionTokens.extension

        )
    }
}
