package cruxui.android.lib.wrappers.core.tokens

import cruxui.android.lib.theme.collections.LibraryIcons
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.theme.catalog.LibraryIconRole

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * ViewIcon describe cómo seleccionar un icono de Library para cualquier componente nativo.
 *
 * Justificación:
 * Separa la selección semántica de la carga Android. IconSourceResolver consulta el tema recibido;
 * AndroidIconLoader carga el recurso con su tamaño y tinte. No almacena Views ni callbacks.
 *
 * Uso:
 * API pública del host. `view.setIconStart(ViewIcon.Role(LibraryIconRole.AlertsInfo))` sigue el tema
 * activo. `ViewIcon.Semantic { it.alerts.info }` admite una selección propia y `Token(icono)` fija un token.
 * `None` retira explícitamente un icono. Un campo null del override conserva la capa anterior.
 * En XML compilado usar `android:drawableStart="?attr/libraryIconAlertsInfo"` bajo el overlay de marca;
 * para recetas dinámicas usar TokensOverride y seleccionar library.icons desde el snapshot del register.
 * El enum LibraryIconRole y las referencias XML incluyen todos los grupos Library al recompilar.
 *
 * Objetivos:
 * - Reutilizar la selección de iconos en componentes diferentes.
 * - Mantener los roles ligados al tema y la carga de drawables fuera del modelo.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
sealed interface ViewIcon {
    /** Selecciona cualquier icono generado contra el tema activo, sin congelar el token. */
    data class Role(val role: LibraryIconRole) : ViewIcon
    /** Eliminación explícita del icono heredado. */
    data object None : ViewIcon
    /** Token fijo proporcionado por el host; no se vuelve a seleccionar al cambiar el tema. */
    data class Token(val token: IconToken) : ViewIcon
    /** Selección diferida que recibe la colección Library del tema vigente. */
    data class Semantic(val select: (LibraryIcons) -> IconToken) : ViewIcon
}
