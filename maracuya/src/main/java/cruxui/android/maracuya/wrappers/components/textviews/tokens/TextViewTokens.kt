package cruxui.android.maracuya.wrappers.components.textviews.tokens

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokens

/** Reuse the public component token contract already consumed by Compose. */
typealias TextViewTokens = LabelTokens

/** Existing DS component tokens, selected recipe and Android input patch; no native render values. */
internal data class TextViewResolvedTokens(
    val label: LabelTokens,
    val foregroundDisabled: ColorToken,
    val typography: TypographyToken,
    val override: TextViewTokensOverride,
)
