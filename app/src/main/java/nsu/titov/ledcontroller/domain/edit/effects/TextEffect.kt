package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.forEachPixel
import nsu.titov.ledcontroller.domain.model.utils.subCanvas

class TextEffect(
    val text: String,
    override val fireOnEvery: Int = 3,
    val offsetY: Int = 1,
    val font: PixelatedFont = PracticalFont(),
) : StatelessEffect {

    private val pixelText =
        text.map { font.getSymbol(it) }.rightMerge(separator = PixelatedCanvas.withSize(1, 7))

    override fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas {

        val result = canvas.copy()
        val symbolOffsetX = ticked / fireOnEvery
        val cutout = pixelText.subCanvas(symbolOffsetX, 0, canvas.width + symbolOffsetX)

        return result.overlayText(cutout, 0, offsetY)
    }
}

private fun List<PixelatedCanvas>.rightMerge(separator: PixelatedCanvas = PixelatedCanvas.Zero): PixelatedCanvas {
    assert(this.map { it.height }.toHashSet().size == 1)
    assert(separator == PixelatedCanvas.Zero || separator.height == this.first().height)

    if (this.isEmpty()) return PixelatedCanvas.Zero

    val width = sumOf { it.width + separator.width } - separator.width

    val result = PixelatedCanvas.withSize(height = first().height, width = width)

    var offsetX = 0
    for ((index, item) in this.withIndex()) {
        item.forEachPixel { x, y, color ->
            result[x + offsetX, y] = color
        }
        offsetX += item.width

        if (index != this.size - 1 && separator != PixelatedCanvas.Zero) {
            separator.forEachPixel { x, y, color ->
                result[x + offsetX, y] = color
            }
            offsetX += separator.width
        }
    }

    return result
}

private fun PixelatedCanvas.overlayText(
    cutout: PixelatedCanvas,
    offsetX: Int,
    offsetY: Int,
): PixelatedCanvas {
    cutout.forEachPixel { x, y, color ->
        if (color != Color.Unspecified) {
            this[x + offsetX, y + offsetY] = color
        }
    }
    return this
}