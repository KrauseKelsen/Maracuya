package cruxui.android.maracuya.atoms

import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.tokens.fonts.FontSizeTokens
import cruxui.android.maracuya.tokens.fonts.FontStyleTokens
import cruxui.android.maracuya.tokens.fonts.FontWeightTokens
import cruxui.android.maracuya.tokens.fonts.LetterSpacingTokens
import cruxui.android.maracuya.tokens.fonts.LineHeightTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryTypographyTokens` define la tipografía semántica corporativa del
 * Design System. Cada estilo tipográfico se genera dinámicamente a partir del
 * tema activo, permitiendo que la fuente (FontFamily) provenga del
 * `CompositionLocal` inyectado por el tema.
 *
 * Proporciona estilos consistentes y tipados, construidos a partir de:
 *
 *  - `fonts/FontFamilies` (fuentes corporativas)
 *  - `fonts/FontSizeTokens` (tamaños base)
 *  - `fonts/FontWeightTokens` (pesos semánticos)
 *  - `fonts/LetterSpacingTokens` (tracking)
 *  - `fonts/LineHeightTokens` (relaciones de altura de línea)
 *
 * Estos tokens representan la **semántica tipográfica corporativa** que luego es
 * expuesta de forma segura mediante:
 *
 *  - `semantics/CorporateTypography` (API pública para el host)
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 * El acceso debe darse únicamente a través de:
 *    - CorporateTypography (semántica final)
 *    - LocalLibraryTypography (CompositionLocal)
 *
 * Ejemplo simplificado del consumo:
 *
 *     val typography = CorporateTypography()
 *     Text(
 *         text = "Título",
 *         style = typography.headline24
 *     )
 * ```
 *
 * Flujo completo de construcción:
 *
 *  1) Tokens tipográficos base (FontSizeTokens, FontWeightTokens, LineHeightTokens)
 *  2) → Semántica corporativa (`LibraryTypographyTokens`)
 *  3) → Exposición controlada al host (`CorporateTypography`)
 *
 * Este objeto incluye estilos para:
 *
 *  - Headings (48sp → 16sp)
 *  - Subtítulos
 *  - Cuerpo de texto
 *  - Etiquetas y legales
 *  - Botones (peso headline)
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 *
 * @param base Función constructora común para todos los estilos
 * @param headline Estilos de encabezado corporativos
 * @param subtitle Estilos de subtítulos
 * @param body Estilos de texto base y párrafo
 * @param caption Estilo de etiquetas
 * @param legal Estilo para textos legales
 * @param buttons Estilo para botones con peso enfatizado
 *
 * @see cruxui.android.lib.atoms.LibraryTypographyTokens
 */


//TODO La semantica no esta bien definida con figma hay que definirla bien
object LibraryTypographyTokens {

    // Headings
    val headline48 = TypographyToken(
        fontSize = FontSizeTokens.s48,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s48, LineHeightTokens.TightRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    val headline36 = TypographyToken(
        fontSize = FontSizeTokens.s36,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s36, LineHeightTokens.TightRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    val headline32 = TypographyToken(
        fontSize = FontSizeTokens.s32,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s32, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    val headline28 = TypographyToken(
        fontSize = FontSizeTokens.s28,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s28, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    val headline24 = TypographyToken(
        fontSize = FontSizeTokens.s24,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s24, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    //new
    val headline24Medium = TypographyToken(
        fontSize = FontSizeTokens.s24,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s24, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    val headline21 = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    //new
    val headline21Bold = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    //new
    val headline21Medium = TypographyToken(
        fontSize = FontSizeTokens.s21,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s21, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    val headline18 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    val headline16 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.ExtraBold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    // Subtítulos
    val subtitle1 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )
    val subtitle2 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Normal
    )
    val subtitle3 = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        //weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Bold
        //style = FontStyleTokens.Normal
    )

    // Cuerpo
    val body1 = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    val body1Italic = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Italic
    )

    val body2 = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    val body2Italic = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Italic
    )

    val introduction = TypographyToken(
        fontSize = FontSizeTokens.s20,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s20, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    // Etiquetas y legales
    val caption = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    val captionItalic = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Italic
    )

    val legal = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        style = FontStyleTokens.Normal
    )

    val legalItalic = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Regular,
        italic = true,
        style = FontStyleTokens.Italic
    )

    val legalBold = TypographyToken(
        fontSize = FontSizeTokens.s12,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s12, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Bold,
        style = FontStyleTokens.Bold
    )

    val buttons = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Headline,
        style = FontStyleTokens.Normal
    )

    //Provisional para LabelMrcy - Parecido al subtitle3
    val label = TypographyToken(
        fontSize = FontSizeTokens.s16,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s16, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Headline,
        style = FontStyleTokens.Normal
    )

    //Provisional para InputFieldBasicMrcy - Parecido al body1
    val placeholder = TypographyToken(
        fontSize = FontSizeTokens.s18,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s18, LineHeightTokens.ShortRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

    //Provisional para InputFieldBasicMrcy - Parecido al body1
    val bottomText = TypographyToken(
        fontSize = FontSizeTokens.s14,
        lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s14, LineHeightTokens.NormalRatio),
        letterSpacing = LetterSpacingTokens.None,
        weight = FontWeightTokens.Medium,
        style = FontStyleTokens.Normal
    )

}