package cruxui.android.lib.theme

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryThemeStyles` expone nombres públicos de conveniencia para los valores de ThemeStyle.
 *
 * Justificación:
 * Conserva una entrada de acceso compatible con consumidores que ya usan LibraryThemeStyles, sin mantener otro
 * enum.
 *
 * Se comunica con:
 * Sus propiedades referencian los mismos valores de `ThemeStyle` que acepta `MyLibraryTheme`.
 *
 * Uso:
 * El host puede usar `MyLibraryTheme(style = LibraryThemeStyles.AUTO)` o utilizar `ThemeStyle.AUTO`
 * directamente.
 * LIGHT y DARK fuerzan el modo; AUTO sigue la preferencia del sistema.
 *
 * Objetivos:
 * - Conservar la fachada de acceso.
 * - Evitar estados de modo duplicados.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object LibraryThemeStyles {
    /**
     * Alias público de ThemeStyle.LIGHT; solicita modo claro.
     */
    val LIGHT = ThemeStyle.LIGHT
    /**
     * Alias público de ThemeStyle.DARK; solicita modo oscuro.
     */
    val DARK = ThemeStyle.DARK
    /**
     * Alias público de ThemeStyle.AUTO; sigue el modo del sistema.
     */
    val AUTO = ThemeStyle.AUTO
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `ThemeExports` agrupa el acceso público a las referencias XML generadas del catálogo del DS.
 *
 * Justificación:
 * Permite localizar los atributos de colores, tipografías, fuentes e iconos desde una API común sin copiar sus
 * valores en otro catálogo.
 *
 * Se comunica con:
 * Expone `LibraryXmlReferences`, producido por token-exporter a partir de `LibraryThemes` y empaquetado en la
 * biblioteca Android.
 *
 * Uso:
 * API pública del host Android. Cada referencia es un ID de atributo, no el valor final:
 * ```kotlin
 * val attr = ThemeExports.xmlReferences.typography.headline24
 * val values = context.obtainStyledAttributes(intArrayOf(attr))
 * try {
 *     val appearance = values.getResourceId(0, 0)
 *     if (appearance != 0) textView.setTextAppearance(appearance)
 * } finally {
 *     values.recycle()
 * }
 * ```
 * El Context debe tener el overlay de marca, por ejemplo `ThemeOverlay.Maracuya`.
 * En XML usar `android:textAppearance="?attr/libraryTypographyHeadline24"`,
 * `android:textColor="?attr/libraryColorBrandPrimary"` o `android:fontFamily="?attr/libraryFontPrimary"`.
 * Para iconos usar `android:src="?attr/libraryIconAlertsInfo"`; iconSizes e iconTints exponen sus
 * dimensiones y tintes opcionales. Estas referencias también sirven a ImageView u otros componentes.
 * Las referencias pertenecen al tema Android del Context. Cambiar el proveedor semántico no reinfla los estilos;
 * para estilos XML explícitos aplicar el overlay coherente y recrear o reinflar la pantalla.
 * No editar los archivos generados de build; cambiar el catálogo fuente y compilar.
 *
 * Objetivos:
 * - Agrupar referencias nativas del DS.
 * - Reutilizar el catálogo que ya consume Compose.
 * - Distinguir atributos de valores resueltos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemes
 */
object ThemeExports {
    /**
     * Referencias generadas, agrupadas en colors, typography, lineHeight, fonts, icons, iconSizes e iconTints. Cada hoja es un
     * @AttrRes que se resuelve contra el Context temático.
     */
    val xmlReferences = LibraryXmlReferences
}
