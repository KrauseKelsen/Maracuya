package cruxui.android.maracuya.wrappers.components.labels

import androidx.compose.ui.Modifier
import cruxui.android.maracuya.ui.components.labels.LabelTokens
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride

/**
 * Agrupa la configuración del `LabelWrp` resuelta desde XML o desde valores por defecto.
 *
 * Mantiene los datos tipados y separados del render para que el wrapper conserve paridad
 * con la API Compose de `LabelMrcy` sin acoplar el parseo de atributos a la vista.
 */
internal data class LabelAttributes(
    val modifier: Modifier = Modifier,
    val text: String,
    val optionalText: Boolean = false,
    val showIcon: Boolean = false,
    val error: Boolean = false,
    val onClickContentDescription: String? = null,
    val labelTokens: LabelTokens? = null,
    val labelTokensOverride: LabelTokensOverride? = null,
    val labelTokensOverrideName: String? = null,
    val limitMaxLabel: Boolean = true,
)
