package cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.simple.ButtonVariant

/**
 * Resuelve la variante declarativa al estilo base de ButtonMrcy que hereda.
 */
internal object ButtonNavigationVariantResolver {

    /**
     * Convierte la variante publica en su equivalente de ButtonVariant.
     */
    fun resolve(
        variant: ButtonNavigationVariant,
    ): ButtonVariant =
        when (variant) {
            ButtonNavigationVariant.PRIMARY -> ButtonVariant.PRIMARY
            ButtonNavigationVariant.SECONDARY -> ButtonVariant.SECONDARY
            ButtonNavigationVariant.PRIMARYSQUARE -> ButtonVariant.PRIMARY
            ButtonNavigationVariant.SECONDARYSQUARE -> ButtonVariant.SECONDARY
        }

    /**
     * Indica si la variante pertenece a la API icon-only.
     */
    fun isSquare(variant: ButtonNavigationVariant): Boolean =
        when (variant) {
            ButtonNavigationVariant.PRIMARYSQUARE,
            ButtonNavigationVariant.SECONDARYSQUARE -> true

            ButtonNavigationVariant.PRIMARY,
            ButtonNavigationVariant.SECONDARY -> false
        }

    /**
     * Conserva la posicion nativa del icono para la API con label.
     */
    fun resolveIconAtEnd(variant: ButtonNavigationVariant): Boolean =
        when (variant) {
            ButtonNavigationVariant.PRIMARY,
            ButtonNavigationVariant.PRIMARYSQUARE -> true

            ButtonNavigationVariant.SECONDARY,
            ButtonNavigationVariant.SECONDARYSQUARE -> false
        }

    /**
     * Resuelve el modifier final del boton segun la variante declarada.
     */
    fun resolveButtonModifier(
        variant: ButtonNavigationVariant,
        modifier: Modifier,
    ): Modifier =
        if (isSquare(variant) && modifier == Modifier) {
            Modifier.height(SquareButtonHeight)
        } else {
            modifier
        }

    /**
     * Resuelve la forma final del boton segun la variante declarada.
     */
    fun resolveButtonShape(
        variant: ButtonNavigationVariant,
        shape: Shape,
    ): Shape =
        if (isSquare(variant) && shape == DefaultButtonShape) {
            SquareButtonShape
        } else {
            shape
        }

    /**
     * Resuelve el label que debe pintarse dentro del boton.
     */
    fun resolveButtonLabel(
        variant: ButtonNavigationVariant,
        label: String?,
    ): String? =
        if (isSquare(variant)) {
            null
        } else {
            label
        }

    /**
     * Resuelve el label externo inferior usado por variantes square.
     */
    fun resolveBottomLabel(
        variant: ButtonNavigationVariant,
        label: String?,
    ): String? =
        if (isSquare(variant) && !label.isNullOrBlank()) {
            label
        } else {
            null
        }

    /**
     * Resuelve la separacion entre el boton y el label externo inferior.
     */
    fun resolveBottomLabelSpacing(variant: ButtonNavigationVariant): Dp =
        if (isSquare(variant)) {
            SquareBottomLabelSpacing
        } else {
            0.dp
        }

    private val DefaultButtonShape = RoundedCornerShape(100.dp)
    private val SquareButtonShape = RoundedCornerShape(25.dp)
    private val SquareButtonHeight = 70.dp
    private val SquareBottomLabelSpacing = 6.dp
}
