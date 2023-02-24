package nsu.titov.ledcontroller.domain.edit

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface ToolApplier {

    fun applyTool(canvas: PixelatedCanvas): PixelatedCanvas
}