package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.forEachPixel

class MoveEffect(
    private val shiftX: Int,
    private val shiftY: Int,
) : Effect {

    private var timesApplied: Int = 0

    override fun apply(canvas: PixelatedCanvas, timestamp: Long): PixelatedCanvas {
        val newCanvas =
            canvas.copy(pixels = Array(canvas.width * canvas.height) { Color.Unspecified })
        canvas.forEachPixel { x, y, color ->
            val newX = canvas.width.fitCycled(x + shiftX * timesApplied)
            val newY = canvas.height.fitCycled(y + shiftY * timesApplied)

            newCanvas[newX, newY] = color
        }

        timesApplied++
        return newCanvas
    }

    private fun Int.fitCycled(value: Int): Int {
        val x = value % this

        return if (x < 0) {
            x + this
        } else {
            x
        }
    }
}