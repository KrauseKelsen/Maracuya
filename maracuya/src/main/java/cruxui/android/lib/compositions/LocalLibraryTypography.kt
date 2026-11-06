package cruxui.android.lib.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import cruxui.android.lib.theme.collections.LibraryTypography

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LocalLibraryTypography publica la colección de tipografías del tema en el árbol Compose.
 *
 * Justificación:
 * MyLibraryTheme proporciona LibraryTypography; LibraryTheme.current agrupa este local con las demás colecciones.
 * No declara ni exporta tokens y no es un proveedor para XML. Fuera del proveedor conserva una colección LibraryTypography con valores Library base.
 *
 * Uso:
 * API de ámbito Compose. El host normalmente consulta LibraryTheme.current.typography
 * dentro de MyLibraryTheme. También puede leer LocalLibraryTypography.current cuando solo necesita esta familia.
 * Los resolutores de componentes reciben sus tokens desde el tema. En XML se usa el snapshot del proveedor
 * y el register/TokensOverride, o los recursos generados con un ThemeOverlay de marca.
 *
 * Objetivos:
 * - Distribuir la colección activa sin reconstruir valores ni duplicar el catálogo Library.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
val LocalLibraryTypography = staticCompositionLocalOf {
    LibraryTypography()
}
