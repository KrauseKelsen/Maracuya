package cruxui.android.lib.utils.composeadapters

import cruxui.android.lib.theme.LibraryAssetResources

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.lib.compositions.LocalLibraryColorTokens
import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * IconComposeAdapter representa un IconToken de Library en Compose.
 *
 * Justificación:
 * Resuelve el asset con LibraryAssetResources y adapta tamaño y color a tipos Compose.
 * No contiene listas de iconos ni reconstruye el catálogo; AndroidIconLoader adapta el mismo token para Views.
 *
 * Uso:
 * API de renderizado del host y del DS. Dentro de MyLibraryTheme:
 * ```
 * IconComposeAdapter.Render(
 *     icon = LibraryTheme.current.icons.alerts.info,
 *     contentDescription = "Información",
 * )
 * ```
 * El tamaño explícito prevalece sobre icon.iconSize; Dp.Unspecified conserva el tamaño Library en dp.
 * El color explícito prevalece sobre icon.iconColor; si ambos faltan, usa brandPrimary del ámbito
 * cuando existe. Sin color disponible conserva el asset sin tinte. Para iconos decorativos dejar
 * contentDescription en null; para información necesaria proporcionar un texto localizado.
 *
 * Objetivos:
 * - Consumir los valores Library y permitir ajustes explícitos del componente sin alterar el token.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
object IconComposeAdapter {


    /**
     * Carga el asset y aplica las prioridades visuales del adaptador.
     * @param icon Token Library o token del host con recurso explícito.
     * @param modifier Ajustes de disposición y semántica Compose.
     * @param size Tamaño explícito; Unspecified utiliza icon.iconSize.
     * @param fillColor Tinte explícito; Unspecified consulta token y tema.
     * @param contentDescription Descripción accesible; null indica decoración.
     */
    @Composable
    fun Render(
        icon: IconToken,
        modifier: Modifier = Modifier,
        size: Dp = Dp.Unspecified,
        fillColor: Color = Color.Unspecified,
        contentDescription: String? = null
    ) {
        val fallbackColor = runCatching {
            ColorComposeAdapter.toComposeColor(LocalLibraryColorTokens.current.brandPrimary)
        }.getOrElse { Color.Unspecified }

        val resolvedFillColor = when {
            fillColor != Color.Unspecified -> fillColor
            icon.iconColor != null -> ColorComposeAdapter.toComposeColor(icon.iconColor)
            else -> fallbackColor
        }

        val resolvedSize = if (size == Dp.Unspecified) icon.iconSize.dp else size
        Box(modifier = modifier.size(resolvedSize)) {
            Image(
                painter = painterResource(LibraryAssetResources.icon(icon)),
                contentDescription = contentDescription,
                modifier = Modifier.size(resolvedSize),
                colorFilter =
                    if (resolvedFillColor != Color.Unspecified)
                        ColorFilter.tint(resolvedFillColor)
                    else
                        null
            )
        }
    }
}
