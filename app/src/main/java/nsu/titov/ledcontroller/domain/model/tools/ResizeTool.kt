package nsu.titov.ledcontroller.domain.model.tools

import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class ResizeTool(
    override val overlay: PixelatedCanvas
) : Tool {

    private var newSize: Point? = null

    override fun consumePoint(x: Int, y: Int) {
        newSize = Point(x, y)
    }

    override fun applyReady(): Boolean = newSize != null
}