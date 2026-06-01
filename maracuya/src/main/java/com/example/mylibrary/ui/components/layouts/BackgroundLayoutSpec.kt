package com.example.mylibrary.ui.components.layouts

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * Immutable contract to define reusable background behavior for full-screen layouts.
 */
@Immutable
data class BackgroundLayoutSpec(
    @DrawableRes val backgroundResId: Int,
    val backgroundHeightFraction: Float = DEFAULT_BACKGROUND_HEIGHT_FRACTION,
    val backgroundOffsetXFraction: Float = DEFAULT_BACKGROUND_OFFSET_X_FRACTION,
    val backgroundOffsetYFraction: Float = DEFAULT_BACKGROUND_OFFSET_Y_FRACTION,
    val backgroundScale: Float = DEFAULT_BACKGROUND_SCALE
) {
    init {
        require(backgroundHeightFraction in 0f..1f) {
            "backgroundHeightFraction must be in [0, 1]"
        }
        require(backgroundScale > 0f) {
            "backgroundScale must be greater than 0"
        }
    }

    companion object {
        const val DEFAULT_BACKGROUND_HEIGHT_FRACTION: Float = 0.72f
        const val DEFAULT_BACKGROUND_OFFSET_X_FRACTION: Float = 0f
        const val DEFAULT_BACKGROUND_OFFSET_Y_FRACTION: Float = 0f
        const val DEFAULT_BACKGROUND_SCALE: Float = 1f
    }
}