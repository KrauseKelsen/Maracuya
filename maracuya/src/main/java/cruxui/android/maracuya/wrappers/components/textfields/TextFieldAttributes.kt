package cruxui.android.maracuya.wrappers.components.textfields

import androidx.compose.ui.Modifier
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.textfields.TextFieldTokens
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant

/**
 * Modelo inmutable con la configuración inicial de `TextFieldWrp`.
 *
 * Centraliza los valores leídos desde XML y los defaults del wrapper para mantener
 * una frontera clara entre el parseo de atributos Android y la API Compose.
 */
internal data class TextFieldAttributes(
    val value: String,
    val pinValue: List<String>,
    val label: String,
    val placeholder: String,
    val length: Int,
    val modifier: Modifier = Modifier,
    val enabled: Boolean,
    val readOnly: Boolean,
    val hasError: Boolean,
    val optionalText: Boolean,
    val showLabelIcon: Boolean,
    val bottomText: String?,
    val showBottomIcon: Boolean,
    val textFieldVariant: TextFieldVariant,
    val pinIconToken: IconToken? = null,
    val enableImplicitTrailingClear: Boolean,
    val textFieldTokens: TextFieldTokens? = null,
    val textFieldTokensOverrideName: String? = null,
)
