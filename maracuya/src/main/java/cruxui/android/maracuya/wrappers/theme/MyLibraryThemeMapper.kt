package cruxui.android.maracuya.wrappers.theme

import cruxui.android.maracuya.theme.ThemeStyle
import java.util.Locale

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryThemeWrpThemeMapper` traduce valores textuales provenientes de XML al enum
 * `ThemeStyle`, asegurando una resolución robusta frente a diferencias de mayúsculas,
 * espacios o valores no soportados.
 *
 * Este mapper actúa como una capa defensiva para que la API pública en XML sea flexible,
 * mientras que internamente el sistema trabaje con tipos fuertes (`ThemeStyle`).
 *
 * Función:
 * - Normalizar el string recibido (`trim`, `uppercase`).
 * - Mapear explícitamente `LIGHT` y `DARK`.
 * - Aplicar fallback a `AUTO` en caso de valor ausente/inválido.
 *
 * Relación con otras clases:
 * - `MyLibraryThemeAttributeParser` utiliza este mapper al construir `MyLibraryThemeConfig`.
 * - `MyLibraryThemeWrp` expone el `ThemeStyle` resultante para wrappers hijos.
 * - `MyLibraryTheme` consume finalmente ese `ThemeStyle` para resolver modo visual.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see cruxui.android.maracuya.theme.ThemeStyle
 */
internal object MyLibraryThemeWrpThemeMapper {

    fun toThemeStyle(rawValue: String?): ThemeStyle {
        return when (rawValue?.trim()?.uppercase(Locale.ROOT)) {
            ThemeStyle.LIGHT.name -> ThemeStyle.LIGHT
            ThemeStyle.DARK.name -> ThemeStyle.DARK
            else -> ThemeStyle.AUTO
        }
    }
}
