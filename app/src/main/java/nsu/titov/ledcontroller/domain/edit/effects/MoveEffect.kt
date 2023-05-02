package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.fitCycled
import nsu.titov.ledcontroller.domain.model.utils.forEachPixel

class MoveEffect(
    val shiftX: Int,
    val shiftY: Int,
    override val fireOnEvery: Int,
) : StatelessEffect {

    override fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas {
        val newCanvas =
            canvas.copy(pixels = Array(canvas.width * canvas.height) { Color.Unspecified })
        canvas.forEachPixel { x, y, color ->
            val newX = canvas.width.fitCycled(x + shiftX * ticked / fireOnEvery)
            val newY = canvas.height.fitCycled(y + shiftY * ticked / fireOnEvery)

            newCanvas[newX, newY] = color
        }

        return newCanvas
    }
}