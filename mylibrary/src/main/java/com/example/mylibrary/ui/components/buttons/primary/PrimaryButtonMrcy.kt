package com.example.mylibrary.ui.components.buttons.primary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import com.example.mylibrary.ui.utils.compose.collectPressedAsState
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

@Composable
fun PrimaryButtonMrcy (
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp), // pasar esto hacia spacings and shape
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    // Precedencia:
    // 1) buttonTokens explícitos
    // 2) libraryTokens (si la jerarquía los provee via LocalLibraryColorTokens, o por parámetro)
    // 3) MaterialTheme.colorScheme (si la app host habilitó useMaterial en MyLibraryTheme)
    primaryButtonTokens: PrimaryButtonTokens? = null,


    /**
     * Si es true: al presionar (finger down) se muestra spinner en vez de texto.
     * Ojo: esto es "pressed", no "loading real". Para loading real se usa el overload abajo.
     */
    showProgressOnPress: Boolean = true,
){
    val tokens = PrimaryButtonTokensResolver.resolve(primaryButtonTokens)
    val pressed by interactionSource.collectPressedAsState()

    val showProgress = showProgressOnPress && pressed

    //Color del fondo del boton segun lo que ocurre
    val backgroundColor = when {
        !enabled -> tokens.disabledContainerColor // si el boton se desabilita
        pressed -> tokens.hoverContainerColor // si es presionado
        else -> tokens.containerColor // color del boton
    }

    val borderColor = when {
        !enabled -> tokens.borderDisabledColor // si el boton se desabilita
        pressed -> tokens.hoverContainerColor // si es presionado
        else -> tokens.borderContainerColor // color del borde
    }

    val contentColor = when {
        !enabled -> tokens.disabledContentColor // si el boton se desabilita
        else -> tokens.contentColor // color del borde
    }

    // === Convertimos todos los ColorToken necesarios a Compose Color una sola vez ===
    val containerColorCompose = ColorComposeAdapter.toComposeColor(backgroundColor)
    val contentColorCompose = ColorComposeAdapter.toComposeColor(contentColor)
    val disabledContainerColorCompose = ColorComposeAdapter.toComposeColor(tokens.disabledContainerColor)
    val borderContainerColorCompose = ColorComposeAdapter.toComposeColor(borderColor)

    // === TypographyToken → TextStyle usando adapter del DS ===
    val resolvedTextStyle = TypographyComposeAdapter.toTextStyle(
        token = tokens.textTypography,
        fontFamily = FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken)
    )

    Button(
        onClick = onClick,
        modifier = modifier.padding(0.dp),
        enabled = enabled,
        shape = shape,
        border = BorderStroke(1.dp, borderContainerColorCompose),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColorCompose,
            contentColor = contentColorCompose,
            disabledContainerColor = disabledContainerColorCompose,
            disabledContentColor = contentColorCompose,
        )
    ) {
        PrimaryButtonContent(
            text = text,
            textStyle = resolvedTextStyle,
            contentColor = contentColorCompose,
            showProgress = showProgress
        )
    }
}
