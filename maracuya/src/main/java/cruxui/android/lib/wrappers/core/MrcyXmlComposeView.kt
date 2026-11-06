package cruxui.android.lib.wrappers.core

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import cruxui.android.lib.wrappers.adapters.compose.AndroidLayoutParamsModifierResolver.fromLayoutParams
import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import cruxui.android.lib.theme.LibraryThemes
import cruxui.android.lib.theme.MyLibraryComponentTheme
import cruxui.android.lib.wrappers.theme.MyLibraryThemeProvider
import cruxui.android.lib.wrappers.core.theme.AndroidThemeAware

/**
 * Base común para wrappers XML que renderizan componentes Maracuya en Compose.
 *
 * Delega `android:layout_*`, márgenes, constraints, weights, gravity y padding al
 * sistema de Views. El wrapper solo sincroniza Compose con los `LayoutParams` ya
 * normalizados por Android, sin parsear atributos XML por nombre.
 */
abstract class MrcyXmlComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr), AndroidThemeAware {

    private var themeRevision by mutableIntStateOf(0)

    override fun refreshTheme() {
        themeRevision++
    }

    private var layoutParamsModifierState by mutableStateOf<Modifier>(Modifier)
    private var componentModifierState by mutableStateOf<Modifier>(Modifier)

    init {
        syncLayoutParamsModifier(layoutParams)
    }

    /**
     * Modifier definido por código Kotlin sobre el wrapper.
     *
     * El espacio resuelto desde XML se aplica por fuera para que el layout padre tenga
     * prioridad y el componente mantenga su API Compose programática.
     */
    var modifier: Modifier
        get() = componentModifierState
        set(value) {
            componentModifierState = value
        }

    /**
     * Modifier final que deben enviar los wrappers al componente Mrcy.
     */
    protected val modifierState: Modifier
        get() = layoutParamsModifierState.then(componentModifierState)

    /**
     * Actualiza el modifier cuando el padre asigna o cambia los `LayoutParams`.
     */
    override fun setLayoutParams(params: LayoutParams?) {
        super.setLayoutParams(params)
        syncLayoutParamsModifier(params)
    }

    /**
     * Sincroniza params ya disponibles al entrar en la jerarquía.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        syncLayoutParamsModifier(layoutParams)
    }

    /**
     * Aplica tokens de tema XML sin pintar `bgBase` detrás de cada componente.
     */
    @Composable
    protected fun WithXmlTheme(content: @Composable () -> Unit) {
        val themeConfig = themeRevision.let { MyLibraryThemeProvider.findFrom(this)?.currentConfig() }

        if (themeConfig == null) {
            content()
            return
        }

        MyLibraryComponentTheme(
            theme = LibraryThemes.fromName(themeConfig.themeName),
            style = themeConfig.themeStyle,
            useMaterial = themeConfig.useMaterial,
            content = content,
        )
    }

    /**
     * Recalcula el modifier derivado de los params resueltos por Android.
     */
    private fun syncLayoutParamsModifier(params: LayoutParams?) {
        val nextModifier = Modifier.fromLayoutParams(params)
        if (nextModifier != layoutParamsModifierState) {
            layoutParamsModifierState = nextModifier
        }
    }
}
