package nsu.titov.ledcontroller.ui.custom.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PixelCanvasView(
    modifier: Modifier,
    source: PixelCanvasUIS,
) = source.run {
    // для программиста удобнее передать спейс между прямоугольниками, но считать проще через
    // разность систем отсчета
    val frameDifference = Offset(
        x = rectSize.width + rectSpacing.x,
        y = rectSize.height + rectSpacing.y,
    )

    Canvas(modifier = modifier) {
        repeat(canvas.width) { x ->
            repeat(canvas.height) { y ->
                val color = canvas[x, y]
                drawRoundRect(
                    color = Color.LightGray,
                    topLeft = initialOffset + Offset(
                        x = frameDifference.x * x, y = frameDifference.y * y
                    ),
                    style = Stroke(1f),
                    size = rectSize,
                    cornerRadius = cornerRadius,
                )

                drawRoundRect(
                    color = color,
                    topLeft = initialOffset + Offset(
                        x = frameDifference.x * x, y = frameDifference.y * y
                    ),
                    style = Fill,
                    size = rectSize,
                    cornerRadius = cornerRadius,
                )
            }

        }
    }
}

@Composable
@Preview
fun PixelCanvasPreview() {
    PixelCanvasView(
        modifier = Modifier.fillMaxSize(),
        source = PixelCanvasUIS(
            previewCanvas,
            initialOffset = Offset(0f, 0f),
            rectSpacing = Offset(8f, 8f),
            rectSize = Size(32f, 32f),
            cornerRadius = CornerRadius(4f, 4f)
        ),
    )
}

private val previewCanvas = PixelsSource(16, 8, List(18 * 8) { Color.Transparent })

