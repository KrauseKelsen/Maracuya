package cruxui.android.maracuya.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.ui.components.dropdowns.DropDownContent
import cruxui.android.maracuya.ui.components.dropdowns.DropDownItem
import cruxui.android.maracuya.ui.components.dropdowns.DropDownMrcy
import cruxui.android.maracuya.ui.components.dropdowns.DropDownSelectionMode
import cruxui.android.maracuya.ui.components.dropdowns.DropDownVariant
import cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownMrcy
import cruxui.android.maracuya.ui.components.inputs.dropdown.InputDropDownVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper

@Preview(name = "InputDropDown – Default", showBackground = true)
@Composable
fun InputDropDown_Default_Preview() {
    PreviewWrapper(style = Mode.current) {
        InputDropDownMrcy(
            value = "",
            placeholder = "Selecciona una opción",
            onClick = {},
        )
    }
}

@Preview(name = "InputDropDown – With Leading Icon", showBackground = true)
@Composable
fun InputDropDown_With_Leading_Icon_Preview() {
    PreviewWrapper(style = Mode.current) {
        InputDropDownMrcy(
            value = "Recent Transactions",
            placeholder = "Selecciona una opción",
            onClick = {},
            expanded = true,
            leadingIcon = LocalLibraryIcons.current.general.calendarToday,
            variant = InputDropDownVariant.CHECKBOX,
        )
    }
}

@Preview(name = "DropDown – Simple", showBackground = true)
@Composable
fun DropDown_Simple_Preview() {
    var selectedIds by remember { mutableStateOf(setOf("national")) }

    val content = DropDownContent.Simple(
        items = listOf(
            DropDownItem(id = "national", label = "Nacional"),
            DropDownItem(id = "passport", label = "Pasaporte"),
            DropDownItem(id = "foreign", label = "Cédula extranjera"),
        ),
    )

    PreviewWrapper(style = Mode.current) {
        DropDownMrcy(
            selectedIds = selectedIds,
            onSelectionChange = { selectedIds = it },
            label = "Tipo de identificación",
            placeholder = "Selecciona una opción",
            content = content,
            dropDownVariant = DropDownVariant.SIMPLE,
            bottomText = "Selecciona un tipo de documento",
        )
    }
}

@Preview(name = "DropDown – Checkbox Multiple", showBackground = true)
@Composable
fun DropDown_Checkbox_Multiple_Preview() {
    var selectedIds by remember { mutableStateOf(setOf("recent")) }

    val content = DropDownContent.Checkbox(
        items = listOf(
            DropDownItem(id = "recent", label = "Recent Transactions"),
            DropDownItem(id = "purchases", label = "Purchases"),
            DropDownItem(id = "payments", label = "Payments"),
            DropDownItem(id = "subscriptions", label = "Subscriptions"),
        ),
        selectionMode = DropDownSelectionMode.MULTIPLE,
    )

    PreviewWrapper(style = Mode.current) {
        DropDownMrcy(
            selectedIds = selectedIds,
            onSelectionChange = { selectedIds = it },
            label = "Label",
            placeholder = "Selecciona una opción",
            content = content,
            dropDownVariant = DropDownVariant.CHECKBOX,
            leadingIcon = LocalLibraryIcons.current.general.calendarToday,
            optionalText = true,
        )
    }
}

@Preview(name = "DropDown – Error Disabled", showBackground = true)
@Composable
fun DropDown_Error_Disabled_Preview() {
    var selectedIds by remember { mutableStateOf(emptySet<String>()) }

    val content = DropDownContent.Simple(
        items = listOf(
            DropDownItem(id = "1", label = "Opción 1"),
            DropDownItem(id = "2", label = "Opción 2"),
        ),
    )

    PreviewWrapper(style = Mode.current) {
        DropDownMrcy(
            selectedIds = selectedIds,
            onSelectionChange = { selectedIds = it },
            label = "Dropdown en error",
            placeholder = "Selecciona una opción",
            content = content,
            enabled = false,
            hasError = true,
            bottomText = "Mensaje de error",
            showBottomIcon = true,
        )
    }
}
