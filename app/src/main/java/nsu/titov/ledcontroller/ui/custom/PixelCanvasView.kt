package nsu.titov.ledcontroller.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import nsu.titov.ledcontroller.ui.Spacing

@Composable
fun PixelCanvas(
    modifier: Modifier = Modifier,
    source: PixelCanvasUIState,
) = source.run {
    // для программиста удобнее передать спейс между прямоугольниками, но считать проще через
    // разность систем отсчета
    val frameDifference = Offset(
        x = rectSize.width + rectSpacing.x,
        y = rectSize.height + rectSpacing.y,
    )

    val minSizeDp = source.getMinSizeDp(androidx.compose.ui.platform.LocalDensity.current)

    Canvas(
        modifier = modifier.defaultMinSize(
            minWidth = minSizeDp.x,
            minHeight = minSizeDp.y,
        )
    ) {
        repeat(canvas.width) { x ->
            repeat(canvas.height) { y ->

                when (val color = canvas[x, y]) {
                    Color.Transparent ->
                        drawRoundRect(
                            color = Color.LightGray,
                            topLeft = initialOffset + Offset(
                                x = frameDifference.x * x,
                                y = frameDifference.y * y
                            ),
                            style = Stroke(Spacing.Small.value),
                            size = rectSize,
                            cornerRadius = cornerRadius,
                        )
                    else -> {
                        drawRoundRect(
                            color = color,
                            topLeft = initialOffset + Offset(
                                x = frameDifference.x * x,
                                y = frameDifference.y * y
                            ),
                            style = Fill,
                            size = rectSize,
                            cornerRadius = cornerRadius,
                        )
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun PixelCanvasPreview() {
    PixelCanvas(
        source = PixelCanvasUIState(
            previewCanvas,
            initialOffset = Offset(0f, 0f),
            rectSpacing = Offset(8f, 8f),
            rectSize = Size(32f, 32f),
            cornerRadius = CornerRadius(4f, 4f)
        ),
    )
}

private val previewCanvas = PixelsSource(16, 8, List(18 * 8) { Color.Transparent })

