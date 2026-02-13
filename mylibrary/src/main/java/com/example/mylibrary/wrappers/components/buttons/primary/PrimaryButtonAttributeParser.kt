package com.example.mylibrary.wrappers.components.buttons.primary

import android.content.Context
import android.util.AttributeSet
import com.example.mylibrary.R

internal object PrimaryButtonAttributeParser {

    private const val DEFAULT_TEXT = "Primary Button"

    fun parse(context: Context, attrs: AttributeSet?): PrimaryButtonAttributes {
        if (attrs == null) {
            return PrimaryButtonAttributes(
                text = DEFAULT_TEXT,
                showProgressOnPress = true,
                enabled = true,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PrimaryButtonWrp)
        return try {
            val text = typedArray.getString(R.styleable.PrimaryButtonWrp_text)
                ?: DEFAULT_TEXT

            val showProgressOnPress = typedArray.getBoolean(
                R.styleable.PrimaryButtonWrp_showProgressOnPress,
                true,
            )

            val enabled = typedArray.getBoolean(
                R.styleable.PrimaryButtonWrp_isEnabled,
                true,
            )

            PrimaryButtonAttributes(
                text = text,
                showProgressOnPress = showProgressOnPress,
                enabled = enabled,
            )
        } finally {
            typedArray.recycle()
        }
    }
}