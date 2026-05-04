package com.example.mylibrary.ui.components.inputs.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.spacings.InputCheckBoxSpacings
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokens
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * Átomo base para checkbox.
 *
 * Decisiones de arquitectura:
 * - Usa `Checkbox` de Material 3 para delegar comportamiento, accesibilidad y
 *   consistencia visual base del control nativo de Compose.
 * - Mantiene una capa de tokens para desacoplar la semántica de diseño de la UI.
 * - Expone `showContainerBorder` para reutilizar el mismo átomo en modo card o modo plano.
 */
@Composable
fun InputCheckBoxMrcy(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showContainerBorder: Boolean = true,
    tokensGroup: InputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
    inputCheckBoxTokens: InputCheckBoxTokens? = null,
) {
    val tokens = InputCheckBoxTokensResolver.resolve(
        group = tokensGroup,
        override = inputCheckBoxTokens,
    )

    val borderColor = if (enabled) tokens.borderDefault else tokens.borderDisabled
    val labelColor = if (enabled) tokens.labelColor else tokens.labelDisabledColor

    val checkboxColors = CheckboxDefaults.colors(
        checkedColor = ColorComposeAdapter.toComposeColor(tokens.checkBackground),
        uncheckedColor = ColorComposeAdapter.toComposeColor(tokens.borderDefault),
        checkmarkColor = ColorComposeAdapter.toComposeColor(tokens.checkIconColor),
        disabledCheckedColor = ColorComposeAdapter.toComposeColor(tokens.borderDefault),
        disabledUncheckedColor = ColorComposeAdapter.toComposeColor(tokens.borderDisabled),
        disabledIndeterminateColor = ColorComposeAdapter.toComposeColor(tokens.borderDisabled),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (showContainerBorder) {
                    Modifier
                        .border(
                            width = InputCheckBoxSpacings.CardBorderWidth.dp,
                            color = ColorComposeAdapter.toComposeColor(borderColor),
                            shape = RoundedCornerShape(InputCheckBoxSpacings.CardCornerRadius.dp),
                        )
                        .padding(
                            horizontal = InputCheckBoxSpacings.RowHorizontalPadding.dp,
                            vertical = InputCheckBoxSpacings.RowVerticalPadding.dp,
                        )
                } else {
                    Modifier.padding(vertical = 4.dp)
                }
            )
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(InputCheckBoxSpacings.ContentSpacing.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            modifier = Modifier.size(InputCheckBoxSpacings.CheckSize.dp),
            colors = checkboxColors,
        )

        LabelMrcy(
            text = label,
            labelTokensOverride = LabelTokensOverride(
                labelTypography = tokens.labelTypography,
                fontFamily = tokens.fontFamilyToken,
                foregroundDefault = tokens.labelColor,
            ),
            limitMaxLabel = true,
        )
    }
}