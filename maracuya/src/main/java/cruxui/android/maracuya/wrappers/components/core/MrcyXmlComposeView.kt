package cruxui.android.maracuya.wrappers.components.core

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView

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
) : AbstractComposeView(context, attrs, defStyleAttr) {

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
    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
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
     * Recalcula el modifier derivado de los params resueltos por Android.
     */
    private fun syncLayoutParamsModifier(params: ViewGroup.LayoutParams?) {
        val nextModifier = AndroidLayoutParamsModifierResolver.fromLayoutParams(params)
        if (nextModifier != layoutParamsModifierState) {
            layoutParamsModifierState = nextModifier
        }
    }
}

private object AndroidLayoutParamsModifierResolver {

    /**
     * Usa los params genéricos de `ViewGroup`, no atributos XML ni clases de layouts.
     */
    fun fromLayoutParams(params: ViewGroup.LayoutParams?): Modifier {
        if (params == null) return Modifier

        return Modifier
            .then(params.width.toAxisModifier(isWidth = true))
            .then(params.height.toAxisModifier(isWidth = false))
    }

    private fun Int.toAxisModifier(isWidth: Boolean): Modifier {
        if (this == ViewGroup.LayoutParams.WRAP_CONTENT) return Modifier
        return if (isWidth) Modifier.fillMaxWidth() else Modifier.fillMaxHeight()
    }
}
