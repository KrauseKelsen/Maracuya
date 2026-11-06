package cruxui.android.lib.wrappers.components.buttons.simple

import androidx.compose.runtime.Composable
import cruxui.android.lib.ui.components.buttons.simple.ButtonTokensOverride
import cruxui.android.lib.wrappers.adapters.compose.resolveInComposition
import cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry

/**
 * Registro público exclusivo de este componente. Hereda almacenamiento de recetas del núcleo común.
 * El host registra antes de inflar; los wrappers consultan el tema mediante el adaptador Compose.
 * No comparte recetas ni estado con registros de otros componentes.
 */
object ButtonTokensOverrideRegistry : TokensOverrideRegistry<ButtonTokensOverride>() {
    /** Resuelve la receta dentro del tema Compose del ámbito actual. */
    @Composable
    fun resolve(name: String?): ButtonTokensOverride? = resolveInComposition(name)
}
