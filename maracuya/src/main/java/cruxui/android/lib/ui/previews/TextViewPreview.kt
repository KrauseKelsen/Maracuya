package cruxui.android.lib.ui.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cruxui.android.lib.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cruxui.android.lib.compositions.LocalLibraryColorTokens
import cruxui.android.lib.compositions.LocalLibraryIcons
import cruxui.android.lib.compositions.LocalLibraryTypography
import cruxui.android.lib.ui.components.textviews.TextView
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariant
import cruxui.android.lib.ui.previews.core.Mode
import cruxui.android.lib.ui.previews.core.PreviewWrapper

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextViewGalleryPreview presenta los nueve casos de referencia del TextView Compose.
 *
 * Justificación:
 * Documenta la apariencia esperada del DS y permite compararla con text_view_preview.xml.
 * Comparte strings localizados con la galería XML; recibe colores, tipografía e iconos del tema.
 *
 * Uso:
 * Galería de demostración para Android Studio o una pantalla de pruebas. El host consume TextView
 * directamente en sus pantallas y conecta callbacks reales; los callbacks de esta galería son vacíos.
 * PreviewWrapper configura tema y padding exterior de 16dp; la columna separa ejemplos con 12dp.
 * Esos márgenes pertenecen a la galería, no a los defaults del componente.
 *
 * Objetivos:
 * - Mostrar defaults, overrides, estados, iconos, gestos, selección e hipervínculo.
 * - Conservar una referencia visual equivalente para los dos frontends.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
@Preview(name = "TextView - Gallery", showBackground = true)
@Composable
fun TextViewGalleryPreview() {
    PreviewWrapper(style = Mode.current) {
        val colors = LocalLibraryColorTokens.current
        val icons = LocalLibraryIcons.current
        val typography = LocalLibraryTypography.current

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextView(text = stringResource(R.string.text_view_preview_basic))

            TextView(
                text = stringResource(R.string.text_view_preview_custom),
                tokensOverride = TextViewTokensOverride(
                    textColor = colors.fgMuted,
                    backgroundColor = colors.bgSubtle,
                    typography = typography.caption,
                ),
            )

            TextView(
                text = stringResource(R.string.text_view_preview_error),
                error = true,
            )

            TextView(
                text = stringResource(R.string.text_view_preview_disabled),
                enabled = false,
            )

            TextView(
                text = stringResource(R.string.text_view_preview_icons),
                tokensOverride = TextViewTokensOverride(
                    iconStart = icons.general.link,
                    iconEnd = icons.arrows.navigateNext,
                ),
                onIconStartClick = {},
                onIconStartLongClick = {},
                onIconEndClick = {},
                onIconEndLongClick = {},
                iconStartContentDescription = stringResource(R.string.text_view_preview_start_description),
                iconEndContentDescription = stringResource(R.string.text_view_preview_end_description),
            )

            TextView(
                text = stringResource(R.string.text_view_preview_label),
                tokensOverride = TextViewTokensOverride(
                    iconEnd = icons.alerts.info,
                ),
                onIconEndClick = {},
                iconEndContentDescription = stringResource(R.string.text_view_preview_end_description),
            )

            TextView(
                text = stringResource(R.string.text_view_preview_gestures),
                onClick = {},
                onDoubleClick = {},
                onLongClick = {},
                onClickLabel = stringResource(R.string.text_view_preview_click_label),
                onLongClickLabel = stringResource(R.string.text_view_preview_long_click_label),
            )

            TextView(
                text = stringResource(R.string.text_view_preview_selectable),
                behavior = TextViewBehavior.Selectable,
            )

            TextView(
                text = stringResource(R.string.text_view_preview_link),
                variant = TextViewVariant.HIPERTEXT,
                onClick = {},
                onClickLabel = stringResource(R.string.text_view_preview_link_label),
            )
        }
    }
}
