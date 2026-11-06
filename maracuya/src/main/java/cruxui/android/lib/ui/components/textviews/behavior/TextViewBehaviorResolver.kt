package cruxui.android.lib.ui.components.textviews.behavior

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewBehaviorResolver` convierte capacidades y callbacks en modificadores Compose y semántica accesible.
 *
 * Justificación:
 * Evita que el contenido visual decida qué gesto pertenece al texto, a los iconos o a la selección. También
 * evita publicar pulsaciones simples sin una acción real.
 *
 * Se comunica con:
 * TextView entrega TextViewBehavior y sus callbacks. El resultado TextViewBehaviorState es aplicado por
 * TextViewContent.
 *
 * Uso:
 * Objeto interno del DS. El host configura behavior, enabled, callbacks y descripciones en TextView.
 * El resolutor mantiene acciones independientes por zona. La selección desactiva las acciones personalizadas de
 * pulsación larga y doble toque del texto, pero no las de iconos.
 * Cuando solo hay doble toque, la activación de accesibilidad ejecuta ese callback; cuando solo hay pulsación
 * larga no se inventa un click.
 *
 * Objetivos:
 * - Separar interacción y dibujo.
 * - Respetar la propiedad de los gestos de selección.
 * - Publicar solo acciones accesibles con callback.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewBehaviorResolver {

    /**
     * Construye el estado funcional del contenido Compose. Uso interno desde TextView.
     *
     * @param behavior Capacidad de selección del texto.
     * @param enabled Habilitación común de texto e iconos.
     * @param onClick Acción sencilla del texto.
     * @param onLongClick Acción larga del texto, reservada a selección si está activa.
     * @param onDoubleClick Doble toque del texto, reservado a selección si está activa.
     * @param onIconStartClick Acción sencilla del icono inicial.
     * @param onIconStartLongClick Acción larga del icono inicial.
     * @param onIconEndClick Acción sencilla del icono final.
     * @param onIconEndLongClick Acción larga del icono final.
     * @param onClickLabel Etiqueta accesible de la acción del texto.
     * @param onLongClickLabel Etiqueta accesible de la acción larga del texto.
     * @param iconStartContentDescription Descripción localizada inicial.
     * @param iconEndContentDescription Descripción localizada final.
     * @return Modificadores y capacidades listos para TextViewContent.
     */
    fun resolve(
        behavior: TextViewBehavior,
        enabled: Boolean,
        onClick: (() -> Unit)?,
        onLongClick: (() -> Unit)?,
        onDoubleClick: (() -> Unit)?,
        onIconStartClick: (() -> Unit)?,
        onIconStartLongClick: (() -> Unit)?,
        onIconEndClick: (() -> Unit)?,
        onIconEndLongClick: (() -> Unit)?,
        onClickLabel: String?,
        onLongClickLabel: String?,
        iconStartContentDescription: String?,
        iconEndContentDescription: String?,
    ): TextViewBehaviorState {
        val selectionOwnsTextGestures = behavior.textSelectionEnabled
        val textInteraction = Interaction(
            enabled = enabled,
            onClick = onClick,

            onLongClick = onLongClick.takeUnless { selectionOwnsTextGestures },
            onDoubleClick = onDoubleClick.takeUnless { selectionOwnsTextGestures },
            onClickLabel = onClickLabel,
            onLongClickLabel = onLongClickLabel,
        )
        val iconStartInteraction = Interaction(
            enabled = enabled,
            onClick = onIconStartClick,
            onLongClick = onIconStartLongClick,
        )
        val iconEndInteraction = Interaction(
            enabled = enabled,
            onClick = onIconEndClick,
            onLongClick = onIconEndLongClick,
        )

        return TextViewBehaviorState(
            textSelectionEnabled = enabled && behavior.textSelectionEnabled,
            containerModifier = if (enabled) {
                Modifier
            } else {
                Modifier.semantics { disabled() }
            },
            textModifier = Modifier.applyInteraction(textInteraction),
            iconStartModifier = Modifier.applyInteraction(iconStartInteraction),
            iconEndModifier = Modifier.applyInteraction(iconEndInteraction),
            iconStartInteractive = iconStartInteraction.hasCallbacks,
            iconEndInteractive = iconEndInteraction.hasCallbacks,
            iconStartContentDescription = iconStartContentDescription,
            iconEndContentDescription = iconEndContentDescription,
        )
    }

    /**
     * Agrega al receptor los gestos y la semántica apropiados para una zona. Uso privado del resolutor.
     * Sin callbacks devuelve el receptor; con click usa clickable o combinedClickable. Sin click delega al
     * manejo de gestos específicos.
     *
     * @param interaction Configuración de la zona.
     * @return Modificador compuesto sin publicar acciones vacías.
     */
    private fun Modifier.applyInteraction(interaction: Interaction): Modifier {
        if (!interaction.hasCallbacks) return this

        if (interaction.onClick == null) {
            return gestureOnlyInteraction(interaction)
        }

        return if (interaction.onLongClick != null || interaction.onDoubleClick != null) {
            combinedClickable(
                enabled = interaction.enabled,
                onClickLabel = interaction.onClickLabel,
                onLongClickLabel = interaction.onLongClickLabel,
                onLongClick = interaction.onLongClick,
                onDoubleClick = interaction.onDoubleClick,
                onClick = interaction.onClick,
            )
        } else {
            clickable(
                enabled = interaction.enabled,
                onClickLabel = interaction.onClickLabel,
                onClick = requireNotNull(interaction.onClick),
            )
        }
    }

    /**
     * Configura pulsación larga o doble toque cuando no existe callback de click sencillo. Uso privado.
     * Evita el click obligatorio de combinedClickable. Para accesibilidad expone la acción larga y, si hay doble
     * toque, una activación que ejecuta ese callback.
     *
     * @param interaction Acciones y estado habilitado de la zona.
     * @return Modificador de entrada táctil y semántica; disabled bloquea las acciones.
     */
    private fun Modifier.gestureOnlyInteraction(interaction: Interaction): Modifier {
        val gestures = pointerInput(
            interaction.enabled,
            interaction.onLongClick,
            interaction.onDoubleClick,
        ) {
            if (interaction.enabled) {
                detectTapGestures(
                    onLongPress = interaction.onLongClick?.let { callback ->
                        { _ -> callback() }
                    },
                    onDoubleTap = interaction.onDoubleClick?.let { callback ->
                        { _ -> callback() }
                    },
                )
            }
        }

        return gestures.semantics(mergeDescendants = true) {
            if (!interaction.enabled) {
                disabled()
            }
            interaction.onLongClick?.let { callback ->
                onLongClick(label = interaction.onLongClickLabel) {
                    if (interaction.enabled) {
                        callback()
                        true
                    } else {
                        false
                    }
                }
            }

            interaction.onDoubleClick?.let { callback ->
                onClick(label = interaction.onClickLabel) {
                    if (interaction.enabled) {
                        callback()
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }

    /**
     * s4121779 - Krause Kelsen
     *
     * Descripción:
     * `Interaction` reúne los callbacks y etiquetas de una sola zona interactiva Compose.
     *
     * Justificación:
     * Permite que texto e iconos utilicen el mismo algoritmo de composición de gestos sin compartir sus
     * acciones.
     *
     * Se comunica con:
     * Es un tipo privado de TextViewBehaviorResolver; applyInteraction y gestureOnlyInteraction lo consultan.
     *
     * Uso:
     * Uso privado del DS. El host declara callbacks en TextView; no construye este tipo. `hasCallbacks` indica
     * si debe añadirse interacción a la zona.
     *
     * Objetivos:
     * - Normalizar los datos de interacción de cada zona.
     * - Conservar el estado habilitado y las etiquetas accesibles.
     *
     * @author Krause Kelsen
     * @since 07-16-2026
     * @version 0.0.13
     * @param enabled Habilitación de la zona.
     * @param onClick Callback de click sencillo o null.
     * @param onLongClick Callback de pulsación larga o null.
     * @param onDoubleClick Callback de doble toque o null.
     * @param onClickLabel Etiqueta accesible del click.
     * @param onLongClickLabel Etiqueta accesible de la pulsación larga.
     * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
     */
    private data class Interaction(
        val enabled: Boolean,
        val onClick: (() -> Unit)? = null,
        val onLongClick: (() -> Unit)? = null,
        val onDoubleClick: (() -> Unit)? = null,
        val onClickLabel: String? = null,
        val onLongClickLabel: String? = null,
    ) {
        /**
         * Indica si la zona tiene al menos una acción. Se calcula sin considerar enabled para conservar su
         * configuración al deshabilitarla.
         */
        val hasCallbacks: Boolean
            get() = onClick != null || onLongClick != null || onDoubleClick != null
    }
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewBehaviorState` contiene el resultado funcional que el contenido Compose debe aplicar.
 *
 * Justificación:
 * Separa la política de eventos de su aplicación al árbol visual.
 *
 * Se comunica con:
 * Lo produce TextViewBehaviorResolver y lo consume TextViewContent para el contenedor, texto e iconos.
 *
 * Uso:
 * Tipo interno del DS. El host usa los parámetros de TextView. El contenido aplica los modificadores y crea
 * SelectionContainer únicamente cuando textSelectionEnabled es true.
 *
 * Objetivos:
 * - Transportar modificadores y capacidades resueltos.
 * - Mantener texto e iconos independientes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textSelectionEnabled Selección efectiva, condicionada también por enabled.
 * @param containerModifier Semántica del contenedor, incluido el estado deshabilitado.
 * @param textModifier Gestos y semántica del texto.
 * @param iconStartModifier Gestos del icono inicial.
 * @param iconEndModifier Gestos del icono final.
 * @param iconStartInteractive El icono inicial tiene callbacks y necesita área interactiva.
 * @param iconEndInteractive El icono final tiene callbacks y necesita área interactiva.
 * @param iconStartContentDescription Descripción localizada del icono inicial.
 * @param iconEndContentDescription Descripción localizada del icono final.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewBehaviorState(
    val textSelectionEnabled: Boolean,
    val containerModifier: Modifier,
    val textModifier: Modifier,
    val iconStartModifier: Modifier,
    val iconEndModifier: Modifier,
    val iconStartInteractive: Boolean,
    val iconEndInteractive: Boolean,
    val iconStartContentDescription: String?,
    val iconEndContentDescription: String?,
)
