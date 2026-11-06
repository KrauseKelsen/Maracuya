package cruxui.android.lib.wrappers.core.tokens

import cruxui.android.lib.theme.collections.LibraryTypography
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextTypography representa una fuente tipográfica nativa mediante un rol, un valor o un selector.
 *
 * Justificación:
 * Conserva la intención del host hasta resolver contra LibraryTypography del tema activo.
 * Los roles se generan desde LibraryTypographyTokens; ningún componente mantiene una lista propia.
 * Se comunica con TypographySourceResolver y NativeTextStyleOverrides, sin cargar fuentes o Views.
 *
 * Uso:
 * API pública. Para seguir el tema elegir Role(TextTypographyRole.Headline24Medium) o
 * Semantic { it.headline24Medium }. Value(token) conserva un token concreto y no sigue cambios de marca.
 * TextView también permite setTypography(TextTypographyRole.LegalBold).
 * Todos los estilos de Library tienen su rol; Body se conserva como alias histórico de Body1.
 * En XML no es necesario usar esta clase: con un ThemeOverlay de marca se puede escribir
 * android:textAppearance="?attr/libraryTypographyLegalBold". Para combinaciones de tokens usar register
 * y el atributo TokensOverride del componente.
 *
 * Objetivos:
 * - Compartir el catálogo completo con cualquier componente.
 * - Diferenciar valores fijos de selecciones dependientes del tema.
 * - Evitar enums y conversiones manuales al incorporar tipografías.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see TextTypographyRole
 * @see LibraryTypography
 */
sealed interface TextTypography {
    /** Selección tipada del catálogo; se reevalúa con cada resolución del tema. */
    data class Role(val role: TextTypographyRole) : TextTypography
    /** Token explícito; permanece fijo aunque cambie el ámbito temático. */
    data class Value(val token: TypographyToken) : TextTypography
    /** Selector de la colección efectiva; respeta cualquier personalización de tema. */
    data class Semantic(val select: (LibraryTypography) -> TypographyToken) : TextTypography
}
