package cruxui.android.maracuya.ui.components.buttons.navigation.simple

import cruxui.android.maracuya.ui.components.buttons.simple.ButtonVariant

/**
 * Variantes visuales del ButtonNavigation.
 *
 * PRIMARY hereda el estilo visual de ButtonMrcy primary y ubica el icono a la derecha.
 * SECONDARY hereda el estilo visual de ButtonMrcy secondary y ubica el icono a la izquierda.
 */
enum class ButtonNavigationVariant {
    PRIMARY,
    SECONDARY,
}

fun ButtonNavigationVariant.toButtonVariant(): ButtonVariant =
    when (this) {
        ButtonNavigationVariant.PRIMARY -> ButtonVariant.PRIMARY
        ButtonNavigationVariant.SECONDARY -> ButtonVariant.SECONDARY
    }