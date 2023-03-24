package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface Effect {

    fun apply(canvas: PixelatedCanvas, timestamp: Long): PixelatedCanvas
}