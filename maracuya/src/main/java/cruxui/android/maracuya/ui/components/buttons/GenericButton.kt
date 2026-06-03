package cruxui.android.maracuya.ui.components.buttons

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter


@Composable
fun GenericButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    // Precedencia:
    // 1) buttonTokens explícitos
    // 2) libraryTokens (si la jerarquía los provee via LocalLibraryColorTokens, o por parámetro)
    // 3) MaterialTheme.colorScheme (si la app host habilitó useMaterial en MyLibraryTheme)
    buttonTokens: ButtonTokens? = null,
) {
    val pressedState by interactionSource.collectPressedAsState()

    // RESOLUCIÓN DE TOKENS
    // No invocamos LocalLibraryColorTokens.current sin comprobar nullabilidad.
    val providedLibraryColorTokens = LocalLibraryColorTokens.current
    val resolvedTokens: ButtonTokens = when {
        // 1) Si el host pasó ButtonTokens personalizados
        buttonTokens != null -> buttonTokens

        // 2) Si existe libraryTokens (pasado o en CompositionLocal) -> derivar desde la semántica corporativa
        providedLibraryColorTokens != null -> ButtonTokens.fromLibraryTokens(providedLibraryColorTokens)

        // 3) Fallback: derivar desde MaterialTheme (si existe un MaterialTheme en jerarquía)
        else -> ButtonTokens.fromMaterialTheme()
    }

    val backgroundColor = when {
        !enabled -> resolvedTokens.disabledContainerColor // si el boton se desabilita
        pressedState -> resolvedTokens.hoverContainerColor // si es presionado
        else -> resolvedTokens.containerColor // color del boton
    }

    // Convertimos todos los ColorToken necesarios a Compose Color una sola vez
    val containerColorCompose = ColorComposeAdapter.toComposeColor(backgroundColor)
    val contentColorCompose = ColorComposeAdapter.toComposeColor(resolvedTokens.contentColor)
    val disabledContainerColorCompose = ColorComposeAdapter.toComposeColor(resolvedTokens.disabledContainerColor)
    val disabledContentColorCompose = ColorComposeAdapter.toComposeColor(resolvedTokens.disabledContentColor)


    // === 2. TypographyToken → TextStyle usando adapter del DS ===


    val resolvedTextStyle = TypographyComposeAdapter.toTextStyle(
        token = resolvedTokens.textTypography,
        fontFamily = FontFamiliesComposeAdapter.toCompose(resolvedTokens.fontFamilyToken)
    )


    Button(
        onClick = onClick,
        modifier = modifier.padding(0.dp),
        enabled = enabled,
        shape = shape,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColorCompose,
            contentColor = contentColorCompose,
            disabledContainerColor = disabledContainerColorCompose,
            disabledContentColor = disabledContentColorCompose,
        )
    ) {
        Text(text = text, style = resolvedTextStyle)
    }
}

/** Exporta la interaccion del boton en Jetpack Compose */
@Composable
private fun InteractionSource.collectPressedAsState(): State<Boolean> {
    val pressed = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> pressed.value = true
                is PressInteraction.Release,
                is PressInteraction.Cancel -> pressed.value = false
            }
        }
    }
    return pressed
}