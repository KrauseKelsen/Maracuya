package cruxui.android.maracuya.ui.components.buttons.navigation.group.core

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Valores por defecto del layout de ButtonNavigationGroup.
 *
 * Centraliza las medidas para que el componente pueda cambiar su layout sin tocar
 * la definicion publica, los resolvers ni el adapter de botones hijos.
 */
object ButtonNavigationGroupDefaults {
    val ButtonSpacing = Dp.Unspecified
    val LeadingButtonWidth = Dp.Unspecified
    val TrailingButtonWidth = Dp.Unspecified
    val MinLeadingButtonWidth = 136.dp
    val MinTrailingButtonWidth = 176.dp
    val CornerRadius = 100.dp
}
