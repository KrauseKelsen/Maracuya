package cruxui.android.lib.wrappers.core.resources

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import cruxui.android.lib.theme.LibraryAssetResources
import cruxui.android.lib.tokens.base.IconToken
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * AndroidIconLoader: Carga un drawable independiente y aplica tamaño y tinte del IconToken.
 *
 * Justificación:
 * Cada consumo obtiene estado mutable propio; no se almacenan Views ni callbacks.
 * El tamaño del token define una caja cuadrada y el asset conserva su proporción dentro de ella,
 * igual que el adaptador Compose. Así los vectores rectangulares no se estiran en XML.
 *
 * Uso:
 * Infraestructura interna del DS. Cada componente incorpora solo las capacidades que necesita.
 * No contiene variantes, callbacks ni valores predeterminados de TextView, Button u otro componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object AndroidIconLoader {
    /** Resuelve el asset y lo ajusta conservando proporciones, igual que ContentScale.Fit de Compose. */
    fun load(context: Context, token: IconToken): Drawable {
        val asset = requireNotNull(AppCompatResources.getDrawable(context, LibraryAssetResources.icon(token))) {
            "Missing icon resource: ${token.name}"
        }.mutate()
        val size = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, token.iconSize.toFloat(), context.resources.displayMetrics,
        ).roundToInt()
        return FittedIconDrawable(asset, size).apply {
            setBounds(0, 0, size, size)
            token.iconColor?.let { setTint(AndroidTokenAdapter.color(it)) }
        }
    }
}
