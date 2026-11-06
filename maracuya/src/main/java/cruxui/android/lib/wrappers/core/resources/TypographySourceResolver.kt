package cruxui.android.lib.wrappers.core.resources

import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.wrappers.core.tokens.TextTypography

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Resuelve una fuente tipográfica contra el tema de la instancia.
 *
 * Justificación:
 * Los roles contienen una clave del catálogo generado, por lo que no se mantiene un when por tipografía.
 * LibraryTypography obtiene sus defaults de LibraryTypographyTokens y conserva los overrides del tema.
 *
 * Uso:
 * Función interna de NativeTextStyleResolver, reutilizable por cualquier componente con texto.
 * Value conserva un token explícito; Semantic ejecuta el selector y Role consulta la colección activa.
 * Agregar un estilo a Library no requiere modificar este resolutor.
 *
 * Objetivos:
 * - Resolver el catálogo completo y respetar las sustituciones del ámbito temático.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param library Biblioteca del ámbito; nunca se lee un CompositionLocal desde esta función.
 * @return Tipografía efectiva para la fuente solicitada.
 */
internal fun TextTypography.resolve(library: LibraryThemeSnapshot): TypographyToken = when (this) {
    is TextTypography.Value -> token
    is TextTypography.Semantic -> select(library.typography)
    is TextTypography.Role -> library.typography[role.catalogName]
}
