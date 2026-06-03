package cruxui.android.maracuya.theme

import cruxui.android.maracuya.atoms.LibraryColorTokens
import cruxui.android.maracuya.atoms.LibraryIconTokens
import java.util.Locale

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Punto de acceso público a los temas y estilos de la librería
 *
 * Uso:
 * ```
 * Construir variables de acceso a los temas y sus modos
 * ```
 *
 * @author Krause Klesen
 * @since 10-27-2025
 * @version 1.0.0
 *
 * @param
 * @return
 * @throws
 * @see cruxui.android.maracuya.theme.LibraryThemes
 */
object LibraryThemes {


    // Tema principal de la librería (Maracuyá)
    val Maracuya = LibraryThemeSet(
        name = "Maracuya",
        lightTokens = LibraryColorTokens.DefaultLight,
        darkTokens = LibraryColorTokens.DefaultDark,
        iconAtoms = LibraryIconTokens.Default
    )

    // TODO apartir de aqui el codigo no se utiliza

    // Futuro: Registrar mas temas aqui
    // val Ocean = LibraryThemeSet("Ocean", OceanLightTokens, OceanDarkTokens)

    // Agregar más temas aquí en el futuro
    private val themes: Map<String, LibraryThemeSet> = listOf(Maracuya)
        .associateBy { it.name.lowercase() }

    /**
     * Obtiene un tema por nombre (case-insensitive).
     * Si no se encuentra, retorna el tema por defecto (Maracuya).
     */
    fun fromName(name: String?): LibraryThemeSet {
        if (name.isNullOrBlank()) return Maracuya
        return themes[name.lowercase(Locale.ROOT)] ?: Maracuya
    }
}