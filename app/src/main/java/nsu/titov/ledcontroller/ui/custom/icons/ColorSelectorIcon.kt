package nsu.titov.ledcontroller.ui.custom.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nsu.titov.ledcontroller.R

@Composable
fun ColorSelectorIcon(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_hsv_wheel_24),
            contentDescription = "",
            tint = Color.Unspecified,
        )
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_baseline_circle_24),
            contentDescription = "",
            tint = color,
        )
    }
}

@Composable
@Preview
fun ColorSelectorIconPreview() {
    ColorSelectorIcon(color = Color.Red)
}