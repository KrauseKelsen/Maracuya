package com.example.mylibrary.ui.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylibrary.ui.components.checkboxes.CheckBoxMrcy
import com.example.mylibrary.ui.components.checkboxes.CheckBoxOption
import com.example.mylibrary.ui.components.inputs.checkbox.InputCheckBoxMrcy
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper

@Preview(name = "InputCheckBox - States", showBackground = true)
@Composable
fun InputCheckBox_States_Preview() {
    PreviewWrapper(style = Mode.current) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputCheckBoxMrcy(
                label = "Checkbox text",
                checked = false,
                onCheckedChange = {},
                showContainerBorder = false,
            )
            InputCheckBoxMrcy(
                label = "Checkbox text",
                checked = true,
                onCheckedChange = {},
                showContainerBorder = false,
            )
            InputCheckBoxMrcy(
                label = "Checkbox text",
                checked = true,
                onCheckedChange = {},
                enabled = false,
                showContainerBorder = false,
            )
            InputCheckBoxMrcy(
                label = "Checkbox text",
                checked = false,
                onCheckedChange = {},
                enabled = false,
                showContainerBorder = false,
            )
        }
    }
}

@Preview(name = "CheckBoxMrcy - Single", showBackground = true)
@Composable
fun CheckBox_Single_Preview() {
    var checked by remember { mutableStateOf(false) }

    PreviewWrapper(style = Mode.current) {
        CheckBoxMrcy(
            checked = checked,
            onCheckedChange = { checked = it },
            checkBoxText = "Checkbox text",
            label = "Label",
            optionalText = true,
            showLabelIcon = true,
            showInputBorder = false,
        )
    }
}

@Preview(name = "CheckBoxMrcy - List Error", showBackground = true)
@Composable
fun CheckBox_List_Error_Preview() {
    var items by remember {
        mutableStateOf(
            listOf(
                CheckBoxOption(id = "1", label = "Checkbox text", checked = false),
                CheckBoxOption(id = "2", label = "Checkbox text", checked = false),
                CheckBoxOption(id = "3", label = "Checkbox text", checked = false),
                CheckBoxOption(id = "4", label = "Checkbox text", checked = false),
                CheckBoxOption(id = "5", label = "Checkbox text", checked = false),
            )
        )
    }

    PreviewWrapper(style = Mode.current) {
        CheckBoxMrcy(
            items = items,
            onItemsChange = { items = it },
            label = "Label",
            hasError = true,
            bottomText = "Field-specific error message",
            showBottomIcon = true,
            showInputBorder = false,
            optionalText = true,
            showLabelIcon = true,
        )
    }
}
