package cruxui.android.maracuya.wrappers.components.textviews.tokens

import cruxui.android.maracuya.theme.LibraryThemeSnapshot
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokens
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokensOverride
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokensResolver
import cruxui.android.maracuya.ui.components.labels.variant.LabelVariantResolver
import cruxui.android.maracuya.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.maracuya.wrappers.core.tokens.TextTypography
import cruxui.android.maracuya.wrappers.core.tokens.TextTypographyRole

/** Reuses the same Label resolver as Compose; Android overrides only describe additional inputs. */
internal object TextViewTokensResolver {
    fun resolve(
        theme: LibraryThemeSnapshot,
        variant: TextViewVariant,
        tokens: LabelTokens?,
        override: LabelTokensOverride?,
        xml: TextViewTokensOverride,
        runtime: TextViewTokensOverride,
    ): TextViewResolvedTokens {
        val label = LabelTokensResolver.resolve(theme, tokens, override)
        val patch = xml.merge(runtime)
        val typography = when (val source = patch.typography) {
            is TextTypography.Value -> source.token
            is TextTypography.Semantic -> source.select(theme.typography)
            is TextTypography.Role -> when (source.role) {
                TextTypographyRole.Label -> theme.typography.label
                TextTypographyRole.Caption -> theme.typography.caption
                TextTypographyRole.Body -> theme.typography.body1
            }
            null -> LabelVariantResolver.typography(variant, label)
        }
        return TextViewResolvedTokens(label, theme.colors.fgSubtle, typography, patch)
    }
}
