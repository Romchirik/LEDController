package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class RainbowEffect(
    period: Long = 300L,
) : Effect {

    private val period: Float = period.toFloat()

    override fun apply(canvas: PixelatedCanvas, timestamp: Long): PixelatedCanvas {
        val newPixels = canvas.pixels.map {
            if(it != Color.Unspecified) {
                val r = 255 * it.red.toInt().toByte()
                val g = 255 * it.green.toInt().toByte()
                val b = 255 * it.blue.toInt().toByte()

                val hsv = floatArrayOf(0f, 0f, 0f)
                android.graphics.Color.RGBToHSV(r, g, b, hsv)

                val shift = (timestamp % period) / period
                Color.hsv(hsv[0], (hsv[1] + shift) % 1f, hsv[2])
            } else {
                it
            }
        }

        return canvas.copy(pixels = newPixels.toTypedArray())
    }
}