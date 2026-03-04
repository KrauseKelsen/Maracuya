package com.example.mylibrary.ui.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mylibrary.R

private val PilotWellkommenBackgroundSpec = BackgroundLayoutSpec(
    backgroundResId = R.drawable.bg_pilot_wellkommen_shape,
    backgroundHeightFraction = 0.85f,
    backgroundOffsetXFraction = -0.1f,
    backgroundOffsetYFraction = -0.3f,
    backgroundScale = 1.2f
)

/**
 * Background container for Pilot Wellkommen screens.
 */
@Composable
fun BackgroundPilotWellkommen(
    modifier: Modifier = Modifier,
    backgroundSpec: BackgroundLayoutSpec = PilotWellkommenBackgroundSpec,
    content: @Composable () -> Unit
) {
    BackgroundVectorLayout(
        spec = backgroundSpec,
        modifier = modifier,
        content = content
    )
}

/**
 * Public layout API for Wellkommen usage in host apps.
 */
@Composable
fun LayoutPilotWellkommen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BackgroundPilotWellkommen(
        modifier = modifier,
        backgroundSpec = PilotWellkommenBackgroundSpec,
        content = content
    )
}