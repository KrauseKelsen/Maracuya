package cruxui.android.lib.ui.components.buttons.simple.render

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cruxui.android.lib.R
import cruxui.android.lib.core.components.buttons.tokens.ButtonLayoutTokens
import cruxui.android.lib.core.components.buttons.tokens.ButtonLoadingTokens
import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonComposeBehavior
import cruxui.android.lib.ui.components.buttons.simple.tokensrender.ButtonTokensRender
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariantResolver
import cruxui.android.lib.ui.components.textviews.TextView
import cruxui.android.lib.utils.composeadapters.ColorComposeAdapter
import cruxui.android.lib.utils.composeadapters.IconComposeAdapter

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonRenderer dibuja superficies, TextView y el icono de carga resueltos.
 *
 * Justificación: Conservar medido el TextView invisible evita saltos de ancho al cargar; su
 * semántica se retira durante la carga.
 *
 * Se comunica con: Recibe ButtonComposeBehavior y ButtonTokensRender; TextView dibuja y tiñe todos
 * los iconos laterales.
 *
 * Uso:
 * ```kotlin
 * // Dentro de una función @Composable del módulo DS, bajo MyLibraryTheme:
 * val source = androidx.compose.runtime.remember { MutableInteractionSource() }
 * val tokens = cruxui.android.lib.ui.components.buttons.simple.tokens.ButtonTokensResolver
 *     .resolve(ButtonVariant.PRIMARY)
 * val behavior = cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehaviorResolver
 *     .resolve(
 *         cruxui.android.lib.core.components.buttons.behavior.ButtonBehavior.Default,
 *         enabled = true,
 *         interactionSource = source,
 *         onClick = {},
 *         onLongClick = null,
 *     )
 * ButtonRenderer.Render(
 *     text = "Guardar",
 *     modifier = Modifier,
 *     variant = ButtonVariant.PRIMARY,
 *     tokens = cruxui.android.lib.core.components.buttons.tokensrender.ButtonTokensRenderResolver
 *         .resolve(tokens, behavior.state),
 *     behavior = behavior,
 *     shape = androidx.compose.foundation.shape.RoundedCornerShape(100.dp),
 *     interactionSource = source,
 *     onClickLabel = null,
 *     iconStartContentDescription = null,
 *     iconEndContentDescription = null,
 * )
 * ```
 *
 * Objetivos:
 * - Delegar texto y decoración al DS; representar una única acción accesible.
 *
 * Flujo: Apariencia y comportamiento resueltos → superficies, TextView e indicador decorativo.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonRenderer {
    /**
     * @param text Etiqueta visible y nombre accesible durante loading.
     * @param modifier Tamaño y posición del host.
     * @param variant Variante de superficie; las tres usan TextView DEFAULT.
     * @param tokens Colores finales y override textual.
     * @param behavior Estado, ángulo y callbacks seguros.
     * @param shape Forma de la superficie; no cambia la receta.
     * @param interactionSource Fuente que conecta gestos con behavior.
     * @param onClickLabel Descripción localizada opcional de la acción.
     * @param iconStartContentDescription Texto accesible del icono inicial; no crea una acción
     *   independiente.
     * @param iconEndContentDescription Texto accesible del icono final; se pasa sin cambios a
     *   TextView.
     */
    @Composable
    fun Render(
        text: String,
        modifier: Modifier,
        variant: ButtonVariant,
        tokens: ButtonTokensRender,
        behavior: ButtonComposeBehavior,
        shape: Shape,
        interactionSource: MutableInteractionSource,
        onClickLabel: String?,
        iconStartContentDescription: String?,
        iconEndContentDescription: String?,
    ) {
        val loading = behavior.state.isLoading
        val loadingLabel = stringResource(R.string.button_loading)
        Box(
            modifier =
                modifier
                    .sizeIn(
                        minWidth = ButtonLayoutTokens.MinWidthDp.dp,
                        minHeight = ButtonLayoutTokens.MinHeightDp.dp,
                    )
                    .clip(shape)
                    .background(ColorComposeAdapter.toComposeColor(tokens.background))
                    .border(
                        ButtonLayoutTokens.BorderWidthDp.dp,
                        ColorComposeAdapter.toComposeColor(tokens.border),
                        shape,
                    )
                    .combinedClickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = behavior.state.canClick,
                        role = Role.Button,
                        onClickLabel = onClickLabel,
                        onLongClick = behavior.onLongClick,
                        onClick = behavior.onClick,
                    )
                    .semantics(mergeDescendants = true) {
                        if (loading) {
                            contentDescription = text
                            stateDescription = loadingLabel
                            progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
                        }
                    }
                    .padding(
                        horizontal = ButtonLayoutTokens.HorizontalPaddingDp.dp,
                        vertical = ButtonLayoutTokens.VerticalPaddingDp.dp,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            TextView(
                text = text,
                variant = ButtonVariantResolver.resolve(variant),
                tokensOverride = tokens.text,
                modifier = if (loading) Modifier.alpha(0f).clearAndSetSemantics {} else Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                iconStartContentDescription = iconStartContentDescription,
                iconEndContentDescription = iconEndContentDescription,
            )
            if (loading)
                IconComposeAdapter.Render(
                    icon = ButtonLoadingTokens.Icon,
                    modifier = Modifier.rotate(behavior.rotation),
                    fillColor =
                        ColorComposeAdapter.toComposeColor(requireNotNull(tokens.text.textColor)),
                    contentDescription = null,
                )
        }
    }
}
