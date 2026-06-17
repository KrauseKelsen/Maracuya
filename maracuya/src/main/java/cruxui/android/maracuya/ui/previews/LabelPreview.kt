package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper
import cruxui.android.maracuya.wrappers.components.labels.LabelTokensOverrideRegistry

@Preview(name = "Label – Default", showBackground = true)
@Composable
fun Label_Default() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = false, error = false)
    }
}

@Preview(name = "Label – With Icon", showBackground = true)
@Composable
fun Label_With_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = true, error = false)
    }
}

@Preview(name = "Label – With Optional Icon", showBackground = true)
@Composable
fun Label_With_Optional_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optionalText = true, showIcon = true, error = false)
    }
}

@Preview(name = "Label – Error", showBackground = true)
@Composable
fun Label_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = true, error = true)
    }
}

@Preview(name = "Label – With Optional Icon Error", showBackground = true)
@Composable
fun Label_With_Optional_Icon_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optionalText = true, showIcon = true, error = true)
    }
}

@Preview(name = "Label – With Large Text", showBackground = true)
@Composable
fun Label_With_Large_Text() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "This a label that could extend to more than one line")
    }
}

private const val XML_PREVIEW_TOKENS_OVERRIDE = "mytokensoverride"

@Preview(name = "LabelWrp XML - Estados y tokens override", showBackground = true)
@Composable
fun LabelWrp_XmlStatesAndTokensOverridePreview() {
    PreviewWrapper(style = Mode.current) {
        LabelTokensOverrideRegistry.register(XML_PREVIEW_TOKENS_OVERRIDE) {
            val colors = LocalLibraryColorTokens.current

            LabelTokensOverride(
                foregroundDefault = colors.fgError,
                foregroundError = colors.fgDefault,
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                LabelTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE)
            }
        }

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.label_wrp_preview, null)
            },
        )
    }
}
