package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant

import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Traduce la variante publica del child a la variante nativa de `ButtonNavigationMrcy`.
 */
internal object ButtonNavigationChildVariantResolver {

    fun resolveNativeVariant(
        variant: ButtonNavigationChildVariant,
    ): ButtonNavigationVariant =
        when (variant) {
            ButtonNavigationChildVariant.PRIMARY -> ButtonNavigationVariant.PRIMARY
            ButtonNavigationChildVariant.SECONDARY -> ButtonNavigationVariant.SECONDARY
        }
}
