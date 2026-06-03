package cruxui.android.maracuya.ui.components.dropdowns

/**
 * Contrato de contenido para listas desplegables.
 *
 * Se modela como sealed class para permitir extensión controlada por variante,
 * manteniendo una API tan simple de consumir como un enum + payload.
 */
sealed class DropDownContent {

    data class Simple(
        val items: List<DropDownItem>,
    ) : DropDownContent()

    data class Checkbox(
        val items: List<DropDownItem>,
        val selectionMode: DropDownSelectionMode = DropDownSelectionMode.MULTIPLE,
    ) : DropDownContent()
}

enum class DropDownSelectionMode {
    SINGLE,
    MULTIPLE,
}

data class DropDownItem(
    val id: String,
    val label: String,
    val enabled: Boolean = true,
)
