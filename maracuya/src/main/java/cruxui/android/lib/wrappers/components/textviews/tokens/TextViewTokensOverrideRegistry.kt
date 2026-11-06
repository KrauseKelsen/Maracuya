package cruxui.android.lib.wrappers.components.textviews.tokens

import cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensOverrideRegistry` asocia nombres XML con recetas del override semántico compartido.
 *
 * Justificación:
 * Permite elegir desde XML una tipografía o combinación de tokens sin inventar atributos para cada estilo y sin
 * almacenar un tema activo global.
 *
 * Se comunica con:
 * Hereda TokensOverrideRegistry<TextViewTokensOverride>. La View lo consulta con su LibraryThemeSnapshot antes
 * de resolver tokens.
 *
 * Uso:
 * API pública del host. Registrar antes de inflar cualquier layout que utilice la receta:
 * ```kotlin
 * TextViewTokensOverrideRegistry.register("sectionTitle") { library: LibraryThemeSnapshot ->
 *     TextViewTokensOverride(
 *         typography = library.typography.headline24,
 *         textColor = library.colors.brandPrimary,
 *         fontFamily = library.fontFamily,
 *         iconEnd = library.icons.alerts.info,
 *     )
 * }
 * ```
 * En XML escribir `app:textViewTokensOverride="sectionTitle"`; desde código llamar a
 * `view.setTokensOverrideName("sectionTitle")`.
 * La función recibe el tema del ámbito de cada View. No es composable y no debe leer locals ni capturar una
 * Activity. Una función de receta puede reutilizarse en Compose pasándole LibraryTheme.current.
 *
 * Operaciones heredadas disponibles al host:
 * - `register(name, builder)` guarda o reemplaza una receta dependiente de library.
 * - `register(name, tokensOverride)` registra un valor fijo, que no cambia automáticamente con la paleta.
 * - `register(name) { colors, typography, icons, fontFamily -> ... }` conserva el formato anterior de cuatro
 *   argumentos.
 * - `resolve(name, library)` evalúa una receta contra un snapshot explícito; la View lo hace internamente.
 * - `contains(name)`, `unregister(name)` y `clear()` permiten consultar y retirar entradas. clear afecta a todas
 *   las recetas de este componente.
 *
 * Los nombres se recortan y comparan sin distinguir mayúsculas usando Locale.ROOT. Un registro vacío no se
 * admite. Resolver un nombre ausente/no vacío falla explícitamente; null o vacío no selecciona receta.
 * Reemplazar una receta no notifica automáticamente a las Views existentes: llamar refreshTheme. Antes de
 * retirar una receta usada por una View, retirar o cambiar su selección para evitar errores en futuras
 * resoluciones.
 *
 * Objetivos:
 * - Conectar XML con TokensOverride.
 * - Reutilizar las colecciones del tema por ámbito.
 * - Mantener un registro común reutilizable sin Context ni estado visual.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry
 * @see cruxui.android.lib.wrappers.components.textviews.TextView
 */
object TextViewTokensOverrideRegistry : TokensOverrideRegistry<TextViewTokensOverride>()
