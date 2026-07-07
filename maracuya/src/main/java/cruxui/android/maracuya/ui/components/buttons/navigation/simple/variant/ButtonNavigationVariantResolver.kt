package cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant

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
        }
}
