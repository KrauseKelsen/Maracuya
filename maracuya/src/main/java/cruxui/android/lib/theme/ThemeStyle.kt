package cruxui.android.lib.theme

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `ThemeStyle` expresa la política de modo claro, oscuro o automático del DS.
 *
 * Justificación:
 * Separa la decisión de modo de la lectura del sistema operativo; el núcleo puede resolverla sin depender de
 * Android ni Compose.
 *
 * Se comunica con:
 * `LibraryThemeSet.resolve`, `MyLibraryTheme` y el proveedor XML suministran o consumen este valor.
 *
 * Uso:
 * API pública del host. En Compose: `MyLibraryTheme(style = ThemeStyle.DARK)`.
 * En XML: `app:themeStyle="light"`, `"dark"` o `"auto"` en `MyLibraryThemeWrp`; en Kotlin cambiar su propiedad
 * `themeStyle`.
 * LIGHT y DARK fuerzan un modo. AUTO toma el indicador del sistema entregado por el adaptador.
 *
 * Objetivos:
 * - Compartir la política de modo.
 * - Mantener AUTO comprobable sin UI.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
enum class ThemeStyle {
    LIGHT, DARK, AUTO;

    /**
     * Decide si debe utilizarse la paleta oscura.
     *
     * @param systemDark Preferencia del sistema, leída por la plataforma.
     * @return false para LIGHT, true para DARK y systemDark para AUTO.
     */
    fun isDark(systemDark: Boolean): Boolean = when (this) {
        LIGHT -> false
        DARK -> true
        AUTO -> systemDark
    }
}
