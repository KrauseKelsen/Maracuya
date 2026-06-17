package cruxui.android.maracuya.wrappers.components.buttons.primary

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cruxui.android.maracuya.theme.LibraryThemes
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.ui.components.buttons.primary.PrimaryButtonMrcy
import cruxui.android.maracuya.wrappers.components.core.MrcyXmlComposeView
import cruxui.android.maracuya.wrappers.theme.MyLibraryThemeProvider

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `PrimaryButtonWrp` es el wrapper Android View que expone `PrimaryButtonMrcy` para
 * uso desde layouts XML tradicionales, permitiendo integrar componentes Compose en
 * pantallas basadas en el sistema de Views.
 *
 * El wrapper se enfoca únicamente en API funcional del botón (texto, enabled,
 * progreso en press) y delega la resolución de tema al host `MyLibraryThemeWrp`
 * cuando está disponible en la jerarquía.
 *
 * Función:
 * - Parsear atributos XML del botón y sincronizarlos con estado Compose.
 * - Respetar cambios de `isEnabled` del sistema de Views.
 * - Renderizar `PrimaryButtonMrcy` con o sin envoltura temática (`MyLibraryTheme`).
 *
 * Relación con otras clases:
 * - `PrimaryButtonAttributeParser` obtiene configuración inicial desde XML.
 * - `MyLibraryThemeProvider` busca un `MyLibraryThemeWrp` padre.
 * - `MyLibraryTheme` aplica tokens/estilo de tema cuando existe host.
 * - `PrimaryButtonMrcy` es el componente Compose real que se dibuja.
 *
 * Uso (host app XML):
 * ```xml
 * <cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:themeName="Maracuya"
 *     app:themeStyle="auto"
 *     app:useMaterial="false">
 *
 *     <cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
 *         android:id="@+id/primaryButtonFromXml"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         app:text="Continuar"
 *         app:showProgressOnPress="true"
 *         app:isEnabled="true" />
 *
 * </cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp>
 * ```
 *
 * Notas de integración:
 * - Si no existe `MyLibraryThemeWrp` padre, el botón se renderiza igual con comportamiento
 *   por defecto, sin aplicar tema explícito de wrappers.
 * - `performClick()` se delega como `onClick`, permitiendo interop con listeners de View.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see cruxui.android.maracuya.ui.components.buttons.primary.PrimaryButtonMrcy
 * @see cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
 */
class PrimaryButtonWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MrcyXmlComposeView(context, attrs, defStyleAttr) {

    private var buttonTextState by mutableStateOf("Primary Button")
    private var showProgressOnPressState by mutableStateOf(true)

    private var enabledState by mutableStateOf(isEnabled)

    init {
        val initialValues = PrimaryButtonAttributeParser.parse(context, attrs)
        buttonTextState = initialValues.text
        showProgressOnPressState = initialValues.showProgressOnPress
        enabledState = initialValues.enabled
    }

    var text: String
        get() = buttonTextState
        set(value) {
            buttonTextState = value
        }

    var showProgressOnPress: Boolean
        get() = showProgressOnPressState
        set(value) {
            showProgressOnPressState = value
        }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    @Composable
    override fun Content() {
        val themeHost = MyLibraryThemeProvider.findFrom(this)
        val themeConfig = themeHost?.currentConfig()

        if (themeConfig == null) {
            PrimaryButtonMrcy(
                text = buttonTextState,
                onClick = ::performClick,
                modifier = modifierState,
                enabled = enabledState,
                showProgressOnPress = showProgressOnPressState,
            )
            return
        }

        MyLibraryTheme(
            theme = LibraryThemes.fromName(themeConfig.themeName),
            style = themeConfig.themeStyle,
            useMaterial = themeConfig.useMaterial,
        ) {
            PrimaryButtonMrcy(
                text = buttonTextState,
                onClick = ::performClick,
                modifier = modifierState,
                enabled = enabledState,
                showProgressOnPress = showProgressOnPressState,
            )
        }
    }
}
