package cruxui.android.maracuya.wrappers.theme

import android.content.Context
import android.util.AttributeSet
import cruxui.android.maracuya.R

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryThemeAttributeParser` centraliza el parseo de atributos XML del contenedor
 * `MyLibraryThemeWrp` y los transforma en una configuración interna tipada
 * (`MyLibraryThemeConfig`).
 *
 * Su objetivo es desacoplar la lectura de recursos Android (`TypedArray`) de la clase
 * visual host, manteniendo la responsabilidad de `MyLibraryThemeWrp` enfocada en exponer
 * estado de tema y no en detalles de parseo.
 *
 * Función:
 * - Leer `themeName`, `themeStyle` y `useMaterial` desde XML.
 * - Aplicar defaults seguros cuando faltan atributos o no existe `attrs`.
 * - Entregar una instancia consistente de `MyLibraryThemeConfig`.
 *
 * Relación con otras clases:
 * - `MyLibraryThemeWrp` delega aquí la construcción inicial de su `configState`.
 * - `MyLibraryThemeWrpThemeMapper` convierte el valor string de `themeStyle` a `ThemeStyle`.
 * - `MyLibraryThemeConfig` encapsula el resultado de este parseo.
 *
 * No debe ser invocado por el host app directamente.
 * El host configura atributos declarativos en XML y el wrapper resuelve internamente.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
 * @see cruxui.android.maracuya.wrappers.theme.MyLibraryThemeConfig
 */
internal object MyLibraryThemeAttributeParser {

    private const val DEFAULT_THEME_NAME = "Maracuya"

    fun parse(context: Context, attrs: AttributeSet?): MyLibraryThemeConfig {
        if (attrs == null) {
            return MyLibraryThemeConfig(
                themeName = DEFAULT_THEME_NAME,
                themeStyle = MyLibraryThemeWrpThemeMapper.toThemeStyle(null),
                useMaterial = false,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLibraryThemeWrp)
        return try {
            MyLibraryThemeConfig(
                themeName = typedArray.getString(R.styleable.MyLibraryThemeWrp_themeName)
                    ?: DEFAULT_THEME_NAME,
                themeStyle = MyLibraryThemeWrpThemeMapper.toThemeStyle(
                    typedArray.getString(R.styleable.MyLibraryThemeWrp_themeStyle),
                ),
                useMaterial = typedArray.getBoolean(R.styleable.MyLibraryThemeWrp_useMaterial, false),
            )
        } finally {
            typedArray.recycle()
        }
    }
}
