package nsu.titov.ledcontroller.domain.edit.history

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface EditHistory {

    var currentCanvas: PixelatedCanvas
    val size: Int
    val capacity: Int
    fun undo(): PixelatedCanvas?
}