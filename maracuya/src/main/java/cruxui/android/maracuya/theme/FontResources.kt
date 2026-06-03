package cruxui.android.maracuya.theme

import cruxui.android.maracuya.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontResources` define el origen de las familias tipográficas corporativas según el
 * tema activo. Su función es centralizar la selección de fuentes de marca para evitar
 * decisiones dispersas dentro del Design System.
 *
 * Esta clase permite que cada tema —por ejemplo “Maracuyá”, “Davivienda” u otros—
 * resuelva su propia familia tipográfica a través de un único punto de acceso.
 * Gracias a esto, el sistema puede escalar a múltiples marcas o configuraciones sin
 * duplicar lógica y garantizando consistencia tipográfica.
 *
 * Función principal:
 * - Entregar la familia tipográfica correspondiente al tema seleccionado.
 * - Servir como puente entre:
 *      - La capa semántica del DS (`LibraryThemeSet`)
 *      - Los tokens de fuentes (`FontFamilies`)
 * - Habilitar el soporte multitema/multimarca de forma ordenada.
 *
 * Relación con otras clases:
 * - `MyLibraryTheme` utiliza esta clase para asignar la familia de fuente base antes
 *   de proveer los CompositionLocals del Design System.
 * - `FontFamilies` contiene las familias concretas que esta clase devuelve.
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 *
 * Su uso correcto se da únicamente a través de `MyLibraryTheme`, por ejemplo:
 *
 *     val fontFamily = FontResources.forTheme(theme.name)
 *
 * Los componentes del Design System no deben leer esta clase directamente.
 * ```
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.3
 *
 * @param forTheme Función que resuelve la familia tipográfica según el nombre del tema.
 *                 Retorna únicamente valores provenientes de `FontFamilies`.
 *
 * @see cruxui.android.maracuya.theme.FontResources
 */

object FontResources {

    fun forTheme(themeName: String): FontFamilyToken {
        return when (themeName.lowercase()) {
            "maracuya" -> FontFamilyToken.MULISH
            "davivienda" -> FontFamilyToken.DAVIVIENDA
            else -> FontFamilyToken.MULISH
        }
    }
}