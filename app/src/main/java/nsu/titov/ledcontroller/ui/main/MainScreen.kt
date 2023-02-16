package ru.nsu.ledcontroller.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.nsu.ledcontroller.domain.model.canvas.PixelCanvas
import ru.nsu.ledcontroller.ui.custom.canvas.PixelCanvasView
import ru.nsu.ledcontroller.ui.utils.toDomain

@Composable
@Preview
fun MainScreen() {
     PixelCanvasView(
        source = PixelCanvas(16, 16, Color.Black.toDomain()),
        rectSpacing = Offset(20f, 20f),
        rectSize = Size(40f, 40f),
        cornerRadius = CornerRadius(8f)
    )
}