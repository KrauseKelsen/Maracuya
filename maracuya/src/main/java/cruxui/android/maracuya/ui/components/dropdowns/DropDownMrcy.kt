package cruxui.android.maracuya.ui.components.dropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.spacings.LabelSpacings
import cruxui.android.maracuya.ui.components.inputs.checkbox.InputCheckBoxMrcy
import cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownMrcy
import cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownTokens
import cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownVariant
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

/**
 * Componente compuesto de dropdown con lista personalizada (sin usar spinner nativo Android).
 */
@Composable
fun DropDownMrcy(
    selectedIds: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    label: String,
    placeholder: String,
    content: DropDownContent,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hasError: Boolean = false,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,
    bottomText: String? = null,
    showBottomIcon: Boolean = true,
    leadingIcon: IconToken? = null,
    dropDownVariant: DropDownVariant = DropDownVariant.SIMPLE,
    inputDropDownTokens: InputDropDownTokens? = null,
    dropDownTokens: DropDownTokens? = null,
) {
    validateVariantContent(
        variant = dropDownVariant,
        content = content,
    )

    val resolvedTokens = DropDownTokensResolver.resolve(override = dropDownTokens)
    var expanded by remember { mutableStateOf(false) }

    val selectedLabel = remember(selectedIds, content) {
        resolveSelectedText(selectedIds = selectedIds, content = content)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError,
        )

        InputDropDownMrcy(
            value = selectedLabel,
            placeholder = placeholder,
            onClick = { expanded = !expanded },
            enabled = enabled,
            readOnly = readOnly,
            hasError = hasError,
            expanded = expanded,
            leadingIcon = leadingIcon,
            variant = when (dropDownVariant) {
                DropDownVariant.SIMPLE -> InputDropDownVariant.SIMPLE
                DropDownVariant.CHECKBOX -> InputDropDownVariant.CHECKBOX
            },
            tokenGroup = resolvedTokens.inputDropDownTokenGroup,
            inputDropDownTokens = inputDropDownTokens,
        )

        if (expanded && enabled && !readOnly) {
            val menuTokens = cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownTokensResolver.resolve(
                group = resolvedTokens.inputDropDownTokenGroup,
                variant = when (dropDownVariant) {
                    DropDownVariant.SIMPLE -> InputDropDownVariant.SIMPLE
                    DropDownVariant.CHECKBOX -> InputDropDownVariant.CHECKBOX
                },
                leadingIconOverride = leadingIcon,
                override = inputDropDownTokens,
            )

            DropDownMenuContent(
                content = content,
                selectedIds = selectedIds,
                onSelectionChange = { next ->
                    onSelectionChange(next)
                    if (dropDownVariant == DropDownVariant.SIMPLE ||
                        (content is DropDownContent.Checkbox && content.selectionMode == DropDownSelectionMode.SINGLE)
                    ) {
                        expanded = false
                    }
                },
                tokens = menuTokens,
            )
        }

        if (bottomText != null) {
            DropDownBottomText(
                text = bottomText,
                hasError = hasError,
                showBottomIcon = showBottomIcon,
                tokens = resolvedTokens,
            )
        }
    }
}

private fun validateVariantContent(
    variant: DropDownVariant,
    content: DropDownContent,
) {
    when (variant) {
        DropDownVariant.SIMPLE -> require(content is DropDownContent.Simple) {
            "DropDownVariant.SIMPLE requiere DropDownContent.Simple"
        }

        DropDownVariant.CHECKBOX -> require(content is DropDownContent.Checkbox) {
            "DropDownVariant.CHECKBOX requiere DropDownContent.Checkbox"
        }
    }
}

private fun resolveSelectedText(
    selectedIds: Set<String>,
    content: DropDownContent,
): String {
    val selectedLabels = when (content) {
        is DropDownContent.Simple -> content.items
        is DropDownContent.Checkbox -> content.items
    }
        .filter { selectedIds.contains(it.id) }
        .map { it.label }

    return selectedLabels.joinToString(", ")
}

