package cruxui.android.lib.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import cruxui.android.lib.atoms.LibraryColorTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LocalLibraryColorTokens publica la colección de colores del tema en el árbol Compose.
 *
 * Justificación:
 * MyLibraryTheme proporciona LibraryColorTokens; LibraryTheme.current agrupa este local con las demás colecciones.
 * No declara ni exporta tokens y no es un proveedor para XML. Sin proveedor produce un error explícito.
 *
 * Uso:
 * API de ámbito Compose. El host normalmente consulta LibraryTheme.current.colors
 * dentro de MyLibraryTheme. También puede leer LocalLibraryColorTokens.current cuando solo necesita esta familia.
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
val LocalLibraryColorTokens = staticCompositionLocalOf<LibraryColorTokens> {
    error(
        "Falta LocalLibraryColorTokens. Envuelve el contenido con MyLibraryTheme para proporcionar los colores."
    )
}
