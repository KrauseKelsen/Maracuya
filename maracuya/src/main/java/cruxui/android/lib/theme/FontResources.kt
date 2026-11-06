package cruxui.android.lib.theme

import cruxui.android.lib.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontResources` elige la familia corporativa predeterminada según el nombre de marca.
 *
 * Justificación:
 * Centraliza esa asociación para que los constructores de temas no repitan decisiones sobre archivos o familias.
 *
 * Se comunica con:
 * `LibraryThemeSet` llama a `forTheme` cuando no recibe una familia explícita. Devuelve `FontFamilyToken`,
 * consumido después por los adaptadores.
 *
 * Uso:
 * Objeto público destinado a la construcción del DS. El host normalmente consume `library.fontFamily` y no
 * necesita consultar esta tabla.
 * Para construir un tema propio puede llamar `FontResources.forTheme("davivienda")` o entregar una familia
 * explícita a `LibraryThemeSet`.
 * Maracuya y nombres desconocidos usan MULISH; Davivienda usa DAVIVIENDA. La búsqueda actual convierte el nombre
 * a minúsculas y no recorta espacios.
 *
 * Objetivos:
 * - Concentrar la familia predeterminada por marca.
 * - Mantener la selección independiente de la UI.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object FontResources {

    /**
     * Obtiene la familia predeterminada de una marca sin cargar recursos Android.
     *
     * @param themeName Nombre de marca; se compara convertido a minúsculas.
     * @return DAVIVIENDA para davivienda; MULISH para maracuya o cualquier otro nombre.
     */
    fun forTheme(themeName: String): FontFamilyToken {
        return when (themeName.lowercase()) {
            "maracuya" -> FontFamilyToken.MULISH
            "davivienda" -> FontFamilyToken.DAVIVIENDA
            else -> FontFamilyToken.MULISH
        }
    }
}
