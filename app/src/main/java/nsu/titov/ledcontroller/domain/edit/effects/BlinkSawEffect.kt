package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.forEachPixel

class BlinkSawEffect(
    override val fireOnEvery: Int = 1,
    val fadeFrom: Float = 1f,
    val fadeTo: Float = 0f,
    val period: Long,
) : StatelessEffect {

    override fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas {
        val result = PixelatedCanvas.withSize(canvas.width, canvas.height)

        val indent = timestamp % period
        if (indent < period / 2) {
            val newAlpha = ((2f / period) * indent).bound(0f, 1f)
            canvas.forEachPixel { x, y, color ->
                result[x, y] = color.copy(alpha = newAlpha)
            }
        } else {
            val newAlpha = (2f - (2f / period) * indent).bound(0f, 1f)
            canvas.forEachPixel { x, y, color ->
                result[x, y] = color.copy(alpha = newAlpha)
            }
        }

        return result
    }

    fun Int.bound(min: Int, max: Int) = if (this < min) {
        min
    } else if (this > 1f) {
        max
    } else {
        this
    }

    private fun Float.bound(min: Float, max: Float) = if (this < min) {
        min
    } else if (this > 1f) {
        max
    } else {
        this
    }
}