package cruxui.android.lib.ui.components.textviews.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehaviorState
import cruxui.android.lib.core.components.textviews.tokens.TextViewLayoutTokens
import cruxui.android.lib.ui.components.textviews.tokensrender.TextViewTokensRender
import cruxui.android.lib.utils.composeadapters.IconComposeAdapter

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewContent` construye el árbol Compose que dibuja texto, iconos y capas visuales.
 *
 * Justificación:
 * Separa el render de la resolución de tema y políticas. Recibe los valores finales, por lo que no consulta
 * registros ni elige variantes.
 *
 * Se comunica con:
 * TextView le proporciona TextViewTokensRender, TextViewBehaviorState y adaptaciones Compose; usa TextViewIcon,
 * TextViewLayoutTokens y SelectionContainer.
 *
 * Uso:
 * Función interna del DS; el host utiliza TextView. Internamente dibuja una fila con iconos opcionales, texto y
 * separaciones; aplica el fondo antes del contenido y foregroundColor por encima.
 * La selección envuelve solo el texto. Los iconos conservan sus zonas de interacción.
 *
 * Objetivos:
 * - Dibujar valores previamente resueltos.
 * - Mantener selección y acciones independientes.
 * - Ordenar fondo, contenido y capa superior.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param text String visible.
 * @param modifier Modificador externo del contenedor.
 * @param tokens Valores semánticos del estado activo.
 * @param behaviorState Modificadores y capacidades ya resueltos.
 * @param textColor Color Compose efectivo del texto.
 * @param backgroundColor Color Compose del fondo.
 * @param foregroundColor Capa Compose dibujada sobre el contenido.
 * @param iconStartColor Tinte del icono inicial.
 * @param iconEndColor Tinte del icono final.
 * @param textStyle Tipografía y decoración adaptadas.
 * @param minLines Mínimo de líneas.
 * @param maxLines Máximo de líneas.
 * @param overflow Política de desbordamiento.
 * @param textAlign Alineación explícita o null para conservar el estilo.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
@Composable
internal fun TextViewContent(
    text: String,
    modifier: Modifier,
    tokens: TextViewTokensRender,
    behaviorState: TextViewBehaviorState,
    textColor: Color,
    backgroundColor: Color,
    foregroundColor: Color,
    iconStartColor: Color,
    iconEndColor: Color,
    textStyle: TextStyle,
    minLines: Int,
    maxLines: Int,
    overflow: TextOverflow,
    textAlign: TextAlign?,
) {
    val hasIcons = tokens.iconStart != null || tokens.iconEnd != null

    Row(
        modifier = modifier
            .then(behaviorState.containerModifier)
            .background(backgroundColor)
            .drawWithContent {
                drawContent()
                if (foregroundColor.alpha > 0f) {
                    drawRect(foregroundColor)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tokens.iconStart?.let { icon ->
            TextViewIcon(
                icon = icon,
                color = iconStartColor,
                modifier = behaviorState.iconStartModifier,
                interactive = behaviorState.iconStartInteractive,
                contentDescription = behaviorState.iconStartContentDescription,
            )
            Spacer(modifier = Modifier.width(TextViewLayoutTokens.IconSpacingDp.dp))
        }

        val textLayoutModifier = if (hasIcons) {
            Modifier.weight(weight = 1f, fill = false)
        } else {
            Modifier
        }

        val textContent: @Composable (Modifier) -> Unit = { layoutModifier ->
            Text(
                text = text,
                modifier = layoutModifier.then(behaviorState.textModifier),
                color = textColor,
                style = textStyle,
                minLines = minLines,
                maxLines = maxLines,
                overflow = overflow,
                textAlign = textAlign,
            )
        }

        if (behaviorState.textSelectionEnabled) {
            SelectionContainer(modifier = textLayoutModifier) {
                textContent(Modifier)
            }
        } else {
            textContent(textLayoutModifier)
        }

        tokens.iconEnd?.let { icon ->
            Spacer(modifier = Modifier.width(TextViewLayoutTokens.IconSpacingDp.dp))
            TextViewIcon(
                icon = icon,
                color = iconEndColor,
                modifier = behaviorState.iconEndModifier,
                interactive = behaviorState.iconEndInteractive,
                contentDescription = behaviorState.iconEndContentDescription,
            )
        }
    }
}

/**
 * Dibuja un icono y, si tiene acciones, reserva el área táctil mínima del DS. Uso privado del contenido.
 *
 * @param icon Token visual del icono; su tamaño se interpreta en dp.
 * @param color Tinte Compose ya resuelto.
 * @param modifier Gestos y semántica de esa zona.
 * @param interactive Indica si hay callbacks.
 * @param contentDescription Descripción localizada; puede omitirse en iconos decorativos.
 * @throws IllegalArgumentException Si el icono es interactivo y su descripción es nula o vacía.
 */
@Composable
private fun TextViewIcon(
    icon: IconToken,
    color: Color,
    modifier: Modifier,
    interactive: Boolean,
    contentDescription: String?,
) {
    require(!interactive || !contentDescription.isNullOrBlank()) {
        "Interactive TextView icons require a localized content description"
    }
    val containerModifier = if (interactive) {
        Modifier
            .sizeIn(
                minWidth = TextViewLayoutTokens.MinimumInteractiveIconSizeDp.dp,
                minHeight = TextViewLayoutTokens.MinimumInteractiveIconSizeDp.dp,
            )
            .then(modifier)
    } else {
        Modifier
    }

    Box(
        modifier = containerModifier,
        contentAlignment = Alignment.Center,
    ) {
        IconComposeAdapter.Render(
            icon = icon,
            size = icon.iconSize.dp,
            fillColor = color,
            contentDescription = contentDescription,
        )
    }
}
