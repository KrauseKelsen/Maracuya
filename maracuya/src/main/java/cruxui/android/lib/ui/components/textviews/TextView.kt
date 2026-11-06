package cruxui.android.lib.ui.components.textviews

import cruxui.android.lib.core.components.textviews.tokens.TextViewLayoutTokens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.ui.components.textviews.behavior.TextViewBehaviorResolver
import cruxui.android.lib.ui.components.textviews.content.TextViewContent
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.ui.components.textviews.tokens.TextViewTokensResolver
import cruxui.android.lib.ui.components.textviews.tokensrender.TextViewTokensRenderResolver
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariant
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariantResolver
import cruxui.android.lib.utils.composeadapters.ColorComposeAdapter
import cruxui.android.lib.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.lib.utils.composeadapters.TypographyComposeAdapter

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextView` es la entrada Compose del componente de texto del DS, con variantes, estados, iconos opcionales,
 * selección y eventos independientes.
 *
 * Justificación:
 * Ofrece al host una API declarativa con apariencia consistente. Coordina resolutores y adaptadores sin obligar
 * a reconstruir el tema ni a usar controles Android imperativos.
 *
 * Se comunica con:
 * Lee `TextViewTokensResolver`; deriva interacciones con `TextViewBehaviorResolver`, aplica el estado con
 * `TextViewTokensRenderResolver`, adapta fuentes/colores y delega el dibujo en `TextViewContent`.
 *
 * Uso:
 * API pública del host Compose. Usar dentro de `MyLibraryTheme`; el texto requerido es un String. Para texto
 * Android con spans se utiliza el wrapper nativo.
 *
 * Ejemplo mínimo:
 * ```kotlin
 * MyLibraryTheme {
 *     TextView(text = "Nombre completo")
 * }
 * ```
 *
 * Personalizar mediante los tokens del tema activo:
 * ```kotlin
 * MyLibraryTheme {
 *     val library = LibraryTheme.current
 *     TextView(
 *         text = "Ver condiciones",
 *         modifier = Modifier.fillMaxWidth(),
 *         variant = TextViewVariant.HIPERTEXT,
 *         tokensOverride = TextViewTokensOverride(
 *             typography = library.typography.body1,
 *             textColor = library.colors.brandPrimary,
 *             errorColor = library.colors.fgError,
 *             disabledColor = library.colors.fgSubtle,
 *             iconEnd = library.icons.alerts.info,
 *         ),
 *         onClick = { /* el host abre las condiciones */ },
 *         onIconEndClick = { /* el host muestra ayuda */ },
 *         iconEndContentDescription = "Más información", // localizar en recursos del host
 *         onClickLabel = "Abrir condiciones",             // localizar en recursos del host
 *         maxLines = 2,
 *         overflow = TextOverflow.Ellipsis,
 *     )
 * }
 * ```
 *
 * Opciones de apariencia:
 * - `tokensOverride` acepta colores normal/error/deshabilitado, fondo, capa superior, iconos, tipografía y
 *   familia. Los campos null conservan defaults.
 * - DEFAULT usa el color normal; HIPERTEXT usa el color de enlace y subrayado. El host define la acción; la
 *   variante no abre URLs.
 * - `enabled = false` precede visualmente a `error = true` y bloquea callbacks. Error por sí mismo no
 *   deshabilita.
 * - `minLines`, `maxLines`, `overflow` y `textAlign` controlan presentación del texto. Usar límites positivos y
 *   coherentes; `textAlign = null` conserva el estilo adaptado.
 *
 * Eventos y selección:
 * - `onClick`, `onLongClick` y `onDoubleClick` pertenecen al texto.
 * - Los callbacks start/end pertenecen a cada icono y no activan la acción del texto.
 * - Un icono con callback necesita una descripción localizada no vacía; sin ella el componente falla
 *   explícitamente. Un icono decorativo puede omitirla.
 * - `behavior = TextViewBehavior.Selectable` activa copia/selección y reserva pulsación larga y doble toque del
 *   texto. Los iconos mantienen sus acciones.
 * - El tamaño táctil mínimo de los iconos interactivos es 48.dp; no altera el tamaño visual del recurso.
 *
 * Cambiar el estado del host y recomponer actualiza texto, tokens y comportamiento. No hay setters ni un
 * parámetro público de tokens completos. Para compartir una receta con XML, definir una función
 * `(LibraryThemeSnapshot) -> TextViewTokensOverride`, llamarla aquí y registrarla en el wrapper.
 *
 * Objetivos:
 * - Proveer un contrato declarativo público.
 * - Compartir semántica con el TextView XML.
 * - Separar eventos del texto y de los iconos.
 * - Conservar selección y accesibilidad coherentes.
 *
 * Flujo de construcción o ejecución:
 * 1) Defaults de variante y override.
 * 2) Capacidades y eventos.
 * 3) Apariencia del estado.
 * 4) Adaptación a Compose y decoración.
 * 5) Render de texto e iconos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param text Contenido String que dibuja el componente.
 * @param modifier Modificadores del contenedor; por defecto Modifier.
 * @param variant DEFAULT o HIPERTEXT.
 * @param tokensOverride Personalización semántica parcial; null usa defaults.
 * @param behavior Capacidad de selección; por defecto Default.
 * @param enabled Habilita interacción; false selecciona disabledColor.
 * @param error Selecciona errorColor cuando enabled es true.
 * @param onClick Acción de pulsación sencilla del texto; null la omite.
 * @param onLongClick Acción de pulsación larga del texto; se reserva para selección cuando esta está activa.
 * @param onDoubleClick Acción de doble toque del texto; se reserva para selección cuando esta está activa.
 * @param onIconStartClick Pulsación sencilla del icono inicial.
 * @param onIconStartLongClick Pulsación larga del icono inicial.
 * @param onIconEndClick Pulsación sencilla del icono final.
 * @param onIconEndLongClick Pulsación larga del icono final.
 * @param onClickLabel Etiqueta accesible localizada de la acción del texto.
 * @param onLongClickLabel Etiqueta accesible localizada de su pulsación larga.
 * @param iconStartContentDescription Descripción localizada del icono inicial; obligatoria si es interactivo.
 * @param iconEndContentDescription Descripción localizada del icono final; obligatoria si es interactivo.
 * @param minLines Mínimo de líneas, 1 por defecto.
 * @param maxLines Máximo de líneas, sin límite práctico por defecto.
 * @param overflow Política de desbordamiento; Clip por defecto.
 * @param textAlign Alineación específica de esta instancia o null para conservar el estilo.
 * @see cruxui.android.lib.theme.MyLibraryTheme
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
 */
@Composable
fun TextView(
    text: String,
    modifier: Modifier = Modifier,
    variant: TextViewVariant = TextViewVariant.DEFAULT,
    tokensOverride: TextViewTokensOverride? = null,
    behavior: TextViewBehavior = TextViewBehavior.Default,
    enabled: Boolean = true,
    error: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onIconStartClick: (() -> Unit)? = null,
    onIconStartLongClick: (() -> Unit)? = null,
    onIconEndClick: (() -> Unit)? = null,
    onIconEndLongClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    iconStartContentDescription: String? = null,
    iconEndContentDescription: String? = null,
    minLines: Int = TextViewLayoutTokens.MinLines,
    maxLines: Int = TextViewLayoutTokens.MaxLines,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
) {
    val tokens = TextViewTokensResolver.resolve(
        variant = variant,
        override = tokensOverride,
    )
    val behaviorState = TextViewBehaviorResolver.resolve(
        behavior = behavior,
        enabled = enabled,
        onClick = onClick,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
        onIconStartClick = onIconStartClick,
        onIconStartLongClick = onIconStartLongClick,
        onIconEndClick = onIconEndClick,
        onIconEndLongClick = onIconEndLongClick,
        onClickLabel = onClickLabel,
        onLongClickLabel = onLongClickLabel,
        iconStartContentDescription = iconStartContentDescription,
        iconEndContentDescription = iconEndContentDescription,
    )
    val renderTokens = TextViewTokensRenderResolver.resolve(
        tokens = tokens,
        enabled = enabled,
        error = error,
    )

    val textStyle = TextViewVariantResolver.resolveTextStyle(
        variant = variant,
        textStyle = TypographyComposeAdapter.toTextStyle(
            token = renderTokens.typography,
            fontFamily = FontFamiliesComposeAdapter.toCompose(renderTokens.fontFamily),
        ).copy(platformStyle = PlatformTextStyle(includeFontPadding = TextViewLayoutTokens.IncludeFontPadding)),
    )

    TextViewContent(
        text = text,
        modifier = modifier,
        tokens = renderTokens,
        behaviorState = behaviorState,
        textColor = ColorComposeAdapter.toComposeColor(renderTokens.textColor),
        backgroundColor = ColorComposeAdapter.toComposeColor(renderTokens.backgroundColor),
        foregroundColor = ColorComposeAdapter.toComposeColor(renderTokens.foregroundColor),
        iconStartColor = ColorComposeAdapter.toComposeColor(renderTokens.iconStartColor),
        iconEndColor = ColorComposeAdapter.toComposeColor(renderTokens.iconEndColor),
        textStyle = textStyle,
        minLines = minLines,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
    )
}
