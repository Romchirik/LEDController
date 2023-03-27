package nsu.titov.ledcontroller.domain.edit.effects

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.utils.hsvValue
import nsu.titov.ledcontroller.domain.model.utils.hue
import nsu.titov.ledcontroller.domain.model.utils.saturation

class RainbowEffect(
    period: Long = 300L,
) : Effect {

    private val period: Float = period.toFloat()

    override fun apply(canvas: PixelatedCanvas, timestamp: Long): PixelatedCanvas {
//        val newPixels: Array<Color> = canvas.pixels.map {
//            val hue = it.hue
//            val value = it.hsvValue
//            val saturation = it.saturation
//            val alpha = it.alpha
//            android.graphics.Color.RGBToHSV(it.)
//
//        }.toTypedArray()

        return canvas.copy()
    }
}