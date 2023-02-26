package nsu.titov.ledcontroller.domain.model.tools

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface Tool : ColorChangeListener {
    val overlay: PixelatedCanvas

    fun consumePoint(x: Int, y: Int)

    fun applyReady(): Boolean
}