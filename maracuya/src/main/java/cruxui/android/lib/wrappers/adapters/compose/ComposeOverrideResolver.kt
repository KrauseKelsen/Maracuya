package cruxui.android.lib.wrappers.adapters.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.wrappers.core.registry.TokensOverrideSource
import cruxui.android.lib.wrappers.core.registry.resolveRecipe

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * resolveInComposition adapta LibraryTheme y LocalInspectionMode al contrato común de recetas.
 *
 * Justificación:
 * Mantiene TokensOverrideSource y resolveRecipe independientes de Compose. Cualquier wrapper
 * que aloje Compose puede resolver su receta sin duplicar la consulta del tema activo.
 *
 * Uso:
 * Extensión interna y composable del DS. El wrapper invoca source.resolveInComposition(nombre);
 * el host registra sus recetas mediante el registro público del componente.
 *
 * Objetivos:
 * - Resolver con el snapshot de la composición actual.
 * - Aplicar la política común de recetas ausentes en ejecución y preview.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param T Tipo de override admitido por el registro.
 * @param name Nombre de receta o null si no se solicita una.
 * @return Override resuelto o null cuando la política común permite omitirlo.
 */
@Composable
internal fun <T : Any> TokensOverrideSource<T>.resolveInComposition(name: String?): T? =
    resolveRecipe(name, LibraryTheme.current, LocalInspectionMode.current)
