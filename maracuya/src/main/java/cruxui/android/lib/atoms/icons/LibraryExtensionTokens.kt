package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryExtensionTokens declara los IconToken de una categoría Library.
 *
 * Justificación:
 * Es la única declaración de los iconos de esta categoría. LibraryIconTokens la incorpora al tema;
 * token-exporter descubre todos sus getters para generar roles, referencias XML y consultas sin duplicarlos.
 *
 * Uso:
 * API pública de configuración. Personalizar con copy(nombreDelIcono = otroToken) y entregar la categoría
 * al campo correspondiente de LibraryIconTokens; LibraryThemes consume esa colección.
 * El host normalmente lee library.icons.<grupo>.<icono> desde LibraryTheme.current o una receta.
 * Para ampliar el DS agregar un val IconToken al constructor y su valor en el catálogo del companion.
 * resourceName debe nombrar un drawable existente en res/drawable, sin extensión ni prefijo @drawable/.
 * Recompilar genera LibraryIconRole, LibraryXmlReferences.icons y ?attr/libraryIcon<Grupo><Icono>.
 * Cambiar el recurso del token existente actualiza ambos consumidores sin modificar el exportador.
 * Los recursos propios del host mediante filledResId son solo de ejecución, no exportables por este módulo.
 *
 * Objetivos:
 * - Mantener nombres, assets y personalizaciones en una única fuente.
 * - Evitar mapeos por icono en semantics o wrappers.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.atoms.LibraryIconTokens
 */
data class LibraryExtensionTokens (
    /** Token davibankHouseEmpty; puede sustituirse al configurar la categoría del tema. */
    val davibankHouseEmpty: IconToken
) {
    /** Alias histórico de lectura y XML; utiliza el mismo token que davibankHouseEmpty, sin almacenar otro valor.
     * Para personalizaciones con copy utilizar el nombre Library davibankHouseEmpty.
     */
    val lgDavibankHouseEmpty: IconToken get() = davibankHouseEmpty

    companion object {
        /** Valores base de la categoría; fuente única de sus assets. */
        val extension = LibraryExtensionTokens(
            davibankHouseEmpty = IconToken("logo_davibank_house_empty", resourceName = "logo_davibank_house_empty")
        )
    }
}
