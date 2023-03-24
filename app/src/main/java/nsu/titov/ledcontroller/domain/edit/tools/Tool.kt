package nsu.titov.ledcontroller.domain.edit.tools

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface Tool : ColorChangeListener {

    var overlay: PixelatedCanvas
    val type: ToolType
    fun consumePoint(x: Int, y: Int)
    fun isOverlayReady(): Boolean
}