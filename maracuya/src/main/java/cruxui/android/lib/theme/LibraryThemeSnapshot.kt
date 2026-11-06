package cruxui.android.lib.theme

import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.theme.collections.LibraryIcons
import cruxui.android.lib.theme.collections.LibraryTypography
import cruxui.android.lib.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryThemeSnapshot` agrupa las colecciones semánticas de una marca en un modo ya resuelto.
 *
 * Justificación:
 * Es el contrato común entre Compose, XML y recetas. No selecciona el tema ni reconstruye colores, tipografías o
 * iconos; conserva las colecciones recibidas.
 *
 * Se comunica con:
 * `LibraryThemeSet.resolve` lo entrega. `LibraryTheme.current` agrupa los locals activos y
 * `AndroidThemeResolver` resuelve el proveedor XML. Los registros reciben este tipo.
 *
 * Uso:
 * API pública del host para recetas y adaptaciones. En Compose leer `val library = LibraryTheme.current` dentro
 * de `MyLibraryTheme`.
 * En una View del DS usar `val library = view.getThemeTokens()`; fuera de UI usar
 * `LibraryThemes.Maracuya.resolve(false)`.
 * Acceso: `library.colors.brandPrimary`, `library.typography.headline24`, `library.fontFamily`,
 * `library.icons.alerts.info`.
 * `copy` permite crear una configuración explícita sin alterar la original. Inyectarla con
 * `view.setThemeTokens(snapshot)` afecta solo a esa instancia; no modifica los recursos XML compilados.
 *
 * Objetivos:
 * - Transportar el tema completo con tipos existentes.
 * - Evitar catálogos paralelos por plataforma.
 * - Permitir recetas independientes de la UI.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param colors Paleta semántica del modo activo.
 * @param typography Colección de estilos tipográficos corporativos.
 * @param icons Colección de iconos con significado semántico.
 * @param fontFamily Familia corporativa base.
 * @see cruxui.android.lib.theme.LibraryThemeSet
 * @see cruxui.android.lib.theme.LibraryTheme
 */
data class LibraryThemeSnapshot(
    val colors: LibraryColorTokens,
    val typography: LibraryTypography,
    val icons: LibraryIcons,
    val fontFamily: FontFamilyToken,
)
