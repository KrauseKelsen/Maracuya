package com.example.mylibrary.ui.components.buttons.primary

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

@Composable
internal fun PrimaryButtonContent(
    text: String,
    textStyle: TextStyle,
    contentColor: Color,
    showProgress: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical= 8.dp).size(20.dp),
                strokeWidth = 2.dp,
                color = contentColor
            )
        } else {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                text = text,
                style = textStyle
            )
        }
    }
}