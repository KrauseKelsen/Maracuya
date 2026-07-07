package cruxui.android.maracuya.ui.components.buttons.navigation.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehaviorState
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokensrender.ButtonNavigationTokensRender
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter


/**
 * Renderiza texto e icono respetando la posicion resuelta por la variante.
 */
@Composable
internal fun ButtonNavigationContent(
    label: String?,
    tokens: ButtonNavigationTokensRender,
    iconAtEnd: Boolean,
    behaviorState: ButtonNavigationBehaviorState,
    contentColor: Color,
    iconColor: Color,
    textStyle: TextStyle,
) {
    val visibleIconToken = tokens.iconToken
    val hasLabel = !label.isNullOrBlank()

    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (visibleIconToken != null && !iconAtEnd) {
            IconComposeAdapter.Render(
                icon = visibleIconToken,
                modifier = Modifier.rotate(behaviorState.loadingIconRotationDegrees),
                fillColor = iconColor,
                contentDescription = null,
                size = visibleIconToken.iconSize.dp,
            )
            if (hasLabel) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        if (hasLabel) {
            Text(
                text = label,
                style = textStyle,
                color = contentColor,
            )
        }

        if (visibleIconToken != null && iconAtEnd) {
            if (hasLabel) {
                Spacer(modifier = Modifier.width(8.dp))
            }
            IconComposeAdapter.Render(
                icon = visibleIconToken,
                modifier = Modifier.rotate(behaviorState.loadingIconRotationDegrees),
                fillColor = iconColor,
                contentDescription = null,
                size = visibleIconToken.iconSize.dp,
            )
        }
    }
}