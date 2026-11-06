package cruxui.android.lib.core.components.textviews.tokens

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokens` contiene la configuración visual completa de un TextView, independiente de Android Views y
 * Compose.
 *
 * Justificación:
 * Evita que ambos renderizadores mantengan contratos y valores predeterminados divergentes. No contiene estado
 * de interacción ni callbacks.
 *
 * Se comunica con:
 * `TextViewTokenDefaults` lo construye; `merge` aplica personalizaciones; los resolutores de render consultan el
 * estado habilitado/error.
 *
 * Uso:
 * Es un tipo público compartido. El host normalmente entrega `TextViewTokensOverride` al componente; no necesita
 * construir todos estos campos.
 * Para una integración propia puede obtener los defaults sin composición:
 * ```kotlin
 * val library = LibraryThemes.Maracuya.resolve(dark = false)
 * val tokens = TextViewTokenDefaults.resolve(library)
 * ```
 * El TextView Compose no recibe un objeto completo `TextViewTokens` en su API pública.
 *
 * Objetivos:
 * - Transportar la semántica visual completa.
 * - Mantener iguales los campos en ambos frontends.
 * - Separar apariencia de contenido y eventos.
 *
 * Flujo de construcción o ejecución:
 * 1) Tema resuelto.
 * 2) Defaults de variante y override.
 * 3) Tokens completos.
 * 4) Estado visual y adaptación a la plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textColor Color del texto en estado normal; error y deshabilitado tienen tokens propios.
 * @param backgroundColor Color de fondo del componente.
 * @param errorColor Color del contenido cuando error está activo y el componente está habilitado.
 * @param foregroundColor Color de la capa visual dibujada sobre el contenido.
 * @param disabledColor Color del contenido deshabilitado; tiene prioridad sobre error.
 * @param iconStart Icono inicial relativo a la dirección de lectura; null representa ausencia en los tokens
 *     resueltos.
 * @param iconEnd Icono final relativo a la dirección de lectura; null representa ausencia en los tokens
 *     resueltos.
 * @param typography Estilo semántico con tamaño, altura de línea, tracking, peso, cursiva y alineación.
 * @param fontFamily Familia corporativa independiente de las métricas de typography.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
data class TextViewTokens(
    val textColor: ColorToken,
    val backgroundColor: ColorToken,
    val errorColor: ColorToken,
    val foregroundColor: ColorToken,
    val disabledColor: ColorToken,
    val iconStart: IconToken?,
    val iconEnd: IconToken?,
    val typography: TypographyToken,
    val fontFamily: FontFamilyToken,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensOverride` expresa sustituciones parciales de la apariencia semántica del componente.
 *
 * Justificación:
 * Permite personalizar una tipografía, color o icono conservando el resto del DS. El mismo objeto sirve para
 * Compose y para las recetas registradas del wrapper XML.
 *
 * Se comunica con:
 * Lo consumen `TextViewTokens.merge`, `TextViewTokenDefaults`, el TextView Compose y
 * `TextViewTokensOverrideRegistry`.
 *
 * Uso:
 * API pública del host. Seleccionar valores de la biblioteca activa y entregar únicamente los campos que se
 * desean sustituir:
 * ```kotlin
 * val override = TextViewTokensOverride(
 *     typography = library.typography.headline24,
 *     textColor = library.colors.brandPrimary,
 *     fontFamily = library.fontFamily,
 *     iconEnd = library.icons.alerts.info,
 * )
 * ```
 * En Compose usar `tokensOverride = override`; en Views usar `view.setTokensOverride(override)` o devolverlo
 * desde `register`.
 * `null` significa no aportar el campo, incluso en iconos. En XML/Views se retira un icono con los setters del
 * wrapper o `ViewIcon.None` en su parche nativo.
 * `textColor` modifica el estado normal; usar `errorColor` y `disabledColor` para esos estados. No confundirlo
 * con `setTextColor`, que es una API nativa para todos los estados.
 *
 * Objetivos:
 * - Evitar reconstruir los defaults.
 * - Compartir recetas entre plataformas.
 * - Mantener explícitas las personalizaciones por estado.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textColor Color del texto en estado normal; error y deshabilitado tienen tokens propios. Null conserva
 *     el valor inferior.
 * @param backgroundColor Color de fondo del componente. Null conserva el valor inferior.
 * @param errorColor Color del contenido cuando error está activo y el componente está habilitado. Null conserva
 *     el valor inferior.
 * @param foregroundColor Color de la capa visual dibujada sobre el contenido. Null conserva el valor inferior.
 * @param disabledColor Color del contenido deshabilitado; tiene prioridad sobre error. Null conserva el valor
 *     inferior.
 * @param iconStart Icono inicial relativo a la dirección de lectura; null conserva el icono de la configuración
 *     inferior.
 * @param iconEnd Icono final relativo a la dirección de lectura; null conserva el icono de la configuración
 *     inferior.
 * @param typography Estilo semántico con tamaño, altura de línea, tracking, peso, cursiva y alineación. Null
 *     conserva el valor inferior.
 * @param fontFamily Familia corporativa independiente de las métricas de typography. Null conserva el valor
 *     inferior.
 * @see cruxui.android.lib.wrappers.components.textviews.tokens.TextViewTokensOverrideRegistry
 */
data class TextViewTokensOverride(
    val textColor: ColorToken? = null,
    val backgroundColor: ColorToken? = null,
    val errorColor: ColorToken? = null,
    val foregroundColor: ColorToken? = null,
    val disabledColor: ColorToken? = null,
    val iconStart: IconToken? = null,
    val iconEnd: IconToken? = null,
    val typography: TypographyToken? = null,
    val fontFamily: FontFamilyToken? = null,
)

/**
 * Combina el receptor con los campos presentes del override sin modificar ninguno de los objetos.
 *
 * Uso: lo invoca el DS al resolver defaults; el host puede usarlo al construir una integración propia.
 * `null` en el override completo devuelve el mismo receptor; `null` en un campo conserva su valor previo.
 *
 * @param override Personalización parcial o null.
 * @return Tokens completos con las sustituciones presentes; no elimina iconos mediante null.
 */
fun TextViewTokens.merge(override: TextViewTokensOverride?): TextViewTokens {
    if (override == null) return this

    return copy(
        textColor = override.textColor ?: textColor,
        backgroundColor = override.backgroundColor ?: backgroundColor,
        errorColor = override.errorColor ?: errorColor,
        foregroundColor = override.foregroundColor ?: foregroundColor,
        disabledColor = override.disabledColor ?: disabledColor,
        iconStart = override.iconStart ?: iconStart,
        iconEnd = override.iconEnd ?: iconEnd,
        typography = override.typography ?: typography,
        fontFamily = override.fontFamily ?: fontFamily,
    )
}
