package cruxui.android.lib.ui.components.buttons.simple

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.lib.core.components.buttons.tokens.ButtonLayoutTokens
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehaviorResolver
import cruxui.android.lib.ui.components.buttons.simple.render.ButtonRenderer
import cruxui.android.lib.ui.components.buttons.simple.tokens.ButtonTokensOverride
import cruxui.android.lib.ui.components.buttons.simple.tokens.ButtonTokensResolver
import cruxui.android.lib.ui.components.buttons.simple.tokensrender.ButtonTokensRenderResolver
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: Button es la entrada Compose del botón simple del DS; reemplaza ButtonMrcy.
 *
 * Justificación: Coordina capas pequeñas sin resolver colores ni ejecutar animaciones dentro del
 * componente.
 *
 * Se comunica con: TokensResolver lee LibraryTheme; BehaviorResolver observa interacciones;
 * TokensRenderResolver aplica estado; Renderer usa TextView.
 *
 * Uso:
 * ```kotlin
 * import androidx.compose.runtime.Composable
 * import cruxui.android.lib.theme.MyLibraryTheme
 * import cruxui.android.lib.theme.LibraryTheme
 * import cruxui.android.lib.ui.components.buttons.simple.Button
 * import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehavior
 *
 * @Composable
 * fun SaveButton(saving: Boolean, save: () -> Unit) {
 *     MyLibraryTheme {
 *         val library = LibraryTheme.current
 *         Button(
 *             text = "Guardar",
 *             onClick = save,
 *             behavior = ButtonBehavior(isLoading = saving),
 *             iconEnd = library.icons.alerts.info,
 *         )
 *     }
 * }
 * ```
 *
 * Imports completos y un ejemplo con operación suspendida están en ButtonSimpleArchitecture.md. El
 * host actualiza saving desde el resultado real; pressed nunca inicia loading. Para observar
 * pressed, compartir interactionSource y usar collectIsPressedAsState. Los iconos laterales son
 * decorativos: toda la superficie, incluidos los iconos, ejecuta onClick. Las tres variantes usan
 * TextView DEFAULT.
 *
 * Objetivos:
 * - Exponer un contrato declarativo; reutilizar TextView y preservar colores de marca.
 *
 * Flujo: Entrada pública → tokens y behavior → tokensrender → representación de plataforma.
 *
 * @param text Etiqueta del botón; no se modifica al entrar en loading.
 * @param onClick Acción del host. Loading/disabled bloquean su ejecución.
 * @param modifier Tamaño y posición; fillMaxWidth permite botones de ancho completo.
 * @param enabled Habilitación estándar; false prevalece sobre todos los flags de behavior.
 * @param variant PRIMARY, SECONDARY o TERCIARY; no representa tamaño ni estado.
 * @param tokensOverride Sustituciones semánticas del tema, incluidos iconStart/iconEnd opcionales.
 * @param iconStart Icono inicial decorativo; por defecto toma tokensOverride.iconStart. Null
 *   explícito lo retira.
 * @param iconEnd Icono final decorativo; por defecto toma tokensOverride.iconEnd. Null explícito lo
 *   retira.
 * @param iconStartContentDescription Descripción opcional del icono inicial, entregada a TextView;
 *   no agrega acción.
 * @param iconEndContentDescription Descripción opcional del icono final, entregada a TextView; no
 *   agrega acción.
 * @param behavior Flags isLoading/isDisabled/isPressed controlados por el host.
 * @param shape Forma opcional; por defecto cápsula con el radio estándar del core.
 * @param interactionSource Fuente observable de interacciones; estable mientras vive esta
 *   composición.
 * @param onLongClick Acción opcional de pulsación larga; se bloquea igual que onClick.
 * @param onClickLabel Etiqueta localizada para accesibilidad; null conserva la acción estándar.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    tokensOverride: ButtonTokensOverride? = null,
    iconStart: IconToken? = tokensOverride?.iconStart,
    iconEnd: IconToken? = tokensOverride?.iconEnd,
    iconStartContentDescription: String? = null,
    iconEndContentDescription: String? = null,
    behavior: ButtonBehavior = ButtonBehavior.Default,
    shape: Shape = RoundedCornerShape(ButtonLayoutTokens.CornerRadiusDp.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onLongClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
) {
    val tokens =
        ButtonTokensResolver.resolve(variant, tokensOverride)
            .copy(iconStart = iconStart, iconEnd = iconEnd)
    val resolvedBehavior =
        ButtonBehaviorResolver.resolve(behavior, enabled, interactionSource, onClick, onLongClick)
    val render = ButtonTokensRenderResolver.resolve(tokens, resolvedBehavior.state)
    ButtonRenderer.Render(
        text,
        modifier,
        variant,
        render,
        resolvedBehavior,
        shape,
        interactionSource,
        onClickLabel,
        iconStartContentDescription,
        iconEndContentDescription,
    )
}
