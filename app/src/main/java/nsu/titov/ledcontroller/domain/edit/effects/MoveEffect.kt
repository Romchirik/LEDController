package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.forEachPixel

class MoveEffect(
    private val shiftX: Int,
    private val shiftY: Int,
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

    @Suppress("NOTHING_TO_INLINE")
    private inline fun Int.fitCycled(value: Int): Int {
        val x = value % this

        return if (x < 0) {
            x + this
        } else {
            x
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun Int.fitCycled(value: Long): Int {
        val x = value % this

        return if (x < 0) {
            (x + this).toInt()
        } else {
            x.toInt()
        }
    }
}