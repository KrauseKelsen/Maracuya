package cruxui.android.lib.atoms

import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.tokens.fonts.FontSizeTokens
import cruxui.android.lib.tokens.fonts.FontStyleTokens
import cruxui.android.lib.tokens.fonts.FontWeightTokens
import cruxui.android.lib.tokens.fonts.LetterSpacingTokens
import cruxui.android.lib.tokens.fonts.LineHeightTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryTypographyTokens es la fuente única de las definiciones tipográficas del Design System.
 * Cada propiedad construye un TypographyToken puro con tamaño, altura, tracking, peso y cursiva.
 * La familia de fuentes pertenece al tema; este objeto no lee CompositionLocals ni Context.
 *
 * Justificación:
 * Define las métricas una sola vez. generateCatalogApi descubre sus propiedades al compilar y genera
 * LibraryTypography y TextTypographyRole. El exportador completo deriva después TextAppearance,
 * dimensiones, atributos y LibraryXmlReferences a partir de los valores efectivos de cada tema.
 *
 * Se comunica con:
 * FontSizeTokens, FontWeightTokens, LineHeightTokens, LetterSpacingTokens y FontStyleTokens aportan
 * las magnitudes base. LibraryTypography conserva estos valores por defecto; LibraryThemeSnapshot
 * los distribuye a Compose y a los componentes XML, admitiendo sustituciones explícitas por marca.
 *
 * Uso:
 * Los mantenedores agregan aquí un val público de tipo TypographyToken. Es la única declaración necesaria:
 * ```kotlin
 * // Dentro de este objeto; ejemplo de ampliación, no es un estilo existente:
 * val campaignTitle = TypographyToken(fontSize = 30f, lineHeight = 36f, weight = 700)
 * ```
 * Tras compilar aparecen automáticamente typography.campaignTitle, TextTypographyRole.CampaignTitle,
 * ?attr/libraryTypographyCampaignTitle y @style/TextAppearance.Maracuya.CampaignTitle.
 * No editar los archivos de build/generated ni mantener un enum o un when adicional.
 * Los nombres son API pública: renombrar/eliminar una propiedad cambia sus accesos Kotlin y XML.
 *
 * El host normalmente consume el tema activo para respetar personalizaciones de marca:
 * ```kotlin
 * val library = LibraryTheme.current // dentro de Compose
 * TextView(text = "Título", tokensOverride = TextViewTokensOverride(
 *     typography = library.typography.headline24Medium,
 * ))
 * ```
 * En XML, con ThemeOverlay.Maracuya en el ámbito Android:
 * ```xml
 * android:textAppearance="?attr/libraryTypographyHeadline24Medium"
 * ```
 * También se puede seleccionar directamente @style/TextAppearance.Maracuya.Headline24Medium para fijar marca.
 * Los recursos XML se actualizan al reconstruir el AAR; no se generan IDs nuevos durante la ejecución.
 * Para temas modificados en ejecución, usar una receta del registro o un rol de TextTypographyRole.
 * Acceder directamente a este objeto entrega el catálogo base y omite las sustituciones del tema activo.
 *
 * Objetivos:
 * - Propagar cambios de una definición a todos sus consumidores sin duplicar valores.
 * - Incorporar estilos nuevos con una sola propiedad.
 * - Exponer toda la tipografía desde Compose, Kotlin y XML.
 *
 * Flujo: tokens base → LibraryTypographyTokens → API generada → tema → Compose y recursos/adaptadores XML.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.collections.LibraryTypography
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object LibraryTypographyTokens {

    // Encabezados
    /** Define headline48; sus valores alimentan los temas y todas las APIs generadas. */
    val headline48 = TypographyToken(
        fontSize = FontSizeTokens.s48,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s48, LineHeightTokens.TightRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    /** Define headline36; sus valores alimentan los temas y todas las APIs generadas. */
    val headline36 = TypographyToken(
        fontSize = FontSizeTokens.s36,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s36, LineHeightTokens.TightRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline32; sus valores alimentan los temas y todas las APIs generadas. */
    val headline32 = TypographyToken(
        fontSize = FontSizeTokens.s32,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s32, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline28; sus valores alimentan los temas y todas las APIs generadas. */
    val headline28 = TypographyToken(
        fontSize = FontSizeTokens.s28,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s28, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline24; sus valores alimentan los temas y todas las APIs generadas. */
    val headline24 = TypographyToken(
        fontSize = FontSizeTokens.s24,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s24, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline24Medium; sus valores alimentan los temas y todas las APIs generadas. */
    val headline24Medium = TypographyToken(
        fontSize = FontSizeTokens.s24,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s24, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    /** Define headline21; sus valores alimentan los temas y todas las APIs generadas. */
    val headline21 = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline21Bold; sus valores alimentan los temas y todas las APIs generadas. */
    val headline21Bold = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    /** Define headline21Medium; sus valores alimentan los temas y todas las APIs generadas. */
    val headline21Medium = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    /** Define headline18; sus valores alimentan los temas y todas las APIs generadas. */
    val headline18 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    /** Define headline16; sus valores alimentan los temas y todas las APIs generadas. */
    val headline16 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        style = FontStyleTokens.Bold
    )

    // Subtítulos
    /** Define subtitle1; sus valores alimentan los temas y todas las APIs generadas. */
    val subtitle1 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )
    /** Define subtitle2; sus valores alimentan los temas y todas las APIs generadas. */
    val subtitle2 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Normal
    )
    /** Define subtitle3; sus valores alimentan los temas y todas las APIs generadas. */
    val subtitle3 = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    // Cuerpo
    /** Define body1; sus valores alimentan los temas y todas las APIs generadas. */
    val body1 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    /** Define body1Italic; sus valores alimentan los temas y todas las APIs generadas. */
    val body1Italic = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        italic = true,
        style = FontStyleTokens.Italic
    )

    /** Define body2; sus valores alimentan los temas y todas las APIs generadas. */
    val body2 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    /** Define body2Italic; sus valores alimentan los temas y todas las APIs generadas. */
    val body2Italic = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        italic = true,
        style = FontStyleTokens.Italic
    )

    /** Define introduction; sus valores alimentan los temas y todas las APIs generadas. */
    val introduction = TypographyToken(
        fontSize = FontSizeTokens.s20,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s20, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    // Etiquetas y legales
    /** Define caption; sus valores alimentan los temas y todas las APIs generadas. */
    val caption = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    /** Define captionItalic; sus valores alimentan los temas y todas las APIs generadas. */
    val captionItalic = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        italic = true,
        style = FontStyleTokens.Italic
    )

    /** Define legal; sus valores alimentan los temas y todas las APIs generadas. */
    val legal = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    /** Define legalItalic; sus valores alimentan los temas y todas las APIs generadas. */
    val legalItalic = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        italic = true,
        style = FontStyleTokens.Italic
    )

    /** Define legalBold; sus valores alimentan los temas y todas las APIs generadas. */
    val legalBold = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    /** Define buttons; sus valores alimentan los temas y todas las APIs generadas. */
    val buttons = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Headline,
        style = FontStyleTokens.Normal
    )

    //Provisional para LabelMrcy - Parecido al subtitle3
    /** Define label; sus valores alimentan los temas y todas las APIs generadas. */
    val label = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    //Provisional para InputFieldBasicMrcy - Parecido al body1
    /** Define placeholder; sus valores alimentan los temas y todas las APIs generadas. */
    val placeholder = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    //Provisional para InputFieldBasicMrcy - Parecido al body1
    /** Define bottomText; sus valores alimentan los temas y todas las APIs generadas. */
    val bottomText = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

}
