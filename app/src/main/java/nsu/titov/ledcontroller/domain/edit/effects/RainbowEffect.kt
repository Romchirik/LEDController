package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class RainbowEffect(
    val period: Long,
    override val fireOnEvery: Int = 0,
) : StatelessEffect {

    override fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas {

        val currHueShift = (timestamp % period) * 360f / period
        val newPixels = canvas.pixels.map {
            if (it != Color.Unspecified) {
                val r = (255 * it.red).toInt() % 256
                val g = (255 * it.green).toInt() % 256
                val b = (255 * it.blue).toInt() % 256

                val hsv = floatArrayOf(0f, 0f, 0f)
                android.graphics.Color.RGBToHSV(r, g, b, hsv)

                Color.hsv(
                    hue = (hsv[0] + currHueShift) % 360f,
                    saturation = hsv[1],
                    value = hsv[2],
                )
            } else {
                it
            }
        }

        return canvas.copy(pixels = newPixels.toTypedArray())
    }
}