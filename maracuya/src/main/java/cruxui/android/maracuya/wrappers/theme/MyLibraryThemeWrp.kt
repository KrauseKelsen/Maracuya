package cruxui.android.maracuya.wrappers.theme

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import cruxui.android.maracuya.theme.ThemeStyle

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryThemeWrp` es el host XML de theming para wrappers basados en Views.
 * Su misión es definir una sola vez la configuración de tema para toda una jerarquía
 * de componentes wrappers, evitando duplicar la misma configuración en cada View hija.
 *
 * Función:
 * - Actuar como contenedor de alcance temático dentro del árbol XML.
 * - Parsear atributos de tema al crearse y mantener un estado de configuración actual.
 * - Exponer `currentConfig()` para que wrappers hijos apliquen `MyLibraryTheme`.
 *
 * Relación con otras clases:
 * - `MyLibraryThemeAttributeParser` resuelve atributos XML a `MyLibraryThemeConfig`.
 * - `MyLibraryThemeProvider` permite a hijos encontrar este host por jerarquía.
 * - `PrimaryButtonWrp` consume esta configuración para envolver su contenido Compose.
 *
 * Uso (host app):
 * ```xml
 * <cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:themeName="Maracuya"
 *     app:themeStyle="auto"
 *     app:useMaterial="false">
 *
 *     <!-- Wrappers hijos aquí -->
 * </cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp>
 * ```
 *
 * Recomendación:
 * - Definir este host en el nivel más alto posible de la sección de UI que comparta
 *   una misma configuración de tema.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see cruxui.android.maracuya.wrappers.theme.MyLibraryThemeConfig
 * @see cruxui.android.maracuya.wrappers.theme.MyLibraryThemeProvider
 */
class MyLibraryThemeWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var configState: MyLibraryThemeConfig =
        MyLibraryThemeAttributeParser.parse(context, attrs)

    var themeName: String
        get() = configState.themeName
        set(value) {
            configState = configState.copy(themeName = value)
        }

    var themeStyle: ThemeStyle
        get() = configState.themeStyle
        set(value) {
            configState = configState.copy(themeStyle = value)
        }

    var useMaterial: Boolean
        get() = configState.useMaterial
        set(value) {
            configState = configState.copy(useMaterial = value)
        }

    internal fun currentConfig(): MyLibraryThemeConfig = configState
}