@Composable
private fun DropDownMenuContent(
    content: DropDownContent,
    selectedIds: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    tokens: InputDropDownTokens,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ColorComposeAdapter.toComposeColor(tokens.menuBackground),
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                color = ColorComposeAdapter.toComposeColor(tokens.menuBorder),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(vertical = 8.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 260.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            when (content) {
                is DropDownContent.Simple -> {
                    items(content.items, key = { it.id }) { item ->
                        SimpleRow(
                            item = item,
                            selected = selectedIds.contains(item.id),
                            tokens = tokens,
                            onClick = {
                                if (item.enabled) onSelectionChange(setOf(item.id))
                            },
                        )
                    }
                }

                is DropDownContent.Checkbox -> {
                    items(content.items, key = { it.id }) { item ->
                        InputCheckBoxMrcy(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            label = item.label,
                            checked = selectedIds.contains(item.id),
                            onCheckedChange = { nextChecked ->
                                if (!item.enabled) return@InputCheckBoxMrcy

                                val next = when (content.selectionMode) {
                                    DropDownSelectionMode.SINGLE -> {
                                        if (nextChecked) setOf(item.id) else emptySet()
                                    }
                                    DropDownSelectionMode.MULTIPLE -> {
                                        if (nextChecked) {
                                            selectedIds + item.id
                                        } else {
                                            selectedIds - item.id
                                        }
                                    }
                                }
                                onSelectionChange(next)
                            },
                            enabled = item.enabled,
                            showContainerBorder = false,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SimpleRow(
    item: DropDownItem,
    selected: Boolean,
    tokens: InputDropDownTokens,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (item.enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (selected) {
            IconComposeAdapter.Render(
                icon = tokens.selectedItemLeadingIcon,
                fillColor = ColorComposeAdapter.toComposeColor(tokens.menuItemCheckColor),
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else {
            Spacer(modifier = Modifier.width(28.dp))
        }

        LabelMrcy(
            text = item.label,
            labelTokensOverride = LabelTokensOverride(
                labelTypography = tokens.menuItemTypography,
                foregroundDefault = if (selected) {
                    tokens.menuItemSelectedTextColor
                } else {
                    tokens.menuItemTextColor
                },
                foregroundError = if (selected) {
                    tokens.menuItemSelectedTextColor
                } else {
                    tokens.menuItemTextColor
                },
            ),
            limitMaxLabel = false,
        )
    }
}

@Composable
private fun CheckboxRow(
    item: DropDownItem,
    selected: Boolean,
    tokens: InputDropDownTokens,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (item.enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 1.dp,
                    color = ColorComposeAdapter.toComposeColor(tokens.menuBorder),
                    shape = RoundedCornerShape(6.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                IconComposeAdapter.Render(
                    icon = tokens.selectedItemLeadingIcon,
                    fillColor = ColorComposeAdapter.toComposeColor(tokens.menuItemCheckColor),
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        Text(
            text = item.label,
            style = TypographyComposeAdapter.toTextStyle(
                tokens.menuItemTypography,
                FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken),
            ),
            color = ColorComposeAdapter.toComposeColor(tokens.menuItemTextColor),
        )
    }
}

@Composable
private fun DropDownBottomText(
    text: String,
    hasError: Boolean,
    showBottomIcon: Boolean,
    tokens: DropDownTokens,
) {
    val color = if (hasError) tokens.bottomTextErrorColor else tokens.bottomTextColor

    Row(modifier = Modifier.padding(top = 10.dp)) {
        if (showBottomIcon) {
            IconComposeAdapter.Render(
                icon = tokens.bottomTextIcon,
                fillColor = ColorComposeAdapter.toComposeColor(color),
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        LabelMrcy(
            modifier = Modifier.widthIn(max = LabelSpacings.MaxLabelWidth.dp),
            text = text,
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = color,
                foregroundError = color,
                labelTypography = tokens.bottomTextTypography,
                optionalTypography = tokens.bottomTextTypography,
                fontFamily = tokens.fontFamilyToken,
            ),
            limitMaxLabel = false,
        )
    }
}
