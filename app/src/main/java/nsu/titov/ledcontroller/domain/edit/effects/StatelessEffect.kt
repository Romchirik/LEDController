package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface StatelessEffect {

    val fireOnEvery: Int
    fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas
}