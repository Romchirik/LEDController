package nsu.titov.ledcontroller.ui.custom.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nsu.titov.ledcontroller.R

@Composable
fun SelectableIcon(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit = {},
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Surface(
            shape = CircleShape,
            color = if (selected) Color.Gray else Color.Transparent
        ) {
            content()
        }
    }
}

@Composable
@Preview
fun SelectableIconPreview() {
    SelectableIcon(
        modifier = Modifier.size(32.dp),
        selected = true

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_check_24),
            contentDescription = "",
        )
    }
}