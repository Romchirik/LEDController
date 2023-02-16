package ru.nsu.ledcontroller.ui.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nsu.ledcontroller.domain.model.canvas.PixelCanvas
import ru.nsu.ledcontroller.ui.custom.canvas.PixelCanvasView
import ru.nsu.ledcontroller.ui.custom.canvas.ViewMode
import ru.nsu.ledcontroller.ui.utils.toDomain

@Composable
@Preview
fun GraphicsEditorScreen() {
    var viewMode by remember { mutableStateOf(ViewMode.LOCKED) }
    var canvas by remember {
        mutableStateOf(
            value = PixelCanvas(16, 8, Color.White.toDomain()), policy = neverEqualPolicy()
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        PixelCanvasView(source = canvas,
            rectSpacing = Offset(20f, 20f),
            rectSize = Size(40f, 40f),
            cornerRadius = CornerRadius(8f),
            viewMode = viewMode,
            onPixelClicked = { x, y ->
                @Suppress("SelfAssignment")
                if ((0 <= x) && (x < 16) && (0 <= y) && (y < 8)) {
                    canvas[x, y] = Color.DarkGray.toDomain()
                    canvas = canvas
                }
            })
        Button(onClick = {
            viewMode = when (viewMode) {
                ViewMode.LOCKED -> {
                    ViewMode.FREE_MOVE
                }
                ViewMode.FREE_MOVE -> {
                    ViewMode.LOCKED
                }
            }
        }) {
            Text(
                when (viewMode) {
                    ViewMode.LOCKED -> "Unlock view"
                    ViewMode.FREE_MOVE -> "Lock view"
                }
            )
        }
    }
}