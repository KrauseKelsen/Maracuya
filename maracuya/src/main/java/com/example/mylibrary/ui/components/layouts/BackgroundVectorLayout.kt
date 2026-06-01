package com.example.mylibrary.ui.components.layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * Generic background container for screens that need a fixed vector illustration on top.
 * Content is rendered over the background to allow placing texts/icons above it.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun BackgroundVectorLayout(
    spec: BackgroundLayoutSpec,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val horizontalOffset = maxWidth * spec.backgroundOffsetXFraction
        val verticalOffset = maxHeight * spec.backgroundOffsetYFraction

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = spec.backgroundResId),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(
                        x = horizontalOffset,
                        y = verticalOffset
                    )
                    .fillMaxHeight(spec.backgroundHeightFraction)
                    .graphicsLayer {
                        scaleX = spec.backgroundScale
                        scaleY = spec.backgroundScale
                        transformOrigin = TransformOrigin(0.5f, 0f)
                    },
                contentScale = ContentScale.FillHeight
            )

            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}