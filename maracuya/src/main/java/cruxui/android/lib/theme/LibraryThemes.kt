package cruxui.android.lib.theme

import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.atoms.LibraryIconTokens
import cruxui.android.lib.theme.collections.LibraryTypography
import cruxui.android.lib.theme.collections.LibraryIcons
import cruxui.android.lib.tokens.base.FontFamilyToken
import java.util.Locale

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryThemes` es el catálogo de marcas disponibles en el Design System.
 *
 * Justificación:
 * Centraliza el alta y la búsqueda de temas. La misma lista alimenta la resolución de la UI y la generación de
 * recursos XML.
 *
 * Se comunica con:
 * Contiene `LibraryThemeSet`; `MyLibraryTheme`, `AndroidThemeResolver` y el módulo token-exporter consumen sus
 * entradas.
 *
 * Uso:
 * API pública del host. Usar `MyLibraryTheme(theme = LibraryThemes.Maracuya)` en Compose; en XML declarar
 * `app:themeName="Maracuya"` en `MyLibraryThemeWrp`.
 * `fromName(name)` permite búsquedas sin distinguir mayúsculas. Un nombre ausente o desconocido devuelve
 * Maracuya.
 * Para mantener el DS, declarar cada marca como `LibraryThemeSet` y agregarla a `all`; compilar regenera sus
 * recursos. El host no puede mutar `all` ni registrar marcas dinámicas en este objeto.
 *
 * Objetivos:
 * - Centralizar las marcas soportadas.
 * - Compartir el catálogo con el exportador XML.
 * - Ofrecer un tema de respaldo documentado.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object LibraryThemes {

    /**
     * Tema público predeterminado. Agrupa paletas claras/oscuras e iconos; tipografía y familia usan los
     * defaults de LibraryThemeSet.
     */
    val Maracuya = LibraryThemeSet(
        name = "Maracuya",
        lightTokens = LibraryColorTokens.DefaultLight,
        darkTokens = LibraryColorTokens.DefaultDark,
        iconAtoms = LibraryIconTokens.Default
    )

    /**
     * Lista pública de temas exportables. Al agregar una marca debe incluirse aquí para búsqueda por nombre y
     * generación XML.
     */
    val all: List<LibraryThemeSet> = listOf(Maracuya)
    private val themes = all.associateBy { it.name.lowercase(Locale.ROOT) }

    /**
     * Busca una marca del catálogo sin distinguir mayúsculas, usando Locale.ROOT.
     *
     * @param name Nombre del tema; null, vacío o desconocido selecciona Maracuya. No recorta espacios de nombres
     *     no vacíos.
     * @return Instancia de LibraryThemeSet del catálogo.
     */
    fun fromName(name: String?): LibraryThemeSet {
        if (name.isNullOrBlank()) return Maracuya
        return themes[name.lowercase(Locale.ROOT)] ?: Maracuya
    }
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryThemeSet` define una marca completa, con paletas clara/oscura y colecciones comunes de tipografía,
 * iconos y familia.
 *
 * Justificación:
 * Concentra la construcción semántica que antes podían repetir los adaptadores. Sus dos snapshots se construyen
 * una vez por instancia y se reutilizan.
 *
 * Se comunica con:
 * Consume `LibraryColorTokens`, `LibraryIconTokens`, `LibraryTypography` y `FontResources`; convierte iconos
 * mediante `LibraryIcons.fromAtoms` y entrega `LibraryThemeSnapshot`.
 *
 * Uso:
 * Tipo público. El host normalmente selecciona una entrada de `LibraryThemes`. También puede copiar un tema para
 * un ámbito Compose propio:
 * ```kotlin
 * val custom = LibraryThemes.Maracuya.copy(
 *     typography = LibraryThemes.Maracuya.typography.copy(
 *         headline24 = LibraryThemes.Maracuya.typography.headline24.copy(fontSize = 26f),
 *     ),
 * )
 * MyLibraryTheme(theme = custom) { /* componentes del host */ }
 * ```
 * En una View inyectar `custom.resolve(false)` con `setThemeTokens`. Esta copia no registra un nombre XML ni
 * genera recursos nuevos durante la ejecución.
 * Los mantenedores agregan marcas permanentes en `LibraryThemes.all`.
 *
 * Objetivos:
 * - Resolver una marca para ambas plataformas.
 * - Reutilizar colecciones y snapshots.
 * - Admitir tipografía y familia propias de cada marca.
 *
 * Flujo de construcción o ejecución:
 * 1) Se reciben las colecciones de marca.
 * 2) Se derivan los iconos semánticos.
 * 3) Se construyen snapshots claro y oscuro.
 * 4) resolve selecciona uno, sin reconstruirlo.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param name Nombre público de marca y entrada para la familia predeterminada.
 * @param lightTokens Paleta del modo claro.
 * @param darkTokens Paleta del modo oscuro.
 * @param iconAtoms Catálogo atómico del que se deriva LibraryIcons.
 * @param typography Colección tipográfica de marca; por defecto LibraryTypography().
 * @param fontFamily Familia base; por defecto FontResources.forTheme(name).
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
data class LibraryThemeSet(
    val name: String,
    val lightTokens: LibraryColorTokens,
    val darkTokens: LibraryColorTokens,
    val iconAtoms: LibraryIconTokens,
    val typography: LibraryTypography = LibraryTypography(),
    val fontFamily: FontFamilyToken = FontResources.forTheme(name),
) {
    /**
     * Colección semántica derivada una vez de iconAtoms, compartida entre los snapshots claro y oscuro.
     */
    val icons: LibraryIcons = LibraryIcons.fromAtoms(iconAtoms)
    private val light = LibraryThemeSnapshot(lightTokens, typography, icons, fontFamily)
    private val dark = LibraryThemeSnapshot(darkTokens, typography, icons, fontFamily)

    /**
     * Selecciona el snapshot ya construido para un modo explícito.
     *
     * @param dark true selecciona la paleta oscura; false, la clara.
     * @return Snapshot compartido por las llamadas al mismo modo en esta instancia.
     */
    fun resolve(dark: Boolean): LibraryThemeSnapshot = if (dark) this.dark else light

    /**
     * Resuelve LIGHT, DARK o AUTO con la preferencia del sistema proporcionada por el llamador.
     *
     * @param style Política de modo solicitada.
     * @param systemDark Modo del sistema; solo influye cuando style es AUTO.
     * @return Snapshot reutilizado para el modo resultante.
     */
    fun resolve(style: ThemeStyle, systemDark: Boolean): LibraryThemeSnapshot = resolve(
        style.isDark(systemDark)
    )
}
