package cruxui.android.lib.wrappers.theme

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.content.res.Configuration
import android.view.ViewGroup
import cruxui.android.lib.wrappers.core.theme.AndroidThemeAware
import cruxui.android.lib.theme.ThemeStyle

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
 * <cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:themeName="Maracuya"
 *     app:themeStyle="auto"
 *     app:useMaterial="false">
 *
 *     <!-- Wrappers hijos aquí -->
 * </cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp>
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
 * @see cruxui.android.lib.wrappers.theme.MyLibraryThemeConfig
 * @see cruxui.android.lib.wrappers.theme.MyLibraryThemeProvider
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
            notifyNativeChildren(this)
        }

    var themeStyle: ThemeStyle
        get() = configState.themeStyle
        set(value) {
            configState = configState.copy(themeStyle = value)
            notifyNativeChildren(this)
        }

    var useMaterial: Boolean
        get() = configState.useMaterial
        set(value) {
            configState = configState.copy(useMaterial = value)
            notifyNativeChildren(this)
        }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        notifyNativeChildren(this)
    }

    private fun notifyNativeChildren(parent: ViewGroup) {
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            if (child is MyLibraryThemeWrp) continue
            if (child is AndroidThemeAware) child.refreshTheme()
            if (child is ViewGroup) notifyNativeChildren(child)
        }
    }

    internal fun currentConfig(): MyLibraryThemeConfig = configState
}
