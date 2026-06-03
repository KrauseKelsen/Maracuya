package cruxui.android.maracuya.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.layouts.LayoutPilotWellkommen

@Preview(
    name = "LayoutPilotWellkommen - Full Screen",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun LayoutPilotWellkommenPreview() {
    LayoutPilotWellkommen(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, end = 24.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = Color.Blue, shape = CircleShape)
            )
        }
    }
}